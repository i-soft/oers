package org.isf.oers.rcp.application;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.isf.oers.rcp.connector.ConnectorConfigurationHandler;
import org.isf.oers.rcp.dialog.ConnectorLoginDialog;
import org.isf.rcp.commons.SWTUtil;

@SuppressWarnings("restriction")
public class LifeCycleHandler {
		
	@Inject
	@Named("ConnectorConfigurationHandler")
	private ConnectorConfigurationHandler cchandler;
	
	@PostContextCreate
	public void postContextCreate(IApplicationContext appContext, Display display) {
		final Shell shell = new Shell(SWT.INHERIT_NONE);
		
		final ConnectorLoginDialog dialog = new ConnectorLoginDialog(shell);
		dialog.setHandler(cchandler);
		
		appContext.applicationRunning();
		
		SWTUtil.centerShellOnPrimaryMonitor(display, shell);
		
		if (dialog.open() != Window.OK) {
			System.out.println("ABORT!");
			System.exit(-1);
		}
	}
	
	@PreSave
	public void preSave() {
		
	}
	
}
