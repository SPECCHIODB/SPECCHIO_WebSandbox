package com.example.specchiowebsandbox.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import specchio.SPECCHIODatabaseConnection;
import specchio.Spectrum;
import specchio.SpectrumQueryBank;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import eav_db.DatabaseConnection;
import eav_db.SQL_StatementBuilder;

public class PicturePanel extends VerticalLayout { // implements StreamSource

	private Panel panel;

	private Connection conn = null;

	private Statement stmt;

	ByteArrayOutputStream imagebuffer = null;

	public PicturePanel(SpecchiowebsandboxApplication app, Spectrum spec,
			SpectrumMetadata meta) {

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

		try {

			DatabaseConnection db = SPECCHIODatabaseConnection.getInstance();
			// conn = db.get_db_conn();
			stmt = db.get_default_statement();
			// SQL_StatementBuilder SQL = SQL_StatementBuilder.getInstance();
			//
			// String table_PK_name = SQL.get_primary_key_name("picture");

			SpectrumQueryBank qb = SpectrumQueryBank.getInstance();

			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(spec.spectrum_id);

			// String query = qb.get_picture_id_query(ids);

			// String query =
			// "select p.picture_id, p.image_data from picture p where p.picture_id in (select picture_id from spectrum_x_picture where spectrum_id="+spec.spectrum_id;
			String query = "select p.picture_id, p.caption, p.image_data from picture p inner join spectrum_x_picture sxp on p.picture_id=sxp.picture_id where sxp.spectrum_id="
					+ spec.spectrum_id;

			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Integer> picture_ids;

//			Blob blob = null;
			ArrayList<String> captions = new ArrayList<String>();
			ArrayList<Blob> blobs = new ArrayList<Blob>();
//			String caption = null;
			picture_ids = new ArrayList<Integer>();

			while (rs.next()) {
				picture_ids.add(rs.getInt(1));
				captions.add(rs.getString(2));
				blobs.add(rs.getBlob(3));

			}

			rs.close();
			// stmt.close();
			// conn.close();
			
			float height = 0;

			for(int i = 0; i < picture_ids.size(); i++){
			if (blobs.get(i) != null) {
				final byte[] img = blobs.get(i).getBytes(1, (int) blobs.get(i).length());

				StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
					public InputStream getStream() {
						return new ByteArrayInputStream(img);
					}
				};
				StreamResource imageResource = new StreamResource(imageSource,
						captions.get(i) + ".png", app);
				imageResource.setCacheTime(0);
				Embedded image = new Embedded(captions.get(i), imageResource);
				image.requestRepaint();
				
				height = height + image.getHeight();

				grid.addComponent(image);
				grid.setComponentAlignment(image, Alignment.TOP_CENTER);

				
			}
			}
			
			if(picture_ids.isEmpty()){
				setHeight(0,UNITS_PIXELS);
			}else{
				
//				panel.setHeight(height, UNITS_PIXELS);
				
				panel.addComponent(grid);

				addComponent(panel);
				
//				setHeight(height, UNITS_PIXELS);
			}
			


			// JPanel jpanel = spec.get_picture_panel();
			// Component[] comps = jpanel.getComponents();
			// JScrollPane scroll = (JScrollPane) comps[0];
			// comps = scroll.getComponents();
			// JViewport view = (JViewport)comps[0];
			// comps = view.getComponents();
			// Graphics pic = jpanel.getGraphics();

			int test = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// SQLConnection db_con = new SQLConnection();
		// try {
		// conn = db_con.connect2db();
		// Statement stm = conn.createStatement();
		//
		// ResultSet rs =
		// stm.executeQuery("select picture_i from spectrum_x_picture where spectrum_id="
		// + spec.spectrum_id);
		// int picture_id = rs.getInt("picture_id");
		// rs.close();
		//
		// rs =
		// stm.executeQuery("select caption, image_data from picture where picture_id="
		// + picture_id);
		//
		// while (rs.next()) {
		//
		//
		//
		// }
		//
		// }catch (Exception e){
		// e.printStackTrace();
		// }
	}

	// public InputStream getStream() {
	// // TODO Auto-generated method stub
	//
	// BufferedImage image = new BufferedImage(200,200,ColorSpace.TYPE_RGB);
	//
	//
	//
	// return null;
	// }

}
