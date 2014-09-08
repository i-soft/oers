package org.isf.oers.jpa;

public interface JPQLColumn extends JPQLAlias {

//	public JPQLEntity getEntity();
	
	public JPQLAlias getParent();
	public String getColumnName();
	
}
