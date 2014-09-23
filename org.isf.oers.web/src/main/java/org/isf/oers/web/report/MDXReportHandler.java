package org.isf.oers.web.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.isf.commons.data.Column;
import org.isf.commons.data.Container;
import org.isf.commons.data.Tuple;
import org.isf.mdx.MDXContainerBuilder;
import org.isf.mdx.data.MDXColumn;

@Named("mdxReportHandler")
//@SessionScoped
@RequestScoped
public class MDXReportHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8865810096134250364L;

	@Inject
	private MDXContainerBuilder builder;
	
//	private HashMap<String, String> mdxQueryCache = new HashMap<String, String>();
	
	private Container container;

	@PostConstruct
	public void initial() throws Exception {
		String mdx = "SELECT "
				+ "NON EMPTY {[Time].[2013.09], [Time].[2013.10], [Time].[2013.11], [Time].[2013.12], [Time].[2014.01], [Time].[2014.02], [Time].[2014.03], [Time].[2014.04], [Time].[2014.05], [Time].[2014.06], [Time].[2014.07], [Time].[2014.08], [Time].[2014.09]} ON COLUMNS, "
//				+ "NON EMPTY [Time].Children ON COLUMNS, "
//				+ "NON EMPTY {([Devision].[LZM].[Abgänge], [Vehicle].[All Vehicles]), ([Devision].[LZM].[Angebote], [Vehicle].[All Vehicles]), ([Devision].[LZM].[Neuverträge], [Vehicle].[All Vehicles]), ([Devision].[LZM].[Portfolio-EOP], [Vehicle].[All Vehicles]), ([Devision].[LZM].[Prolongiert], [Vehicle].[All Vehicles]), ([Devision].[LZM].[Storniert], [Vehicle].[All Vehicles])} ON ROWS "
				+ "NON EMPTY {[Devision].[LZM].[Abgänge], [Devision].[LZM].[Angebote], [Devision].[LZM].[Neuverträge], [Devision].[LZM].[Portfolio-EOP], [Devision].[LZM].[Prolongiert], [Devision].[LZM].[Storniert]} ON ROWS "
//				+ "NON EMPTY Union(CrossJoin({[Devision].[LZM].[Abgänge]}, {[Vehicle].[All Vehicles]}), CrossJoin({[Devision].[LZM].[Abgänge]}, [Vehicle].[All Vehicles].Children)) ON ROWS "
				+ "FROM [Sales] "
				+ "WHERE [Country].[DE]";
		setContainer(builder.build(mdx));
	}
	
	public Container getContainer() { return container; }
	public void setContainer(Container container) { this.container=container; }
	
	public List<Column> getColumns() { return getContainer().columns(); }
	public Column column(int index) { return getContainer().getColumn(index); }	
	
	public List<Tuple> getData() { return getContainer().list(); }
	
	public void expand(Object rowColumn) throws Exception {
		if (!(rowColumn instanceof MDXColumn)) return;
		MDXColumn col = (MDXColumn)rowColumn;
		setContainer(builder.drill(col, true));
	}
	
}
