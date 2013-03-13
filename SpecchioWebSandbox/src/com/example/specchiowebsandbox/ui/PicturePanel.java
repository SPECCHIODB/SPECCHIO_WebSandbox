package com.example.specchiowebsandbox.ui;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import specchio.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class PicturePanel extends VerticalLayout implements StreamSource{
	
	private Panel panel;
	
	private Connection conn = null;
	
	ByteArrayOutputStream imagebuffer = null;
	
	public PicturePanel(SpecchiowebsandboxApplication app, Spectrum spec, SpectrumMetadata meta){
		
		setSpacing(true);
		
		setHeight(320, Sizeable.UNITS_PIXELS);
		
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(1, 1);
        grid.setSpacing(false);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("250px");
		
		panel = new Panel("Picture");
		panel.setHeight(320, Sizeable.UNITS_PIXELS);
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
		
		
		
		SQLConnection db_con = new SQLConnection();
		try {
			conn = db_con.connect2db();
			Statement stm = conn.createStatement();
			
			ResultSet rs = stm.executeQuery("select picture_i from spectrum_x_picture where spectrum_id=" + spec.spectrum_id);
			int picture_id = rs.getInt("picture_id");
			rs.close();
			
			rs = stm.executeQuery("select caption, image_data from picture where picture_id=" + picture_id);

			while (rs.next()) {
				
				
				
			}
		
	}catch (Exception e){
		e.printStackTrace();
	}
	}

	public InputStream getStream() {
		// TODO Auto-generated method stub
		
		BufferedImage image = new BufferedImage(200,200,ColorSpace.TYPE_RGB);
		
		
		
		return null;
	}

}
