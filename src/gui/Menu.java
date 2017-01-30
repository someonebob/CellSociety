package gui;

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

public class Menu {
	private StackPane layout;
	private Scene titlescreen;
	private VBox vbox;
	private Text title;
	private HBox buttons;
	private Button load, quit;
	private int buttonwidth = 150;
	private int buttonheight = 75;
	private int width = 1280;
	private int height = 720;
	private Image image;
	private ImageView background;
	private String CELL_IMAGE = "background.jpg";
	
	public Menu(){
		setupBackground();
		setupInterface();	
	}
	
	public Scene getMenu(){
		return titlescreen;
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
		
		vbox = new VBox(200);
		
		vbox.getChildren().addAll(title, setupButtons());
		vbox.setAlignment(Pos.CENTER);
		layout.getChildren().add(vbox);
	}
	
	private HBox setupButtons(){
		load = new Button("Load File");	
		//load.setOnMouseClicked(e -> );
		load.setPrefSize(buttonwidth, buttonheight);
		
		quit = new Button("Quit");
		quit.setOnMouseClicked(e -> System.exit(0));
		quit.setPrefSize(buttonwidth, buttonheight);
		
		//magic number for spacing
		buttons = new HBox(300);
		buttons.getChildren().addAll(load, quit);
		buttons.setAlignment(Pos.CENTER);
		
		return buttons;
	}
	
}
