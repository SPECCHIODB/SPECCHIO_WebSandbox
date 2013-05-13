package com.example.specchiowebsandbox.ui;

import java.util.ArrayList;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.types.ConflictTable;
import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.EAV_Attribute;
import com.example.specchiowebsandbox.data.EAV_DataAccess;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class EAVDataPanel extends VerticalLayout {

	private Panel panel;

	public EAVDataPanel(Spectrum s, ConflictTable conflicts, SpecchiowebsandboxApplication app) {
		
		setHeight(620, Sizeable.UNITS_PIXELS);
		
		panel = new Panel("EAV Metadata Table");
		panel.setHeight(620, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(false);
		layout.setSpacing(false);

		Table table = new Table(null);
		// set a style name, so we can style rows and cells
		table.setStyleName("iso3166");
		//
		// // size
		table.setWidth("100%");
		table.setHeight("550");
		// setSizeFull();

		BeanItemContainer<EAV_Attribute> dataSource = EAV_DataAccess
				.createEAVDataContainer(s, conflicts, app);

		table.setContainerDataSource(dataSource);

		table.setVisibleColumns(EAV_DataAccess.NATURAL_COL_ORDER);
		table.setColumnHeaders(EAV_DataAccess.COL_HEADERS);
		
		table.setSelectable(true);
		table.setImmediate(true);
		table.setColumnCollapsingAllowed(true);
		table.setColumnReorderingAllowed(true);
		
		
		
		panel.addComponent(table);
		
		addComponent(panel);


		// // set a style name, so we can style rows and cells
		// table.setStyleName("iso3166");
		//
		// // size
		// table.setWidth("100%");
		// table.setHeight("170px");
		//
		// EAV_DataAccess dataSource =
		// EAV_DataAccess.createEAVDataContainer(spec_id);
		//
		// table.setContainerDataSource(dataSource);
		//
		// table.setVisibleColumns(EAV_DataAccess.NATURAL_COL_ORDER);
		//
		// table.setColumnHeaders(EAV_DataAccess.COL_HEADERS);

		// panel.addComponent(table);
		//
		// addComponent(panel);

	}

}
