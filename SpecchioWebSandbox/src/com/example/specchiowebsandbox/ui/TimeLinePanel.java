package com.example.specchiowebsandbox.ui;

import java.text.DecimalFormat;

import specchio.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.invient.vaadin.charts.InvientCharts;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TimeLinePanel extends VerticalLayout implements Property.ValueChangeListener{
	
	private Panel panel;
	
	final GridLayout grid;
	
	private static final String[] parameters = new String[] { "", "Wavelength", "CO2", "WDVI", "MODIS_NDVI" };
	
	final double[] wavelength;
	
	private SpecchiowebsandboxApplication application;
	
	private InvientCharts chart;
	
	private DecimalFormat df;

	
	public TimeLinePanel(SpecchiowebsandboxApplication app, Spectrum s, final double[] wvl){
		
		application = app;
		
		setSpacing(true);
		
		setHeight(700, Sizeable.UNITS_PIXELS);
		
		wavelength = wvl;
		
		df = new DecimalFormat("#0.00");
		
		
		// Create a grid layout
        grid = new GridLayout(1, 4);
        grid.setSpacing(false);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("600px");
		
		panel = new Panel("Time Line Panel");
		panel.setHeight(700, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		
//		setSpacing(true);
//        setWidth("100%");
		
		//Create DropDown Menu to select parameter to be visualized
		
		NativeSelect dropdown = new NativeSelect("Please select parameter to be visualized");
        for (int i = 0; i < parameters.length; i++) {
            dropdown.addItem(parameters[i]);
        }

        dropdown.setNullSelectionAllowed(false);
        dropdown.setValue("");
        dropdown.setImmediate(true);
        dropdown.addListener(this);

        grid.addComponent(dropdown);

		
		
		
        
        
        panel.addComponent(grid);
        
        addComponent(panel);

	}


	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
		System.out.println("WTF");
		
		
		if (event.getProperty().getValue().equals("Wavelength")){
			//Create Textfield to display selected Wavelength
			final TextField selected_wvl = new TextField("Selected Wavelength:");
			selected_wvl.setValue(wavelength[0]);
			selected_wvl.setWidth("100%");
			selected_wvl.setReadOnly(true);
			


	        final Slider slider = new Slider("Select the band number:");
	        slider.setWidth("100%");
	        slider.setMin(1);
	        slider.setMax(wavelength.length);
	        slider.setImmediate(true);
	        slider.addListener(new ValueChangeListener() {

	            public void valueChange(ValueChangeEvent ev) {
	            	Double index = (Double) ev.getProperty().getValue();
	            	int i = index.intValue() - 1;
	            	Double value = wavelength[i];
	            	selected_wvl.setReadOnly(false);
	                selected_wvl.setValue(df.format(value));
	                selected_wvl.setReadOnly(true);
	                grid.removeComponent(chart);
	                chart = application.getTimelinePlot("Wavelength", index.intValue());
	                chart.setWidth("75%");
	    			grid.addComponent(chart);
	    			grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
	                
	            }
	        });

	        grid.addComponent(slider);
	        
	        grid.addComponent(selected_wvl);
			grid.setComponentAlignment(selected_wvl, Alignment.MIDDLE_LEFT);
			
			Double slider_val = (Double)slider.getValue();
			
			
			chart = application.getTimelinePlot(event.getProperty().getValue().toString(), slider_val.intValue());
			
			chart.setWidth("75%");
			grid.addComponent(chart);
			grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
			
			
			
		}
		
		
	}
	

}
