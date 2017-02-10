package gui;

import java.io.File;

import org.w3c.dom.NodeList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import xml.XMLParser;

public class SideMenu{
	private VBox root;
	private File setup;
	private XMLParser parser;
	private GridPane grid;
	
	public SideMenu(File setupInfo){
		setup = setupInfo;
		parser = new XMLParser(setup);
		root = new VBox(20);
		setupHeading();
		root.setAlignment(Pos.TOP_CENTER);
		
		setupControls();
	}
	
	public VBox getMenu(){
		return root;
	}
	
	private void setupHeading(){	
		Text title = new Text(parser.getParameter("title"));
		title.setFont(new Font(50));
		title.setWrappingWidth(500);
		
		Text author = new Text(parser.getParameter("author"));
		author.setFont(new Font(40));

		VBox heading = new VBox(10);
		heading.getChildren().addAll(title, author);
		heading.setAlignment(Pos.CENTER);
		root.getChildren().add(heading);
	}
	
	private void setupControls(){
		grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setHgap(10);
		grid.setVgap(20);
		
		makeCellTypeControl();
		makeGridTypeControl();
		makeCellSizeControl();
		makeOutlinesControl();
		makeColorControl();
		makeParameterControl();
		
		root.getChildren().add(grid);
	}
	
	private void makeCellTypeControl(){
		Label cellType = new Label("Cell Type");
		GridPane.setConstraints(cellType, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ChoiceBox<String> type = new ChoiceBox<>();
		GridPane.setConstraints(type, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER);

		grid.getChildren().addAll(cellType, type);
	}
	
	private void makeGridTypeControl(){
		Label gridType = new Label("Grid Type");
		GridPane.setConstraints(gridType, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ChoiceBox<String> type = new ChoiceBox<>();
		GridPane.setConstraints(type, 1, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		grid.getChildren().addAll(gridType, type);
	}
	
	private void makeCellSizeControl(){
		Label cellSize = new Label("Cell Size");
		GridPane.setConstraints(cellSize, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER);

		Slider size = new Slider();
		GridPane.setConstraints(size, 1, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		grid.getChildren().addAll(cellSize, size);
	}
	
	private void makeOutlinesControl(){
		Label outlines = new Label("Outlines");
		GridPane.setConstraints(outlines, 0, 3, 1, 1, HPos.LEFT, VPos.CENTER);

		CheckBox check = new CheckBox();
		check.setSelected(true);
		GridPane.setConstraints(check, 1, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		grid.getChildren().addAll(outlines, check);

	}
	
	private void makeColorControl(){
		Label color = new Label("Color Scheme");
		GridPane.setConstraints(color, 0, 4, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ChoiceBox<String> type = new ChoiceBox<>();
		GridPane.setConstraints(type, 1, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		grid.getChildren().addAll(color, type);
	}
	
	private void makeParameterControl(){
		Label parameter = new Label("Parameter");
		GridPane.setConstraints(parameter, 0, 5, 1, 1, HPos.LEFT, VPos.CENTER);
		
		ChoiceBox<String> type = new ChoiceBox<>();
		fillChoiceBox(type, "parameters");
				
		GridPane.setConstraints(type, 1, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		TextField input = new TextField();
		GridPane.setConstraints(input, 2, 5, 1, 1, HPos.RIGHT, VPos.CENTER);

		
		grid.getChildren().addAll(parameter, type, input);
	}
	
	private void fillChoiceBox(ChoiceBox<String> type, String parameter){
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
