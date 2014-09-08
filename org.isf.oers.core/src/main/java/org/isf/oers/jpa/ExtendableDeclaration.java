package org.isf.oers.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.isf.commons.data.Column;

public class ExtendableDeclaration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1396619856171946118L;

	private Class<? extends ExtendableEntityData> entity;
	private List<Column> columns = Collections.synchronizedList(new ArrayList<Column>());
	
	public ExtendableDeclaration() {
		super();
	}
	
	public ExtendableDeclaration(Class<? extends ExtendableEntityData> entityClass) {
		this();
		setEntity(entityClass);
	}
	
	public Class<? extends ExtendableEntityData> getEntity() {
		return entity;
	}
	public void setEntity(Class<? extends ExtendableEntityData> entity) {
		this.entity = entity;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
}
