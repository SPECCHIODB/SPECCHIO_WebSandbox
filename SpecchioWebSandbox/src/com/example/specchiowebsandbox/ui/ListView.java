package com.example.specchiowebsandbox.ui;

import com.invient.vaadin.charts.InvientCharts;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class ListView extends VerticalLayout {
	
	private final GridLayout grid;

	public ListView(SpectrumDataPanel spectrumDetail, TwoComponentView position, TwoComponentView info_view, EAVDataPanel eav_data, PicturePanel pic) {
		// Create a grid layout
		
		float height = spectrumDetail.getHeight() + position.getHeight() + info_view.getHeight() + eav_data.getHeight() + pic.getHeight();
		
		setWidth("100%");
		
		setSpacing(true);
		
		setMargin(false);
		
		grid = new GridLayout(1, 9);
		grid.setSpacing(true);
		grid.setMargin(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("100%");
		grid.setHeight(height + 50, Sizeable.UNITS_PIXELS);

		grid.addComponent(spectrumDetail);
		grid.setComponentAlignment(spectrumDetail, Alignment.TOP_CENTER);
		
		
		grid.addComponent(position);
		grid.setComponentAlignment(position, Alignment.TOP_CENTER);
		
		grid.addComponent(info_view);
		grid.setComponentAlignment(info_view, Alignment.TOP_CENTER);
		
		grid.addComponent(eav_data);
		grid.setComponentAlignment(eav_data, Alignment.TOP_CENTER);
		
		grid.addComponent(pic);
		grid.setComponentAlignment(pic, Alignment.TOP_CENTER);

		

		addComponent(grid);
		this.setComponentAlignment(grid, Alignment.TOP_CENTER);
	}

	public ListView(SpectrumDataPanel spectrumDetail, TwoComponentView position, TwoComponentView info_view,
			TimeLinePanel time_line_panel, DataExplorationPanel data_expl_panel) {

		// Create a grid layout
		setSpacing(false);
		setWidth("100%");
		
		
		setMargin(false);
		
		grid = new GridLayout(1, 9);
		grid.setSpacing(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("100%");
		grid.setHeight("2700px");

		grid.addComponent(spectrumDetail);
		grid.setComponentAlignment(spectrumDetail, Alignment.TOP_CENTER);
		
		grid.addComponent(position);
		grid.setComponentAlignment(position, Alignment.TOP_CENTER);
		
		grid.addComponent(info_view);
		grid.setComponentAlignment(info_view, Alignment.TOP_CENTER);

		grid.addComponent(time_line_panel);
		grid.setComponentAlignment(time_line_panel, Alignment.TOP_CENTER);
		
		grid.addComponent(data_expl_panel);
		grid.setComponentAlignment(data_expl_panel, Alignment.TOP_CENTER);
		
		


		// specPlot.setWidth("100%");
		// grid.addComponent(specPlot);
		// grid.setComponentAlignment(specPlot, Alignment.TOP_CENTER);

		// Add the layout to the containing layout.
		addComponent(grid);
		setComponentAlignment(grid,Alignment.TOP_CENTER);

		// Align the grid itself within its container layout.
		// setComponentAlignment(grid, Alignment.MIDDLE_CENTER);

		// addComponent(spectrumDetail);
		// addComponent(specPlot);
		// setSplitPosition(40);

	}
	
	public void resizeWindow(int pix){
		grid.setHeight(-1, Sizeable.UNITS_PIXELS);
	}

}
