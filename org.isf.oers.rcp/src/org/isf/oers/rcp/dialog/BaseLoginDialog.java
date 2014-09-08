package org.isf.oers.rcp.dialog;

import javax.inject.Named;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@Named
public class BaseLoginDialog extends Dialog {

	private Text txtUsername;
	private Text txtPassword;
	
	private String username;
	private String password;
	
	public BaseLoginDialog(Shell parentShell) {
		super(parentShell);
		setUsername(System.getProperty("user.name"));
		setPassword("");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginRight = 5;
		layout.marginLeft = 10;
		container.setLayout(layout);
		
		Label lbUsername = new Label(container, SWT.NONE);
		lbUsername.setText("Username:");
		
		txtUsername = new Text(container, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true, false, 1, 1));
		txtUsername.setText(getUsername());
		
		Label lbPassword = new Label(container, SWT.NONE);
		lbPassword.setText("Password:");
		
		txtPassword = new Text(container, SWT.BORDER);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true,false,1,1));
		txtPassword.setText(getPassword());
		
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
	
}
