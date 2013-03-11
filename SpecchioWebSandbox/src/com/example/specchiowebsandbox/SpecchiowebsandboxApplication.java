package com.example.specchiowebsandbox;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;




import spaces.SensorAndInstrumentSpace;
import spaces.Space;
import specchio.MetaDatatype;
import specchio.SPECCHIODatabaseConnection;
import specchio.SpaceFactory;
import specchio.Spectrum;

import com.example.specchiowebsandbox.data.DBHelper;
import com.example.specchiowebsandbox.data.EAV_Attribute;
//import com.example.specchiowebsandbox.data.DataProcessor;
import com.example.specchiowebsandbox.data.EAV_DataAccess;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.example.specchiowebsandbox.data.SpectrumDetailContainer;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.example.specchiowebsandbox.ui.DataExplorationPanel;
import com.example.specchiowebsandbox.ui.DetailList;
import com.example.specchiowebsandbox.ui.EAVDataPanel;
import com.example.specchiowebsandbox.ui.GeneralDataPanel;
import com.example.specchiowebsandbox.ui.GoogleMapsPanel;
import com.example.specchiowebsandbox.ui.ListView;
import com.example.specchiowebsandbox.ui.NavigationTree;
import com.example.specchiowebsandbox.ui.PositionPanel;
import com.example.specchiowebsandbox.ui.SamplingGeometryPanel;
import com.example.specchiowebsandbox.ui.ScatterPlot;
import com.example.specchiowebsandbox.ui.SpectrumDataPanel;
import com.example.specchiowebsandbox.ui.SpectrumPlot;
import com.example.specchiowebsandbox.ui.TimeLinePanel;
import com.example.specchiowebsandbox.ui.TimeLinePlot;
import com.example.specchiowebsandbox.ui.TwoComponentView;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import eav_db.DatabaseConnection;

public class SpecchiowebsandboxApplication extends Application implements
		HttpServletRequestListener, ValueChangeListener, Button.ClickListener {

	private Boolean isAppRunningOnGAE;

	private DBHelper dbHelp = new DBHelper(this);

	NavigationTree navtree = new NavigationTree(this);

	private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();

	private SpectrumDetailContainer spec_cont = null;

	private SpectrumPlot specPlot = null;

	private InvientCharts chart = null;

	private SpectrumMetadata metadata = null;

	private Spectrum s = null;

	private SpectrumDataPanel spec_dat_panel = null;
	
	private TimeLinePanel time_line_panel = null;
	
	private PositionPanel position_panel = null;
	
	private GeneralDataPanel general_data_panel = null;

	private SamplingGeometryPanel sampling_geom_panel = null;

	private GoogleMapsPanel map_panel = null;

	private TwoComponentView map = null;

	private TwoComponentView info_view = null;

	private DataExplorationPanel data_expl_panel;

	private EAVDataPanel eav_data;

	
	
	

	@Override
	public void init() {
		// SpecchiowebsandboxWin win = new SpecchiowebsandboxWin(this);
		// setMainWindow(win);
		buildMainLayout();
		// setMainComponent(getListView());
	}

	private void buildMainLayout() {
		setMainWindow(new Window("SpecchioWeb Sandbox Application"));
		int test = getMainWindow().getBrowserWindowWidth();
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(createHeaderBar());
		layout.addComponent(horizontalSplit);

		/* Allocate all available extra space to the hoizontal split panel */

		layout.setExpandRatio(horizontalSplit, 1);
		/*
		 * Set the initial split position so we can have a 200 pixel menu to the
		 * left
		 */

		horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);
		horizontalSplit.setFirstComponent(navtree);

		getMainWindow().setContent(layout);

	}
	
	private Component createHeaderBar(){
		
		HorizontalLayout h = new HorizontalLayout();
		
		Button logout = new Button("Logout");
		
		logout.addListener(new ClickListener(){
			 public void buttonClick(ClickEvent event){
				 DatabaseConnection db = SPECCHIODatabaseConnection.getInstance();
//				 Connection db_conn = db.get_new_thread_conn_to_current_server(Thread.currentThread());
				 
//				 db.close_thread_conn_to_current_server(Thread.currentThread());
				 
				 
//				 try {
//					db_conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				 getApp().close();
				 
//				 showLogoutWindow();
			 }
		});
		
		h.addComponent(logout);
		h.setComponentAlignment(logout, Alignment.TOP_RIGHT);
		
		return h;
	}

//	public void itemClick(ItemClickEvent event) {
//
//		System.out.println("WTF");
//
//		if (event.getSource() == navtree) {
//
//			if (!event.isCtrlKey() && !event.isAltKey() && !event.isShiftKey()) {
//
//				SpectrumData itemId = (SpectrumData) event.getItemId();
//
//				if (itemId != null) {
//
//					try {
//						s = new Spectrum(itemId.getSpectrum_id());
//
//						// ArrayList<MetaDatatype> md = s.md_attribute_list;
//						//
//						//
//						// ListIterator<MetaDatatype> li = md.listIterator();
//						//
//						// while(li.hasNext())
//						// {
//						// MetaDatatype mp = li.next();
//						//
//						// System.out.println(mp.colon_name() + ":" +
//						// mp.toString());
//						// }
//
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					spec_cont = new SpectrumDetailContainer(this,
//							itemId.getSpectrum_id());
//					
//					
//					specPlot = new SpectrumPlot();
//					specPlot.generatePlot(s);
////					if (specPlot == null) {
////						specPlot = new SpectrumPlot();
////						specPlot.generatePlot(s);
////					} else {
////						specPlot.addPlot(s);
////					}
//
//					metadata = new SpectrumMetadata(s);
//					metadata.fillMetadata();
//					spec_dat_panel = new SpectrumDataPanel(s, specPlot.getChart(), metadata);
//					// horizontalSplit.setSecondComponent(new
//					// ListView(spec_dat_panel));
//					
//					specPlot = null;
//
//				}
//			} else if (event.isShiftKey()){
//				
//				
//				
//				Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();
////						((Set<Object>) navtree.getValue()).toArray();
//				SpectrumData itemId = (SpectrumData) event.getItemId();
//
//				if (itemId != null) {
//
//					try {
//						s = new Spectrum(itemId.getSpectrum_id());
//
//						// ArrayList<MetaDatatype> md = s.md_attribute_list;
//						//
//						//
//						// ListIterator<MetaDatatype> li = md.listIterator();
//						//
//						// while(li.hasNext())
//						// {
//						// MetaDatatype mp = li.next();
//						//
//						// System.out.println(mp.colon_name() + ":" +
//						// mp.toString());
//						// }
//
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					spec_cont = new SpectrumDetailContainer(this,
//							itemId.getSpectrum_id());
//					
//					
//					
//					if (specPlot == null) {
//						specPlot = new SpectrumPlot();
//						specPlot.generatePlot(s);
//					} else {
//						specPlot.addPlot(s);
//					}
//
//					metadata = new SpectrumMetadata(s);
//					metadata.fillMetadata();
//					spec_dat_panel = new SpectrumDataPanel(s, specPlot.getChart(), metadata);
//			}
//		}
//		}
//
//		horizontalSplit.setSecondComponent(new ListView(spec_dat_panel));
//
//	}

	public DBHelper getDbHelp() {
		return dbHelp;
	}

	public SpectrumDetailContainer getSpectrumContainer() {
		return spec_cont;
	}

	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		if (isAppRunningOnGAE == null) {
			isAppRunningOnGAE = false;
			String serverInfo = request.getSession().getServletContext()
					.getServerInfo();
			if (serverInfo != null && serverInfo.contains("Google")) {
				isAppRunningOnGAE = true;
			}
		}

	}

	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String serverInfo = request.getSession().getServletContext().getServerInfo();
		String test = request.getRequestURI();
		
		

	}

	public boolean isAppRunningOnGAE() {
		if (isAppRunningOnGAE == null) {
			return false;
		}
		return isAppRunningOnGAE;
	}

	public void valueChange(ValueChangeEvent event) {
		System.out.println("WTF");	
		
		
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();
		
		for (int i = 0; i < selected_items.length; i++){
			SpectrumData itemId = (SpectrumData) selected_items[i];
			
			if (itemId != null){
				try {
//		
					
					
//					System.setProperty("user.dir","/var/lib/tomcat6/webapps/SpecchioWebSandbox/WEB-INF/lib/");
//					System.out.println(System.getProperty("user.dir"));
//					getMainWindow().showNotification(System.getProperty("user.dir"));
//					
//					//Test to copy one file to another directory
//					if(!new File("/var/lib/tomcat6/db_config.txt").exists()){
//					try {
//						FileUtils.copyFileToDirectory(new File("/var/lib/tomcat6/webapps/SpecchioWebSandbox/WEB-INF/lib/db_config.txt"), new File("/var/lib/tomcat6"));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						getMainWindow().showNotification(e.toString());
//						e.printStackTrace();
//					}
//					}
					
					String test = System.getProperty("user.dir");
					
					s = new Spectrum(itemId.getSpectrum_id());
					
					
					
					
//					System.setProperty("user.dir","/var/lib/tomcat6");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					getMainWindow().showNotification(e.toString());
					e.printStackTrace();
				}
				
//				EAV_DataAccess eav_access = null;
//				try {
//					eav_access = new EAV_DataAccess();
//				} catch (InstantiationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				ArrayList<EAV_Attribute> entries = eav_access.createEAVDataContainer(s.spectrum_id);
				
				
				
				eav_data = new EAVDataPanel(s.spectrum_id);
				
				if(specPlot == null){
					specPlot = new SpectrumPlot();
					specPlot.generatePlot(s, false);
				}else {
					specPlot.addPlot(s, false);
				}
				
				metadata = new SpectrumMetadata(s);
				metadata.fillMetadata();
				spec_dat_panel = new SpectrumDataPanel(this, s, specPlot.getChart(), metadata, false);
				position_panel = new PositionPanel(s, metadata);
				general_data_panel = new GeneralDataPanel(s, metadata);
				sampling_geom_panel  = new SamplingGeometryPanel(s, metadata);
				map_panel = new GoogleMapsPanel(this, s, metadata);
				
				map = new TwoComponentView(position_panel, map_panel);
				info_view  = new TwoComponentView(general_data_panel, sampling_geom_panel);
				info_view.setHeight(200, 0);
			}
		}
		
		
		if(selected_items.length > 1){
			time_line_panel = new TimeLinePanel(this, s, specPlot.get_wvl_vector());
			
			data_expl_panel = new DataExplorationPanel(this, s, selected_items);
			
			horizontalSplit.setSecondComponent(new ListView(spec_dat_panel, map, info_view, time_line_panel, data_expl_panel));
		} else{
			horizontalSplit.setSecondComponent(new ListView(spec_dat_panel, map, info_view, eav_data));
		}
		
		
		specPlot = null;
		
		


	}
	
	
	
	public InvientCharts getTimelinePlot(String parameter, int band_no){
		
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();
		
		TimeLinePlot timeline = new TimeLinePlot();
		
		timeline.generatePlot(parameter, band_no, selected_items);
		
		return timeline.getChart();
	}
	
	public InvientCharts getScatterPlot(String parameter, int band_no_param1, int band_no_param2){
		
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();
		
		ScatterPlot scatterplot = new ScatterPlot();
		
//		scatterplot.generatePlot(parameter, band_no_param1, band_no_param2, selected_items);
		
		return scatterplot.getChart();
	}

	
	//method to toggle between full res spectrum plot or scaled (just every 10th datapoint)
	public void buttonClick(ClickEvent event) {
		System.out.println("WTF");	
		
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();
		
		if(event.getButton().getValue().equals(true)){
			
			
			for (int i = 0; i < selected_items.length; i++){
				SpectrumData itemId = (SpectrumData) selected_items[i];
				
				if (itemId != null){
					try {
						s = new Spectrum(itemId.getSpectrum_id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(specPlot == null){
						specPlot = new SpectrumPlot();
						specPlot.generatePlot(s, true);
					}else {
						specPlot.addPlot(s, true);
					}
					
//					EAV_DataAccess eav_access = new EAV_DataAccess();
//					ArrayList<EAV_Attribute> entries = eav_access.getEAVdata(s.spectrum_id);
					
					metadata = new SpectrumMetadata(s);
					metadata.fillMetadata();
					if(selected_items.length == 1){
						
						eav_data = new EAVDataPanel(s.spectrum_id);
					}
					spec_dat_panel = new SpectrumDataPanel(this, s, specPlot.getChart(), metadata, true);
					position_panel = new PositionPanel(s, metadata);
					general_data_panel = new GeneralDataPanel(s, metadata);
					sampling_geom_panel  = new SamplingGeometryPanel(s, metadata);
					map_panel = new GoogleMapsPanel(this, s, metadata);
					map = new TwoComponentView(position_panel, map_panel);
					info_view  = new TwoComponentView(general_data_panel, sampling_geom_panel);
				}
			}
		} else {
			
			for (int i = 0; i < selected_items.length; i++){
				SpectrumData itemId = (SpectrumData) selected_items[i];
				
				if (itemId != null){
					try {
						s = new Spectrum(itemId.getSpectrum_id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(specPlot == null){
						specPlot = new SpectrumPlot();
						specPlot.generatePlot(s, false);
					}else {
						specPlot.addPlot(s, false);
					}
					
//					EAV_DataAccess eav_access = new EAV_DataAccess();
//					ArrayList<EAV_Attribute> entries = eav_access.getEAVdata(s.spectrum_id);
					
					metadata = new SpectrumMetadata(s);
					metadata.fillMetadata();
					if(selected_items.length == 1){
						
						eav_data = new EAVDataPanel(s.spectrum_id);
					}
					spec_dat_panel = new SpectrumDataPanel(this, s, specPlot.getChart(), metadata, false);
					position_panel = new PositionPanel(s, metadata);
					general_data_panel = new GeneralDataPanel(s, metadata);
					sampling_geom_panel  = new SamplingGeometryPanel(s, metadata);
					map_panel = new GoogleMapsPanel(this, s, metadata);
					map = new TwoComponentView(position_panel, map_panel);
					info_view  = new TwoComponentView(general_data_panel, sampling_geom_panel);
				}
			}
		}
		
		
		
		
		
		if(selected_items.length > 1){
			time_line_panel = new TimeLinePanel(this, s, specPlot.get_wvl_vector());
			
			data_expl_panel = new DataExplorationPanel(this, s, selected_items);
			
			horizontalSplit.setSecondComponent(new ListView(spec_dat_panel, map, info_view, time_line_panel, data_expl_panel));
		} else{
			horizontalSplit.setSecondComponent(new ListView(spec_dat_panel, map, info_view, eav_data));
		}
		
		
		specPlot = null;
		
		
		
	}
	
	public SpecchiowebsandboxApplication getApp(){
		return this;
	}
	
	

		
	

}
