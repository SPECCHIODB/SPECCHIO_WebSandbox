package com.example.specchiowebsandbox.ui;

import specchio.Spectrum;

import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PositionPanel extends VerticalLayout{
	
	private Panel panel;
	
	public PositionPanel(Spectrum spec, SpectrumMetadata meta){
		
		setSpacing(true);
		
		setHeight(320, Sizeable.UNITS_PIXELS);
		
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(1, 4);
        grid.setSpacing(false);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("250px");
		
		panel = new Panel("Position");
		panel.setHeight(320, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
//		addComponent(panel);
	
		
		TextField latitude = new TextField("Latitude:");
		latitude.setValue(meta.latitude);
		latitude.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(latitude);
		grid.setComponentAlignment(latitude, Alignment.MIDDLE_LEFT);
		
		TextField longitude = new TextField("Longitude:");
		longitude.setValue(meta.longitude);
		longitude.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(longitude);
		grid.setComponentAlignment(longitude, Alignment.MIDDLE_LEFT);
		
		TextField altitude = new TextField("Altitude:");
		altitude.setValue(meta.altitude);
		altitude.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(altitude);
		grid.setComponentAlignment(altitude, Alignment.MIDDLE_LEFT);
		
		TextField location = new TextField("Location:");
		location.setValue(meta.location);
		location.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(location);
		grid.setComponentAlignment(location, Alignment.MIDDLE_LEFT);
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
	}

}
