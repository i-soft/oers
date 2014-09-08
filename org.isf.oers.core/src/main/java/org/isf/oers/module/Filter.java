package org.isf.oers.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.isf.commons.data.FilterOperator;
import org.isf.commons.data.LinkOperator;

@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
	ColumnDef column();
	FilterOperator operator() default FilterOperator.EQUAL;
	LinkOperator link() default LinkOperator.AND;
	String value();
}
