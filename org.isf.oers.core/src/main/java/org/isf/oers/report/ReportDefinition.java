package org.isf.oers.report;

import java.io.Serializable;
import java.util.Properties;

public class ReportDefinition implements Serializable, Comparable<ReportDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4790316505651253048L;

	private String id;
	private ReportType type = ReportType.UNKNOWN;
	private String name;
	private Properties properties; 
	
	public ReportDefinition() {
		properties = new Properties();
	}
	
	public ReportDefinition(String id, ReportType type) {
		this();
		setId(id);
		setType(type);
	}
	
	public ReportDefinition(String id, String name, ReportType type) {
		this(id, type);
		setName(name);
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ReportType getType() { return type; }
	public void setType(ReportType type) { this.type = type; }
	
	public Properties getProperties() { return properties; }
	public void setProperties(Properties properties) { this.properties = properties; }
	public boolean hasProperty(String name) { return getProperties().containsKey(name); }
	public String getProperty(String name) { return getProperties().getProperty(name); }
	public void setProperty(String name, String value) { getProperties().setProperty(name, value); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportDefinition other = (ReportDefinition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	public String toString() { return getName()!=null ? getName() : getId(); }
	
	public int compareTo(ReportDefinition rdef) {
		return getId().compareTo(rdef.getId());
	}
}
