package org.isf.rcp.commons.mvc;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.isf.commons.mvc.DataBindingSupport;
import org.isf.commons.mvc.IController;
import org.isf.commons.mvc.ViewDataBindingSupport;

public class JFaceViewDataBindingSupport extends ViewDataBindingSupport {
	
	public JFaceViewDataBindingSupport(IController<?> controller, Object view) {
		super(controller, view);
	}	
	
	@Override
	public void addBinding(DataBindingSupport binding) {
		super.addBinding(binding);
		if (getController() != null && getController() instanceof JFaceBindingProvider && binding != null && binding instanceof AbstractJFaceDataBindingSupport) {
			AbstractJFaceDataBindingSupport support = (AbstractJFaceDataBindingSupport)binding;
			JFaceBindingProvider<?> provider = (JFaceBindingProvider<?>)getController();
			IObservableValue modelValue = support.generateModelObservableValue();
			IObservableValue targetValue = support.generateTargetObservableValue();
			provider.getDataBindingContext().bindValue(targetValue, modelValue);
		}
	}
	
}
