package org.isf.oers.rcp.connector;

public class ConnectorDefinition {

	private String id;
	private String name;
	private Class<IConnector> connectorClass;
	private String description;
	private boolean sessionProvider = false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<IConnector> getConnectorClass() {
		return connectorClass;
	}
	public void setConnectorClass(Class<IConnector> connectorClass) {
		this.connectorClass = connectorClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSessionProvider() {
		return sessionProvider;
	}
	public void setSessionProvider(boolean sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
}
