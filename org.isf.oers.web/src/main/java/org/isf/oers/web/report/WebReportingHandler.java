package org.isf.oers.web.report;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.isf.commons.data.Column;
import org.isf.commons.data.Container;
import org.isf.commons.data.Tuple;
import org.isf.mdx.MDXContainerBuilder;
import org.isf.mdx.data.MDXColumn;
import org.isf.oers.io.ReportDefinitionReader;
import org.isf.oers.report.ReportDefinition;
import org.isf.oers.report.ReportType;
import org.isf.oers.web.util.JSFUtil;
import org.jboss.logging.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@SessionScoped
public class WebReportingHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1589146030400612668L;

	@Inject
	private Logger log;
	
	@Inject
	private MDXContainerBuilder mdxbuilder;
	
	private HashMap<String, String> cache;
	
	private TreeNode root;
	private TreeNode selectedNode;
	
	private Container container;
	
	protected void readDirectory(TreeNode parent, File dir) {
		if (!dir.exists() || !dir.isDirectory()) return;
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".xrpd");
			}
		});
		
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				if (f1 == null && f2 == null) return 0;
				else if (f1 == null && f2 != null) return -1;
				else if (f1 != null && f2 == null) return 1;
				return f1.getName().compareTo(f2.getName());
			}			
		});
		
		for (File file : files) {
//			TreeNode node = new DefaultTreeNode(file.getName(), parent);
			if (file.isDirectory()) {
				TreeNode node = new DefaultTreeNode("folder", new ReportDefinition(file.getAbsolutePath(), file.getName(), ReportType.FOLDER), parent);
				readDirectory(node, file);
			} else {
				try {
					ReportDefinitionReader reader = new ReportDefinitionReader(file);
					ReportDefinition rd = reader.readReportDefinition();
					new DefaultTreeNode(rd.getType().type(), rd, parent);
					
					if (rd.getType() == ReportType.MDX) cache.put("mdx."+rd.getId(), rd.getProperty("mdx"));
				} catch(IOException e) {
					log.error("Error while read report-definition '"+file.getAbsolutePath()+"'.", e);
				}
			}
		}
	}
	
	@PostConstruct
	public void initial() {
		cache = new HashMap<String, String>();
		
		root = new DefaultTreeNode("ROOT");
		if (System.getProperties().containsKey("oers.reporting.dir")) {
			File dir = new File(System.getProperty("oers.reporting.dir"));
			readDirectory(root, dir);	
		}
	}
	
	public TreeNode getRoot() { return root; }
	
	public TreeNode getSelectedNode() { return selectedNode; }
	public void setSelectedNode(TreeNode node) { this.selectedNode = node; }
	
	public ReportDefinition getSelectedReportDefinition() { 
		return (getSelectedNode() != null && getSelectedNode().getData() instanceof ReportDefinition) ? (ReportDefinition)getSelectedNode().getData() : null; 
	}
	
	public String getUIReportPath() {
		if (getSelectedNode() != null && getSelectedNode().getData() instanceof ReportDefinition) {
			ReportDefinition rd = (ReportDefinition)getSelectedNode().getData();
			if (rd.getType() != ReportType.FOLDER && rd.getType() != ReportType.UNKNOWN && rd.hasProperty("ui.include")) {
				return rd.getProperty("ui.include");
			}
		}
		return "/app/template/common/content.xhtml";
	}
	
	public void onSelectReport(NodeSelectEvent evt) {
		setSelectedNode(evt.getTreeNode());
		JSFUtil.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Report selected", evt.getTreeNode().getData().toString()));
	}
	
	public Container getContainer() { 
		if (getSelectedReportDefinition() == null) {
			setContainer(null);
		} else if (container == null || !container.getName().equals(getSelectedReportDefinition().getId())) {
			String key = "mdx."+getSelectedReportDefinition().getId();
			String query = cache.get(key);
			try {
				Container c = mdxbuilder.build(query);
				c.setName(getSelectedReportDefinition().getId());
				setContainer(c);
			} catch(Exception e) {
				setContainer(null);
				JSFUtil.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not execute query:", query));
				log.error("Error while executing MDXBuilder.", e);
			}
		} 
		return container;
	}
	public void setContainer(Container container) { this.container=container; }
	
	public List<Column> getColumns() { return getContainer().columns(); }
	public Column column(int index) { return getContainer().getColumn(index); }	
	
	public List<Tuple> getData() { return getContainer().list(); }
	
	public void expand(Object rowColumn) throws Exception {
		if (!(rowColumn instanceof MDXColumn)) return;
		MDXColumn col = (MDXColumn)rowColumn;
		setContainer(mdxbuilder.drill(col, true));
	}
	
}
