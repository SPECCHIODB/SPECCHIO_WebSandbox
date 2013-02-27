package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ListIterator;

import spaces.SensorAndInstrumentSpace;
import spaces.Space;
import specchio.SpaceFactory;
import specchio.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.invient.vaadin.charts.InvientCharts;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class DataExplorationPanel extends VerticalLayout implements
		Property.ValueChangeListener {

	private Panel panel;

	private static String[] parameters = new String[] {"Reflectance" };

	private SpecchiowebsandboxApplication application;

	private InvientCharts chart;

	private GridLayout grid;
	
	private GridLayout chart_grid;

	private double[] wvl;
	
	private double[][] vectors;
	
	private Object[] selected_items;
	
	private Double slider1_val;
	
	private Double slider2_val;
	
	DecimalFormat df;
	

	public DataExplorationPanel(SpecchiowebsandboxApplication app, Spectrum spec, Object[] selection) {
		
		application = app;
		
		df = new DecimalFormat("#0.00");
		
		selected_items = selection;
		
		setSpacing(false);

		// Create a grid layout
		grid = new GridLayout(2, 5);
		grid.setSpacing(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("100%");
		grid.setHeight("100px");

		panel = new Panel("Data Exploration Panel");
		panel.setHeight(700, Sizeable.UNITS_PIXELS);

		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
		
		
		// Create DropDown Menu to select parameter to be visualized

		final NativeSelect dropdown1 = new NativeSelect(
				"Please select first parameter (X-Axis)");
		for (int i = 0; i < parameters.length; i++) {
			dropdown1.addItem(parameters[i]);
		}

		dropdown1.setNullSelectionAllowed(true);
		dropdown1.setValue(null);
		dropdown1.setImmediate(true);
		dropdown1.addListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
				if (event.getProperty().getValue().equals("Reflectance")){
					
					getData("Reflectance");
				
					//Create Textfield to display selected Wavelength
					
					final TextField selected_wvl = new TextField("Selected Wavelength:");
					selected_wvl.setValue(wvl[0]);
					selected_wvl.setWidth("100%");
					selected_wvl.setReadOnly(true);
					
					
					final Slider slider1 = new Slider("Select the band number:");
			        slider1.setWidth("75%");
			        slider1.setMin(1);
			        slider1.setMax(wvl.length);
			        slider1.setImmediate(true);
			        slider1.addListener(new ValueChangeListener() {

			            public void valueChange(ValueChangeEvent ev) {
			            	Double index = (Double) ev.getProperty().getValue();
			            	int i = index.intValue() - 1;
			            	Double value = wvl[i];
			            	selected_wvl.setReadOnly(false);
			                selected_wvl.setValue(df.format(value));
			                selected_wvl.setReadOnly(true);
			                slider1_val = (Double)slider1.getValue();
			                
			            }
			        });

			        int test = grid.getRows();
			        if(grid.getRows() < 2){
			        	 grid.insertRow(1);
			        }
			        grid.addComponent(slider1, 0, 1);
			        
			        if(grid.getRows() < 3){
			        	grid.insertRow(2);
			        }
			        grid.addComponent(selected_wvl, 0, 2);
					grid.setComponentAlignment(selected_wvl, Alignment.TOP_LEFT);
					
					grid.setHeight("200px");
					slider1_val = (Double)slider1.getValue();


			}
			}
			
		});
		
		grid.addComponent(dropdown1);
		
		final NativeSelect dropdown2 = new NativeSelect("Please select second parameter (Y-Axis)");
		for(int i = 0; i < parameters.length; i++){
			dropdown2.addItem(parameters[i]);
		}
		
		dropdown2.setNullSelectionAllowed(true);
		dropdown2.setValue(null);
		dropdown2.setImmediate(true);
		dropdown2.addListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
				if (event.getProperty().getValue().equals("Reflectance")){
					
					getData("Reflectance");
				
					//Create Textfield to display selected Wavelength
					
					final TextField selected_wvl = new TextField("Selected Wavelength:");
					selected_wvl.setValue(wvl[0]);
					selected_wvl.setWidth("100%");
					selected_wvl.setReadOnly(true);
					
					
					final Slider slider2 = new Slider("Select the band number:");
			        slider2.setWidth("75%");
			        slider2.setMin(1);
			        slider2.setMax(wvl.length);
			        slider2.setImmediate(true);
			        slider2.addListener(new ValueChangeListener() {

			            public void valueChange(ValueChangeEvent ev) {
			            	Double index = (Double) ev.getProperty().getValue();
			            	int i = index.intValue() - 1;
			            	Double value = wvl[i];
			            	
			            	selected_wvl.setReadOnly(false);
			                selected_wvl.setValue(df.format(value));
			                selected_wvl.setReadOnly(true);
			                slider2_val = (Double) slider2.getValue();
			                
			            }
			        });

			        
			        if(grid.getRows() < 2){
			        	 grid.insertRow(1);
			        }
			        grid.addComponent(slider2, 1, 1);
			        
			        if(grid.getRows() < 3){
			        	grid.insertRow(2);
			        }
			        grid.addComponent(selected_wvl, 1, 2);
					grid.setComponentAlignment(selected_wvl, Alignment.TOP_LEFT);
					
					grid.setHeight("200px");
					
					slider2_val = (Double)slider2.getValue();


			}
			}
			
		});
		
		grid.addComponent(dropdown2);
		
		Button b = new Button("Generate Scatter Plot");
        b.setDescription("Click to visualize selected Parameters");
        b.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (chart != null){
					chart_grid.removeComponent(chart);
					panel.removeComponent(chart_grid);
				}
				
				chart_grid = new GridLayout(1, 1);
				chart_grid.setSpacing(false);
				chart_grid.setSizeFull();

				ScatterPlot scatter = new ScatterPlot();
				scatter.generatePlot(dropdown1.getValue().toString(), dropdown2.getValue().toString(), slider1_val.intValue(), slider2_val.intValue(), wvl, vectors);
				chart = scatter.getChart();
//				grid.setHeight("650px");
//				grid.addComponent(chart, 0, 4);
				
				chart.setWidth("75%");
				chart_grid.addComponent(chart);
				chart_grid.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
				panel.addComponent(chart_grid);
				
			}
		});
//        grid.insertRow(grid.getRows()-1);
        grid.addComponent(b, 0, 3);

		
		
		panel.addComponent(grid);

		addComponent(panel);
	}
	
	public void getData(String parameter){
		
		if (parameter.equalsIgnoreCase("Reflectance")){
			
			ArrayList<SpectrumData> itemIds = new ArrayList<SpectrumData>();
			ArrayList<Spectrum> spectra = new ArrayList<Spectrum>();
			ArrayList<SpectrumMetadata> metadata = new ArrayList<SpectrumMetadata>();
			
			for(int i = 0; i < selected_items.length; i++){
				itemIds.add((SpectrumData)selected_items[i]);
				try {
					spectra.add(new Spectrum(itemIds.get(i).getSpectrum_id()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SpectrumMetadata meta = new SpectrumMetadata(spectra.get(i));
				meta.fillMetadata();
				metadata.add(meta);
			}
			
			SpaceFactory sf = SpaceFactory.getInstance();
			
			
			
			ArrayList<Space> spaces = null;
			ArrayList<Integer> ids = new ArrayList<Integer>();
//			ArrayList<double[][]> vectors = new ArrayList<double[][]>();
			ArrayList<Double> data = new ArrayList<Double>();
			
			ListIterator<Spectrum> li = spectra.listIterator();
			
			while (li.hasNext()){
				Spectrum spectrum = li.next();
				ids.add(spectrum.spectrum_id);
			}
			
			spaces = sf.create_spaces(ids);
			
			ListIterator<Space> spaces_li = spaces.listIterator();
			
//			while (spaces_li.hasNext()){
//				spaces_li.next().load_data();
//				
//			}
//			
//			for(int i = 0; i < spaces.size(); i++){
//				vectors.add(spaces.get(i).get_array());
//				data.add(vectors.get(i)[i]);
//			}
			
			spaces.Space space = spaces_li.next();
			
			space.load_data();
			
			vectors = space.get_array();
			
			SensorAndInstrumentSpace instr_space = (SensorAndInstrumentSpace) space;

			wvl = instr_space.get_wvls();
			
			
		}
	}

	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}

}
