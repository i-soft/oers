package org.isf.rcp.commons.mvc;

import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Text;
import org.isf.commons.mvc.DataBindingContext;

public class JFaceTextModifyDataBindingSupport extends
		JFaceTextDataBindingSupport {

	public JFaceTextModifyDataBindingSupport(DataBindingContext context) {
		super(context);
	}
	
	protected boolean validateTarget(Object target) {
		if (target == null) return false;
		return  target instanceof StyledText
				|| target instanceof Text;
	}
	
	@Override
	public IWidgetValueProperty getTargetValueProperty() {
		return WidgetProperties.text(SWT.Modify);
	}

}
