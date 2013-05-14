package com.example.specchiowebsandbox.ui;

import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;

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
	
	public SamplingGeometryPanel(Spectrum spec){
		
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
	
		
		createTextfield(grid, "Sensor zenith:", spec.getMetadata().get_entry("Sensor Zenith"));
		createTextfield(grid, "Illumination zenith:", spec.getMetadata().get_entry("Illumination Zenith"));
		createTextfield(grid, "Sensor azimuth:", spec.getMetadata().get_entry("Sensor Zenith"));
		createTextfield(grid, "Illumination azimuth:", spec.getMetadata().get_entry("Illumination Azimuth"));
		createTextfield(grid, "Sensor distance:", spec.getMetadata().get_entry("Sensor Distance"));
		
		
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
	}
	
	private void createTextfield(GridLayout grid, String title, MetaParameter meta){
		
		TextField text_field = new TextField(title);
		if(meta != null){
			text_field.setValue((Double)meta.getValue());
		}
		text_field.setWidth("100%");
		grid.addComponent(text_field);
		grid.setComponentAlignment(text_field, Alignment.MIDDLE_LEFT);
		
		
		
	}

}
