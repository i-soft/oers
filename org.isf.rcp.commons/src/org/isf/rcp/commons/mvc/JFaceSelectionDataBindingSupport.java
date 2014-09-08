package org.isf.rcp.commons.mvc;

import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.isf.commons.mvc.DataBindingContext;

public class JFaceSelectionDataBindingSupport extends
		AbstractJFaceDataBindingSupport {

	public JFaceSelectionDataBindingSupport(DataBindingContext context) {
		super(context);
	}

	@Override
	protected boolean validateTarget(Object target) {
		if (target == null) return false;
		return target instanceof Button
				|| target instanceof CCombo
				|| target instanceof Combo
				|| target instanceof DateTime
				|| target instanceof List
				|| target instanceof MenuItem
				|| target instanceof Scale
				|| target instanceof Slider
				|| target instanceof Spinner;
	}

	@Override
	public IWidgetValueProperty getTargetValueProperty() {
		return WidgetProperties.selection();
	}

}
