package gui;
import java.io.File;
import java.util.ResourceBundle;

import javax.xml.transform.TransformerException;

import org.w3c.dom.NodeList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
	private static final double MAX_FPS = 10;
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
	private GridImager grid;
	private VBox sideBox;
	private XMLParser parser;
	private ScrollPane scroll;
	private GridPane gridPane;
	private double dimension;
	private ComboBox<String> gridShape;
	private CheckBox check;
	private Slider size;
	private ComboBox<String> colorType;
	
	/**
	 * Initializes the Scene and Group for the animation.
	 */
	public Animation(Stage window, File setupInfo) {
		setup = setupInfo;
		screen = Screen.getPrimary().getVisualBounds();
		root = new BorderPane();
		simulation = new Scene(root, screen.getWidth(), screen.getHeight());
		this.window = window;
		}
	
	/**
	 * Initializes the animation pane, including control buttons.
	 * @return the Scene with everything in it.
	 */
	public Scene initialize() {
		dimension = screen.getHeight() - 70;
		grid = new SquareGridImager(setup, dimension, dimension);
		setupControls();
		setupSideMenu();
		window.setOnCloseRequest(e -> {
			resetDefault();
		});
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
		window.setMaximized(true);
	}
	
	private void setupAnimation(GridImager imager) {
		Group g = imager.getGroup();
		scroll = new ScrollPane();
		scroll.setContent(g);
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scroll.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		
		root.setCenter(scroll);
		
		
		KeyFrame frame = new KeyFrame(Duration.millis(1000.0/DEFAULT_FPS), e -> {
			double scrollHVal = scroll.getHvalue();
			double scrollVVal = scroll.getVvalue();
			imager.nextFrame(check.isSelected());
			scroll.setHvalue(scrollHVal);
			scroll.setVvalue(scrollVVal);
		});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		imager.nextFrame(check.isSelected());
	}
	
	
	private void setupControls() {		
		Button side = makeSideButton();
		Button menu = makeMenuButton();
		Button noo = makeNewButton();
		Button step = makeStepButton();		
		Button playPause = makePlayPauseButton();
		Button reset = makeResetButton();		
		Slider slider = makeFPSSlider();
		
		toolBar = new ToolBar();
		toolBar.setLayoutY(screen.getMinY());
		toolBar.setPrefWidth(screen.getWidth());
		toolBar.getItems().addAll(side, menu, noo, step, playPause, reset, slider);
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
			boolean wasPlaying = animation.getCurrentRate() != 0;
			animation.stop();
			resetDefault();
			setupAnimation(grid);
			if(wasPlaying) {
				animation.play();
			}
		});
		return reset;
	}
	
	private Button makePlayPauseButton() {
		Button playPause = new Button(RESOURCES.getString("pause"));
		playPause.setOnMouseClicked(e -> {
			if(animation.getCurrentRate() != 0) {
				animation.pause();
				playPause.setText(RESOURCES.getString("play"));
			}
			else {
				animation.play();
				playPause.setText(RESOURCES.getString("pause"));
			}
		});
		return playPause;
	}
	
	private Button makeStepButton() {
		Button step = new Button(RESOURCES.getString("step"));
		step.setOnMouseClicked(e -> grid.nextFrame(check.isSelected()));
		return step;
	}
	
	private Button makeMenuButton() {
		Button menu = new Button(RESOURCES.getString("menu"));
		menu.setOnMouseClicked(e -> {
			animation.stop();
			window.setScene(new Menu(window).initialize());
		});
		return menu;
	}
	
	private Button makeNewButton() {
		Button menu = new Button(RESOURCES.getString("new"));
		menu.setOnMouseClicked(e -> {
			Stage stage = new Stage();
			stage.setScene(new Menu(stage).initialize());
			stage.show();
		});
		return menu;
	}
	
	private Button makeSideButton() {
		Button side = new Button(RESOURCES.getString("sideIn"));
		side.setOnMouseClicked(e -> {
			if(sideBox.isVisible()) {
				sideBox.setVisible(false);
				root.setLeft(null);
				side.setText(RESOURCES.getString("sideOut"));
			}
			else {
				sideBox.setVisible(true);
				root.setLeft(sideBox);
				side.setText(RESOURCES.getString("sideIn"));
			}
		});
		return side;
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
		
		makeGridEdgeControl();
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
	private void makeGridEdgeControl(){
		Label gridEdge = new Label("Grid Edge Type");
		GridPane.setConstraints(gridEdge, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ComboBox<String> edgeType = new ComboBox<>();
		GridPane.setConstraints(edgeType, 1, 0, 2, 1, HPos.RIGHT, VPos.CENTER);
		
		edgeType.getItems().addAll("Finite", "Toroidal", "Infinite");
		edgeType.setPromptText(edgeType.getItems().get(0));
		
		edgeType.setOnAction(e -> {
			
		});

		gridPane.getChildren().addAll(gridEdge, edgeType);
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
		GridPane.setConstraints(gridShape, 1, 1, 2, 1, HPos.RIGHT, VPos.CENTER);
		
		gridShape.setOnAction(e -> {
			chooseGrid(size.getValue());
		});
		
		gridPane.getChildren().addAll(gridType, gridShape);
	}		
	
	/**
	 * Allows user to adjust the cell size
	 */
	private void makeCellSizeControl(){
		Label cellSize = new Label("Cell Size");
		GridPane.setConstraints(cellSize, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);

		size = new Slider(dimension/10, dimension*4, dimension);
		GridPane.setConstraints(size, 1, 2, 2, 1, HPos.RIGHT, VPos.CENTER);
		
		size.setOnMouseReleased(e -> chooseGrid(size.getValue()));
		
		gridPane.getChildren().addAll(cellSize, size);
	}
	
	/**
	 * Allows user to choose if they want outlines or not
	 */
	private void makeOutlinesControl(){
		Label outlines = new Label("Outlines");
		GridPane.setConstraints(outlines, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);

		check = new CheckBox();
		GridPane.setConstraints(check, 1, 3, 2, 1, HPos.RIGHT, VPos.CENTER);
		
		gridPane.getChildren().addAll(outlines, check);
	}
	
	/**
	 * Allows user to change the color scheme
	 */
	private void makeColorControl(){
		Label color = new Label("Color Scheme");
		GridPane.setConstraints(color, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
		
		colorType = new ComboBox<>();
		GridPane.setConstraints(colorType, 1, 4, 2, 1, HPos.RIGHT, VPos.CENTER);
		
		fillComboBox(colorType, "scheme");
		
		colorType.setOnAction(event -> {
			try {
				parser.setColor(colorType.getValue());
			} catch (TransformerException e) {
				
			}
			chooseGrid(size.getValue());
		});
		
		gridPane.getChildren().addAll(color, colorType);
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
			input.setPromptText(parser.getParameterAttribute(type.getValue(), "type"));
		});
		
		input.setOnAction(event -> {
			try {
				int value = Integer.parseInt(input.getText());
				int min = Integer.parseInt(parser.getParameterAttribute(type.getValue(), "minconstraint"));
				int max = Integer.parseInt(parser.getParameterAttribute(type.getValue(), "maxconstraint"));
				if(value >= min && value <= max){
					parser.setParameter(type.getValue(), input.getText());
					chooseGrid(size.getValue());
				}else{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Input must be a number between "+min+" and "+max);
					alert.showAndWait();
				}
			} catch(Exception e){
				System.out.println("Must choose a parameter");
			}
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
	
	/**
	 * Chooses between the 3 types of grids
	 * @param dimension
	 */
	private void chooseGrid(double dimension){
		animation.stop();

		if(gridShape.getValue().equals(gridShape.getItems().get(0))){
			grid = new SquareGridImager(setup, dimension, dimension);
			setupAnimation(grid);
		}
		if(gridShape.getValue().equals(gridShape.getItems().get(1))){
			grid = new TriangleGridImager(setup, dimension, dimension);
			setupAnimation(grid);
		}
		if(gridShape.getValue().equals(gridShape.getItems().get(2))){
			grid = new HexagonGridImager(setup, dimension, dimension);
			setupAnimation(grid);
		}
	}
	
	private void resetDefault(){
		size.setValue(dimension);
		try {
			parser.setColor(colorType.getItems().get(0));
			colorType.setValue(colorType.getItems().get(0));
		} catch (TransformerException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Cannot write color specified to XML file");
			alert.showAndWait();
		}
		check.setSelected(false);
		grid = new SquareGridImager(setup, dimension, dimension);
		gridShape.setValue(gridShape.getItems().get(0));
	}

}