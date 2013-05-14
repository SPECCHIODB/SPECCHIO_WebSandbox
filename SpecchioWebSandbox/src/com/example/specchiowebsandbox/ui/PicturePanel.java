package com.example.specchiowebsandbox.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

//import specchio.SPECCHIODatabaseConnection;
import ch.specchio.types.MetaDatatype;
import ch.specchio.types.SerialisableBufferedImage;
import ch.specchio.types.Spectrum;
//import ch.specchio.factories.SpectrumQueryBank;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

//import ch.specchio.eav_db.DatabaseConnection;
//import ch.specchio.eav_db.SQL_StatementBuilder;

public class PicturePanel extends VerticalLayout implements ClickListener { // implements StreamSource

	private Panel panel;

	private Connection conn = null;

	private Statement stmt;

	ByteArrayOutputStream imagebuffer = null;
	
	private Spectrum spec;
	private SpecchiowebsandboxApplication app;
	final GridLayout grid = new GridLayout(1, 1);

	public PicturePanel(SpecchiowebsandboxApplication app, Spectrum spec,
			SpectrumMetadata meta) {
		
		this.spec = spec;
		this.app = app;
		
		setSpacing(true);

		setHeight(550, Sizeable.UNITS_PIXELS);

		// Create a grid layout
		
		grid.setSpacing(false);

		// The style allows us to visualize the cell borders in this example.
		grid.addStyleName("gridexample");

		grid.setWidth("100%");
		grid.setHeight("500px");

		panel = new Panel("Target Picture");
		panel.setHeight(550, Sizeable.UNITS_PIXELS);

		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
		
		Button button = new Button("Display Target Picture");
		button.addListener(this);
		
		grid.addComponent(button);
		
		panel.addComponent(grid);

		addComponent(panel);
		
	}

	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		grid.removeAllComponents();
		
		Embedded image = getPictures();
		
		if(image != null){
			grid.addComponent(image);
			grid.setComponentAlignment(image, Alignment.TOP_CENTER);
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
