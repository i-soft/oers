package org.isf.oers.rcp.connector;

import java.util.Properties;

public interface IConnector {
	
	public static final String CONNECTION_URL = "oers.connection.url";
	public static final String CONNECTION_USERNAME = "oers.connection.username";
	public static final String CONNECTION_PASSWORD = "oers.connection.password";
	
	public Properties getProperties();
	
	public Object connect(Properties properties) throws Exception;
	public Object connect() throws Exception;
	public Object connect(String url) throws Exception;
	public Object connect(String url, String username, String password) throws Exception;
	
	public Object getConnectionObject();
	
	public void close() throws Exception;
	
	public <T> T lookup(Class<T> clazz, String lookup) throws Exception;
	
}
