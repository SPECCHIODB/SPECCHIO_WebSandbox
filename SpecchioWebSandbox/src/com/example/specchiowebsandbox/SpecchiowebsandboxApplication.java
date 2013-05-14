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
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import ch.specchio.spaces.SensorAndInstrumentSpace;
import ch.specchio.spaces.Space;

import ch.specchio.types.MetaDatatype;
import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.client.SPECCHIOClientFactory;
import ch.specchio.client.SPECCHIOServerDescriptor;
//import specchio.SPECCHIODatabaseConnection;
//import ch.specchio.factories.SpaceFactory;
import ch.specchio.gui.SpectralDataBrowser.SpectralDataBrowserNode;
import ch.specchio.types.ConflictTable;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;
import ch.specchio.types.TaxonomyNodeObject;
import ch.specchio.types.spectral_node_object;

import com.example.specchiowebsandbox.data.DBHelper;
import com.example.specchiowebsandbox.data.EAV_Attribute;
//import com.example.specchiowebsandbox.data.DataProcessor;
import com.example.specchiowebsandbox.data.EAV_DataAccess;
import com.example.specchiowebsandbox.data.SpectrumData;
//import com.example.specchiowebsandbox.data.SpectrumDetailContainer;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.example.specchiowebsandbox.ui.DataExplorationPanel;
//import com.example.specchiowebsandbox.ui.DetailList;
import com.example.specchiowebsandbox.ui.EAVDataPanel;
import com.example.specchiowebsandbox.ui.GeneralDataPanel;
import com.example.specchiowebsandbox.ui.GoogleMapsPanel;
import com.example.specchiowebsandbox.ui.ListView;
import com.example.specchiowebsandbox.ui.PicViewer;
import com.example.specchiowebsandbox.ui.NavigationTree;
import com.example.specchiowebsandbox.ui.PicturePanel;
import com.example.specchiowebsandbox.ui.PositionPanel;
import com.example.specchiowebsandbox.ui.SamplingGeometryPanel;
import com.example.specchiowebsandbox.ui.ScatterPlot;
import com.example.specchiowebsandbox.ui.SideBar;
import com.example.specchiowebsandbox.ui.SpectrumDataPanel;
import com.example.specchiowebsandbox.ui.SpectrumPlot;
import com.example.specchiowebsandbox.ui.TabLayout;
import com.example.specchiowebsandbox.ui.TimeLinePanel;
import com.example.specchiowebsandbox.ui.TimeLinePlot;
import com.example.specchiowebsandbox.ui.TwoComponentView;
import com.example.specchiowebsandbox.ui.WelcomePage;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

//import eav_db.DatabaseConnection;

public class SpecchiowebsandboxApplication extends Application implements
		HttpServletRequestListener, ValueChangeListener, Button.ClickListener {

	private Boolean isAppRunningOnGAE;

	// private DBHelper dbHelp = new DBHelper(this);

	// NavigationTree navtree = new NavigationTree(this);
	// NavigationTree navtree = null;

	private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();

	// private SpectrumDetailContainer spec_cont = null;

	private SpectrumPlot specPlot = null;

	private InvientCharts chart = null;

	private SpectrumMetadata metadata = null;

	private Spectrum s = null;

	public ArrayList<Spectrum> spectra;

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

	private PicturePanel pic_panel;

	private TabLayout tabs = new TabLayout(this);

	private PicViewer pic_viewer;

	private SideBar sidebar;

	private SPECCHIOClientFactory cf;

	public SPECCHIOClient specchio_client;

	private ConflictTable conflicts;

	public ArrayList<EAV_Attribute> eav_attributes;

	private Space space;

	@Override
	public void init() {
		// SpecchiowebsandboxWin win = new SpecchiowebsandboxWin(this);
		// setMainWindow(win);
		buildMainLayout();
		// setMainComponent(getListView());
	}

	private void buildMainLayout() {
		setTheme("reindeer");
		setMainWindow(new Window("SpecchioWeb Sandbox Application"));
		int test = getMainWindow().getBrowserWindowWidth();
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		try {
			cf = SPECCHIOClientFactory.getInstance();
			List<SPECCHIOServerDescriptor> descriptor_list = cf
					.getAllServerDescriptors();

			SPECCHIOServerDescriptor descriptor = descriptor_list.get(2);

			specchio_client = cf.connect(descriptor);

		} catch (SPECCHIOClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sidebar = new SideBar(this);
		// navtree = new NavigationTree(this);

		// Component header = createHeaderBar();
		layout.addComponent(createHeaderBar());
		// layout.setComponentAlignment(header, Alignment.TOP_RIGHT);
		layout.addComponent(horizontalSplit);

		/* Allocate all available extra space to the hoizontal split panel */

		layout.setExpandRatio(horizontalSplit, 1);

		horizontalSplit.setSplitPosition(300, SplitPanel.UNITS_PIXELS);
		horizontalSplit.setFirstComponent(sidebar);
		horizontalSplit.setSecondComponent(tabs);

		getMainWindow().setContent(layout);

	}

	private Component createHeaderBar() {

		HorizontalLayout h = new HorizontalLayout();

		String local_path = "/usr/local/apache-tomcat-7.0.32/webapps/SpecchioWebSandbox/graphics/";
		String remote_path = "/var/lib/tomcat6/webapps/SpecchioWebSandbox/graphics/";

		h.setWidth("100%");

		Embedded specchio_icon = null;
		Embedded rsl_icon = null;

		// getMainWindow().showNotification(System.getProperty("user.dir"));
		if (System.getProperty("user.dir").equals("/var/lib/tomcat6")) {
			specchio_icon = new Embedded("", new FileResource(new File(
					remote_path + "SPECCHIO_Icon_Mid_Res_small.jpg"), this));
			rsl_icon = new Embedded("", new FileResource(new File(remote_path
					+ "RSL.gif"), this));

		} else {
			specchio_icon = new Embedded("", new FileResource(new File(
					local_path + "SPECCHIO_Icon_Mid_Res_small.jpg"), this));
			rsl_icon = new Embedded("", new FileResource(new File(local_path
					+ "RSL.gif"), this));
		}

		h.addComponent(specchio_icon);
		h.setComponentAlignment(specchio_icon, Alignment.BOTTOM_LEFT);

		h.addComponent(rsl_icon);
		h.setComponentAlignment(rsl_icon, Alignment.BOTTOM_RIGHT);

		return h;
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

		String serverInfo = request.getSession().getServletContext()
				.getServerInfo();
		String test = request.getRequestURI();

	}

	public boolean isAppRunningOnGAE() {
		if (isAppRunningOnGAE == null) {
			return false;
		}
		return isAppRunningOnGAE;
	}

	public void valueChange(ValueChangeEvent event) {

		generateContent(false);

	}

	public InvientCharts getTimelinePlot(String parameter, int band_no) {

		NavigationTree navtree = sidebar.getNavTree();
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();

		TimeLinePlot timeline = new TimeLinePlot(this);

		timeline.generatePlot(space, parameter, band_no, selected_items);

		return timeline.getChart();
	}

	public InvientCharts getTimeLinePlot(String parameter) {
		NavigationTree navtree = sidebar.getNavTree();
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();

		TimeLinePlot timeline = new TimeLinePlot(this);
		timeline.generatePlot(parameter, selected_items);

		return timeline.getChart();
	}

	public InvientCharts getScatterPlot(String parameter, int band_no_param1,
			int band_no_param2) {

		NavigationTree navtree = sidebar.getNavTree();
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();

		ScatterPlot scatterplot = new ScatterPlot();

		// scatterplot.generatePlot(parameter, band_no_param1, band_no_param2,
		// selected_items);

		return scatterplot.getChart();
	}

	// method to toggle between full res spectrum plot or scaled (just every
	// 10th datapoint)
	public void buttonClick(ClickEvent event) {

		if (event.getButton().getValue().equals(true)) {
			generateContent(true);
		} else {
			generateContent(false);
		}

	}

	private void generateContent(boolean full_res) {

		spectra = new ArrayList<Spectrum>();
		NavigationTree navtree = sidebar.getNavTree();
		space = null;
		Object[] selected_items = ((Set<Object>) navtree.getValue()).toArray();

		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<String> filenames = new ArrayList<String>();

		for (Object obj : selected_items) {
			SpectralDataBrowserNode node = (SpectralDataBrowserNode) obj;
			ids.add(node.getNodeId());
			filenames.add(node.toString());
		}

		try {
			Space[] spaces = this.specchio_client.getSpaces(ids, true, false,
					"Acquisition Time");
			space = spaces[0];
			space = specchio_client.loadSpace(space);

		} catch (SPECCHIOClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// for (int i = 0; i < selected_items.length; i++) {
		// SpectralDataBrowserNode itemId = (SpectralDataBrowserNode)
		// selected_items[i];
		//
		// if (itemId != null) {
		//

		try {

			for (Integer id : ids) {
				spectra.add(this.specchio_client.getSpectrum(id, true));
			}

			s = spectra.get(0);

			eav_attributes = getEAVAttributes(s);

			conflicts = specchio_client.getEavMetadataConflicts(ids);
		} catch (SPECCHIOClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eav_data = new EAVDataPanel(s, conflicts, this);

		specPlot = new SpectrumPlot();
		specPlot.generatePlot((SensorAndInstrumentSpace) space, filenames,
				full_res);
		// } else {
		// specPlot.addPlot(s, false);
		// }

		// metadata = new SpectrumMetadata(s);
		// metadata.fillMetadata();
		spec_dat_panel = new SpectrumDataPanel(this, s, specPlot.getChart(),
				full_res);
		position_panel = new PositionPanel(s);
		general_data_panel = new GeneralDataPanel(this,
				(SensorAndInstrumentSpace) space, s);
		sampling_geom_panel = new SamplingGeometryPanel(s);
		map_panel = new GoogleMapsPanel(this, s);
		 pic_panel = new PicturePanel(this, s, metadata);
		//
		map = new TwoComponentView(position_panel, map_panel);
		info_view = new TwoComponentView(general_data_panel,
				sampling_geom_panel);
		info_view.setHeight(200, 0);
		// }
		// }

		// First remove all components from the tabs
		TabSheet tab = (TabSheet) tabs.getComponent(0);
		Iterator<Component> itr = tab.getComponentIterator();
		while (itr.hasNext()) {
			VerticalLayout vert = (VerticalLayout) itr.next();
			vert.removeAllComponents();
		}

		if (selected_items.length > 1) {
			time_line_panel = new TimeLinePanel(this, s,
					specPlot.get_wvl_vector());

			data_expl_panel = new DataExplorationPanel(this,
					(SensorAndInstrumentSpace) space, s, selected_items);

//			pic_viewer = new PicViewer(s, this);

			tabs.fillContent(spec_dat_panel, "general");
			tabs.fillContent(map, "general");
			tabs.fillContent(info_view, "general");
			tabs.fillContent(eav_data, "metadata");
//			tabs.fillContent(pic_viewer, "metadata");
			tabs.fillContent(pic_panel, "metadata");
			tabs.fillContent(time_line_panel, "explor");
			tabs.fillContent(data_expl_panel, "explor");

		} else {

			tabs.fillContent(spec_dat_panel, "general");
			tabs.fillContent(map, "general");
			tabs.fillContent(info_view, "general");
			tabs.fillContent(eav_data, "metadata");
			tabs.fillContent(new WelcomePage(this), "explor");

//			pic_viewer = new PicViewer(s, this);
			tabs.fillContent(pic_panel, "metadata");

		}

		specPlot = null;

	}

	private ArrayList<EAV_Attribute> getEAVAttributes(Spectrum s2) {

		ArrayList<EAV_Attribute> attributes = new ArrayList<EAV_Attribute>();

		for (MetaParameter mp : s.getMetadata().getEntries()) {

			EAV_Attribute attribute = new EAV_Attribute();
			attribute.setAttrName(mp.getAttributeName());
			if (mp.getDefaultStorageField().equals("taxonomy_id")) {
				Long node = (Long) mp.getValue();
				int node_id = Integer.parseInt(node.toString());
				try {
					TaxonomyNodeObject tax = this.specchio_client
							.getTaxonomyNode(node_id);
					attribute.setAttrValue(tax.getName());
				} catch (SPECCHIOClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				attribute.setAttrValue(mp.getValue());
			}
			attribute.setAttrField(mp.getDefaultStorageField());

			attributes.add(attribute);
		}

		return attributes;
	}

	public SpecchiowebsandboxApplication getApp() {
		return this;
	}

	public void showNotification(String notification) {
		this.getMainWindow().showNotification(notification);
	}

}
