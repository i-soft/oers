package org.isf.oers.module;

import org.isf.commons.decl.IDeclaration;

public abstract class HierarchyDeclaration implements IDeclaration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371665596219184220L;

	private HierarchyDeclaration parent;
	
	public HierarchyDeclaration getParent() { return parent; }
	public void setParent(HierarchyDeclaration parent) { this.parent = parent; }

}
