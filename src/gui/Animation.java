package gui;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.util.Duration;

public class Animation {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static final double MIN_FPS = 1;
	private static final double MAX_FPS = 120;
	private static final double DEFAULT_FPS = 60;

	private Scene simulation;
	private File setup;
	private Timeline animation;
	private Group root;
	private boolean inAnimation;
	private boolean isPlaying;
	private Grid grid;

	public Animation() {
		root = new Group();
		simulation = new Scene(root, WIDTH, HEIGHT);
	}
	
	public Scene initialize() {
		setupControls();
		return simulation;
	}
	
	public void runAnimation(File setupInfo) {
		setup = setupInfo;
		setupAnimation();
		inAnimation = true;
		isPlaying = true;
		while(inAnimation);
		animation.stop();
	}
	
	private void setupAnimation() {
		grid = new Grid(setup);
		root.getChildren().add(grid.getGroup());
		KeyFrame frame = makeKeyFrame(DEFAULT_FPS);
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private KeyFrame makeKeyFrame(double framesPerSecond) {
		KeyFrame frame = new KeyFrame(Duration.millis(1000.0/framesPerSecond), e -> {
			grid.nextFrame();
		});
		return frame;
	}
	
	private void setupControls() {		
		Button menu = new Button("Menu");
		menu.setOnMouseClicked(e -> {
			inAnimation = false;
		});
		
		Button step = new Button("Step");
		step.setOnMouseClicked(e -> {
			grid.nextFrame();
		});
		
		Button playPause = new Button("Pause");
		playPause.setOnMouseClicked(e -> {
			if(isPlaying) {
				animation.pause();
				playPause.setText("Play ");
				isPlaying = false;
			}
			else {
				animation.play();
				playPause.setText("Pause");
				isPlaying = true;
			}
		});

		Button reset = new Button("Reset");
		reset.setOnMouseClicked(e -> {
			setupAnimation();
		});
		
		Slider sliderFPS = new Slider(MIN_FPS, MAX_FPS, DEFAULT_FPS);
		sliderFPS.setOnMouseReleased(e -> {
			double fps = sliderFPS.getValue();
			animation.getKeyFrames().clear();
			animation.getKeyFrames().add(makeKeyFrame(fps));
		});
		
		ToolBar toolBar = new ToolBar();
		toolBar.setPrefWidth(WIDTH);
		toolBar.getItems().addAll(menu, step, playPause, reset, sliderFPS);
		root.getChildren().add(toolBar);
	}

}
