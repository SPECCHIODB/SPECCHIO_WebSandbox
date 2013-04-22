package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.util.Enumeration;

import spectral_data_browser.SpectralDataBrowser;
import spectral_data_browser.campaign_node;
import spectral_data_browser.spectral_node_object;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class SideBar extends VerticalLayout implements ValueChangeListener {

	private NativeSelect campaign_select = null;

	private SpecchiowebsandboxApplication app;

	private NavigationTree tree = null;

	public SideBar(SpecchiowebsandboxApplication app) {

		this.app = app;

		setWidth("100%");

		setSpacing(true);

		setMargin(false);

		campaign_select = new NativeSelect("Please select a campaign");

		campaign_select.setImmediate(true);

		campaign_select.addListener(this);

		SpectralDataBrowser browser = new SpectralDataBrowser(true);
		try {
			browser.build_tree();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		spectral_node_object root = browser.root;

		Enumeration children = root.children();

		while (children.hasMoreElements()) {
			campaign_select.addItem((campaign_node) children.nextElement());
		}

		addComponent(campaign_select);

		int test = 1;

	}

	public NavigationTree getNavTree() {
		return tree;
	}

	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub

		if (tree != null) {
			removeComponent(tree);
		}

		tree = new NavigationTree(app);
		tree.buildTree((campaign_node) event.getProperty().getValue());

		addComponent(tree);

	}

}
