package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ConstructorGrafico {
	
	public static XYChart getChart() {
		 
		// Create Chart
	    XYChart chart = new XYChartBuilder().width(800).height(600).title("Valores promedios ultima Hora").xAxisTitle("Fecha").yAxisTitle("Promedio").build();
	 
	    // Customize Chart
	    setEstilo(chart);
	 
	    // generates linear data
	    ArrayList<Date> xData = new ArrayList<Date>();
	    ArrayList<Double> yData = new ArrayList<Double>();
	 
	    DateFormat sdf = new SimpleDateFormat("h:mm a");
	    Date date = null;
	    for (int i = 1; i <= 10; i++) {
	    	try {
	    		date = sdf.parse("00:"+i+" PM");
		    } catch (ParseException e) {
		      e.printStackTrace();
		    }
			    xData.add(date);
			    yData.add(Math.random() * i);
	    }
	 
	    // Series
	    XYSeries series = chart.addSeries("Altura", xData, yData);
	    series.setLineColor(XChartSeriesColors.BLUE);
	    series.setMarkerColor(Color.ORANGE);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    series.setLineStyle(SeriesLines.SOLID);
	 
	    return chart;
    }
	
	private static void setEstilo(XYChart chart) {
		
		chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
	    chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
	    chart.getStyler().setChartBackgroundColor(Color.WHITE);
	    chart.getStyler().setChartTitleBoxVisible(true);
	    chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
	    chart.getStyler().setPlotGridLinesVisible(false);
	 
	    chart.getStyler().setAxisTickPadding(20);
	 
	    chart.getStyler().setAxisTickMarkLength(15);
	 
	    chart.getStyler().setPlotMargin(20);
	 
	    chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
	    chart.getStyler().setLegendFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
	    chart.getStyler().setLegendPosition(LegendPosition.InsideSE);
	    chart.getStyler().setLegendSeriesLineLength(12);
	    chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
	    chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
	    chart.getStyler().setDatePattern("h:mm a");
	    chart.getStyler().setDecimalPattern("%#0.");
		
	}
}
