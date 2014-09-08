package org.isf.oers.module;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.isf.commons.bean.Attribute;
import org.isf.commons.bean.Bean;
import org.isf.commons.bean.BeanFactory;

public class ModuleDeclarationFactory {

	public static ModuleDeclaration createInstance(ModuleHandlerDef def) {
		ModuleDeclaration md = new ModuleDeclaration();
		
		md.setName(def.name());
		md.setDescription(def.description());
		md.setEntityClassName(def.entityClassName());
		md.setDTOClassName(def.dtoClassName());
		
		for (DeviceDef ddef : def.devices())
			md.addDevice(createDeviceDeclaration(ddef));
		
		if (def.columns().length > 0) {
			try {
				Bean b = BeanFactory.createBeanInstance(md.getEntityClassName());
				for (ColumnDef cdef : def.columns()) 
					md.addDeclaredColumn(createColumnDeclaration(b, cdef, md));
			} catch(Exception e) {
				/* TODO ERROR HANDLING NO ENTITY-BEAN */
			}
		}
		
		// TODO implement BusinessLogic calls
		
		return md;
	}
	
	private static DeviceDeclaration createDeviceDeclaration(DeviceDef def) {
		DeviceDeclaration dd = new DeviceDeclaration();
		
		dd.setName(def.name());
		dd.setDescription(def.description());
		dd.setEntityClassName(def.entityClassName());
		dd.setDTOClassName(def.entityClassName());
		
		if (def.columns().length > 0) {
			try {
				Bean b = BeanFactory.createBeanInstance(dd.getEntityClassName());
				for (ColumnDef cdef : def.columns()) 
					dd.addDeclaredColumn(createColumnDeclaration(b, cdef, dd));
			} catch(Exception e) {
				/* TODO ERROR HANDLING NO ENTITY-BEAN */
			}
		}
		
		// TODO implement BusinessLogic calls
		
		return dd;
	}
	
	private static ColumnDeclaration createColumnDeclaration(Bean b, ColumnDef def, HierarchyDeclaration parentDeclaration) {
		ColumnDeclaration cd = new ColumnDeclaration(parentDeclaration, def.name());
		
		cd.setName(def.name());
		if (def.displayName().length() > 0) cd.setDisplayName(def.displayName());
		if (def.description().length() > 0) cd.setDescription(def.description());
		cd.setFillDirectionRight(def.fillDirectionRight());
		try {
			cd.setConverter(def.converter().newInstance());
		} catch(Exception e) {
			/* DO NOTHING ? */
		}
		cd.setDisplayOrder(def.displayOrder());
		
		Attribute attr = b.getChildPerPath(def.name());
		cd.setType(attr.getDataType());
		
		if (attr.isAnnotationPresent(Id.class))
			cd.setPrimary(true);
		if (attr.isAnnotationPresent(Transient.class))
			cd.setTransient(true);
		if (attr.isAnnotationPresent(Column.class)) {
			Column col = attr.getAnnotation(Column.class);
			if (col.length() != 255) cd.setLength(col.length());
			if (col.precision()>0) cd.setPrecision(col.precision());
			if (col.scale()>0) cd.setScale(col.scale());
			cd.setNullable(col.nullable());
			cd.setUnique(col.unique());
		}
		
		return cd;
	}
	
}
