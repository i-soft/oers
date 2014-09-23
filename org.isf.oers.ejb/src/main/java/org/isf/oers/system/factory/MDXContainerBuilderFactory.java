package org.isf.oers.system.factory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.isf.mdx.MDXContainerBuilder;
import org.pivot4j.datasource.SimpleOlapDataSource;
import org.pivot4j.impl.PivotModelImpl;

public class MDXContainerBuilderFactory {

	@Produces
	public MDXContainerBuilder createMDXDataSource(InjectionPoint ip) {
		SimpleOlapDataSource ds = new SimpleOlapDataSource();
		// TODO get ConnectionString from properties
		String connstr = "jdbc:xmla:Server=http://localhost:8080/atlas/xmla";
//				+ ";Cache=org.olap4j.driver.xmla.cache.XmlaOlap4jNamedMemoryCache"
//				+ ";Cache.Name=ATLASConnection"
//				+ ";Cache.Mode=LFU;Cache.Timeout=600;Cache.Size=100";
		ds.setConnectionString(connstr);
		
		return new MDXContainerBuilder(new PivotModelImpl(ds));
	}
	
}
