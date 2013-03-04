package com.example.specchiowebsandbox.ui;

import java.util.ArrayList;

import com.example.specchiowebsandbox.data.EAV_Attribute;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class EAVDataPanel extends VerticalLayout{
	
	private Panel panel;
	
	public EAVDataPanel(ArrayList<EAV_Attribute> entries){
		Table table = new Table("EAV Data Table");
		
		panel = new Panel("Spectrum Data Panel");
		panel.setHeight(620, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(false);
		layout.setSpacing(false);
		
		// set a style name, so we can style rows and cells
        table.setStyleName("iso3166");

        // size
        table.setWidth("100%");
        table.setHeight("170px");
        
        table.setVisibleColumns(new String[]{"Attribute Name", "Storage Field", "Value"});
        
        table.setColumnHeaders(new String[]{"Attribute Name", "Value", "Storage Field"});
        
        for (int i = 0; i < entries.size(); i++){
        	table.addItem(entries.get(i));
        }
        
       
        
        
        
        
        panel.addComponent(table);
        
        addComponent(panel);

	}

}
