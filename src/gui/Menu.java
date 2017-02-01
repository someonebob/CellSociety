package gui;


import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Menu {
	public static final int BUTTON_WIDTH = 150;
	public static final int BUTTON_HEIGHT = 75;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String CELL_IMAGE = "background.jpg";
	public static final String EXTENSION = "*.xml";

	private Stage window;
	private StackPane layout;
	private Scene titlescreen;
	private VBox vbox;
	private Text title;
	private HBox buttons;
	private Button load, quit;	
	private Image image;
	private ImageView background;
	private FileChooser chooser;
	private File defaultDirectory, selectedFile;
	
	/**
	 * Takes in the stage to use throughout class
	 * @param window
	 */
	public Menu(Stage window){
		this.window = window;		
	}
	
	/**
	 * Initializes the titlescreen
	 * @return titlescreen scene
	 */
	public Scene initialize(){
		setupBackground();
		setupInterface();
		return titlescreen;
	}
	
	/**
	 * Returns the file that was selected in the File Chooser
	 * @return selected file
	 */
	public File getFile(){	
		return selectedFile;
	}
	
	/**
	 * Creates the background image for the titlescreen
	 */
	private void setupBackground(){
		layout = new StackPane();
		titlescreen = new Scene(layout, WIDTH, HEIGHT);
		
		image = new Image(getClass().getClassLoader().getResourceAsStream(CELL_IMAGE));
		background = new ImageView(image);
		
		layout.getChildren().add(background);
	}
	
	/**
	 * Sets up the User Interface
	 */
	private void setupInterface(){
		title = new Text("CELL CIVILIZATION");
		title.setFill(Color.WHITE);
		title.setFont(Font.font(100));
		
		//magic number for spacing
		vbox = new VBox(200);
		
		vbox.getChildren().addAll(title, setupButtons());
		vbox.setAlignment(Pos.CENTER);
		layout.getChildren().add(vbox);
	}
	
	/**
	 * Sets up buttons and their functionality
	 * @return HBox containing aligned buttons
	 */
	private HBox setupButtons(){
		load = new Button("Load File");	
		load.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		chooser = new FileChooser();
		chooser.setTitle("CA Simulations");	
		defaultDirectory = new File(System.getProperty("user.dir")+"/data");
		chooser.setInitialDirectory(defaultDirectory);
		chooser.getExtensionFilters().setAll(new ExtensionFilter("XML Files", EXTENSION));
		
		load.setOnMouseClicked(e -> {
			selectedFile = chooser.showOpenDialog(window);		
			if(selectedFile != null){
				Animation animation = new Animation();
				window.setScene(animation.initialize());			
				animation.runAnimation(selectedFile);
			}	 
		});
		
		quit = new Button("Quit");
		quit.setOnMouseClicked(e -> System.exit(0));
		quit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//magic number for spacing
		buttons = new HBox(300);
		buttons.getChildren().addAll(load, quit);
		buttons.setAlignment(Pos.CENTER);
		
		return buttons;
	}
	
}
