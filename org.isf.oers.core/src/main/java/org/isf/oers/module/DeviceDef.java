package org.isf.oers.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeviceDef {
	String name();
	String description() default "";
	String entityClassName();
//	String entityBaseClassName() default ""; // if not set, then Module-EntityClass = BaseClass
	String dtoClassName() default "";
	ColumnDef[] columns() default {};
	BusinessLogicCallDef[] businessLogicCalls() default {};
}
