package com.example.specchiowebsandbox.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.Color.RGB;
import com.invient.vaadin.charts.Color.RGBA;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis;
import com.invient.vaadin.charts.InvientChartsConfig.HorzAlign;
import com.invient.vaadin.charts.InvientChartsConfig.Legend;
import com.invient.vaadin.charts.InvientChartsConfig.LineConfig;
import com.invient.vaadin.charts.InvientChartsConfig.MarkerState;
import com.invient.vaadin.charts.InvientChartsConfig.NumberXAxis;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Position;
import com.invient.vaadin.charts.InvientChartsConfig.ScatterConfig;
import com.invient.vaadin.charts.InvientChartsConfig.SymbolMarker;
import com.invient.vaadin.charts.InvientChartsConfig.VertAlign;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine.NumberValue;
import com.invient.vaadin.charts.InvientChartsConfig.DateTimeAxis.DateTimeLabelFormat;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.Margin;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.ZoomType;
import com.invient.vaadin.charts.InvientChartsConfig.Legend.Layout;

public class ScatterPlot {

	private InvientCharts chart;
	
	private DecimalFormat df;
	

	public void generatePlot(String parameter1, String parameter2, int band_no_param1, int band_no_param2,
			double[] wvl, double[][] vectors) {
		
		String x_unit;
		String y_unit;
		
		String param1_legend_entry;
		String param2_legend_entry;
		
		df = new DecimalFormat("#0.00");
		
		if (parameter1.equals("Reflectance")){
			x_unit = "nm";
			param1_legend_entry = "R @ " + df.format(wvl[band_no_param1-1]);
		} else {
			x_unit = "";
			param1_legend_entry = parameter1;
		}
		
		if (parameter2.equals("Reflectance")){
			y_unit = "nm";
			param2_legend_entry = "R @ " + df.format(wvl[band_no_param2-1]);
		} else {
			y_unit = "";
			param2_legend_entry = parameter2;
		}
		
		InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(SeriesType.SCATTER);
        chartConfig.getGeneralChartConfig().setZoomType(ZoomType.XY);
        chartConfig.getGeneralChartConfig().setMargin(new Margin());
		chartConfig.getGeneralChartConfig().getMargin().setRight(250);
		chartConfig.getGeneralChartConfig().getMargin().setBottom(50);

        
        chartConfig.getTitle().setText(
                parameter1 + " vs. " + parameter2);
        chartConfig.getSubtitle().setText("Source: SPECCHIO");

        chartConfig.getTooltip().setFormatterJsFunc(
                "function() {"
                        + " return ''"
                		+ "+ $wnd.Highcharts.numberFormat(this.x, 4) + '; '"
                        + "+ $wnd.Highcharts.numberFormat(this.y, 4) ;"
                        + "}");
        
        
       

        NumberXAxis xAxis = new NumberXAxis();
        if (parameter1.equals("Reflectance")){
        	xAxis.setTitle(new AxisTitle(parameter1 + " @ " + df.format(wvl[band_no_param1 - 1]) + "nm"));
        } else {
        	xAxis.setTitle(new AxisTitle("Parameter1"));
        }
        xAxis.setStartOnTick(true);
        xAxis.setEndOnTick(true);
        xAxis.setShowLastLabel(true);
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
        xAxesSet.add(xAxis);
        chartConfig.setXAxes(xAxesSet);

        NumberYAxis yAxis = new NumberYAxis();
        if (parameter2.equals("Reflectance")){
        	yAxis.setTitle(new AxisTitle(parameter2 + " @ " + df.format(wvl[band_no_param2 - 1]) + "nm"));
        } else {
        	yAxis.setTitle(new AxisTitle("Parameter2"));
        }
        LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<InvientChartsConfig.YAxis>();
        yAxesSet.add(yAxis);
        chartConfig.setYAxes(yAxesSet);

        Legend legend = new Legend();
        legend.setLayout(Layout.VERTICAL);
        Position legendPos = new Position();
        legendPos.setAlign(HorzAlign.RIGHT);
        legendPos.setVertAlign(VertAlign.TOP);
        legendPos.setX(-10);
		legendPos.setY(100);
        legend.setPosition(legendPos);
        legend.setFloating(true);
        legend.setBorderWidth(1);
        legend.setBackgroundColor(new RGB(255, 255, 255));
        chartConfig.setLegend(legend);

        ScatterConfig scatterCfg = new ScatterConfig();

        SymbolMarker marker = new SymbolMarker(5);
        scatterCfg.setMarker(marker);
        marker.setHoverState(new MarkerState());
        marker.getHoverState().setEnabled(true);
        marker.getHoverState().setLineColor(new RGB(100, 100, 100));
        chartConfig.addSeriesConfig(scatterCfg);

        chart = new InvientCharts(chartConfig);

        ScatterConfig scatter = new ScatterConfig();
        XYSeries seriesData = getSeries(param1_legend_entry + " vs. " + param2_legend_entry, wvl, vectors, band_no_param1, band_no_param2);;
        
        chart.addSeries(seriesData);
        


		
		
		
		chart.addSeries(seriesData);
		

	}

	private XYSeries getSeries(String parameter, double[] wvl, double[][] vectors, int band_no_param1, int band_no_param2) {
		
		
		
		XYSeries series = null;
		
		
		
		series = new XYSeries(parameter);
		
		ArrayList<Double> xdata = new ArrayList<Double>();
		ArrayList<Double> ydata = new ArrayList<Double>();
		
		for(int i = 0; i < vectors.length; i++){
			xdata.add(vectors[i][band_no_param1]);
			ydata.add(vectors[i][band_no_param2]);
		}
		
		
		LinkedHashSet<DecimalPoint> points = getPoints(series, xdata, ydata);
		
		series.setSeriesPoints(points);
		
		
		
		
		
		
		
		
		return series;
	}
	
	 private static LinkedHashSet<DecimalPoint> getPoints(Series series,
	            ArrayList<Double> time, ArrayList<Double> data) {
	        LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
	        for (int i = 0; i < data.size(); i++) {
	            points.add(new DecimalPoint(series, time.get(i), data.get(i)));
	        }
	        return points;
	    }

	public InvientCharts getChart() {
		// TODO Auto-generated method stub
		return chart;
	}

}
