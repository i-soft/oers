package org.isf.rcp.commons.mvc.rule;

import org.isf.commons.mvc.DataBindingContext;
import org.isf.commons.mvc.DataBindingSupport;
import org.isf.commons.mvc.IDataBindingRule;
import org.isf.rcp.commons.mvc.JFaceTextDataBindingSupport;

public class TextRule implements IDataBindingRule {

	@Override
	public DataBindingSupport bind(DataBindingContext ctx) {
		return new JFaceTextDataBindingSupport(ctx);
	}

}
