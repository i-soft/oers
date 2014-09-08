package org.isf.oers.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3243734526842892446L;

	private String name = "";
	private MenuDefType type;
	private List<MenuDef> items;
	private List<String> roles;
	
	public MenuDef() {
		type = MenuDefType.MENU;
		items = new ArrayList<MenuDef>();
		roles = new ArrayList<String>();
	}
	
	public MenuDef(String name) {
		this();
		setName(name);
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public MenuDefType getType() { return type; }
	public void setType(MenuDefType type) { this.type = type; }

	public List<MenuDef> getItems() {
		return items;
	}
	public void setItems(List<MenuDef> items) {
		this.items = items;
	}
	public int itemcount() { return getItems().size(); }
	public MenuDef getItem(int index) { return getItems().get(index); }
	public void addItem(MenuDef item) { getItems().add(item); }

	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public int rolecount() { return getRoles().size(); }
	public String getRole(int index) { return getRoles().get(index); }
	public void addRole(String role) { getRoles().add(role); }
	
}
