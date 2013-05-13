package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.util.Enumeration;

import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.gui.SpectralDataBrowser;
import ch.specchio.gui.SpectralDataBrowser.SpectralDataBrowserNode;
import ch.specchio.types.campaign_node;
import ch.specchio.types.spectral_node_object;

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
		
		SpectralDataBrowser browser = new SpectralDataBrowser(app.specchio_client, false);

		

		//SpectralDataBrowser browser = new SpectralDataBrowser(true);
		try {
			browser.build_tree();
		} catch (SPECCHIOClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 SpectralDataBrowserNode root = browser.root;

		Enumeration children = root.children();

		while (children.hasMoreElements()) {
			campaign_select.addItem(children.nextElement());
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
		tree.buildTree((SpectralDataBrowserNode) event.getProperty().getValue());

		addComponent(tree);

	}

}
