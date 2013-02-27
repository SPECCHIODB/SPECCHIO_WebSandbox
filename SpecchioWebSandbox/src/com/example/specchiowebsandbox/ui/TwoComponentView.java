package com.example.specchiowebsandbox.ui;

import specchio.Spectrum;

import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class TwoComponentView extends HorizontalLayout{
	
	private GridLayout grid;
	
	public TwoComponentView(Component comp1, Component comp2){
		
		setSpacing(true);
		
		setMargin(false);
		
		grid = new GridLayout(2, 1);
		grid.setSpacing(true);
		grid.setMargin(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("1200px");
		grid.setHeight("300px");

		grid.addComponent(comp1);
		grid.setComponentAlignment(comp1, Alignment.TOP_LEFT);
		
		grid.addComponent(comp2);
		grid.setComponentAlignment(comp2, Alignment.TOP_LEFT);
		
		addComponent(grid);
		
	}
	
//	public void setHeight(float height){
//		grid.setHeight(height, UNITS_PIXELS);
//	}
//	

}
