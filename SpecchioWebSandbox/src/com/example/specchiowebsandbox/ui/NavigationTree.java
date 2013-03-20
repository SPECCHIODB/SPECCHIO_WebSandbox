package com.example.specchiowebsandbox.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import spectral_data_browser.SpectralDataBrowser;


import com.example.specchiowebsandbox.*;
import com.example.specchiowebsandbox.data.CampaignNode;
import com.example.specchiowebsandbox.data.RadianceNode;
import com.example.specchiowebsandbox.data.ReflectanceNode;
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
//	private ArrayList<SpectrumData> spectrum_data = new ArrayList<SpectrumData>();
	private ArrayList<CampaignNode> campaign_data = new ArrayList<CampaignNode>();
	
	private int hyrarchi_id = 0;

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
			
	
			

//			for (int i = 0; i < campaign_ids.size(); i++) {
//				this.addItem(campaign_names.get(i));
//				this.setParent(campaign_names.get(i), this.CAMPAIGNS);
//				this.setChildrenAllowed(campaign_ids.get(i), true);
//				this.expandItem(this.CAMPAIGNS);
//				
//				this.addItem(RADIANCES);
//				this.setParent(RADIANCES, campaign_names.get(i));
//				this.setChildrenAllowed(RADIANCES, true);
//				
//				this.addItem(REFLECTANCES);
//				this.setParent(REFLECTANCES, campaign_names.get(i));
//				this.setChildrenAllowed(REFLECTANCES, true);
//				
////				this.addItem(TARGETS);
////				this.setParent(TARGETS, RADIANCES);
////				this.setChildrenAllowed(TARGETS, true);
////				
////				this.addItem(REFERENCES);
////				this.setParent(REFERENCES, RADIANCES);
////				this.setChildrenAllowed(REFERENCES, true);
//
//			}

			rs.close();
			
			

			// new query to get the spectra
			for (int i = 0; i < campaign_ids.size(); i++) {
				CampaignNode node = new CampaignNode();
				ArrayList<Object> subdirs = new ArrayList<Object>();
				ArrayList<SpectrumData> spectrum_data = new ArrayList<SpectrumData>();
				ReflectanceNode reflNode = new ReflectanceNode();
				RadianceNode radNode = new RadianceNode();
				ArrayList<SpectrumData> refl_data = new ArrayList<SpectrumData>();
				ArrayList<SpectrumData> rad_data = new ArrayList<SpectrumData>();
				node.setCampaignId(campaign_ids.get(i).intValue());
				node.setCampaignName(campaign_names.get(i));
				rs = stm.executeQuery("select file_name, spectrum_id, measurement_unit_id from spectrum where campaign_id="
						+ campaign_ids.get(i));
				while (rs.next()) {
					SpectrumData spec = new SpectrumData();
					spec.setFilename(rs.getString("file_name"));
					spec.setSpectrum_id(rs.getInt("spectrum_id"));
					spec.setMeasurement_unit(rs.getInt("measurement_unit_id"));
					if(spec.getMeasurement_unit()==1){
						refl_data.add(spec);
					}else if(spec.getMeasurement_unit()==2){
						rad_data.add(spec);
					}else{
						spectrum_data.add(spec);
					}
//					if(spec.getMeasurement_unit()==1 && !subdirs.contains("Reflectances")){
//						subdirs.add("Reflectances");
//						node.setReflSubdir("Reflectances");
//					} else if(spec.getMeasurement_unit()==2 && !subdirs.contains("Radiances")){
//						subdirs.add("Radiances");
//						node.setRadSubdir("Radiances");
//					}
//					spec.setCampaignId(campaign_ids.get(i));
//					spec.setCampaignName(campaign_names.get(i));
					
					
//					spectrum_data.add(spec);
				}
//				node.setSubdir(subdirs);
				if(!refl_data.isEmpty()){
					reflNode.setSpecDat(refl_data);
					reflNode.setId(hyrarchi_id++);
					
					node.setReflNode(reflNode);
				}
				if(!rad_data.isEmpty()){
					radNode.setSpecDat(rad_data);
					radNode.setId(hyrarchi_id++);
					
					node.setRadNode(radNode);
				}
				if(!spectrum_data.isEmpty()){
					node.setSpecData(spectrum_data);
				}
				
				campaign_data.add(node);
				
				

			}
			rs.close();
			
			for(int i = 0; i < campaign_data.size(); i++){
				this.addItem(campaign_data.get(i).getCampaignName());
				this.setParent(campaign_data.get(i).getCampaignName(), CAMPAIGNS);
				this.setChildrenAllowed(campaign_data.get(i), true);
				this.expandItem(this.CAMPAIGNS);
				
				if(campaign_data.get(i).getReflNode() != null){
					this.addItem(campaign_data.get(i).getReflNode());
					this.setParent(campaign_data.get(i).getReflNode(),campaign_data.get(i).getCampaignName());
					this.setChildrenAllowed(campaign_data.get(i).getReflNode(),true);
					this.setCaption(campaign_data.get(i).getReflNode().getName());
					for(int j = 0; j < campaign_data.get(i).getReflNode().getSpecDat().size(); j++){
						SpectrumData spec_data = campaign_data.get(i).getReflNode().getSpecDat().get(j);
						this.addItem(spec_data);
						this.setParent(spec_data, campaign_data.get(i).getReflNode());
						this.setChildrenAllowed(spec_data, false);
						this.setCaption(spec_data.getFilename());
					}
				}
				if(campaign_data.get(i).getRadNode() != null){
					this.addItem(campaign_data.get(i).getRadNode());
					this.setParent(campaign_data.get(i).getRadNode(),campaign_data.get(i).getCampaignName());
					this.setChildrenAllowed(campaign_data.get(i).getRadNode(),true);
					this.setCaption(campaign_data.get(i).getRadNode().getName());
					for(int j = 0; j < campaign_data.get(i).getRadNode().getSpecDat().size(); j++){
						SpectrumData spec_data = campaign_data.get(i).getRadNode().getSpecDat().get(j);
						this.addItem(spec_data);
						this.setParent(spec_data, campaign_data.get(i).getRadNode());
						this.setChildrenAllowed(spec_data, false);
						this.setCaption(spec_data.getFilename());
					}
					
				}
				if(campaign_data.get(i).getSpecData() != null){
					for(int j = 0; j < campaign_data.get(i).getSpecData().size(); j++){
						SpectrumData spec_data = campaign_data.get(i).getSpecData().get(j);
						this.addItem(spec_data);
						this.setParent(spec_data, campaign_data.get(i).getCampaignName());
						this.setChildrenAllowed(spec_data, false);
						this.setCaption(spec_data.getFilename());
					}
					
				}
				
				int test = 1;
				
//				this.addItem(campaign_data.get(i).getReflSubdir());
//				this.setParent(campaign_data.get(i).getReflSubdir(), campaign_data.get(i).getCampaignName());
//				this.setChildrenAllowed(campaign_data.get(i).getReflSubdir(), true);
//				
//				this.addItem(campaign_data.get(i).getRadSubdir());
//				this.setParent(campaign_data.get(i).getRadSubdir(), campaign_data.get(i).getCampaignName());
//				this.setChildrenAllowed(campaign_data.get(i).getRadSubdir(), true);
				
							
				
//				for(int j = 0; j < campaign_data.get(i).getSubdir().size(); j++){
//					this.addItem(campaign_data.get(i).getSubdir().get(j));
//					this.setParent(campaign_data.get(i).getSubdir().get(j), campaign_data.get(i).getCampaignName());
//					this.setChildrenAllowed(campaign_data.get(i).getSubdir().get(j), true);
//				}
				
//				for(int j = 0; j < campaign_data.get(i).getSpecData().size(); j++){
//					SpectrumData spec_data = campaign_data.get(i).getSpecData().get(j);
//					if(spec_data.getMeasurement_unit()==1){
//						this.addItem(spec_data);
//						Object parent = campaign_data.get(i).getReflSubdir();
//						this.setParent(spec_data, parent);
//						this.setChildrenAllowed(spec_data, false);
//						this.setCaption(spec_data.getFilename());
//					}else if(spec_data.getMeasurement_unit()==2){
//						this.addItem(spec_data);
//						Object parent = campaign_data.get(i).getRadSubdir();
//						this.setParent(spec_data, parent);
//						this.setChildrenAllowed(spec_data, false);
//						this.setCaption(spec_data.getFilename());
//						
//					}else{
//						this.addItem(spec_data);
//						Object parent = campaign_data.get(i).getCampaignName();
//						this.setParent(spec_data, parent);
//						this.setChildrenAllowed(spec_data, false);
//						this.setCaption(spec_data.getFilename());
//					}
//				}
				
			}

//			for (int j = 0; j < campaign_ids.size(); j++) {
//				for (int i = 0; i < spectrum_data.size(); i++) {
//					if(spectrum_data.get(i).getMeasurement_unit()==1){
//						this.addItem(spectrum_data.get(i));
//						this.setParent(spectrum_data.get(i), REFLECTANCES);
//						this.setChildrenAllowed(spectrum_data.get(i), false);
////						this.expandItem(campaign_names.get(j));
////						this.setValue(spectrum_data.get(i));
//						this.setCaption(spectrum_data.get(i).getFilename());
//						
//					}else if(spectrum_data.get(i).getMeasurement_unit()==2){
//						this.addItem(spectrum_data.get(i));
//						this.setParent(spectrum_data.get(i), RADIANCES);
//						this.setChildrenAllowed(spectrum_data.get(i), false);
////						this.expandItem(campaign_names.get(j));
////						this.setValue(spectrum_data.get(i));
//						this.setCaption(spectrum_data.get(i).getFilename());
//					}
//					
//					
//				}
//			}

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
