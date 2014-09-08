package org.isf.oers.rcp.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.isf.commons.mvc.DataBinding;
import org.isf.oers.images.ImageResource;
import org.isf.oers.images.ImageSize;
import org.isf.oers.rcp.connector.ConnectorConfigurationHandler;
import org.isf.oers.rcp.connector.ConnectorDefinition;
import org.isf.rcp.commons.mvc.rule.SelectionRule;
import org.isf.rcp.commons.mvc.rule.TextModifyRule;

public class ConnectorConfigurationDialog extends TitleAreaDialog {

	private SashForm form;
	
	private Table tblConnections;
	
	private CTabFolder folder;
	
	@DataBinding(modelAttribute="name",rule=TextModifyRule.class)
	public Text txtName;
	
	@DataBinding(modelAttribute="connectorId", rule=SelectionRule.class)
	public Combo coConnector;
	@DataBinding(modelAttribute="url", rule=TextModifyRule.class)
	public Text txtConnectionURL;
	
	@DataBinding(modelAttribute="username", rule=TextModifyRule.class)
	public Text txtUsername;
	@DataBinding(modelAttribute="password", rule=TextModifyRule.class)
	public Text txtPassword;
	@DataBinding(modelAttribute="savePassword", rule=SelectionRule.class)
	public Button cbSavePassword;
	
//	private Table tblProperties;
	
	private ConnectorConfigurationHandler handler;
	
	public ConnectorConfigurationDialog(Shell shell) {
		super(shell);		
	}
	
	public ConnectorConfigurationHandler getHandler() { return handler; }
	public void setHandler(ConnectorConfigurationHandler handler) { this.handler = handler; }
	
	@Override
	public void create() {
		super.create();
		setTitle("Connector Configuration");
		setMessage("Define your Connector Configuration here.", IMessageProvider.INFORMATION);
		
		getHandler().adaptView(this);
		if (getHandler().getModel() == null) 
			try {
				getHandler().create();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite)super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		
		form = new SashForm(container, SWT.HORIZONTAL);
		form.setLayout(new FillLayout());
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Left
		Composite left = new Composite(form, SWT.NONE);
		left.setLayout(new FillLayout());
		
		tblConnections = new Table(left, SWT.SINGLE | SWT.BORDER);
		
		// Right
		Composite right = new Composite(form, SWT.NONE);
		right.setLayout(new FillLayout());
		
		folder = new CTabFolder(right, SWT.BORDER | SWT.TOP);
		folder.setTabHeight(32);
		
		CTabItem conf = new CTabItem(folder, SWT.NONE);
		conf.setText("Connection");
		conf.setImage(ImageResource.CONNECTOR_CONFIGURATION.getImage(ImageSize._16x16));
		Composite conconf = new Composite(folder, SWT.NONE);
		conconf.setLayout(new GridLayout(2, false));
		conf.setControl(conconf);
		
		Label lbName = new Label(conconf, SWT.NONE);
		lbName.setText("Connection-Name");
		txtName = new Text(conconf, SWT.SINGLE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lbConnector = new Label(conconf, SWT.NONE);
		lbConnector.setText("Connector:");
		coConnector = new Combo(conconf, SWT.READ_ONLY | SWT.FLAT | SWT.BORDER);
		coConnector.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		coConnector.add("");
		for (ConnectorDefinition def : getHandler().listConnectorDefinitions()) {
			coConnector.add(def.getName());
			coConnector.setData(def.getName(), def.getId());
		}
		coConnector.select(0);
		
		Label lbConnectionUrl = new Label(conconf, SWT.NONE);
		lbConnectionUrl.setText("URL:");
		txtConnectionURL = new Text(conconf, SWT.SINGLE);
		txtConnectionURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lbUsername = new Label(conconf, SWT.NONE);
		lbUsername.setText("Username:");
		txtUsername = new Text(conconf, SWT.SINGLE);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lbPassword = new Label(conconf, SWT.NONE);
		lbPassword.setText("Password:");
		txtPassword = new Text(conconf, SWT.SINGLE | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lbSavePassword = new Label(conconf, SWT.NONE);
		lbSavePassword.setText("Save Password:");
		cbSavePassword = new Button(conconf, SWT.CHECK);
		cbSavePassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
//		CTabItem props = new CTabItem(folder, SWT.NONE);
//		props.setText("Properties");
//		props.setImage(ImageResource.SESSION_PROPERTIES.getImage(ImageSize._16x16));
//		tblProperties = new Table(folder, SWT.SINGLE | SWT.BORDER);
//		props.setControl(tblProperties);		
		
		folder.setSelection(conf);
		
		form.setWeights(new int[]{35,65});
		
		return area;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(800,480);
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Close", true);
		createButton(parent, 8192, "Delete", false);
		createButton(parent, 4096, "Save", false);
		createButton(parent, 2048, "Test", false);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		switch(buttonId) {
		case 8192: System.out.println("delete Entry"); break;
		case 4096: 
			System.out.println("save Entry");
			try {
				getHandler().save();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 2048: System.out.println("test Entry"); break;
		default: super.buttonPressed(buttonId);
		}
	}	
	
}
