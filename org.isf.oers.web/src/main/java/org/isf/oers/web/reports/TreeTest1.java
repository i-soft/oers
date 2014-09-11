package org.isf.oers.web.reports;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@RequestScoped
public class TreeTest1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6416673425432126849L;

	private TreeNode root;
	
	private TreeNode selected;
	
	@PostConstruct
	public void initial() {
		root = new DefaultTreeNode("ROOT");
		TreeNode child = new DefaultTreeNode("Child-1", root);
		TreeNode dummy = new DefaultTreeNode("DUMMY", child);
		child = new DefaultTreeNode("Child-2", root);
		dummy = new DefaultTreeNode("DUMMY", child);
		child = new DefaultTreeNode("Child-3", root);
		dummy = new DefaultTreeNode("DUMMY", child);
	}
	
	public TreeNode getRoot() { return root; }
	
	public TreeNode getSelected() { return selected; }
	public void setSelected(TreeNode selected) { this.selected = selected; }
	
	public void onNodeExpand(NodeExpandEvent event) {
		TreeNode node = event.getTreeNode();
		System.out.println("Expand triggered for "+node);
		if (node.getChildCount() == 1 && node.getChildren().get(0).toString().equals("DUMMY")) {
			node.getChildren().remove(0);
			TreeNode dynaChild = new DefaultTreeNode("DynaChild-1", node);
			dynaChild = new DefaultTreeNode("DynaChild-2", node);
			dynaChild = new DefaultTreeNode("DynaChild-2", node);
		}
	}
	
}
