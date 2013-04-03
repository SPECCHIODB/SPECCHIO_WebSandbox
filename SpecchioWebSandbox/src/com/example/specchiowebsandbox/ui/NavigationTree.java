package com.example.specchiowebsandbox.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import spectral_data_browser.SpectralDataBrowser;
import spectral_data_browser.hierarchy_node;

import com.example.specchiowebsandbox.*;
import com.example.specchiowebsandbox.data.CampaignNode;
import com.example.specchiowebsandbox.data.RadianceNode;
import com.example.specchiowebsandbox.data.ReflectanceNode;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

import eav_db.DatabaseConnection;

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

	public NavigationTree(SpecchiowebsandboxApplication app) {
		addItem(CAMPAIGNS);
		this.expandItem(CAMPAIGNS);
		

		setSelectable(true);
		setNullSelectionAllowed(false);
		setMultiSelect(true);
		setImmediate(true);

		try {

			SpectralDataBrowser browser = new SpectralDataBrowser(true);
			browser.build_tree();
			JTree tree = browser.tree;

			TreeModel model = tree.getModel();
			if (model != null) {
				Object root = model.getRoot();
				// This would display the database name... This is a hack to
				// display "Campaigns" instead of the db name...
				this.addItem(CAMPAIGNS);

//				System.out.println(root.toString());
				walk(model, root, CAMPAIGNS);
			} else {
				System.out.println("Tree is empty");
			}
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

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
			if (model.isLeaf(child)) {
//				System.out.println(child.toString());

			} else {
				this.addItem(child);
				this.setParent(child, camp_caption);
				this.setChildrenAllowed(child, true);
//				System.out.print(child.toString() + "--");
				walk(model, child);
			}
		}
	}
}
