package com.example.specchiowebsandbox.ui;

import specchio.Spectrum;

import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SamplingGeometryPanel extends VerticalLayout{
	
	private Panel panel;
	
	public SamplingGeometryPanel(Spectrum spec, SpectrumMetadata meta){
		
		setSpacing(true);
		
		setHeight(220, Sizeable.UNITS_PIXELS);
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(2, 3);
        grid.setSpacing(true);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("150px");
		
		panel = new Panel("Sampling Geometry");
		panel.setHeight(220, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
//		addComponent(panel);
	
		
		createTextfield(grid, "Sensor zenith:", meta.sensor_zenith);
		createTextfield(grid, "Illumination zenith:", meta.illumination_zenith);
		createTextfield(grid, "Sensor azimuth:", meta.sensor_azimuth);
		createTextfield(grid, "Illumination azimuth:", meta.illumination_azimuth);
		createTextfield(grid, "Sensor distance:", meta.sensor_distance);
		
		
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
	}
	
	private void createTextfield(GridLayout grid, String title, Object value){
		
		TextField text_field = new TextField(title);
		text_field.setValue(value);
		text_field.setWidth("100%");
		grid.addComponent(text_field);
		grid.setComponentAlignment(text_field, Alignment.MIDDLE_LEFT);
		
		
		
	}

}
