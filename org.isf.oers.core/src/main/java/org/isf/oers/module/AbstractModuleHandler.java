package org.isf.oers.module;

public abstract class AbstractModuleHandler implements ModuleHandler {

	@Override
	public ModuleDeclaration moduleDeclaration() {
		Class<?> cls = getClass();
		if (cls.isAnnotationPresent(ModuleHandlerDef.class)) {
			ModuleHandlerDef mhd = cls.getAnnotation(ModuleHandlerDef.class);
//			return new ModuleDeclaration(mhd);
			return ModuleDeclarationFactory.createInstance(mhd);
		} else return null;
	}

}
