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
	private Stage window;
	private StackPane layout;
	private Scene titlescreen;
	private VBox vbox;
	private Text title;
	private HBox buttons;
	private Button load, quit;
	private int buttonWidth = 150;
	private int buttonHeight = 75;
	private int width = 1280;
	private int height = 720;
	private Image image;
	private ImageView background;
	private String CELL_IMAGE = "background.jpg";
	private FileChooser chooser;
	private File defaultDirectory, selectedFile;
	private String extension = "*.xml";
	
	public Menu(Stage window){
		this.window = window;		
	}
	
	public Scene initialize(){
		setupBackground();
		setupInterface();
		return titlescreen;
	}
	
	public File getFile(){	
		return selectedFile;
	}
	
	private void setupBackground(){
		layout = new StackPane();
		titlescreen = new Scene(layout, width, height);
		
		image = new Image(getClass().getClassLoader().getResourceAsStream(CELL_IMAGE));
		background = new ImageView(image);
		
		layout.getChildren().add(background);
	}
	
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
	
	private HBox setupButtons(){
		load = new Button("Load File");	
		load.setPrefSize(buttonWidth, buttonHeight);
		
		chooser = new FileChooser();
		chooser.setTitle("CA Simulations");	
		defaultDirectory = new File(System.getProperty("user.dir")+"/data");
		chooser.setInitialDirectory(defaultDirectory);
		chooser.getExtensionFilters().setAll(new ExtensionFilter("XML Files", extension));
		
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
		quit.setPrefSize(buttonWidth, buttonHeight);
		
		//magic number for spacing
		buttons = new HBox(300);
		buttons.getChildren().addAll(load, quit);
		buttons.setAlignment(Pos.CENTER);
		
		return buttons;
	}
	
}
