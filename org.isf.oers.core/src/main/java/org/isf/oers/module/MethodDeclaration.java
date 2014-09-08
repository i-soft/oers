package org.isf.oers.module;

import org.isf.commons.decl.IDeclaration;

public class MethodDeclaration implements IDeclaration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4448164474969603702L;

	private String name;
	private String description;
	
	@Override
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@Override
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

}
