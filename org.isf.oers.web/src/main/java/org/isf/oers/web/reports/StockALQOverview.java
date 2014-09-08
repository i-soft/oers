package org.isf.oers.web.reports;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.isf.commons.DateUtil;
import org.isf.commons.bean.Type;
import org.isf.commons.data.Column;
import org.isf.commons.data.Header;
import org.isf.commons.data.Tuple;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.row.Row;

import com.el.oers.report.StockALQOverviewBuilderBean;

@Named("stockALQOverview")
@RequestScoped
public class StockALQOverview implements Serializable {

	private static final int VIEWED_WEEKS = 3;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6272862058385630962L;
	
	private Header header;
	private ColumnGroup columnGroup;
	
//	private List<Tuple> list;
	
	@Inject
	private StockALQOverviewBuilderBean builder;
	
	@PostConstruct
	public void initial() throws Exception {
		header = new Header();
		
		Column c = new Column("Type", Type.STRING);
		c.setRowSpan(2);
		header.addColumn(c);
		
		Date[] dates = getDates();
		for (int i=0;i<dates.length;i++) {
			Date d = dates[i];
			if (i%7 == 0) {
				Calendar cal = DateUtil.getISOCalendar();
				cal.setTime(d);
				c = new Column("KW "+cal.get(Calendar.WEEK_OF_YEAR), Type.DOUBLE);
				c.setColumnSpan(7);
				header.addColumn(c);
			}
			Column sub = new Column(DateUtil.toString("dd.MM.", d), Type.DOUBLE);
			c.addColumn(sub);
		}
		
		setColumnGroup(createColumnGroup());
//		setData(builder.build(dates));
	}
	
	public Header getHeader() { return header; }
	
	public ColumnGroup getColumnGroup() { return columnGroup; }
	public void setColumnGroup(ColumnGroup columnGroup) { this.columnGroup = columnGroup; }
	
	protected ColumnGroup createColumnGroup() {
		ColumnGroup grp = new ColumnGroup();
		grp.setType("header");
		
		int cnt = getHeader().levelCount();
		for (int i=1;i<=cnt;i++) {
			Row row = new Row();
			
			for (Column c : getHeader().getColumnsFromLevel(i)) {
				org.primefaces.component.column.Column col = new org.primefaces.component.column.Column();
				col.setHeaderText(c.getDisplayName());
				if (!c.hasColumns())  {
					col.setRowspan(cnt-i+1);
					col.setStyle("min-width: 200px;");
				} else col.setColspan(c.columnSize());
				
				row.getChildren().add(col);
			}
			
			grp.getChildren().add(row);
		}
		
		return grp;
	}
	
	public Date[] getDates() { return DateUtil.getLastWeeks(VIEWED_WEEKS-1); }
	
//	public List<Tuple> getData() { return list; }
//	public void setData(List<Tuple> data) { this.list = data; }
	
	public List<Tuple> getData(String country, String devision) throws Exception {
		return builder.build(country, devision, getDates());
	}
	
}
