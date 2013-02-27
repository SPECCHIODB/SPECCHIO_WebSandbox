package com.example.specchiowebsandbox;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;

import com.example.specchiowebsandbox.ui.NavigationTree;
import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.Color.RGB;
import com.invient.vaadin.charts.InvientCharts.ChartClickEvent;
import com.invient.vaadin.charts.InvientCharts.ChartResetZoomEvent;
import com.invient.vaadin.charts.InvientCharts.ChartSVGAvailableEvent;
import com.invient.vaadin.charts.InvientCharts.ChartZoomEvent;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.PieChartLegendItemClickEvent;
import com.invient.vaadin.charts.InvientCharts.PointClickEvent;
import com.invient.vaadin.charts.InvientCharts.PointRemoveEvent;
import com.invient.vaadin.charts.InvientCharts.PointSelectEvent;
import com.invient.vaadin.charts.InvientCharts.PointUnselectEvent;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesClickEvent;
import com.invient.vaadin.charts.InvientCharts.SeriesHideEvent;
import com.invient.vaadin.charts.InvientCharts.SeriesLegendItemClickEvent;
import com.invient.vaadin.charts.InvientCharts.SeriesShowEvent;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig.CategoryAxis;
import com.invient.vaadin.charts.InvientChartsConfig.HorzAlign;
import com.invient.vaadin.charts.InvientChartsConfig.Legend;
import com.invient.vaadin.charts.InvientChartsConfig.LineConfig;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Position;
import com.invient.vaadin.charts.InvientChartsConfig.VertAlign;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.NumberPlotLine.NumberValue;
import com.invient.vaadin.charts.InvientChartsConfig.GeneralChartConfig.Margin;
import com.invient.vaadin.charts.InvientChartsConfig.Legend.Layout;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class SpecchiowebsandboxWin extends Window{
	
	private final HorizontalSplitPanel mainSplit;
    private final VerticalLayout leftLayout;
    private final VerticalLayout rightLayout;
    NavigationTree navtree;
    
    public SpecchiowebsandboxWin(SpecchiowebsandboxApplication app) {
        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);
        setSizeFull();
        mainLayout.setSizeFull();
        setCaption("Specchio Web");

        HorizontalLayout infoBar = new HorizontalLayout();
        mainLayout.addComponent(infoBar);
        infoBar.setHeight("100px");
        infoBar.setWidth("100%");
        Label lblAppTitle = new Label("Demo Page for SpecchioWebApp");
        lblAppTitle.setSizeFull();
        lblAppTitle.setStyleName("v-label-app-title");
        infoBar.addComponent(lblAppTitle);

//        Embedded em = new Embedded("", new ThemeResource("graphics/SPECCHIO_Icon_Mid_Res_small.jpg"));
//        infoBar.addComponent(em);
//        infoBar.setComponentAlignment(em, Alignment.TOP_LEFT);
//        infoBar.setExpandRatio(em, 1);


        mainSplit = new HorizontalSplitPanel();
        mainSplit.setSizeFull();
        mainLayout.addComponent(mainSplit);
        mainLayout.setExpandRatio(mainSplit, 1);

        leftLayout = new VerticalLayout();
        leftLayout.setSpacing(true);
        mainSplit.setFirstComponent(leftLayout);

        rightLayout = new VerticalLayout();
        rightLayout.setSpacing(true);
        rightLayout.setMargin(true);
        mainSplit.setSecondComponent(rightLayout);

        mainSplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);

        navtree = new NavigationTree(app);
        leftLayout.addComponent(navtree);

   
        setTheme("runo");
        
        showLine();

    }
    
    @Override
    public void attach() {
        super.attach();
        isAppRunningOnGAE = getSpecchiowebApp().isAppRunningOnGAE();
    }

    private boolean isAppRunningOnGAE;

    public boolean isAppRunningOnGAE() {
        return isAppRunningOnGAE;
    }

    private SpecchiowebsandboxApplication getSpecchiowebApp() {
        return (SpecchiowebsandboxApplication) getApplication();
    }
    
    private void showLine() {
        InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(SeriesType.LINE);
        chartConfig.getGeneralChartConfig().setMargin(new Margin());
        chartConfig.getGeneralChartConfig().getMargin().setRight(130);
        chartConfig.getGeneralChartConfig().getMargin().setBottom(25);

        chartConfig.getTitle().setX(-20);
        chartConfig.getTitle().setText("Monthly Average Temperature");
        chartConfig.getSubtitle().setText("Source: WorldClimate.com");
        chartConfig.getTitle().setX(-20);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setCategories(Arrays.asList("Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
        xAxesSet.add(categoryAxis);
        chartConfig.setXAxes(xAxesSet);

        NumberYAxis numberYAxis = new NumberYAxis();
        numberYAxis.setTitle(new AxisTitle("Temperature (°C)"));
        NumberPlotLine plotLine = new NumberPlotLine("TempAt0");
        plotLine.setValue(new NumberValue(0.0));
        plotLine.setWidth(1);
        plotLine.setZIndex(1);
        plotLine.setColor(new RGB(128, 128, 128));
        numberYAxis.addPlotLine(plotLine);
        LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<InvientChartsConfig.YAxis>();
        yAxesSet.add(numberYAxis);
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
        chartConfig.addSeriesConfig(lineCfg);
        // Tooltip formatter
        chartConfig
                .getTooltip()
                .setFormatterJsFunc(
                        "function() { "
                                + " return '<b>' + this.series.name + '</b><br/>' +  this.x + ': '+ this.y +'°C'"
                                + "}");

        InvientCharts chart = new InvientCharts(chartConfig);

        XYSeries seriesData = new XYSeries("Tokyo");
        seriesData.setSeriesPoints(getPoints(seriesData, 7.0, 6.9, 9.5, 14.5,
                18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6));
        chart.addSeries(seriesData);

        seriesData = new XYSeries("New York");
        seriesData.setSeriesPoints(getPoints(seriesData, -0.2, 0.8, 5.7, 11.3,
                17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5));
        chart.addSeries(seriesData);

        seriesData = new XYSeries("Berlin");
        seriesData.setSeriesPoints(getPoints(seriesData, -0.9, 0.6, 3.5, 8.4,
                13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0));
        chart.addSeries(seriesData);

        seriesData = new XYSeries("London");
        seriesData.setSeriesPoints(getPoints(seriesData, 3.9, 4.2, 5.7, 8.5,
                11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8));
        chart.addSeries(seriesData);

        addChart(chart);
    }
    
    private static LinkedHashSet<DecimalPoint> getPoints(Series series,
            double... values) {
        LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
        for (double value : values) {
            points.add(new DecimalPoint(series, value));
        }
        return points;
    }
    
    private void addChart(InvientCharts chart, boolean isPrepend,
            boolean isRegisterEvents) {
        addChart(chart, isPrepend, isRegisterEvents, true);
    }

    private void addChart(InvientCharts chart, boolean isPrepend,
            boolean isRegisterEvents, boolean isRegisterSVGEvent) {
        addChart(chart, isPrepend, isRegisterEvents, isRegisterSVGEvent, true);
    }

    private void addChart(InvientCharts chart, boolean isPrepend,
            boolean isRegisterEvents, boolean isRegisterSVGEvent,
            boolean isSetHeight) {
//        // add events
//        if (isRegisterEvents) {
//            registerEvents(chart);
//        }

        chart.setSizeFull();
        chart.setStyleName("v-chart-min-width");
        if (isSetHeight) {
            chart.setHeight("410px");
        }

        if (isPrepend) {
            rightLayout.setStyleName("v-chart-master-detail");
            rightLayout.addComponentAsFirst(chart);
        } else {
            rightLayout.removeStyleName("v-chart-master-detail");
//            emptyEventLog();
            rightLayout.removeAllComponents();
            // Add chart
            rightLayout.addComponent(chart);
            // Add "Get SVG" button and register SVG available event
            if (isRegisterSVGEvent) {
                registerSVGAndPrintEvent(chart);
            }
            // Server events log
//            rightLayout
//                    .addComponent(new Label("Events received by the server:"));
//            rightLayout.addComponent(eventLog);
        }
    }

    private void addChart(InvientCharts chart) {
        addChart(chart, false, true);
    }
    
    private void registerSVGAndPrintEvent(final InvientCharts chart) {
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setWidth("100%");
        gridLayout.setSpacing(true);
        Button svgBtn;
        gridLayout.addComponent(svgBtn = new Button("Get SVG"));
        gridLayout.setComponentAlignment(svgBtn, Alignment.MIDDLE_RIGHT);
        Button printBtn;
        gridLayout.addComponent(printBtn = new Button("Print"));
        gridLayout.setComponentAlignment(printBtn, Alignment.MIDDLE_LEFT);
        rightLayout.addComponent(gridLayout);
//        svgBtn.addListener(new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                chart.addListener(new InvientCharts.ChartSVGAvailableListener() {
//
//                    @Override
//                    public void svgAvailable(
//                            ChartSVGAvailableEvent chartSVGAvailableEvent) {
//                        logEventInfo("[svgAvailable]" + " svg -> "
//                                + chartSVGAvailableEvent.getSVG());
//                    }
//                });
//            }
//        });
//        printBtn.addListener(new Button.ClickListener() {
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                chart.print();
//            }
//        });
    }


        
        

        

        

    
    





}
