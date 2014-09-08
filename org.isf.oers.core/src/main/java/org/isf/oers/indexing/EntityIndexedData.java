package org.isf.oers.indexing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EntityIndexedData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1287493280359127039L;
	
	private String beanClass;
	private String uuid;
	private String subject;
	private String content;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, Object> getAttributes() { return attributes; }
	public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
	public Object getAttribute(String name) { return getAttributes().get(name); }
	public void setAttribute(String name, Object value) { getAttributes().put(name, value); }
	
	
	
}
