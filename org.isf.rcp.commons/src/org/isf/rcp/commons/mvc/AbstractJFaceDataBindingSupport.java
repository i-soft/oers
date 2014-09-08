package org.isf.rcp.commons.mvc;

import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.swt.widgets.Widget;
import org.isf.commons.mvc.DataBindingContext;
import org.isf.commons.mvc.DataBindingSupport;

public abstract class AbstractJFaceDataBindingSupport extends DataBindingSupport {
	
	public AbstractJFaceDataBindingSupport(DataBindingContext context) {
		super(context);
	}
	
	protected boolean isJavaBean(Class<?> clazz) {
		try {
			clazz.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
			clazz.getMethod("removePropertyChangeListener", PropertyChangeListener.class);
			return true;
		} catch(NoSuchMethodException e) {
			return false;
		}
	}
	
	protected boolean isSWTWidget(Class<?> clazz) {
		return clazz.isAssignableFrom(Widget.class);
	}
	
	protected abstract boolean validateTarget(Object target);
	
	public IValueProperty getModelValueProperty() {
		return isJavaBean(getContext().getModelClass()) 
				? BeanProperties.value(getContext().getModelClass(), getContext().getModelAttribute())
				: PojoProperties.value(getContext().getModelClass(), getContext().getModelAttribute());
	}
	
	public abstract IWidgetValueProperty getTargetValueProperty();
	
	public IObservableValue generateModelObservableValue() {
		if (getContext() != null && getContext().getController() != null && getContext().getController() instanceof JFaceBindingProvider) {
			JFaceBindingProvider<?> provider = (JFaceBindingProvider<?>)getContext().getController();
			return getModelValueProperty().observeDetail(provider.getWritableValue());
		}
		return null;
	}
	
	public IObservableValue generateTargetObservableValue() {
		return validateTarget(getContext().getTarget()) ? getTargetValueProperty().observe((Widget)getContext().getTarget()) : null;
	}
}
