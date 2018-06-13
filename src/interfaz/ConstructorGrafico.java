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
		return new XYChartBuilder().width(800).height(600).xAxisTitle("Fecha").yAxisTitle("Altura").build();
	}
	
	public static XYChart getChart(ArrayList<Date> xData, ArrayList<Integer> yData, String title) {
		
		for(Integer i: yData) {
			System.out.println(i);
		}
		// Create Chart
	    XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle("Fecha").yAxisTitle("Altura").build();
	 
	    // Customize Chart
	    setEstilo(chart);
	 
	    // Series
	    XYSeries series = chart.addSeries("Altura", xData, yData);
	    setEstiloSerie(series);
	    
	 
	    return chart;
    }
	
	private static void setEstiloSerie(XYSeries series) {
	    series.setLineColor(XChartSeriesColors.BLUE);
	    series.setMarkerColor(Color.BLUE);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    series.setLineStyle(SeriesLines.SOLID);
		
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
		
	}
}
