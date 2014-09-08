package org.isf.oers.rcp.connector;

import java.util.Properties;

public abstract class AbstractConnector implements IConnector {

	private Properties properties;
		
	public Properties getProperties() { return properties == null ? new Properties() : properties; }
	public void setProperties(Properties properties) { this.properties = properties; }	
	
	public String getURL() { return getProperties().getProperty(CONNECTION_URL); }
	public void setURL(String url) { getProperties().setProperty(CONNECTION_URL, url); }
	
	public String getUsername() { return getProperties().getProperty(CONNECTION_USERNAME); }
	public void setUsername(String username) { getProperties().setProperty(CONNECTION_USERNAME, username); }
	
	public String getPassword() { return getProperties().getProperty(CONNECTION_PASSWORD); }
	public void setPassword(String password) { getProperties().setProperty(CONNECTION_PASSWORD, password); }
	
	public Object connect() throws Exception {
		return connect(getProperties());
	}
	
	public Object connect(String url) throws Exception {
		setURL(url);
		return connect();
	}
	
	public Object connect(String url, String username, String password) throws Exception {
		setURL(url);
		setUsername(username);
		setPassword(password);
		return connect();
	}
	
}
