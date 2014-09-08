package org.isf.rcp.commons.mvc.rule;

import org.isf.commons.mvc.DataBindingContext;
import org.isf.commons.mvc.DataBindingSupport;
import org.isf.commons.mvc.IDataBindingRule;
import org.isf.rcp.commons.mvc.JFaceSelectionDataBindingSupport;

public class SelectionRule implements IDataBindingRule {

	@Override
	public DataBindingSupport bind(DataBindingContext ctx) {
		return new JFaceSelectionDataBindingSupport(ctx);
	}

}
