//package com.example.specchiowebsandbox.data;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.ListIterator;
//
//import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
//
//import spaces.SensorAndInstrumentSpace;
//import spaces.Space;
//import specchio.SpaceFactory;
//import specchio.Spectrum;
//
//
//public class DataProcessor {
//	
//	private LinkedHashSet<DecimalPoint> spectrum = null;
//	private Spectrum spec = null;
//	
//	public DataProcessor(){
////		spec = s;
//		
//		
//	}
//	
//	public LinkedHashSet<DecimalPoint> writeSeriesData(Spectrum s){
//		LinkedHashSet<DecimalPoint> serie = new LinkedHashSet<DecimalPoint>();
//		
//		SpaceFactory sf = SpaceFactory.getInstance();
//		
//		ArrayList<Integer> ids = new ArrayList<Integer>();
//		
//		ids.add(s.spectrum_id);
//	
//		
//		ArrayList<Space> spaces = null;
//		try {
//			spaces = sf.create_spaces(ids);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		ListIterator<Space> spaces_li = spaces.listIterator();
//
//		spaces.Space space = spaces_li.next();
//
//		space.load_data();
//		
//
//		double[][] vectors = space.get_array();
//		
//		double[] data = vectors[0];
//		
//		SensorAndInstrumentSpace instr_space = (SensorAndInstrumentSpace) space;
//		
//		double[] wvl = instr_space.get_wvls();
//		
//		DecimalPoint p = new DecimalPoint(
//		
//		
//		for(int i = 0; i < data.length; i++){
//			DecimalPoint point = new DecimalPoint(wvl[i], data[i]);
//			serie.add(new DecimalPoint(wvl[i], data[i]));
//		}
//		
////		List<DataPoint> datapoints = new ArrayList<DataPoint>();
////		
////		for(int i = 0; i < data.length; i++){
////			DataPoint datapoint = new DataPoint();
////			datapoint.set_wvl(wvl[i]);
////			datapoint.set_value(data[i]);
////			datapoints.add(datapoint);
////		}
////		
////		System.out.println("ArrayList with Datapoints objects filled at: " + (System.currentTimeMillis() - time));
//		
//		
//		
//		
//		return serie;
//	}
//
//}
