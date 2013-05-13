package com.example.specchiowebsandbox.ui;

import java.util.ArrayList;

import ch.specchio.spaces.SensorAndInstrumentSpace;
import ch.specchio.types.MetaDatatype;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GeneralDataPanel extends VerticalLayout{
	
	private Panel panel;
	
	public GeneralDataPanel(SensorAndInstrumentSpace space, Spectrum spec, SpectrumMetadata meta){
		
		setSpacing(true);
		
		setHeight(220, Sizeable.UNITS_PIXELS);
		
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(2, 3);
        grid.setSpacing(true);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("150px");
		
		panel = new Panel("General Data");
		panel.setHeight(220, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
//		addComponent(panel);
		
	
		
	
		
		TextField measurement_unit = new TextField("Measurement Unit:");
		measurement_unit.setValue(space.getMeasurementUnit().getUnitName());
		measurement_unit.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(measurement_unit);
		grid.setComponentAlignment(measurement_unit, Alignment.MIDDLE_LEFT);
		
		TextField sampling_env = new TextField("Sampling Environment:");
		sampling_env.setValue(meta.sampling_env);
		sampling_env.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(sampling_env);
		grid.setComponentAlignment(sampling_env, Alignment.MIDDLE_LEFT);
		
		TextField beam_geom = new TextField("Beam Geometry:");
		beam_geom.setValue(spec.getMeasurementType());
		beam_geom.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(beam_geom);
		grid.setComponentAlignment(beam_geom, Alignment.MIDDLE_LEFT);
		
		TextField illumination_source = new TextField("Illumination Source:");
		illumination_source.setValue(meta.illum_source);
		illumination_source.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(illumination_source);
		grid.setComponentAlignment(illumination_source, Alignment.MIDDLE_LEFT);
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
	}
		

}
