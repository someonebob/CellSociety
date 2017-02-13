package gui;

import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class Graph {
	
	private LineChart<Number, Number> linechart;
	private PieChart piechart;
	
	private XYChart data;
	

	public Graph() {
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
		linechart = new LineChart<Number, Number>(xAxis, yAxis);
	}
	
	public void addData(List<Number> values) {
		
	}

}
