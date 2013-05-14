package com.example.specchiowebsandbox.ui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;


import ch.specchio.types.SerialisableBufferedImage;
import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;


public class PicViewer extends VerticalLayout implements
		Accordion.SelectedTabChangeListener, Button.ClickListener {
	
	private Accordion a;
	private Tab empty;
	private Tab pictures;
	
	private Connection conn = null;

	private Statement stmt;

	ByteArrayOutputStream imagebuffer = null;
	
	private Spectrum spec;
	
	SpecchiowebsandboxApplication app;
	
	final GridLayout grid = new GridLayout(1, 1);
	
	public PicViewer(Spectrum spec, SpecchiowebsandboxApplication app){
		
		this.spec = spec;
		this.app = app;
		
		setSpacing(true);
		
		a = new Accordion();
		a.setWidth("100%");
		
		
		grid.setSpacing(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("100%");
		grid.setHeight("500px");
		
		
//		eav_metadata = (Tab) a.addTab(eav,"EAV Metadata Table");
//		eav_metadata.setClosable(false);
		pictures = (Tab) a.addTab(grid, "Display Target Pictures");
		empty = (Tab) a.addTab(new Label(""),"");
//		empty.setEnabled(true);
//		empty.setVisible(true);
		
		a.setSelectedTab(empty);
		
		
		a.addListener(this);
		
		
		addComponent(a);
		
		
	}
	

	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	public void selectedTabChange(SelectedTabChangeEvent event) {
		// TODO Auto-generated method stub
		if(event.getTabSheet().getSelectedTab().equals(grid)){
			Embedded image = getPictures();
			
			if(image != null){
				grid.addComponent(image);
				grid.setComponentAlignment(image, Alignment.TOP_CENTER);
			}
			
			
		}

	}
	
	public Embedded getPictures(){
		
		
		
		SerialisableBufferedImage buf_image = (SerialisableBufferedImage)spec.getMetadata().get_entry("Target Picture").getValue();
		BufferedImage image = buf_image.getImage();
		
		final ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", imagebuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
			public InputStream getStream() {
				return new ByteArrayInputStream(imagebuffer.toByteArray());
			}
		};
		
		StreamResource imageResource = new StreamResource(imageSource,
				"test" + ".png", app);
		
		
		Embedded emb = new Embedded("", imageResource);
		emb.requestRepaint();
		
		return emb;
		

	}

}
