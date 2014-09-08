package org.isf.rcp.commons.mvc;

import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.isf.commons.mvc.DataBindingContext;

public class JFaceTextDataBindingSupport extends AbstractJFaceDataBindingSupport {

	public JFaceTextDataBindingSupport(DataBindingContext context) {
		super(context);
	}
	
	protected boolean validateTarget(Object target) {
		if (target == null) return false;
		return target instanceof Button 
				|| target instanceof CCombo
				|| target instanceof CLabel
				|| target instanceof Combo
				|| target instanceof Item
				|| target instanceof Label
				|| target instanceof Link
				|| target instanceof Shell
				|| target instanceof StyledText
				|| target instanceof Text;
	}

	@Override
	public IWidgetValueProperty getTargetValueProperty() {
		return WidgetProperties.text();
	}
	
}
