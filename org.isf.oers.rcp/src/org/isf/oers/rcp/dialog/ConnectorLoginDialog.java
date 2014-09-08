package org.isf.oers.rcp.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.isf.oers.images.ImageResource;
import org.isf.oers.images.ImageSize;
import org.isf.oers.rcp.connector.ConnectorConfigurationHandler;

public class ConnectorLoginDialog extends Dialog {

	private Combo cbConntector; 
	private Button btnConnectorConfiguration;
	private Text txtUsername;
	private Text txtPassword;
	
	private String username;
	private String password;
	
	private ConnectorConfigurationHandler handler;
	
	public ConnectorLoginDialog(Shell parentShell) {
		super(parentShell);
		setUsername(System.getProperty("user.name"));
		setPassword("");
	}

	public ConnectorConfigurationHandler getHandler() { return handler; }
	public void setHandler(ConnectorConfigurationHandler handler) { this.handler = handler; }
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout(3, false);
		layout.marginRight = 5;
		layout.marginLeft = 10;
		container.setLayout(layout);
		
		Label lbConnector = new Label(container, SWT.NONE);
		lbConnector.setText("Connector:");
		
		cbConntector = new Combo(container, SWT.READ_ONLY | SWT.FLAT | SWT.BORDER);
		cbConntector.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true, false, 1, 1));
		// TODO FILL cbConnector
		
		btnConnectorConfiguration = new Button(container, SWT.FLAT | SWT.PUSH);
		btnConnectorConfiguration.setLayoutData(new GridData(SWT.END, SWT.CENTER,false, false, 1, 1));
		btnConnectorConfiguration.setImage(ImageResource.CONNECTOR_CONFIGURATION.getImage(ImageSize._16x16));
		btnConnectorConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				ConnectorConfigurationDialog ccd = new ConnectorConfigurationDialog(getShell());
				ccd.setHandler(getHandler());
				ccd.create();
				if (ccd.open() == Window.OK)
					refresh();
			}
		});
		
		Label lbUsername = new Label(container, SWT.NONE);
		lbUsername.setText("Username:");
		
		txtUsername = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true, false, 2, 1));
		txtUsername.setText(getUsername());
		
		Label lbPassword = new Label(container, SWT.NONE);
		lbPassword.setText("Password:");
		
		txtPassword = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true,false,2,1));
		txtPassword.setText(getPassword());
		
		if (cbConntector.getItemCount() > 0)
			cbConntector.setText(cbConntector.getItem(0));
		
		return container;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Login", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected void okPressed() {
		setUsername(txtUsername.getText());
		setPassword(txtPassword.getText());
		super.okPressed();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(350, 180);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void refresh() {
		System.out.println("Connection-Combo refreshed");
	}

}
