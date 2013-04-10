package com.example.specchiowebsandbox.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import specchio.SPECCHIODatabaseConnection;
import specchio.Spectrum;
import specchio.SpectrumQueryBank;

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

import eav_db.DatabaseConnection;

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
		grid.setHeight("250px");
		
		
//		eav_metadata = (Tab) a.addTab(eav,"EAV Metadata Table");
//		eav_metadata.setClosable(false);
		pictures = (Tab) a.addTab(grid, "Display Pictures");
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
		
		Embedded image = null;
		
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
				image = new Embedded(captions.get(i), imageResource);
				image.requestRepaint();
				
				

//				
			}
			}
			
			



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

}
