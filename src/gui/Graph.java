package gui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import model.State;

public class Graph {
	
	private LineChart<Number, Number> linechart;
	private PieChart piechart;
	
	private ObservableList<PieChart.Data> pieData;
	private Map<String, Series<Number, Number>> lineData;
	
	private int tick;
	
	

	public Graph() {
		tick = 0;
		makeLineChart();
		makePieChart();
	}

	private void makeLineChart() {
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
		linechart = new LineChart<Number, Number>(xAxis, yAxis);
		linechart.setPrefHeight(100);
		lineData = new HashMap<String, Series<Number, Number>>();
	}
	
	private void makePieChart() {
		pieData = FXCollections.observableArrayList();
		piechart = new PieChart(pieData);
		piechart.setTitle("Current Cell Ratios");
	}
		
	public void addData(Map<State, Integer> values) {
		for(State s : values.keySet()) {
			if(!lineData.containsKey(s.getValue())) {
				Series<Number, Number> series = new Series<Number, Number>();
				lineData.put(s.getValue(), series);
				linechart.getData().add(series);
			}
			XYChart.Data<Number, Number> dataPt = new XYChart.Data<>(tick, values.get(s));
			lineData.get(s).getData().add(dataPt);
			pieData.add(new PieChart.Data(s.getValue(), values.get(s.getValue()).doubleValue()));
		}
	}
	
	public PieChart getPieChart() {
		return piechart;
	}
	
	public LineChart<Number, Number> getLineChart() {
		return linechart;
	}

}
