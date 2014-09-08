package org.isf.rcp.commons.mvc.rule;

import org.isf.commons.mvc.DataBindingContext;
import org.isf.commons.mvc.DataBindingSupport;
import org.isf.rcp.commons.mvc.JFaceTextModifyDataBindingSupport;

public class TextModifyRule extends TextRule {

	@Override
	public DataBindingSupport bind(DataBindingContext ctx) {
		return new JFaceTextModifyDataBindingSupport(ctx);
	}
	
}
