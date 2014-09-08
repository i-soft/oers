package org.isf.oers.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ModuleDeclaration extends HierarchyDeclaration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3678877842092727388L;

	private String name;
	private String description;
	private String className;
	private String dtoClassName;
	private List<DeviceDeclaration> devices = new ArrayList<DeviceDeclaration>();
	private List<ColumnDeclaration> declaredColumns = new ArrayList<ColumnDeclaration>();
	
	public ModuleDeclaration() {}
	
	@Override
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@Override
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getEntityClassName() { return className; }
	public void setEntityClassName(String className) { this.className = className; }
	
	public String getDTOClassName() { return dtoClassName; }
	public void setDTOClassName(String className) { this.dtoClassName = className; }
	
	public List<DeviceDeclaration> getDevices() {
		return devices;
	}
	public void setDevices(List<DeviceDeclaration> devices) {
		this.devices = devices;
	}
	public void addDevice(DeviceDeclaration device) {
		device.setParent(this);
		getDevices().add(device);
	}
	public int deviceCount() { return getDevices().size(); }
	public DeviceDeclaration getDevice(int index) {
		return getDevices().get(index); 
	}
	public DeviceDeclaration getDevice(String name) {
		for (DeviceDeclaration dd : getDevices())
			if (name.equals(dd.getName()))
				return dd;
		return null;
	}

	public List<ColumnDeclaration> getDeclaredColumns() {
		return declaredColumns;
	}
	public void setDeclaredColumns(List<ColumnDeclaration> declaredColumns) {
		this.declaredColumns = declaredColumns;
	}
	public int declaredColumnCount() { return getDeclaredColumns().size(); }
	public ColumnDeclaration getDeclaredColumn(int index) {
		return getDeclaredColumns().get(index);
	}
	public void addDeclaredColumn(ColumnDeclaration column) { 
		getDeclaredColumns().add(column);
	}
	
	public List<ColumnDeclaration> getColumns() {
		List<ColumnDeclaration> l = new ArrayList<ColumnDeclaration>();
		l.addAll(getDeclaredColumns());
		for (DeviceDeclaration dev : getDevices())
			l.addAll(dev.getColumns());
		
		Collections.sort(l, new Comparator<ColumnDeclaration>() {

			@Override
			public int compare(ColumnDeclaration o1, ColumnDeclaration o2) {
				if (o1.getDisplayOrder() > o2.getDisplayOrder()) return 1;
				else if (o1.getDisplayOrder() < o2.getDisplayOrder()) return -1;
				else return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
			}
			
		});
		
		return l;
	}
}
