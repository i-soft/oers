package org.isf.oers.module;

import org.isf.commons.data.Column;
import org.isf.commons.decl.IDeclaration;

public class ColumnDeclaration extends Column implements IDeclaration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 868266055136551721L;

	private String description;
	private int displayOrder;
	private boolean trans = false;
	private String path = "";
	private HierarchyDeclaration parent;
	
	public ColumnDeclaration(HierarchyDeclaration parent, String path) {
		super();
		setPath(path);
	}
	
	@Override
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isTransient() {
		return trans;
	}
	public void setTransient(boolean trans) {
		this.trans = trans;
	}
	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	
	public HierarchyDeclaration getParent() { return parent; }
	protected void setParent(HierarchyDeclaration parent) { this.parent = parent; }
	
}
