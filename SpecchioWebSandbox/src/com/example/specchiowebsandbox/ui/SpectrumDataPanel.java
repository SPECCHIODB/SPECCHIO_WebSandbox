package com.example.specchiowebsandbox.ui;

import specchio.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.invient.vaadin.charts.InvientCharts;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SpectrumDataPanel extends VerticalLayout{
	
	private Panel panel;
	
	private CheckBox cb;
	
	public SpectrumDataPanel(SpecchiowebsandboxApplication app, Spectrum spec, InvientCharts chart, SpectrumMetadata meta, boolean full_res){
		setSpacing(true);
		
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(1, 9);
        grid.setSpacing(false);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("550px");
		
		panel = new Panel("Spectrum Data Panel");
		panel.setHeight(620, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(false);
		layout.setSpacing(false);
//		addComponent(panel);
	
		
		TextField file_name = new TextField("Filename:");
		file_name.setValue(spec.file_name);
		file_name.setWidth("100%");
//		file_name_field.setReadOnly(true);
		grid.addComponent(file_name);
		grid.setComponentAlignment(file_name, Alignment.MIDDLE_LEFT);
		
		TextField capture_date = new TextField("Capture Date:");
		capture_date.setValue(meta.capture_date);
		capture_date.setWidth("100%");
		grid.addComponent(capture_date);
		grid.setComponentAlignment(capture_date, Alignment.MIDDLE_LEFT);
		
		TextField loading_date = new TextField("Loading Date:");
		loading_date.setValue(meta.loading_date);
		loading_date.setWidth("100%");
		grid.addComponent(loading_date);
		grid.setComponentAlignment(loading_date, Alignment.MIDDLE_LEFT);
		
		
		
		chart.setWidth("75%");
		grid.addComponent(chart);
		grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
		
		cb = new CheckBox("Show Full Resolution Spectrum");
        cb.setDescription("Enable/Disable Full Resolution of Spectrum. Resampled Spectrum as default!");
        cb.setImmediate(true);
        cb.addListener((ClickListener) app); // react to clicks
        cb.setValue(full_res);
        grid.addComponent(cb);
        grid.setComponentAlignment(cb, Alignment.MIDDLE_CENTER);
//		
//		Button b = new Button("Display Full Resolution Spectrum");
//        b.setDescription("Click to display full resolution spectrum");
//        b.addListener((ClickListener) specPlot); // react to clicks
//        grid.addComponent(b);
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
		
		
		
		
		
		
	}
	
	public CheckBox getCheckBox(){
		return cb;
	}

	
	

}
