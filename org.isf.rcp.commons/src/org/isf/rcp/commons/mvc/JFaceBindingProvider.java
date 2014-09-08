package org.isf.rcp.commons.mvc;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;
import org.isf.commons.mvc.AbstractBindingProvider;
import org.isf.commons.mvc.ViewDataBindingSupport;

public abstract class JFaceBindingProvider<T> extends AbstractBindingProvider<T> {

	private DataBindingContext dataBindingContext;
	private WritableValue value;
	
	public JFaceBindingProvider() {
		super();
		try {
			setDataBindingContext(new DataBindingContext());
			setWritableValue(new WritableValue());
		} catch(Exception e) {
			Realm realm = SWTObservables.getRealm(Display.getCurrent());
			setDataBindingContext(new DataBindingContext(realm));
			setWritableValue(new WritableValue(realm));
		}
	}
	
	public WritableValue getWritableValue() { return value; }
	protected void setWritableValue(WritableValue value) { this.value = value; }
	
	public DataBindingContext getDataBindingContext() {
		return dataBindingContext;
	}

	protected void setDataBindingContext(DataBindingContext dataBindingContext) {
		this.dataBindingContext = dataBindingContext;
	}
	
	@Override
	protected ViewDataBindingSupport createViewDataBindingSupport(Object view) {
		return new JFaceViewDataBindingSupport(this, view);
	}
	
	@Override
	public void refresh() throws Exception {
		getWritableValue().setValue(getModel());
		
	}

}
