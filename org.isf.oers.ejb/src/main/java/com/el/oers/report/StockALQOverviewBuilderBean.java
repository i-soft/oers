package com.el.oers.report;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.isf.commons.DateUtil;
import org.isf.commons.Util;
import org.isf.commons.bean.Type;
import org.isf.commons.data.Column;
import org.isf.commons.data.Container;
import org.isf.commons.data.Filter;
import org.isf.commons.data.Tuple;
import org.isf.oers.system.service.OERSConcurrentTinyCache;

@Stateless 
public class StockALQOverviewBuilderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586411825900141815L;
	
	public static final String[] HIERARCHY = {"ALQ", "Flotte", "Portfolio", "Platz"};
	public static final String FLAT_HIERARCHY = Util.flatLineQuote(HIERARCHY, ",", "'");
	public static final String[] SUB_HIERARCHY = {"GESAMT", "TRUCK", "TRAILER"};
	
	public static final String BASE_SELECT = 
				"select c.type, b.vgroup, a.date_of_event, sum(a.counter) as counter " +
				"from DWH_DATAMART.sto_f_stock a " +
				"left join DWH_DATAMART.sto_d_vehicle b on a.vehicle_dwh_id = b.dwh_id " +
				"left join dwh_datamart.STD_D_HIERARCHY c on a.hierarchy_dwh_id = c.dwh_id " +
				"where c.country like ? and c.devision like ? and c.type in ("+FLAT_HIERARCHY+") " +
				"and a.date_of_event between ? and ? " +
				"group by c.country, c.devision, c.type, b.vgroup, a.date_of_event  " +
				"order by c.country, c.devision, a.date_of_event,c.type";

	@Resource(mappedName="java:/AtlasDSXA")
	private DataSource atlasDS;
	
	@Inject
	private OERSConcurrentTinyCache cache;
	
	protected Connection getConnection() throws SQLException { return atlasDS.getConnection(); }
	
	protected String buildPivotSelect(Date[] dates) {
		String select = "select * from ("+BASE_SELECT+") \n";
		select += "PIVOT (sum(counter) as cnt for (date_of_event) in (";
		for (int i=0;i<dates.length;i++) {
			select += (i==0 ? "" : ",")+"'"+DateUtil.toString("dd.MM.YYYY", dates[i])+"' as col_"+(i+1);
		}
		select += "))";
		return select;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tuple> build(String country, String devision, Date ... dates) throws Exception {
		if (dates.length<1) return new ArrayList<Tuple>();
		String key = country+"."+devision+"."+DateUtil.toString("yyyy-MM-dd",dates[0])+"-"+DateUtil.toString("yyyy-MM-dd",dates[dates.length-1]);
		if (cache.hasCachedData(key)) return (List<Tuple>)cache.getCachedData(key);
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = null;
			ResultSet res = null; 
			try {
				pstmt = con.prepareStatement(buildPivotSelect(dates));
				pstmt.setString(1, country);
				pstmt.setString(2, devision);
				pstmt.setDate(3, DateUtil.toSqlDate(dates[0]));
				pstmt.setDate(4, DateUtil.toSqlDate(dates[dates.length-1]));
				res = pstmt.executeQuery();
				ResultSetMetaData rsmd = res.getMetaData();
				Container buffer = new Container();
				for (int i=1;i<=rsmd.getColumnCount();i++) {
					buffer.addColumn(new Column(rsmd.getColumnName(i), i<=2 ? Type.STRING : Type.DOUBLE ));
				}
				while (res.next()) {
					Tuple t = new Tuple();
					t.addData(res.getString(1));
					t.addData(res.getString(2));
					for (int i=0;i<dates.length;i++) 
						t.addData(new Double(res.getInt(i+3)));
					buffer.addTuple(t);
				}				
				
				/* build GESAMT */
				for (int i=0;i<HIERARCHY.length;i++) {
					Tuple t = new Tuple();
					t.addData(HIERARCHY[i]);
					t.addData(SUB_HIERARCHY[0]);
					List<Tuple> l = buffer.list(new Filter(new Column("TYPE",Type.STRING), HIERARCHY[i]));
					for (Tuple st : l) 
						for (int j=2;j<st.count();j++) {
							if (st.getData(j) == null || !(st.getData(j) instanceof Double)) continue;
							Double dst = (Double)st.getData(j);
							Double cst = j>=t.count() || t.getData(j) == null ? new Double(0.0) : (Double)t.getData(j);
							t.setData(j, new Double(dst.doubleValue()+cst.doubleValue()));
						}
					if (l.size() > 0) buffer.addTuple(t);
				}
				
				/* build ALQ */
				for (String subh : SUB_HIERARCHY) {
					List<Tuple> flotte = buffer.list(new Filter[] {new Filter(new Column("TYPE",Type.STRING), HIERARCHY[1]),
						new Filter(new Column("VGROUP",Type.STRING), subh)});
					List<Tuple> portfolio = buffer.list(new Filter[] {new Filter(new Column("TYPE",Type.STRING), HIERARCHY[2]),
							new Filter(new Column("VGROUP",Type.STRING), subh)});
					
					for (int i=0;i<portfolio.size();i++) {
						Tuple tp = portfolio.get(i);
						Tuple tf = flotte.get(i);
						
						Tuple t = new Tuple();
						t.addData(HIERARCHY[0]);
						t.addData(subh);
						
						for (int j=2;j<tp.count();j++) {
							if (tp.getData(j) == null || !(tp.getData(j) instanceof Double) 
									|| tf.getData(j) == null || !(tf.getData(j) instanceof Double)) continue;
							Double dp = (Double)tp.getData(j);
							Double df = (Double)tf.getData(j);
							t.setData(j, new Double(100.0*dp.doubleValue()/(df.doubleValue() == 0.0 ? 1.0 : df.doubleValue())));
						}
						
						buffer.addTuple(t);
					}
				}
				
				/* build OUTPUT */
				Container output = new Container();
				output.addColumn(new Column("CAPTION", Type.STRING));
				for (int i=2;i<buffer.columnCount();i++)
					output.addColumn(buffer.getColumn(i));
				
				for (String hier : HIERARCHY) 
					for (int i=0;i<SUB_HIERARCHY.length;i++) {
						List<Tuple> l = buffer.list(new Filter(new Column("TYPE",Type.STRING), hier), new Filter(new Column("VGROUP",Type.STRING), SUB_HIERARCHY[i]));
						if (l.size() == 0) continue;
						Tuple st = l.get(0);
						Tuple t = new Tuple();
						if (i == 0) t.addData(hier);
						else if (i == 1) t.addData("Truck");
						else t.addData("Trailer");
						
						for (int j=2;j<st.count();j++)
							t.addData(st.getData(j));
						output.addTuple(t);
					}
				List<Tuple> ret = output.list();
				cache.putCachedData(key, ret);
				return ret;
			} finally {
				try { res.close(); } catch(Exception e) { }
				try { pstmt.close(); } catch(Exception e) { }
			}
		} finally {
			try { con.close(); } catch(Exception e) { }
		}
	}
	
}
