package org.isf.oers.rcp.connector;

import java.util.Properties;

public class ConnectorConfiguration {

	private String name;
	private String connectorId;
	private String url;
	private String username;
	private String password;
	private boolean savePassword = false;
	private Properties properties = new Properties();
	
	public ConnectorConfiguration() {
		super();
	}
	
	public ConnectorConfiguration(Properties properties) {
		this();
		setProperties(properties);
	}
	
	public ConnectorConfiguration(String name) {
		this();
		setName(name);
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		System.out.println("DEBUG-url: "+url);
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(String connectorId) {
		System.out.println("DEBUG-connectorId: "+connectorId);
		this.connectorId = connectorId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		System.out.println("DEBUG-username: "+username);
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		System.out.println("DEBUG-password: "+password);
		this.password = password;
	}
	public boolean getSavePassword() {
		return savePassword;
	}
	public void setSavePassword(boolean savePassword) {
		System.out.println("DEBUG-savePassword: "+savePassword);
		this.savePassword = savePassword;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public String toString() { return getName(); }
	
}
