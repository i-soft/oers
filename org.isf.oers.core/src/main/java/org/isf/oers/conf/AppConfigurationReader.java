package org.isf.oers.conf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.isf.commons.Util;
import org.isf.commons.xml.XMLDocument;
import org.isf.oers.ui.MenuDef;
import org.isf.oers.ui.MenuItemDef;
import org.isf.oers.ui.WidgetDef;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AppConfigurationReader {

	private XMLDocument doc;
	
	protected AppConfigurationReader() {
		doc = new XMLDocument();
	}
	
	public AppConfigurationReader(File file) throws SAXException, IOException {
		this();
		doc.parseDocument(file.getAbsolutePath());
	}
	
	public AppConfigurationReader(InputStream in) throws SAXException, IOException {
		this();
		doc.parseDocument(in);
	}
	
	protected Properties generateProperties(Node n) {
		Properties props = new Properties();
		for (Node pn : doc.getChildNodes(n, "property")) {
			if (doc.hasAttribute(pn, "name")) {
				String name = doc.getAttribute(pn, "name");
				String value = doc.hasAttribute(pn, "value") ? doc.getAttribute(pn, "value") : doc.getCDATAValue(pn);
				props.setProperty(name, value);
			}
		}
		return props;
	}
	
	protected MenuDef generateMenu(Node n, MenuDef parentMenu) {
		MenuDef ret = new MenuDef();
		if (doc.hasAttribute(n, "name")) ret.setName(doc.getAttribute(n, "name"));
		if (doc.hasAttribute(n, "roles")) 
			for (String role : Util.splitLine(doc.getAttribute(n, "roles"), ",")) 
				ret.addRole(role);
		ret.setProperties(generateProperties(n));
		
		NodeList nl = n.getChildNodes();
		for (int i=0;i<nl.getLength();i++) {
			Node cn = nl.item(i);
			if ("menu".equals(cn.getNodeName())) ret.addItem(generateMenu(cn, ret));
			else if ("menuitem".equals(cn.getNodeName())) ret.addItem(generateMenuItem(cn, ret));
		}
		
		return ret;
	}
	
	protected MenuItemDef generateMenuItem(Node n, MenuDef parentMenu) {
		MenuItemDef ret = new MenuItemDef();
		if (doc.hasAttribute(n, "name")) ret.setName(doc.getAttribute(n, "name"));
		if (doc.hasAttribute(n, "roles")) 
			for (String role : Util.splitLine(doc.getAttribute(n, "roles"), ",")) 
				ret.addRole(role);
		if (doc.hasAttribute(n, "command")) ret.setCommand(doc.getAttribute(n, "command"));
		ret.setProperties(generateProperties(n));
		
		return ret;
	}
	
	protected MenuDef generateMainMenu() throws XPathExpressionException {
		Node n = (Node)doc.evaluateXPath("/application/mainmenu", XPathConstants.NODE);
		MenuDef mm = generateMenu(n, null);		
		return mm;
	}
	
	protected void generateWidgets(AppConfiguration conf) throws XPathExpressionException {
		NodeList nl = (NodeList)doc.evaluateXPath("/application/widgets/widget", XPathConstants.NODESET);
		for (int i=0;i<nl.getLength();i++) {
			Node n = nl.item(i);
			WidgetDef wd = new WidgetDef();
			if (doc.hasAttribute(n, "name")) wd.setName(doc.getAttribute(n, "name"));
			if (doc.hasAttribute(n, "path")) wd.setPath(doc.getAttribute(n, "path"));
			wd.setProperties(generateProperties(n));			
			conf.addWidget(wd);
		}
	}
	
	public AppConfiguration read() throws Exception {
		AppConfiguration conf = new AppConfiguration(); 
		
		conf.setMainMenu(generateMainMenu());
		
		generateWidgets(conf);
		
		return conf;
	}
	
}
