package org.isf.oers.module;

import java.util.ArrayList;
import java.util.List;

public class DeviceDeclaration extends ModuleDeclaration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5496522160561002418L;
	
	public DeviceDeclaration() {}
	
	public List<ColumnDeclaration> getColumns() {
		List<ColumnDeclaration> l = new ArrayList<ColumnDeclaration>();
		l.addAll(getDeclaredColumns());
		for (DeviceDeclaration dev : getDevices())
			l.addAll(dev.getColumns());
		
		return l;
	}
	
}
