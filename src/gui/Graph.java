package gui;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * Creates a graph of the amount of various states.
 * @author Nathaniel Brooke
 * @version 02-12-2017
 */
public class Graph {
	
	private LineChart<Number, Number> linechart;	
	private Map<String, Series<Number, Number>> lineData;
	private int tick;
	
	/**
	 * Initializes the graph.
	 */
	public Graph() {
		tick = 0;
		makeLineChart();
	}
		
	/**
	 * Adds a data set to the graph.
	 * Added data will be logged as 1 tick after previously added data.
	 * @param values a map of Strings representing series and their corresponding new values.
	 */
	public void addData(Map<String, Integer> values) {
		for(String s : values.keySet()) {
			if(!lineData.containsKey(s)) {
				Series<Number, Number> series = new Series<Number, Number>();
				series.setName(s);
				lineData.put(s, series);
				linechart.getData().add(series);
			}
			XYChart.Data<Number, Number> dataPt = new XYChart.Data<>(tick, values.get(s));
			lineData.get(s).getData().add(dataPt);
		}
		tick++;
	}
	
	/**
	 * Gets the line chart.
	 * @return LineChart JavaFX object.
	 */
	public LineChart<Number, Number> getLineChart() {
		return linechart;
	}
	
	/**
	 * Clears the graph and resets the time to zero.
	 */
	public void reset() {
		linechart.getData().clear();
		lineData.clear();
		tick = 0;
	}
	
	/**
	 * Sets up the line chart.
	 */
	private void makeLineChart() {
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
		linechart = new LineChart<Number, Number>(xAxis, yAxis);
		linechart.setPrefHeight(100);
		linechart.setLegendVisible(true);
		linechart.setLegendSide(Side.LEFT);
		lineData = new HashMap<String, Series<Number, Number>>();
	}

}
