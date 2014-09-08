package org.isf.oers.rcp.connector.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.isf.oers.rcp.connector.ConnectorConfigurationHandler;

public class ConnectorConfigurationHandlerFunction extends ContextFunction {
	
	@Override
	public Object compute(IEclipseContext context, String contextKey) {
//		MApplication app = context.get(MApplication.class);
//		IEclipseContext ctx = app.getContext();
//		ConnectorConfigurationHandler handler = ctx.get(ConnectorConfigurationHandler.class);
//		if (handler == null) {
//			handler = ContextInjectionFactory.make(ConnectorConfigurationHandler.class, context);
//			ctx.set(ConnectorConfigurationHandler.class, handler);						
//		}
//		return handler;
		
		ConnectorConfigurationHandler handler = context.get(ConnectorConfigurationHandler.class);
		if (handler == null) {
			handler = ContextInjectionFactory.make(ConnectorConfigurationHandler.class, context);
			context.set(ConnectorConfigurationHandler.class, handler);
			try {
				handler.load();
			} catch(Exception e) {
				throw new RuntimeException("Could not load Connector-Configurations", e);
			}
		}
		return handler;
	}

}
