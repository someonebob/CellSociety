package gui;

import java.io.File;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Runs the cells animation pane in the program.
 * @author Nathaniel Brooke
 * @version 02-01-2016
 */
public class Animation {
	
	private static final double MIN_FPS = 0.1;
	private static final double MAX_FPS = 20;
	private static final double DEFAULT_FPS = 4;
    public static final ResourceBundle RESOURCES = ResourceBundle.getBundle("resourcefiles/Animation");

	
	private Stage window;
	private Rectangle2D screen;
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
	public Animation(Stage window) {
		screen = Screen.getPrimary().getVisualBounds();
		root = new Group();
		simulation = new Scene(root, screen.getWidth(), screen.getHeight());
		this.window = window;
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
		animation.play();
		isPlaying = true;
	}
	
	private void setupAnimation() {
		grid = new GridImager(setup, screen.getWidth(), 
				screen.getHeight() - toolBar.getHeight());
		Group g = grid.getGroup();
		g.setLayoutY(toolBar.getHeight() + screen.getMinY());
		g.setLayoutX((screen.getMaxX() - g.getLayoutBounds().getWidth())/2);
		root.getChildren().add(g);
		KeyFrame frame = new KeyFrame(Duration.millis(1000.0/DEFAULT_FPS), e -> {
			grid.nextFrame();
		});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}
	
	private void setupControls() {		
		Button menu = makeMenuButton();
		Button step = makeStepButton();		
		Button playPause = makePlayPauseButton();
		Button reset = makeResetButton();		
		Slider slider = makeFPSSlider();
		
		toolBar = new ToolBar();
		toolBar.setLayoutY(screen.getMinY());
		toolBar.setPrefWidth(screen.getWidth());
		toolBar.getItems().addAll(menu, step, playPause, reset, slider);
		root.getChildren().add(toolBar);
	}

	private Slider makeFPSSlider() {
		Slider sliderFPS = new Slider(MIN_FPS, MAX_FPS, DEFAULT_FPS);
		sliderFPS.setOnMouseReleased(e -> {
			animation.setRate(sliderFPS.getValue());
		});
		return sliderFPS;
	}

	private Button makeResetButton() {
		Button reset = new Button(RESOURCES.getString("reset"));
		reset.setOnMouseClicked(e -> {
			animation.stop();
			setupAnimation();
			if(isPlaying) {
				animation.play();
			}
			
		});
		return reset;
	}

	private Button makePlayPauseButton() {
		Button playPause = new Button(RESOURCES.getString("pause"));
		playPause.setOnMouseClicked(e -> {
			if(isPlaying) {
				animation.pause();
				playPause.setText(RESOURCES.getString("play"));
				isPlaying = false;
			}
			else {
				animation.play();
				playPause.setText(RESOURCES.getString("pause"));
				isPlaying = true;
			}
		});
		return playPause;
	}

	private Button makeStepButton() {
		Button step = new Button(RESOURCES.getString("step"));
		step.setOnMouseClicked(e -> grid.nextFrame());
		return step;
	}

	private Button makeMenuButton() {
		Button menu = new Button(RESOURCES.getString("menu"));
		Menu newMenu = new Menu(window);
		menu.setOnMouseClicked(e -> {
			animation.stop();
			window.setScene(newMenu.initialize());
		});
		return menu;
	}
}