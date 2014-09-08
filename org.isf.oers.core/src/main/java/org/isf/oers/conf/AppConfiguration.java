package org.isf.oers.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.isf.oers.ui.MenuDef;
import org.isf.oers.ui.WidgetDef;

public class AppConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4427298377694372320L;
	
	private MenuDef mainMenu;
	private Map<String, WidgetDef> widgets = new HashMap<String, WidgetDef>();

	public MenuDef getMainMenu() { return mainMenu; } 
	public void setMainMenu(MenuDef mainMenu) { this.mainMenu = mainMenu; }
	
	public Map<String, WidgetDef> getWidgets() {
		return widgets;
	}
	public void setWidgets(Map<String, WidgetDef> widgets) {
		this.widgets = widgets;
	}
	public List<WidgetDef> listWidgets() {
		List<WidgetDef> l = new ArrayList<WidgetDef>();
		for (WidgetDef w : getWidgets().values())
			l.add(w);
		return l;
	}
	public WidgetDef getWidget(String name) { return getWidgets().get(name); }
	public void addWidget(WidgetDef widget) { getWidgets().put(widget.getName(), widget); }
	
	
}
