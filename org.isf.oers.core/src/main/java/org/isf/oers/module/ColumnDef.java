package org.isf.oers.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.isf.commons.bean.DefaultConverter;
import org.isf.commons.bean.IConverter;

@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnDef {
	String name();
	String displayName() default "";
	String description() default "";
	boolean fillDirectionRight() default true;
	int displayOrder() default 0;
	Class<? extends IConverter> converter() default DefaultConverter.class;
}
