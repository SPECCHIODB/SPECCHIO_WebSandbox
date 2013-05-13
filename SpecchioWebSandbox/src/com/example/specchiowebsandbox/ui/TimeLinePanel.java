package com.example.specchiowebsandbox.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.EAV_Attribute;
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
	
	private static ArrayList<String> parameters;
	
	final double[] wavelength;
	
	private SpecchiowebsandboxApplication application;
	
	private InvientCharts chart = null;
	
	private Slider slider = null;
	
	private TextField selected_wvl = null;
	
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
		
//		getParametersList(s);
		
		NativeSelect dropdown = new NativeSelect("Please select parameter to be visualized");
		
		//add Reflectance
		dropdown.addItem("Reflectance");
		
        for (EAV_Attribute attr : app.eav_attributes) {
            dropdown.addItem(attr.getAttrName());
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
		
		if(chart != null){
			grid.removeComponent(chart);
			
		}
		
		
		if (event.getProperty().getValue().equals("Reflectance")){
			//Create Textfield to display selected Wavelength
			selected_wvl = new TextField("Selected Wavelength:");
			selected_wvl.setValue(wavelength[0]);
			selected_wvl.setWidth("100%");
			selected_wvl.setReadOnly(true);
			


	        slider = new Slider("Select the band number:");
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
	                chart = application.getTimelinePlot("Reflectance", index.intValue());
	                chart.setWidth("75%");
	    			grid.addComponent(chart, 0,3);
	    			grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
	                
	            }
	        });

	        grid.addComponent(slider, 0, 1);
	        
	        grid.addComponent(selected_wvl, 0, 2);
			grid.setComponentAlignment(selected_wvl, Alignment.MIDDLE_LEFT);
			
			Double slider_val = (Double)slider.getValue();
			
			
			chart = application.getTimelinePlot(event.getProperty().getValue().toString(), slider_val.intValue());
			
			chart.setWidth("75%");
			grid.addComponent(chart,0,3);
			grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
			
			
			
		} else {
			chart = application.getTimelinePlot(event.getProperty().getValue().toString(), 0);
			
			if(slider != null){
				grid.removeComponent(slider);
				slider = null;
				grid.removeComponent(selected_wvl);
				selected_wvl = null;
			}
			
			chart.setWidth("75%");
			grid.addComponent(chart, 0,1);
			grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
		}
		
		
	}
	
	public void getParametersList(Spectrum spec){
//		EAVDBServices eav_db_service = EAVDBServices.getInstance();
//		
//		ArrayList<Integer> spec_ids = new ArrayList<Integer>();
//		spec_ids.add(spec.spectrum_id);
//		
//		eav_db_service.set_primary_x_eav_tablename("spectrum_x_eav","spectrum_id", "spectrum");
//		
//		Attributes attributes = Attributes.getInstance();
//		ArrayList<attribute> attr = attributes.get_attributes("system");
//		
//		parameters = new ArrayList<String>();
//		
//		parameters.add("Reflectance");
//		
//		for(int i = 1; i< attr.size(); i++){
//			
//			if( attr.get(i-1).get_default_storage_field() != null){
//				parameters.add(attr.get(i-1).getName());
//			}
//		}
//		
		
	}
	

}
