package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.ListIterator;

import spaces.SensorAndInstrumentSpace;
import spaces.Space;
import specchio.SpaceFactory;
import specchio.SpecchioMetadataServices;
import specchio.Spectrum;

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

public class TimeLinePlot extends Panel {

	InvientCharts chart = null;

	private double[] wvl = null;

	public TimeLinePlot() {

	}

	public void generatePlot(String parameter, int band_no, Object[] selected_items) {

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
		reflectanceAxis.setTitle(new AxisTitle("Reflectance"));
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
								+ "+ $wnd.Highcharts.dateFormat('%d.%m.%Y %H:%M:%S',this.x)  + ' nm: '"
								+ "+ $wnd.Highcharts.numberFormat(this.y, 4)"
								+ "}");

		chart = new InvientCharts(chartConfig);
		
		XYSeries seriesData = getSeries(parameter, selected_items, band_no);
		
		chart.addSeries(seriesData);
		

	}

	private XYSeries getSeries(String parameter, Object[] selected_items, int band_no) {
		
		
		
		XYSeries series = null;
		
		if (parameter.equalsIgnoreCase("Wavelength")){
		
		ArrayList<SpectrumData> itemIds = new ArrayList<SpectrumData>();
		ArrayList<Spectrum> spectra = new ArrayList<Spectrum>();
		ArrayList<SpectrumMetadata> metadata = new ArrayList<SpectrumMetadata>();
		
		for(int i = 0; i < selected_items.length; i++){
			itemIds.add((SpectrumData)selected_items[i]);
			try {
				spectra.add(new Spectrum(itemIds.get(i).getSpectrum_id()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SpectrumMetadata meta = new SpectrumMetadata(spectra.get(i));
			meta.fillMetadata();
			metadata.add(meta);
		}
		
		SpaceFactory sf = SpaceFactory.getInstance();
		
		
		
		ArrayList<Space> spaces = null;
		ArrayList<Integer> ids = new ArrayList<Integer>();
//		ArrayList<double[][]> vectors = new ArrayList<double[][]>();
		ArrayList<Double> data = new ArrayList<Double>();
		
		ListIterator<Spectrum> li = spectra.listIterator();
		
		while (li.hasNext()){
			Spectrum spectrum = li.next();
			ids.add(spectrum.spectrum_id);
		}
		
		spaces = sf.create_spaces(ids);
		
		ListIterator<Space> spaces_li = spaces.listIterator();
		
//		while (spaces_li.hasNext()){
//			spaces_li.next().load_data();
//			
//		}
//		
//		for(int i = 0; i < spaces.size(); i++){
//			vectors.add(spaces.get(i).get_array());
//			data.add(vectors.get(i)[i]);
//		}
		
		spaces.Space space = spaces_li.next();
		
		space.load_data();
		
		double[][] vectors = space.get_array();
		
		SensorAndInstrumentSpace instr_space = (SensorAndInstrumentSpace) space;

		wvl = instr_space.get_wvls();
		
		
		for(int i = 0; i < vectors.length; i++){
			data.add(vectors[i][band_no-1]);
		}
		
		
		
		SpecchioMetadataServices sms = new SpecchioMetadataServices();
		
		ArrayList<Long> capture_dates_millis = new ArrayList<Long>();
		
		capture_dates_millis = sms.get_capture_times_in_millis(ids);
		
		
//		ArrayList<Date> time = new ArrayList<Date>();
//		
//		ListIterator<SpectrumMetadata> meta_li = metadata.listIterator();
//		
//		while(meta_li.hasNext()){
//			time.add(new Date(meta_li.next().capture_date.getTime()));
//		}
		
		series = new XYSeries(Double.toString(wvl[band_no-1]) + " nm");
		
		
		LinkedHashSet<DecimalPoint> points = getPoints(series, capture_dates_millis, data);
		
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
