package gui;
import java.io.File;
import java.util.ResourceBundle;

import org.w3c.dom.NodeList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml.XMLParser;
/**
 * Runs the cells animation pane in the program.
 * @author Nathaniel Brooke
 * @version 02-01-2016
 */
public class Animation {
	
	private static final double MIN_FPS = 0.1;
	private static final double MAX_FPS = 20;
	private static final double DEFAULT_FPS = 4;
    public static final ResourceBundle RESOURCES = 
    		ResourceBundle.getBundle("resourcefiles/Animation");
	
	private Stage window;
	private Rectangle2D screen;
	private Scene simulation;
	private File setup;
	private Timeline animation;
	private BorderPane root;
	private ToolBar toolBar;
	private boolean isPlaying;
	private GridImager grid;
	private VBox sideBox;
	private XMLParser parser;
	private GridPane gridPane;
	private double dimension;
	private ComboBox<String> gridShape;
	
	/**
	 * Initializes the Scene and Group for the animation.
	 */
	public Animation(Stage window, File setupInfo) {
		setup = setupInfo;
		screen = Screen.getPrimary().getVisualBounds();
		root = new BorderPane();
		simulation = new Scene(root);
		this.window = window;
		}
	
	/**
	 * Initializes the animation pane, including control buttons.
	 * @return the Scene with everything in it.
	 */
	public Scene initialize() {
		dimension = screen.getHeight() - 60;
		grid = new SquareGridImager(setup, dimension, dimension);
		setupControls();
		setupSideMenu();
		setupScrolling();
		runAnimation(grid);
		return simulation;
	}
	
	/**
	 * Runs an animation with a newly selected simulation.
	 * @param setupInfo the XML file with setup information for the grid.
	 */
	public void runAnimation(GridImager imager) {	
		setupAnimation(imager);
		animation.play();
		isPlaying = true;
		window.setMaximized(true);
	}
	
	private void setupAnimation(GridImager imager) {
		Group g = imager.getGroup();
		root.setCenter(g);
		KeyFrame frame = new KeyFrame(Duration.millis(1000.0/DEFAULT_FPS), e -> {
			imager.nextFrame();
		});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}
	
	private void setupScrolling(){
		ScrollBar vertical = new ScrollBar();
		vertical.setOrientation(Orientation.VERTICAL);
		root.setRight(vertical);
		
		ScrollBar horizontal = new ScrollBar();
		root.setBottom(horizontal);
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
		root.setTop(toolBar);
	}
	
	private void setupSideMenu(){
		parser = new XMLParser(setup);
		sideBox = new VBox(20);
		setupHeading();
		sideBox.setAlignment(Pos.TOP_CENTER);
		
		setupSideControls();

		root.setLeft(sideBox);
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
			chooseGrid(dimension);
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
	
	
	
	/**
	 * Side Menu Stuff
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 */
	
	
	
	/**
	 * Sets up title of simulation and author
	 */
	private void setupHeading(){	
		Text title = new Text(parser.getParameter("title"));
		title.setFont(new Font(50));
		title.setWrappingWidth(500);
		
		Text author = new Text(parser.getParameter("author"));
		author.setFont(new Font(40));

		VBox heading = new VBox(10);
		heading.getChildren().addAll(title, author);
		heading.setAlignment(Pos.CENTER);
		sideBox.getChildren().add(heading);
	}
	
	/**
	 * Sets up all the controls
	 */
	private void setupSideControls(){
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setHgap(10);
		gridPane.setVgap(20);
		
		makeCellTypeControl();
		makeGridTypeControl();
		makeCellSizeControl();
		makeOutlinesControl();
		makeColorControl();
		makeParameterControl();
		
		sideBox.getChildren().add(gridPane);
	}
	
	/**
	 * Changes the shape within the cell
	 */
	private void makeCellTypeControl(){
		Label cellType = new Label("Cell Type");
		GridPane.setConstraints(cellType, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ComboBox<String> type = new ComboBox<>();
		GridPane.setConstraints(type, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

		gridPane.getChildren().addAll(cellType, type);
	}
	/**
	 * Allows user to choose square, triangular, or hexagonal grid
	 */
	private void makeGridTypeControl(){
		Label gridType = new Label("Grid Type");
		GridPane.setConstraints(gridType, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
		
		gridShape = new ComboBox<>();
		gridShape.getItems().addAll("Square", "Triangular", "Hexagonal");
		gridShape.setValue(gridShape.getItems().get(0));
		GridPane.setConstraints(gridShape, 1, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		gridShape.setOnAction(e -> {
			chooseGrid(dimension);
		});
		
		gridPane.getChildren().addAll(gridType, gridShape);
	}
	
	/**
	 * Chooses between the 3 types of grids
	 * @param dimension
	 */
	private void chooseGrid(double dimension){
		animation.stop();

		if(gridShape.getValue().equals(gridShape.getItems().get(0))){
			grid = new SquareGridImager(setup, dimension, dimension);
			runAnimation(grid);
		}
		if(gridShape.getValue().equals(gridShape.getItems().get(1))){
			grid = new TriangleGridImager(setup, dimension, dimension);
			runAnimation(grid);
		}
		if(gridShape.getValue().equals(gridShape.getItems().get(2))){
			grid = new HexagonGridImager(setup, dimension, dimension);
			runAnimation(grid);
		}
	}
	
	/**
	 * Allows user to adjust the cell size
	 */
	private void makeCellSizeControl(){
		Label cellSize = new Label("Cell Size");
		GridPane.setConstraints(cellSize, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);

		Slider size = new Slider(dimension/10, dimension*4, dimension);
		GridPane.setConstraints(size, 1, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		size.setOnMouseReleased(e -> chooseGrid(size.getValue()));
		
		gridPane.getChildren().addAll(cellSize, size);
	}
	
	/**
	 * Allows user to choose if they want outlines or not
	 */
	private void makeOutlinesControl(){
		Label outlines = new Label("Outlines");
		GridPane.setConstraints(outlines, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);

		CheckBox check = new CheckBox();
		check.setSelected(true);
		GridPane.setConstraints(check, 1, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		gridPane.getChildren().addAll(outlines, check);

	}
	
	/**
	 * Allows user to change the color scheme
	 */
	private void makeColorControl(){
		Label color = new Label("Color Scheme");
		GridPane.setConstraints(color, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ComboBox<String> type = new ComboBox<>();
		GridPane.setConstraints(type, 1, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		gridPane.getChildren().addAll(color, type);
	}
	
	/**
	 * Allows user to change the parameters of the simulation 
	 */
	private void makeParameterControl(){
		Label parameter = new Label("Parameter");
		GridPane.setConstraints(parameter, 0, 5, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ComboBox<String> type = new ComboBox<>();
		GridPane.setConstraints(type, 1, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		TextField input = new TextField();
		GridPane.setConstraints(input, 2, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		fillComboBox(type, "parameters");		
		if(type.isDisabled()){
			input.setDisable(true);
		}
		type.setOnAction(e -> {
			input.setPromptText(parser.getParameterType(type.getValue()));
		});
	
		gridPane.getChildren().addAll(parameter, type, input);
	}
	
	/**
	 * Fills out the combo box with the correct parameters
	 * @param type
	 * @param parameter
	 */
	private void fillComboBox(ComboBox<String> type, String parameter){
		try{
			NodeList parameters = parser.getParameters(parameter);
			for(int i = 0; i < parameters.getLength(); i++){
				if(!parameters.item(i).getNodeName().equals("#text")){
					type.getItems().add(parameters.item(i).getNodeName());
				}
			}
		} catch(Exception e){
			type.setDisable(true);
		}
	}

}