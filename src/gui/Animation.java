package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class Animation {
	private Stage window;
	private Group root;
	private Scene simulation;
	private ToolBar toolBar;
	private Button menu, step, playPause, reset;
	private Slider FPSslider;
	private double minFPS = 1;
	private double maxFPS = 120;
	private double startFPS = 60;
	private int width = 1280;
	private int height = 720;
	private boolean isPlaying = true;

	public Animation(Stage window) {
		this.window = window;
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
		
		menu = new Button("Return To Menu");
		menu.setOnMouseClicked(e -> {
			Menu menu = new Menu(window);
			window.setScene(menu.initialize());
		});
		
		step = new Button("Step");
		playPause = new Button("Pause");
		playPause.setOnMouseClicked(e -> {
			if(isPlaying){
				playPause.setText("Play");
				isPlaying = false;
			}else{
				playPause.setText("Pause");
				isPlaying = true;
			}
		});

		reset = new Button("Reset");
		FPSslider = new Slider(minFPS, maxFPS, startFPS);
		toolBar.getItems().addAll(menu, step, playPause, reset, FPSslider);
		
		root.getChildren().add(toolBar);
	}

}
