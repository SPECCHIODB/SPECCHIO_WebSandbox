package com.example.specchiowebsandbox.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.example.specchiowebsandbox.*;
import com.example.specchiowebsandbox.data.SQLConnection;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

import eav_db.DatabaseConnection;

public class NavigationTree extends Tree {
	public static final Object CAMPAIGNS = "Campaigns";
	public static final Object SUB_DIR = "Sub Directory";
	public static final Object RADIANCES = "Radiances";
	public static final Object REFLECTANCES	= "Reflectances";
	public static final Object TARGETS = "Targets";
	public static final Object REFERENCES = "References";

	private ArrayList<String> campaign_names = new ArrayList<String>();
	private ArrayList<Long> campaign_ids = new ArrayList<Long>();
	private ArrayList<Long> spectrum_ids = new ArrayList<Long>();
	private ArrayList<String> file_name = new ArrayList<String>();
	private ArrayList<SpectrumData> spectrum_data = new ArrayList<SpectrumData>();

	private Connection conn = null;

	public NavigationTree(SpecchiowebsandboxApplication app) {
		addItem(CAMPAIGNS);

		/*
		 * We want items to be selectable but do not want the user to be able to
		 * de-select an item.
		 */
		setSelectable(true);
		setNullSelectionAllowed(false);
		setMultiSelect(true);
		setImmediate(true);

		// DatabaseConnection conn = DatabaseConnection.getInstance();
		// try {
		// database_node db_node = new database_node(null,0, "Acquisition time",
		// true);
		// db_node.add_children();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

		SQLConnection db_con = new SQLConnection();
		try {
			conn = db_con.connect2db();
			Statement stm = conn.createStatement();
			ResultSet rs = stm
					.executeQuery("select campaign_id, name from campaign");

			while (rs.next()) {
				campaign_ids.add(rs.getLong("campaign_id"));
				campaign_names.add(rs.getString("name"));

			}

			for (int i = 0; i < campaign_ids.size(); i++) {
				this.addItem(campaign_names.get(i));
				this.setParent(campaign_names.get(i), this.CAMPAIGNS);
				this.setChildrenAllowed(campaign_ids.get(i), true);
				this.expandItem(this.CAMPAIGNS);
				
				this.addItem(RADIANCES);
				this.setParent(RADIANCES, campaign_names.get(i));
				this.setChildrenAllowed(RADIANCES, true);
				
				this.addItem(REFLECTANCES);
				this.setParent(REFLECTANCES, campaign_names.get(i));
				this.setChildrenAllowed(REFLECTANCES, true);
				
//				this.addItem(TARGETS);
//				this.setParent(TARGETS, RADIANCES);
//				this.setChildrenAllowed(TARGETS, true);
//				
//				this.addItem(REFERENCES);
//				this.setParent(REFERENCES, RADIANCES);
//				this.setChildrenAllowed(REFERENCES, true);

			}

			rs.close();
			
			

			// new query to get the spectra
			for (int i = 0; i < campaign_ids.size(); i++) {
				rs = stm.executeQuery("select file_name, spectrum_id, measurement_unit_id from spectrum where campaign_id="
						+ campaign_ids.get(i));
				while (rs.next()) {
					SpectrumData spec = new SpectrumData();
					spec.setFilename(rs.getString("file_name"));
					spec.setSpectrum_id(rs.getInt("spectrum_id"));
					spec.setMeasurement_unit(rs.getInt("measurement_unit_id"));
					spectrum_data.add(spec);
				}

			}

			for (int j = 0; j < campaign_ids.size(); j++) {
				for (int i = 0; i < spectrum_data.size(); i++) {
					if(spectrum_data.get(i).getMeasurement_unit()==1){
						this.addItem(spectrum_data.get(i));
						this.setParent(spectrum_data.get(i), REFLECTANCES);
						this.setChildrenAllowed(spectrum_data.get(i), false);
//						this.expandItem(campaign_names.get(j));
//						this.setValue(spectrum_data.get(i));
						this.setCaption(spectrum_data.get(i).getFilename());
						
					}else if(spectrum_data.get(i).getMeasurement_unit()==2){
						this.addItem(spectrum_data.get(i));
						this.setParent(spectrum_data.get(i), RADIANCES);
						this.setChildrenAllowed(spectrum_data.get(i), false);
//						this.expandItem(campaign_names.get(j));
//						this.setValue(spectrum_data.get(i));
						this.setCaption(spectrum_data.get(i).getFilename());
					}
					
					
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Make application handle item click events
//		addListener((ItemClickListener) app);
		
		addListener((ValueChangeListener) app);
		
		
	}
}
