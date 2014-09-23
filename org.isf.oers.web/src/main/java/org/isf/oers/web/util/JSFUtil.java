package org.isf.oers.web.util;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static ValueExpression createValueExpression(String expression, Class<?> type) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		return ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(), expression, type);
	}
	
	public static MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    return facesContext.getApplication().getExpressionFactory().createMethodExpression(
	        facesContext.getELContext(), expression, returnType, parameterTypes);
	}
	
	public static String getInitParameter(String name) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		return ctx.getExternalContext().getInitParameter(name);
	}
	
	public static void addMessage(FacesMessage msg) {
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
}
