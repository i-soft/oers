package org.isf.oers.module;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.isf.commons.Util;

public class Module implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4976797260080917849L;

	private String name;
	private Class<?> handler;
	private String handlerName;
	private ModuleDeclaration declaration;
	
	public Module() {}
	
	public Module(Class<?> handler) {
		this();
		setHandler(handler);
	}

	public String getName() {
		if (this.name != null) return name;
		else if (getDeclaration() != null) return getDeclaration().getName();
		else if (getHandlerName() != null) return getHandlerName();
		else return "";
	}
	public void setName(String name) { this.name = name; }
	
	public Class<?> getHandler() {
		return handler;
	}
	
	public void setHandler(Class<?> handler) {
		this.handler = handler;
		if (handler.isAnnotationPresent(Named.class)) {
			Named anamed = handler.getAnnotation(Named.class);
			if (anamed.value() != null && anamed.value().length() > 0) setHandlerName(anamed.value());
		} else if (handler.isAnnotationPresent(ManagedBean.class)) {
			ManagedBean ambean = handler.getAnnotation(ManagedBean.class);
			if (ambean.name() != null && ambean.name().length()>0) setHandlerName(ambean.name());
		} else Util.normalizeName("get"+handler.getSimpleName());
	}

	public String getHandlerName() {
		return handlerName;
	}

	protected void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public ModuleDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(ModuleDeclaration declaration) {
		this.declaration = declaration;
	}
	
	
	
}
