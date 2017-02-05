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

/**
 * Runs the cells animation pane in the program.
 * @author Nathaniel Brooke
 * @version 02-01-2016
 */
public class Animation {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 700;
	private static final double MIN_FPS = 1;
	private static final double MAX_FPS = 120;
	private static final double DEFAULT_FPS = 10;
	
	private Scene simulation;
	private File setup;
	private Timeline animation;
	private Group root;
	private ToolBar toolBar;
	private boolean isPlaying;
	private GridImager grid;
	
	/**
	 * Initializes the Scene and Group for the animation.
	 */
	public Animation() {
		root = new Group();
		simulation = new Scene(root, WIDTH, HEIGHT);
	}
	
	/**
	 * Initializes the animation pane, including control buttons.
	 * @return the Scene with everything in it.
	 */
	public Scene initialize() {
		setupControls();
		return simulation;
	}
	
	/**
	 * Runs an animation with a newly selected simulation.
	 * @param setupInfo the XML file with setup information for the grid.
	 */
	public void runAnimation(File setupInfo) {
		setup = setupInfo;
		setupAnimation();
		isPlaying = true;
	}
	
	private void setupAnimation() {
		grid = new GridImager(setup, simulation.getWidth(), 
				simulation.getHeight() - toolBar.getHeight());
		Group g = grid.getGroup();
		g.setLayoutY(toolBar.getHeight());
		root.getChildren().add(g);
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
		Button menu = makeMenuButton();
		Button step = makeStepButton();		
		Button playPause = makePlayPauseButton();
		Button reset = makeResetButton();		
		Slider slider = makeFPSSlider();
		
		toolBar = new ToolBar();
		toolBar.setPrefWidth(WIDTH);
		toolBar.getItems().addAll(menu, step, playPause, reset, slider);
		root.getChildren().add(toolBar);
	}

	private Slider makeFPSSlider() {
		Slider sliderFPS = new Slider(MIN_FPS, MAX_FPS, DEFAULT_FPS);
		sliderFPS.setOnMouseReleased(e -> {
			double fps = sliderFPS.getValue();
			animation.getKeyFrames().clear();
			animation.getKeyFrames().add(makeKeyFrame(fps));
		});
		return sliderFPS;
	}

	private Button makeResetButton() {
		Button reset = new Button("Reset");
		reset.setOnMouseClicked(e -> setupAnimation());
		return reset;
	}

	private Button makePlayPauseButton() {
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
		return playPause;
	}

	private Button makeStepButton() {
		Button step = new Button("Step");
		step.setOnMouseClicked(e -> grid.nextFrame());
		return step;
	}

	private Button makeMenuButton() {
		Button menu = new Button("Menu");
		menu.setOnMouseClicked(e -> {
			animation.stop();
			//TODO make it return to the menu
		});
		return menu;
	}
}