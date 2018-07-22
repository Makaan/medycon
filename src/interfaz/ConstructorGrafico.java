package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ConstructorGrafico {
	
	public static XYChart getChart(ArrayList<Date> xData, ArrayList<Float> yData, String title) {
		
		// Create Chart
	    XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle("Fecha").yAxisTitle("Nivel").build();
	 
	    // Customize Chart
	    setEstilo(chart);
	 
	    // Series
	    XYSeries series = chart.addSeries("Nivel", xData, yData);
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
		
		XYStyler styler = chart.getStyler();
		
		styler.setPlotGridLinesColor(new Color(255, 255, 255));
		styler.setChartBackgroundColor(Color.WHITE);
		styler.setChartTitleBoxVisible(true);
		styler.setPlotGridLinesVisible(false);
		styler.setLegendPosition(LegendPosition.InsideSE);
		styler.setLegendSeriesLineLength(12);
		styler.setDatePattern("E dd - h:mm a");
		styler.setDecimalPattern("% ");
		styler.setYAxisMax(1.0);
		styler.setYAxisMin(0.0);
		
	}
}
