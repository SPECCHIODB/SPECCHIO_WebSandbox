package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import ch.specchio.spaces.SensorAndInstrumentSpace;
import ch.specchio.spaces.Space;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;
import ch.specchio.types.spectral_node_object;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.EAV_Attribute;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;



public class DataExplorationPanel extends VerticalLayout implements
		Property.ValueChangeListener {

	private Panel panel;

	private static ArrayList<String> parameters;

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
	
	private double[] param1_values;
	private double[] param2_values;
	
	final NativeSelect dropdown1 = new NativeSelect(
			"Please select first parameter (X-Axis)");
	final NativeSelect dropdown2 = new NativeSelect("Please select second parameter (Y-Axis)");
	Button gen_plot_button = new Button("Generate Scatter Plot");
	
	final Slider slider1 = new Slider("Select the band number:");
	final TextField selected_wvl = new TextField("Selected Wavelength:");
	
	final TextField selected_wvl2 = new TextField("Selected Wavelength:");
	final Slider slider2 = new Slider("Select the band number:");
	
	private SensorAndInstrumentSpace space;

	public DataExplorationPanel(SpecchiowebsandboxApplication app, SensorAndInstrumentSpace space, Spectrum spec, Object[] selection) {
		
		application = app;
		
		this.space = space;
		
		
		df = new DecimalFormat("#0.00");
		
		selected_items = selection;
		
		setSpacing(false);
		
		setHeight(700, Sizeable.UNITS_PIXELS);

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
		
//		getParametersList(spec);

		dropdown1.addItem("Reflectance");
		for (EAV_Attribute attr : application.eav_attributes) {
			dropdown1.addItem(attr.getAttrName());
		}

		dropdown1.setNullSelectionAllowed(true);
		dropdown1.setValue(null);
		dropdown1.setImmediate(true);
		dropdown1.addListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
//				slider1.setVisible(false);
				
				
				if (event.getProperty().getValue().equals("Reflectance")){
					
					
					
					
					getData("Reflectance", null);
				
					//Create Textfield to display selected Wavelength
					
					
					selected_wvl.setReadOnly(false);
					selected_wvl.setValue(wvl[0]);
					selected_wvl.setWidth("100%");
					selected_wvl.setReadOnly(true);
					selected_wvl.setVisible(true);
					
					
					
			        slider1.setVisible(true);
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


			} else {
//				Iterator<Component> grid_it = grid.getComponentIterator();
//				while(grid_it.hasNext()){
//					Component comp = grid_it.next();
//					String caption = comp.getCaption();
//					if(caption.equals("Select the band number:")){
//						grid.removeComponent(comp);
//					}
//				}
				
				if(slider1.isVisible()){
					slider1.setVisible(false);
					selected_wvl.setVisible(false);
					
					grid.removeAllComponents();
					
					grid.addComponent(dropdown1);
					grid .addComponent(dropdown2);
					
					if(dropdown2.getValue() != null && dropdown2.getValue().equals("Reflectance")){
						grid.addComponent(slider2,1,1);
						grid.addComponent(selected_wvl2,1,2);
					}
					
					grid.addComponent(gen_plot_button,0,3);
					
				}
				getData(event.getProperty().getValue().toString(), "param1");
			}
			}
			
		});
		
		grid.addComponent(dropdown1);
		
		dropdown2.addItem("Reflectance");
		for(EAV_Attribute attr : application.eav_attributes){
			dropdown2.addItem(attr.getAttrName());
		}
		
		dropdown2.setNullSelectionAllowed(true);
		dropdown2.setValue(null);
		dropdown2.setImmediate(true);
		dropdown2.addListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event) {
				
				
				
				
				
				
				if (event.getProperty().getValue().equals("Reflectance")){
					
					getData("Reflectance", null);
				
					//Create Textfield to display selected Wavelength
					
					
					selected_wvl2.setReadOnly(false);
					selected_wvl2.setValue(wvl[0]);
					selected_wvl2.setWidth("100%");
					selected_wvl2.setReadOnly(true);
					selected_wvl2.setVisible(true);
					
					
					slider2.setVisible(true);
			        slider2.setWidth("75%");
			        slider2.setMin(1);
			        slider2.setMax(wvl.length);
			        slider2.setImmediate(true);
			        slider2.addListener(new ValueChangeListener() {

			            public void valueChange(ValueChangeEvent ev) {
			            	Double index = (Double) ev.getProperty().getValue();
			            	int i = index.intValue() - 1;
			            	Double value = wvl[i];
			            	
			            	selected_wvl2.setReadOnly(false);
			                selected_wvl2.setValue(df.format(value));
			                selected_wvl2.setReadOnly(true);
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
			        grid.addComponent(selected_wvl2, 1, 2);
					grid.setComponentAlignment(selected_wvl2, Alignment.TOP_LEFT);
					
					grid.setHeight("200px");
					
					slider2_val = (Double)slider2.getValue();
				

			} else {
//				Iterator<Component> grid_it = grid.getComponentIterator();
//				while(grid_it.hasNext()){
//					Component comp = grid_it.next();
//					String caption = comp.getCaption();
//					if(caption.equals("Select the band number:")){
//						grid.removeComponent(comp);
//					}
//				}
				
				if(slider2.isVisible()){
					slider2.setVisible(false);
					selected_wvl2.setVisible(false);
					
					grid.removeAllComponents();
					
					grid.addComponent(dropdown1);
					grid.addComponent(dropdown2);
					
					if(dropdown1.getValue() != null && dropdown1.getValue().equals("Reflectance")){
						grid.addComponent(slider1,0,1);
						grid.addComponent(selected_wvl,0,2);
					}
					
					grid.addComponent(gen_plot_button,0,3);
					
				}
				getData(event.getProperty().getValue().toString(), "param2");
			}
			}
			
		});
		
		grid.addComponent(dropdown2);
		
		
		gen_plot_button.setDescription("Click to visualize selected Parameters");
		gen_plot_button.addListener(new Button.ClickListener() {
			
			

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
				if(dropdown1.getValue().toString().equals("Reflectance") && dropdown2.getValue().toString().equals("Reflectance")){
					scatter.generatePlot(dropdown1.getValue().toString(), dropdown2.getValue().toString(), slider1_val.intValue(), slider2_val.intValue(), wvl, vectors);
				} else if(dropdown1.getValue().toString().equals("Reflectance") && !dropdown2.getValue().toString().equals("Reflectance")){
					scatter.generatePlot("Reflectance", dropdown2.getValue().toString(), slider1_val.intValue(), wvl, vectors, param2_values);
				} else if(!dropdown1.getValue().toString().equals("Reflectance") && dropdown2.getValue().toString().equals("Reflectance")){
					scatter.generatePlot(dropdown1.getValue().toString(), "Reflectance", slider2_val.intValue(), wvl, vectors, param1_values);
				} else{
					scatter.generatePlot(dropdown1.getValue().toString(), dropdown2.getValue().toString(), param1_values, param2_values);
				}
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
        grid.addComponent(gen_plot_button, 0, 3);

		
		
		panel.addComponent(grid);
		
	

		addComponent(panel);
	}
	
	public void getData(String parameter, String param1orparam2){
		
		if (parameter.equalsIgnoreCase("Reflectance")){
			
			
			
			vectors = space.getVectorsAsArray();
			wvl = space.getAverageWavelengths();
				
			
			
		} else {
			
			int index = 0;
			
			for(Spectrum spec:application.spectra){
				
				if(param1orparam2.equals("param1")){
					if(param1_values==null){
						param1_values = new double[selected_items.length];
					}
					MetaParameter entry = spec.getMetadata().get_entry(parameter);
					//only double values are supported for now...
					if(entry.getDefaultStorageField().equals("double_val")){
						
						param1_values[index]=(Double)entry.getValue();
						index++;
					}else if(entry.getDefaultStorageField().equals("int_val")){
						param1_values[index]=Double.parseDouble(Integer.toString((Integer)entry.getValue()));
						index++;
					}
				}else if(param1orparam2.equals("param2")){
					if(param2_values==null){
						param2_values = new double[selected_items.length];
					}
					
					MetaParameter entry = spec.getMetadata().get_entry(parameter);
					if(entry.getDefaultStorageField().equals("double_val")){
						param2_values[index]=(Double)entry.getValue();
						index++;
					}else if(entry.getDefaultStorageField().equals("int_val")){
						param2_values[index]=Double.parseDouble(Integer.toString((Integer)entry.getValue()));
						index++;
					}
				}
			}
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
//		
	}

	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}

}
