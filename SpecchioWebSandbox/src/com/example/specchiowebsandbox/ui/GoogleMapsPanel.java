package com.example.specchiowebsandbox.ui;

import java.awt.geom.Point2D;

import org.vaadin.hezamu.googlemapwidget.GoogleMap;
import org.vaadin.hezamu.googlemapwidget.GoogleMap.MapControl;
import org.vaadin.hezamu.googlemapwidget.overlay.BasicMarker;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.Layer;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.PointVector;
import org.vaadin.vol.Vector;
import org.vaadin.vol.VectorLayer;

import org.vaadin.vol.client.wrappers.LonLat;
import org.vaadin.vol.client.wrappers.Projection;

//import org.vaadin.hezamu.googlemapwidget.GoogleMap;
//import org.vaadin.hezamu.googlemapwidget.overlay.BasicMarker;

import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class GoogleMapsPanel extends VerticalLayout{
	
	private Panel panel;
	
	public GoogleMapsPanel(SpecchiowebsandboxApplication app, Spectrum spec){
		
		setSpacing(true);
		
		setHeight(320, Sizeable.UNITS_PIXELS);
		setWidth("100%");
		
		
		// Create a grid layout
        final GridLayout grid = new GridLayout(1, 4);
        grid.setSpacing(false);

        // The style allows us to visualize the cell borders in this example.
        grid.addStyleName("gridexample");

        grid.setWidth("100%");
        grid.setHeight("250px");
		
		panel = new Panel("Map");
		panel.setHeight(320, Sizeable.UNITS_PIXELS);
		panel.setWidth("100%");
		
		VerticalLayout layout = (VerticalLayout) panel.getContent();
		layout.setMargin(true);
		layout.setSpacing(false);
//		addComponent(panel);
		
//		GoogleMap map = new GoogleMap(app, new Point2D.Double(8.54908, 47.39730), 8);
//		
//		map.setWidth("640px");
//		map.setHeight("480px");
//		
//		map.addMarker(new BasicMarker(1L, new Point2D.Double(8.54908, 47.39730), "Test marker"));
//		
//		grid.addComponent(map);
		
		
		
//		final OpenLayersMap map = new OpenLayersMap();
//		map.setApiProjection("EPSG:4326");
//		
//		GoogleStreetMapLayer google_map = new GoogleStreetMapLayer();
//		
//		google_map.setParent(map);
//		
//		map.addLayer(google_map);
//		
//		MarkerLayer markers = new MarkerLayer();
//		
//		map.addLayer(markers);
//		
//	
//		
//	
//		
//		VectorLayer vectors = new VectorLayer();
//		map.addLayer(vectors);
//		
//		Point point = new Point(meta.longitude, meta.latitude);
//		PointVector pvec = new PointVector();
//		pvec.setProjection("EPSG:4326");
//		pvec.setPoints(point);
//
//		vectors.addVector(pvec);
//		
//
//		
//		Marker test = new Marker(meta.longitude, meta.latitude);
//		
//		
//
//		
//		markers.addMarker(test);
//		
//		
//		map.setCenter(meta.longitude, meta.latitude);
//		
//		map.setZoom(3);
//		
//		map.setHeight(240, UNITS_PIXELS);
//		map.setWidth("100%");
		
		Double longitude = (Double)spec.getMetadata().get_entry("Longitude").getValue();
		Double latitude = (Double)spec.getMetadata().get_entry("Latitude").getValue();
		
		GoogleMap map = new GoogleMap(app, new Point2D.Double(-1*longitude,latitude), 2);
		map.setWidth("100%");
		map.setHeight("250px");
		
		map.addMarker(new BasicMarker(1L,new Point2D.Double(-1*longitude,latitude),"Test Marker"));
		
		grid.addComponent(map);
		
		panel.addComponent(grid);
		
		addComponent(panel);
		
	}

}
