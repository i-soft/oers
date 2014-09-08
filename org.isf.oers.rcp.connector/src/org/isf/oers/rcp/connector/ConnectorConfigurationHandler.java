package org.isf.oers.rcp.connector;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.isf.commons.mvc.IMasterDetailController;
import org.isf.commons.xml.XMLDocument;
import org.isf.rcp.commons.EclipseUtil;
import org.isf.rcp.commons.mvc.JFaceBindingProvider;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ConnectorConfigurationHandler extends JFaceBindingProvider<ConnectorConfiguration> implements IMasterDetailController<ConnectorConfiguration, ConnectorConfiguration> { 

	private static final String EXTENSION_CONNECTOR_DEFINITION_ID = "org.isf.oers.rcp.connector.connector";
	private static final String DEFAULT_CONFIGURATION_FILENAME = "connector-configurations.xml";
	
	private IExtensionRegistry registry;
	
	private ConnectorConfiguration current = null;
	private List<ConnectorConfiguration> configurations = new ArrayList<ConnectorConfiguration>();
	
	private boolean dirty = false;
	
	public ConnectorConfigurationHandler() {
		super();
		registry = Platform.getExtensionRegistry();
	}
	
	public IExtensionRegistry getExtensionRegistry() { return registry; }
	
	public List<ConnectorDefinition> listConnectorDefinitions() {
		ArrayList<ConnectorDefinition> list = new ArrayList<ConnectorDefinition>();
		IConfigurationElement[] elements = getExtensionRegistry().getConfigurationElementsFor(EXTENSION_CONNECTOR_DEFINITION_ID);
		for (IConfigurationElement elem : elements) {
			ConnectorDefinition cd = new ConnectorDefinition();
			
			cd.setId(elem.getAttribute("id"));
			cd.setName(elem.getAttribute("name"));
			cd.setDescription(elem.getAttribute("description"));
			cd.setSessionProvider(Boolean.valueOf(elem.getAttribute("sessionProvider")));
			
			list.add(cd);
		}
		
		Collections.sort(list, new Comparator<ConnectorDefinition>() {
			@Override
			public int compare(ConnectorDefinition cd1, ConnectorDefinition cd2) {
				if (cd1 == null && cd2 == null) return 0;
				else if (cd1 == null && cd2 != null) return -1;
				else if (cd1 != null && cd2 == null) return 1;
				
				return cd1.getName().compareTo(cd2.getName());
			}
		});
		
		return list;
	}
	
	public ConnectorDefinition getConnectorDefinition(String id) {
		IConfigurationElement[] elements = getExtensionRegistry().getConfigurationElementsFor(EXTENSION_CONNECTOR_DEFINITION_ID);
		for (IConfigurationElement elem : elements) 
			if (id.equals(elem.getAttribute("id"))) {
				ConnectorDefinition cd = new ConnectorDefinition();
				
				cd.setId(elem.getAttribute("id"));
				cd.setName(elem.getAttribute("name"));
				cd.setDescription(elem.getAttribute("description"));
				cd.setSessionProvider(Boolean.valueOf(elem.getAttribute("sessionProvider")));
			}
		return null;
	}
	
	public IConnector createConnectorInstance(String id) throws Exception {
		IConfigurationElement[] elements = getExtensionRegistry().getConfigurationElementsFor(EXTENSION_CONNECTOR_DEFINITION_ID);
		for (IConfigurationElement elem : elements) {
			if (elem.getAttribute("id").equals(id))
				return (IConnector)elem.createExecutableExtension("class");
		}
		throw new Exception("No corresponding class found for Connector '"+id+"'");
	}

	
	/*
	 * CONTROLLER METHODS
	 */
	
	@Override
	public ConnectorConfiguration getModel() {
		return current;
	}
	
	@Override
	public void setModel(ConnectorConfiguration configuration) {
		this.current = configuration;
	}

	@Override
	public ConnectorConfiguration create() throws Exception {
		try {
			ConnectorConfiguration cfg = new ConnectorConfiguration("Dummy");
			dirty = true;
			setModel(cfg);
			return getModel();
		} finally {
			refresh();
		}
	}

	@Override
	public void delete(ConnectorConfiguration arg0) throws Exception {
		remove(arg0);
		dirty = true;
	}
	
	public void delete() throws Exception {
		delete(getModel());
		setModel(null);
	}

	@Override
	public ConnectorConfiguration read(Object... arg0) throws Exception {
		if (arg0.length == 1 && arg0[0] instanceof String) {
			for (int i=0;i<size();i++)
				if (arg0[0].equals(get(i).getName()))
					return get(i);
		} 
		return null;
	}

	@Override
	public ConnectorConfiguration update(ConnectorConfiguration arg0) throws Exception {
		for (int i=0;i<size();i++)
			if (arg0.getName().equals(get(i).getName()))
				return set(i, arg0);
		add(arg0);
		return arg0;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	protected IFile getConfigurationFile(Object ... arg0) throws Exception {
		String filename = (arg0.length > 0 && arg0[0] instanceof String) ? (String)arg0[0] : DEFAULT_CONFIGURATION_FILENAME;
		IProject project = EclipseUtil.getWorkspaceRoot().getProject("connector");
		if (!project.exists()) project.create(null);
		if (!project.isOpen()) project.open(null);
		return project.getFile(filename);
	}
	
	@Override
	public void load(Object... arg0) throws Exception {
		IFile file = getConfigurationFile(arg0);
		if (file.exists()) {
			XMLDocument xdoc = new XMLDocument();
			xdoc.parseDocument(file.getContents());
			Element root = xdoc.getRoot();
			for (Node n : xdoc.getChildNodes(root, "connector")) {
				ConnectorConfiguration cfg = new ConnectorConfiguration();
				cfg.setName(xdoc.getAttribute(n, "name"));
				if (xdoc.nodeExists(n, "connector-id")) cfg.setConnectorId(xdoc.getTextValue(xdoc.getChildNode(n, "connector-id")));
				if (xdoc.nodeExists(n, "username")) cfg.setConnectorId(xdoc.getTextValue(xdoc.getChildNode(n, "username")));
				// TODO PROPERTIES
			}
		}
	}

	@Override
	public void refresh() throws Exception {
		super.refresh();
		// TODO ??? refresh
	}

	@Override
	public void reset() throws Exception {
		setModel(null);
	}
	
	@Override
	public void save(Object... arg0) throws Exception {
		// TODO implement list-logic
		if (getModel() != null) add(getModel());
		
		final XMLDocument xdoc = new XMLDocument();
		xdoc.createDocument("connectors");
		Element root = xdoc.getRoot();
		for (int i=0;i<size();i++) { 
			ConnectorConfiguration cfg = get(i);
			Node cfgn = xdoc.createNode(root, "connector");
			xdoc.setAttribute(cfgn, "name", cfg.getName());
//			Node cidn = xdoc.createNode(cfgn, "connector-id");
//			xdoc.setTextValue(cidn, cfg.getConnectorId());
//			Node cusrn = xdoc.createNode(cfgn, "username");
//			xdoc.setTextValue(cusrn, cfg.getUsername());
		}
		
		IFile file = getConfigurationFile(arg0);
		if (file.exists()) file.delete(true, null);
		
		File f = new File(file.getLocationURI());
		xdoc.saveDocument(f);
		
//		final PipedInputStream in = new PipedInputStream();
//		final PipedOutputStream out = new PipedOutputStream(in);		
//		try {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						xdoc.saveDocument(out);
//					} catch(Exception e) {
//						throw new RuntimeException("Could not save XML to PipedOutput", e);
//					}
//				}
//			}).start();
//			file.create(in, true, null);
//		} finally {
//			in.close();
//			out.close();
//		}
	}

	@Override
	public boolean add(ConnectorConfiguration arg0) {
		try {
			return configurations.add(arg0);
		} finally {
			dirty = true;
		}
	}

	@Override
	public void add(int arg0, ConnectorConfiguration arg1) {
		configurations.add(arg0, arg1);
		dirty = true;
	}

	@Override
	public boolean addAll(Collection<? extends ConnectorConfiguration> arg0) {
		try {
			return configurations.addAll(arg0);
		} finally {
			dirty = true;
		}
	}

	@Override
	public boolean addAll(int arg0,
			Collection<? extends ConnectorConfiguration> arg1) {
		try {
			return configurations.addAll(arg0, arg1);
		} finally {
			dirty = true;
		}
	}

	@Override
	public boolean contains(ConnectorConfiguration arg0) {
		return configurations.contains(arg0);
	}
 
	@Override
	public int[] dirtyIndexes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectorConfiguration get(int arg0) {
		return configurations.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return configurations.indexOf(arg0);
	}

	@Override
	public Iterator<ConnectorConfiguration> iterator() {
		return configurations.iterator();
	}

	@Override
	public ConnectorConfiguration remove(int arg0) {
		try {
			return configurations.remove(arg0);
		} finally {
			dirty = true;
		}
	}

	@Override
	public boolean remove(Object arg0) {
		try {
			return configurations.remove(arg0);
		} finally {
			dirty = true;
		}
	}

	@Override
	public ConnectorConfiguration[] select(int... arg0) {
		ConnectorConfiguration[] ret = new ConnectorConfiguration[arg0.length];
		for (int i=0;i<arg0.length;i++)
			ret[i] = (arg0[i] >= 0 && arg0[i] < size()) ? get(arg0[i]) : null;
		return ret;
	}

	@Override
	public ConnectorConfiguration set(int arg0, ConnectorConfiguration arg1) {
		try {
			return configurations.set(arg0, arg1);
		} finally {
			dirty = true;
		}
	}

	@Override
	public int size() {
		return configurations.size();
	}

	
}
