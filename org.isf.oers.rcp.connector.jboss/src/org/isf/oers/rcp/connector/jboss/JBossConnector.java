package org.isf.oers.rcp.connector.jboss;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.isf.oers.rcp.connector.AbstractConnector;

public class JBossConnector extends AbstractConnector {
	
	public static final String INITIAL_CONTEXT_FACTORY = Context.INITIAL_CONTEXT_FACTORY;
	public static final String PROVIDER_URL = Context.PROVIDER_URL;
	public static final String SECURITY_PRINCIPAL = Context.SECURITY_PRINCIPAL;
	public static final String SECURITY_CREDENTIALS = Context.SECURITY_CREDENTIALS;
	public static final String CLIENT_EJB_CONTEXT = "jboss.naming.client.ejb.context";
	public static final String SASL_POLICY_NOPLAINTEXT = "jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT";
	
	public static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	public static final String DEFAULT_CLIENT_EJB_CONTEXT = "true";
	public static final String DEFAULT_SASL_POLICY_NOPLAINTEXT = "false";
	public static final String DEFAULT_PROVIDER_URL = "remote://localhost:4447";
	
	public Context context;
	
	public JBossConnector() {
		super();
		Properties props = getProperties();
		props.setProperty(INITIAL_CONTEXT_FACTORY, DEFAULT_INITIAL_CONTEXT_FACTORY);
		props.setProperty(CLIENT_EJB_CONTEXT, DEFAULT_CLIENT_EJB_CONTEXT);
		props.setProperty(SASL_POLICY_NOPLAINTEXT, DEFAULT_SASL_POLICY_NOPLAINTEXT);
		props.setProperty(PROVIDER_URL, DEFAULT_PROVIDER_URL);
		setProperties(props);
	}
	
	public void setURL(String url) {
		super.setURL(url);
		getProperties().setProperty(PROVIDER_URL, url); 
	}
	
	
	@Override
	public void setUsername(String username) {
		super.setUsername(username);
		if (username != null) getProperties().setProperty(SECURITY_PRINCIPAL, username);
		else getProperties().remove(SECURITY_PRINCIPAL);
	}
	
	@Override
	public void setPassword(String password) {
		super.setPassword(password);
		if (password != null) getProperties().setProperty(SECURITY_CREDENTIALS, password);
		else getProperties().remove(SECURITY_CREDENTIALS);
	}
	
	@Override
	public Object connect(Properties properties) throws Exception {
		if (context != null) close();
		context = new InitialContext(properties);
		return context;
	}

	@Override
	public Context getConnectionObject() {
		return context;
	}

	@Override
	public void close() throws Exception {
		if (context != null)
			try {
				context.close();
			} finally {
				context = null;
			}
		
	}

	@Override
	public <T> T lookup(Class<T> clazz, String lookup) throws Exception {
		return clazz.cast(getConnectionObject().lookup(lookup));
	}

}
