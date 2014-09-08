package org.isf.oers.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleHandlerDef {
	@Nonbinding String name() default "";
	@Nonbinding String description() default "";
	@Nonbinding String entityClassName() default "";
	@Nonbinding String dtoClassName() default "";
	@Nonbinding DeviceDef[] devices() default {};
	@Nonbinding ColumnDef[] columns() default {};
	@Nonbinding BusinessLogicCallDef[] businessLogicCalls() default {};
}
