package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import spaces.SensorAndInstrumentSpace;
import spaces.Space;
import specchio.MetaDatatype;
import specchio.SpaceFactory;
import specchio.Spectrum;

import com.invient.vaadin.charts.Gradient;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.Color.RGB;
import com.invient.vaadin.charts.Color.RGBA;
import com.invient.vaadin.charts.Gradient.LinearGradient.LinearColorStop;
import com.invient.vaadin.charts.InvientCharts.ChartZoomEvent;
import com.invient.vaadin.charts.InvientCharts.ChartZoomListener;
import com.invient.vaadin.charts.InvientCharts.DateTimePoint;
import com.invient.vaadin.charts.InvientCharts.DateTimeSeries;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig.AreaConfig;
import com.invient.vaadin.charts.InvientChartsConfig.CategoryAxis;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis;
import com.invient.vaadin.charts.InvientChartsConfig.HorzAlign;
import com.invient.vaadin.charts.InvientChartsConfig.Legend;
import com.invient.vaadin.charts.InvientChartsConfig.LineConfig;
import com.invient.vaadin.charts.InvientChartsConfig.MarkerState;
import com.invient.vaadin.charts.InvientChartsConfig.NumberXAxis;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Position;
import com.invient.vaadin.charts.InvientChartsConfig.SeriesState;
import com.invient.vaadin.charts.InvientChartsConfig.SymbolMarker;
import com.invient.vaadin.charts.InvientChartsConfig.VertAlign;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxisDataLabel;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.DateTimePlotBand;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.Grid;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.DateTimePlotBand.DateTimeRange;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine.NumberValue;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.Margin;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.ZoomType;
import com.invient.vaadin.charts.InvientChartsConfig.Legend.Layout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button;

import eav_db.DatabaseConnection;
import eav_db.QueryBuilderBaseClass;
import eav_db.connection_details_class;

public class SpectrumPlot extends Panel {

	InvientCharts chart = null;

	private double[] wvl = null;

	private double[] data = null;

	public SpectrumPlot() {
		// InvientChartsConfig chartConfig = new InvientChartsConfig();
		// chartConfig.getGeneralChartConfig().setType(SeriesType.LINE);
		// chartConfig.getGeneralChartConfig().setZoomType(ZoomType.XY);
		// chartConfig.getGeneralChartConfig().setMargin(new Margin());
		// chartConfig.getGeneralChartConfig().getMargin().setRight(250);
		// chartConfig.getGeneralChartConfig().getMargin().setBottom(50);
		//
		// chartConfig.getTitle().setX(-20);
		// chartConfig.getTitle().setText("Spectrum Plot");
		// chartConfig.getSubtitle().setText("Source: SPECCHIO");
		// chartConfig.getTitle().setX(-20);
		//
		// NumberXAxis wvlAxis = new NumberXAxis();
		// wvlAxis.setTitle(new AxisTitle("Wavelength [nm]"));
		// // categoryAxis.setCategories(Arrays.asList("Jan", "Feb", "Mar",
		// "Apr",
		// // "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
		// LinkedHashSet<XAxis> xAxesSet = new
		// LinkedHashSet<InvientChartsConfig.XAxis>();
		// xAxesSet.add(wvlAxis);
		// chartConfig.setXAxes(xAxesSet);
		//
		// NumberYAxis reflectanceAxis = new NumberYAxis();
		// reflectanceAxis.setTitle(new AxisTitle("Reflectance"));
		// NumberPlotLine plotLine = new NumberPlotLine("Test");
		// plotLine.setValue(new NumberValue(0.0));
		// plotLine.setWidth(1);
		// plotLine.setZIndex(1);
		// plotLine.setColor(new RGB(128,128,128));
		// reflectanceAxis.addPlotLine(plotLine);
		// LinkedHashSet<YAxis> yAxesSet = new
		// LinkedHashSet<InvientChartsConfig.YAxis>();
		// yAxesSet.add(reflectanceAxis);
		// chartConfig.setYAxes(yAxesSet);
		//
		// Legend legend = new Legend();
		// legend.setLayout(Layout.VERTICAL);
		// Position legendPos = new Position();
		// legendPos.setAlign(HorzAlign.RIGHT);
		// legendPos.setVertAlign(VertAlign.TOP);
		// legendPos.setX(-10);
		// legendPos.setY(100);
		// legend.setPosition(legendPos);
		// legend.setBorderWidth(0);
		// chartConfig.setLegend(legend);
		//
		// // Series data label formatter
		// LineConfig lineCfg = new LineConfig();
		// SymbolMarker marker = new SymbolMarker(false);
		// lineCfg.setMarker(marker);
		// marker.setHoverState(new MarkerState());
		// marker.getHoverState().setEnabled(true);
		// marker.getHoverState().setRadius(5);
		// chartConfig.addSeriesConfig(lineCfg);
		// // Tooltip formatter
		// chartConfig
		// .getTooltip()
		// .setFormatterJsFunc(
		// "function() { "
		// + " return '<b>' + this.series.name + '</b><br/>'  " +
		// "+ $wnd.Highcharts.numberFormat(this.x, 2)  + ' nm: '" +
		// "+ $wnd.Highcharts.numberFormat(this.y, 4)"
		// + "}");
		//
		// chart = new InvientCharts(chartConfig);
	}

	public void generatePlot(Spectrum s, boolean full_res) {

		if (s.measurement_unit_id == 1) {
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

			NumberXAxis wvlAxis = new NumberXAxis();
			wvlAxis.setTitle(new AxisTitle("Wavelength [nm]"));
			// categoryAxis.setCategories(Arrays.asList("Jan", "Feb", "Mar",
			// "Apr",
			// "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
			LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
			xAxesSet.add(wvlAxis);
			chartConfig.setXAxes(xAxesSet);

			NumberYAxis reflectanceAxis = new NumberYAxis();
			reflectanceAxis.setTitle(new AxisTitle("Reflectance"));
			NumberPlotLine plotLine = new NumberPlotLine("Test");
			plotLine.setValue(new NumberValue(0.0));
			plotLine.setWidth(1);
			plotLine.setZIndex(1);
			plotLine.setColor(new RGB(128, 128, 128));
			reflectanceAxis.addPlotLine(plotLine);
			reflectanceAxis.setMax(1.2);
			reflectanceAxis.setMin(0.0);
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
									+ "+ $wnd.Highcharts.numberFormat(this.x, 2)  + ' nm: '"
									+ "+ $wnd.Highcharts.numberFormat(this.y, 4)"
									+ "}");

			chart = new InvientCharts(chartConfig);

		} else {
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

			NumberXAxis wvlAxis = new NumberXAxis();
			wvlAxis.setTitle(new AxisTitle("Wavelength [nm]"));
			// categoryAxis.setCategories(Arrays.asList("Jan", "Feb", "Mar",
			// "Apr",
			// "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
			LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
			xAxesSet.add(wvlAxis);
			chartConfig.setXAxes(xAxesSet);

			NumberYAxis reflectanceAxis = new NumberYAxis();
			reflectanceAxis.setTitle(new AxisTitle("Radiance"));
			NumberPlotLine plotLine = new NumberPlotLine("Test");
			plotLine.setValue(new NumberValue(0.0));
			plotLine.setWidth(1);
			plotLine.setZIndex(1);
			plotLine.setColor(new RGB(128, 128, 128));
			reflectanceAxis.addPlotLine(plotLine);
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
									+ "+ $wnd.Highcharts.numberFormat(this.x, 2)  + ' nm: '"
									+ "+ $wnd.Highcharts.numberFormat(this.y, 4)"
									+ "}");

			chart = new InvientCharts(chartConfig);

		}

		XYSeries seriesData = getSeries(s, s.file_name, full_res);

		chart.addSeries(seriesData);

	}

	public void addPlot(Spectrum s, boolean full_res) {
		XYSeries seriesData = getSeries(s, s.file_name, full_res);
		chart.addSeries(seriesData);

	}

	private static LinkedHashSet<DecimalPoint> getPoints(Series series,
			double[] wvls, double[] data) {
		LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
		for (int i = 0; i < wvls.length; i++) {
			points.add(new DecimalPoint(series, wvls[i], data[i]));
		}
		return points;
	}

	public XYSeries getSeries(Spectrum s, MetaDatatype<String> filename,
			boolean full_res) {

		XYSeries seriesData = new XYSeries(filename.toString());

		SpaceFactory sf = SpaceFactory.getInstance();

		ArrayList<Space> spaces = null;
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(s.spectrum_id);
		spaces = sf.create_spaces(ids);

		ListIterator<Space> spaces_li = spaces.listIterator();

		spaces.Space space = spaces_li.next();

		space.load_data();

		double[][] vectors = space.get_array();

		data = vectors[0];

		SensorAndInstrumentSpace instr_space = (SensorAndInstrumentSpace) space;

		wvl = instr_space.get_wvls();
		
		LinkedHashSet<DecimalPoint> points = null;

		if (!full_res) {
			double[] resampled_data = resampleVector(data);

			double[] resampled_wvl = resampleWVLVector(wvl);

			points = getPoints(seriesData,
					resampled_wvl, resampled_data);
		} else {
			points = getPoints(seriesData, wvl, data);
		}

		

		seriesData.setSeriesPoints(points);

		return seriesData;

	}

	public double[] resampleVector(double[] data) {
		ArrayList<Double> refl = new ArrayList<Double>();

		for (int i = 0; i < data.length; i = i + 10) {
			refl.add(data[i]);
		}
		int size = refl.size();

		double[] resampled = new double[size];

		for (int i = 0; i < size; i++) {
			resampled[i] = refl.get(i);
		}

		return resampled;

	}

	public double[] resampleWVLVector(double[] wvls) {
		ArrayList<Double> wvl = new ArrayList<Double>();

		for (int i = 0; i < wvls.length; i = i + 10) {
			wvl.add(wvls[i]);
		}

		int size = wvl.size();

		double[] resampled = new double[size];

		for (int i = 0; i < size; i++) {
			resampled[i] = wvl.get(i);
		}

		return resampled;

	}

	public InvientCharts getChart() {
		return chart;
	}

	public double[] get_wvl_vector() {
		return wvl;
	}

	

}
