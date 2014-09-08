package org.isf.oers.web;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.isf.oers.IOERSSessionLocal;
import org.isf.oers.auth.User;
import org.isf.oers.ui.MenuDef;
import org.isf.oers.ui.MenuDefType;
import org.isf.oers.ui.MenuItemDef;
import org.isf.oers.ui.WidgetDef;
import org.isf.oers.web.app.Application;
import org.isf.oers.web.util.JSFUtil;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class WebSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -800157138809085004L;
	
	private MenuModel mainMenuModel;
	private Map<String, DashboardModel> dashBoards = new HashMap<String, DashboardModel>();
	
	@Inject
	private IOERSSessionLocal session;
	
	@Inject
	private Application app;
	
	protected DefaultMenuItem buildMenuItem(MenuItemDef def) {
		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue(def.getName());
		item.setCommand(def.getCommand());
		return item;
	}
	
	protected DefaultSubMenu buildSubMenu(MenuDef def) {
		DefaultSubMenu sm = new DefaultSubMenu();
		sm.setLabel(def.getName());
		
		for (MenuDef md : def.getItems())
			if (md.getType() == MenuDefType.MENU) sm.addElement(buildSubMenu(md));
			else if (md.getType() == MenuDefType.MENUITEM) sm.addElement(buildMenuItem((MenuItemDef)md));
			
		return sm;
	}
	
	protected DefaultMenuModel buildMainMenu() {
		DefaultMenuModel mm = new DefaultMenuModel();
		
		for (MenuDef md : app.getConfiguration().getMainMenu().getItems())
			if (md.getType() == MenuDefType.MENU) mm.addElement(buildSubMenu(md));
			else if (md.getType() == MenuDefType.MENUITEM) mm.addElement(buildMenuItem((MenuItemDef)md));
		
		return mm;
	}
	
	protected DefaultDashboardModel buildDefaultDashboard() {
		DefaultDashboardModel model = new DefaultDashboardModel();
		
		DashboardColumn col1 = new DefaultDashboardColumn();
		DashboardColumn col2 = new DefaultDashboardColumn();
		DashboardColumn col3 = new DefaultDashboardColumn();
		
		for (WidgetDef w : listWidgets())
			col1.addWidget("widget-"+w.getName());
		
		model.addColumn(col1);
		model.addColumn(col2);
		model.addColumn(col3);
		
		return model;
	}
	
	@PostConstruct
	public void initial() {
		mainMenuModel = buildMainMenu();
		
		dashBoards.put("default", buildDefaultDashboard());
	}
	
	public User getUser() { return session.getUser(); }
	
	public MenuModel getMainMenuModel() {
		return mainMenuModel;
	}
	
	public DashboardModel dashboardModel(String name) {
		return dashBoards.get("default");
	}
	
	public List<WidgetDef> listWidgets() {
		return app.getConfiguration().listWidgets();
	}
	
	public void handleDashReorder(DashboardReorderEvent evt) {
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("TODO - DASH-REORDER");
		msg.setDetail("Implment reoder behavior");
		JSFUtil.addMessage(msg);
	}
	
	public void logout() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) 
			session.invalidate();
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
	}
	
}
