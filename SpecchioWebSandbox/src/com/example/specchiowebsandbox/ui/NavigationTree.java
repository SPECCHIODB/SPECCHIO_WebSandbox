package com.example.specchiowebsandbox.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import ch.specchio.gui.SpectralDataBrowser;
import ch.specchio.gui.SpectralDataBrowser.SpectralDataBrowserNode;
import ch.specchio.types.hierarchy_node;
import ch.specchio.types.spectral_node_object;

import com.example.specchiowebsandbox.*;
import com.example.specchiowebsandbox.data.CampaignNode;
import com.example.specchiowebsandbox.data.RadianceNode;
import com.example.specchiowebsandbox.data.ReflectanceNode;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;


public class NavigationTree extends Tree {
	public static final Object CAMPAIGNS = "Campaigns";
	public static final Object SUB_DIR = "Sub Directory";
	public static final Object RADIANCES = "Radiances";
	public static final Object REFLECTANCES = "Reflectances";
	public static final Object TARGETS = "Targets";
	public static final Object REFERENCES = "References";

	private ArrayList<String> campaign_names = new ArrayList<String>();
	private ArrayList<Long> campaign_ids = new ArrayList<Long>();
	private ArrayList<Long> spectrum_ids = new ArrayList<Long>();
	private ArrayList<String> file_name = new ArrayList<String>();
	// private ArrayList<SpectrumData> spectrum_data = new
	// ArrayList<SpectrumData>();
	private ArrayList<CampaignNode> campaign_data = new ArrayList<CampaignNode>();

	private int hyrarchi_id = 0;

	private Connection conn = null;
	
	private SpecchiowebsandboxApplication app;

	public NavigationTree(SpecchiowebsandboxApplication app) {
		this.app = app;
//		addItem(CAMPAIGNS);
//		this.expandItem(CAMPAIGNS);
		

		setSelectable(true);
		setNullSelectionAllowed(false);
		setMultiSelect(true);
		setImmediate(true);
		
		addListener((ValueChangeListener) app);

		

	}

	protected void walk(TreeModel model, Object obj) {
		int cc;
		cc = model.getChildCount(obj);
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(obj, i);
			if (model.isLeaf(child)) {
				this.addItem(child);
				this.setParent(child, obj);
				this.setChildrenAllowed(child, false);
//				System.out.println(child.toString());

			} else {
				this.addItem(child);
				this.setParent(child, obj);
				this.setChildrenAllowed(child, true);
//				System.out.print(child.toString() + "--");
				walk(model, child);
			}
		}
	}

	protected void walk(TreeModel model, Object obj, Object camp_caption) {
		int cc;
		cc = model.getChildCount(obj);
		for (int i = 0; i < cc; i++) {
			Object child = model.getChild(obj, i);
			walk(model,child);
		}
	}
	
	public void buildTree(SpectralDataBrowserNode campaign){
		try {

			SpectralDataBrowser browser = new SpectralDataBrowser(app.specchio_client, false);
			browser.build_tree(campaign.getNodeId());
			JTree tree = browser.tree;

			TreeModel model = tree.getModel();
			if (model != null) {
				Object root = model.getRoot();
				// This would display the database name... This is a hack to
				// display "Campaigns" instead of the db name...
//				this.addItem(CAMPAIGNS);

//				System.out.println(root.toString());
				
//				this.addItem(root);
//				this.expandItem(root);
//				this.setChildrenAllowed(root, true);
				int cc;
				cc = model.getChildCount(root);
				for (int i = 0; i < cc; i++) {
					Object child = model.getChild(root, i);
					this.addItem(child);
					this.setParent(child, root);
					this.expandItem(child);
					this.setChildrenAllowed(child, true);
					walk(model,child);
				}
				
			} else {
				System.out.println("Tree is empty");
			}
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			app.showNotification(e.getMessage());
		}
		

		
	}
}
