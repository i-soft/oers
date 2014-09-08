package org.isf.oers.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WidgetDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1196686174903989971L;

	private String name;
	private String path;
	private List<String> roles = new ArrayList<String>();
	private Properties properties = new Properties();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public int rolecount() { return getRoles().size(); }
	public String getRole(int index) { return getRoles().get(index); }
	public void addRole(String role) { getRoles().add(role); }
	
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public String getProperty(String name) { return getProperties().getProperty(name); }
	public void setProperty(String name, String value) { getProperties().setProperty(name, value); }
	public boolean hasProperty(String name) { return getProperties().containsKey(name); }
	
}
