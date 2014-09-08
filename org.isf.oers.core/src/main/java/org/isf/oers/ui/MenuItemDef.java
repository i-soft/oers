package org.isf.oers.ui;

public class MenuItemDef extends MenuDef {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5186881222169979868L;

	private String name;
	private String command;
	
	public MenuItemDef() {
		setType(MenuDefType.MENUITEM);
	}
	
	public MenuItemDef(String name) {
		this();
		setName(name);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCommand() {
		return command; 
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
}
