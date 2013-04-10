package com.example.specchiowebsandbox.ui;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabLayout extends VerticalLayout{
	
	private TabSheet t;
	private VerticalLayout general;
	private VerticalLayout metadata;
	private VerticalLayout explorer;
	
	private SpecchiowebsandboxApplication app;
	
	public TabLayout(SpecchiowebsandboxApplication app){
		
		this.app = app;
		
		general = new VerticalLayout();
		metadata = new VerticalLayout();
		explorer = new VerticalLayout();
		
//		VerticalLayout l1 = new VerticalLayout();
//		l1.addComponent(spectrumDetail);
//		l1.addComponent(position);
//		l1.addComponent(info_view);
////		l1.addComponent(eav_data);
////		l1.addComponent(pic);
////		
//		VerticalLayout l2 = new VerticalLayout();
//		l2.addComponent(eav_data);
//		l2.addComponent(pic);
//		
//		float height = spectrumDetail.getHeight() + position.getHeight() + info_view.getHeight() + eav_data.getHeight() + pic.getHeight();
		
		t = new TabSheet();
//		t.setHeight(height, UNITS_PIXELS);
		t.setWidth("100%");
		
		t.addTab(general,"Spectrum Data");
		t.addTab(metadata, "Metadata");
		t.addTab(explorer, "Data Explorer");
		
		addComponent(t);
		
		
		
	}
	
	public void fillContent(Component comp, String tab){
		if (tab.equalsIgnoreCase("general")){
			general.addComponent(comp);
			
		}
		if(tab.equalsIgnoreCase("metadata")){
			metadata.addComponent(comp);
		}
		if(tab.equalsIgnoreCase("explor")){
			explorer.addComponent(comp);
		}
		
	}

}

