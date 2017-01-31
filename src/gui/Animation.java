package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;

public class Animation {
	private Group root;
	private Scene simulation;
	private ToolBar toolBar;
	private Button menu, step, start, stop, reset;
	private Slider FPSslider;
	private double minFPS = 1;
	private double maxFPS = 120;
	private double startFPS = 60;
	private int width = 1280;
	private int height = 720;

	public Animation() {
		// TODO Auto-generated constructor stub
	}
	
	public Scene initialize() {
		root = new Group();
		simulation = new Scene(root, width, height);
		setupControls();
		return simulation;
	}
	
	public void runAnimation(File setupInfo) {
		// TODO Unimplemented method
	}
	
	private void setupAnimation() {
		// TODO Unimplemented method
	}
	
	private void setupControls() {
		toolBar = new ToolBar();
		toolBar.setPrefWidth(width);
		
		menu = new Button("Menu");
		step = new Button("Step");
		start = new Button("Start");
		stop = new Button("Stop");
		reset = new Button("Reset");
		FPSslider = new Slider(minFPS, maxFPS, startFPS);
		toolBar.getItems().addAll(menu, step, start, stop, reset, FPSslider);
		
		root.getChildren().add(toolBar);
	}

}
