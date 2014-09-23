package org.isf.oers.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.isf.commons.XMLUtil;
import org.isf.commons.xml.XMLDocument;
import org.isf.oers.report.ReportDefinition;
import org.isf.oers.report.ReportType;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ReportDefinitionReader {

	private XMLDocument doc = new XMLDocument();
	
	public ReportDefinitionReader(String filepath) throws IOException {
		try {
			doc.parseDocument(filepath);
		} catch(SAXException se) {
			throw new IOException("Error while parse document \""+filepath+"\".", se);
		}
	}
	
	public ReportDefinitionReader(File file) throws IOException {
		this(file.getAbsolutePath());
	}
	
	public ReportDefinitionReader(InputStream in) throws IOException {
		try {
			doc.parseDocument(in);
		} catch(SAXException se) {
			throw new IOException("Error while parse document from stream.", se);
		}
	}
	
	protected Properties createProperties(Node n) {
		Properties props = new Properties();
		for (Node np : XMLUtil.getChildNodes(n, "property")) {
			String name = XMLUtil.getAttribute(np, "name");
			String value = XMLUtil.hasAttribute(np, "value") ? XMLUtil.getAttribute(np, "value") : XMLUtil.getTextValue(np);
			props.setProperty(name, value);
		}
		return props;
	}
	
	protected String getName(Node n) {
		Node nc = XMLUtil.getChildNode(n, "name");
		if (nc != null) return XMLUtil.getTextValue(nc); 
		return "";
	}
	
	public ReportDefinition readReportDefinition() throws IOException {
		ReportDefinition rd = new ReportDefinition(); 
		Node root = doc.getRoot();
		rd.setId(XMLUtil.getAttribute(root, "id"));
		rd.setType(ReportType.perType(XMLUtil.getAttribute(root, "type")));
		rd.setName(getName(root));
		rd.setProperties(createProperties(root));
		return rd;
	}
	
}
