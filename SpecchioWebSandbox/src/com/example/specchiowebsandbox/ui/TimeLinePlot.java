package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.ListIterator;

import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.gui.SpectralDataBrowser.SpectralDataBrowserNode;
import ch.specchio.spaces.SensorAndInstrumentSpace;
import ch.specchio.spaces.Space;
import ch.specchio.types.MetaParameter;
//import ch.specchio.factories.SPECCHIOFactory;
//import ch.specchio.factories.SpaceFactory;
//import specchio.SpecchioMetadataServices;
import ch.specchio.types.Spectrum;
import ch.specchio.types.spectral_node_object;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.example.specchiowebsandbox.data.SpectrumData;
import com.example.specchiowebsandbox.data.SpectrumMetadata;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.DateTimePoint;
import com.invient.vaadin.charts.InvientCharts.DateTimeSeries;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.Color.RGB;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis.DateTimeLabelFormat;
import com.invient.vaadin.charts.InvientChartsConfig.HorzAlign;
import com.invient.vaadin.charts.InvientChartsConfig.Legend;
import com.invient.vaadin.charts.InvientChartsConfig.LineConfig;
import com.invient.vaadin.charts.InvientChartsConfig.MarkerState;
import com.invient.vaadin.charts.InvientChartsConfig.NumberXAxis;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Position;
import com.invient.vaadin.charts.InvientChartsConfig.SymbolMarker;
import com.invient.vaadin.charts.InvientChartsConfig.VertAlign;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine.NumberValue;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.Margin;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.ZoomType;
import com.invient.vaadin.charts.InvientChartsConfig.Legend.Layout;
import com.vaadin.ui.Panel;

//import eav_db.Attributes;
//import eav_db.EAVDBServices;
//import eav_db.Attributes.attribute;

public class TimeLinePlot extends Panel {

	InvientCharts chart = null;

//	private double[] wvl = null;
	
	private SpecchiowebsandboxApplication app;

	public TimeLinePlot(SpecchiowebsandboxApplication app) {
		this.app = app;
	}

	public void generatePlot(Space space, String parameter, int band_no, Object[] selected_items) {

		InvientChartsConfig chartConfig = new InvientChartsConfig();
		chartConfig.getGeneralChartConfig().setType(SeriesType.LINE);
		chartConfig.getGeneralChartConfig().setZoomType(ZoomType.XY);
		chartConfig.getGeneralChartConfig().setMargin(new Margin());
		chartConfig.getGeneralChartConfig().getMargin().setRight(250);
		chartConfig.getGeneralChartConfig().getMargin().setBottom(50);

		chartConfig.getTitle().setX(-20);
		chartConfig.getTitle().setText("Spectrum Plot");
		chartConfig.getSubtitle().setText("Source: SPECCHIO");
		chartConfig.getTitle().setX(-20);

		DateTimeAxis detailXAxis = new DateTimeAxis();
		DateTimeLabelFormat dateTimeLabelFormat = new DateTimeLabelFormat();
		detailXAxis.setDateTimeLabelFormat(dateTimeLabelFormat);
        LinkedHashSet<XAxis> detailXAxes = new LinkedHashSet<InvientChartsConfig.XAxis>();
        detailXAxes.add(detailXAxis);
        chartConfig.setXAxes(detailXAxes);

		NumberYAxis reflectanceAxis = new NumberYAxis();
		reflectanceAxis.setTitle(new AxisTitle(parameter));
		NumberPlotLine plotLine = new NumberPlotLine("Test");
		plotLine.setValue(new NumberValue(0.0));
		plotLine.setWidth(1);
		plotLine.setZIndex(1);
		plotLine.setColor(new RGB(128, 128, 128));
		reflectanceAxis.addPlotLine(plotLine);
//		reflectanceAxis.setMax(1.2);
//		reflectanceAxis.setMin(0.0);
		LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<InvientChartsConfig.YAxis>();
		yAxesSet.add(reflectanceAxis);
		chartConfig.setYAxes(yAxesSet);

		Legend legend = new Legend();
		legend.setLayout(Layout.VERTICAL);
		Position legendPos = new Position();
		legendPos.setAlign(HorzAlign.RIGHT);
		legendPos.setVertAlign(VertAlign.TOP);
		legendPos.setX(-10);
		legendPos.setY(100);
		legend.setPosition(legendPos);
		legend.setBorderWidth(0);
		chartConfig.setLegend(legend);

		// Series data label formatter
		LineConfig lineCfg = new LineConfig();
		SymbolMarker marker = new SymbolMarker(false);
		lineCfg.setMarker(marker);
		marker.setHoverState(new MarkerState());
		marker.getHoverState().setEnabled(true);
		marker.getHoverState().setRadius(5);
		chartConfig.addSeriesConfig(lineCfg);
		// Tooltip formatter
		chartConfig
				.getTooltip()
				.setFormatterJsFunc(
						"function() { "
								+ " return '<b>' + this.series.name + '</b><br/>'  "
								+ "+ $wnd.Highcharts.dateFormat('%d.%m.%Y %H:%M:%S',this.x)  + ': '"
								+ "+ $wnd.Highcharts.numberFormat(this.y, 4)"
								+ "}");

		chart = new InvientCharts(chartConfig);
		
		XYSeries seriesData = null;
		
		if(band_no > 0){
			seriesData = getSeries(space, parameter, selected_items, band_no);
		} else {
			seriesData = getSeries(parameter, selected_items);
		}
		
		chart.addSeries(seriesData);
		

	}
	
	private XYSeries getSeries(String parameter, Object[] selected_items) {
		
		XYSeries series = null;
		
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Long> capture_dates_milis = new ArrayList<Long>();
		
		
		for(Spectrum spec : app.spectra){
			MetaParameter entry = spec.getMetadata().get_entry(parameter);
			//Only double values can be displayed for now...
			if(entry.getDefaultStorageField().equals("double_val")){
				values.add((Double) spec.getMetadata().get_entry(parameter).getValue());
			}
			Date date = (Date)spec.getMetadata().get_entry("Acquisition Time").getValue();
			capture_dates_milis.add(date.getTime());
		}

		series = new XYSeries(parameter);
		
		
		LinkedHashSet<DecimalPoint> points = getPoints(series, capture_dates_milis, values);
		
		series.setSeriesPoints(points);
		
		
		
		
		return series;
	}

	public void generatePlot(String parameter, Object[] selected_items){
		
	}

	private XYSeries getSeries(Space space, String parameter, Object[] selected_items, int band_no) {
		
		SensorAndInstrumentSpace si_space = (SensorAndInstrumentSpace)space;
		
		XYSeries series = null;
		
		ArrayList<Long> capture_dates_milis = new ArrayList<Long>();
		ArrayList<Double> data = new ArrayList<Double>();
		
		double[] wvls = null;
		
		if (parameter.equalsIgnoreCase("Reflectance")){
			
			for(Spectrum spec : app.spectra){
				double[] vectors = si_space.getVector(spec.spectrum_id);
				wvls = si_space.getAverageWavelengths();
				
				Date date = (Date)spec.getMetadata().get_entry("Acquisition Time").getValue();
				capture_dates_milis.add(date.getTime());
				
				data.add(vectors[band_no-1]);
				
			}
			
			series = new XYSeries(Double.toString(wvls[band_no-1]) + " nm");
			
			
			LinkedHashSet<DecimalPoint> points = getPoints(series, capture_dates_milis, data);
			
			series.setSeriesPoints(points);
		}
				
		
		return series;
	}
	
	 private static LinkedHashSet<DecimalPoint> getPoints(Series series,
	            ArrayList<Long> time, ArrayList<Double> data) {
	        LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
	        for (int i = 0; i < data.size(); i++) {
	            points.add(new DecimalPoint(series, time.get(i), data.get(i)));
	        }
	        return points;
	    }
	 
	 public InvientCharts getChart(){
		 return chart;
	 }
}
