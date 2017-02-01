package gui;

import java.io.File;

import org.w3c.dom.NodeList;

import javafx.scene.Group;
import javafx.scene.Node;
import model.Cell;
import model.Rules;
import xml.XMLParser;

public class Grid {
	
	public static final int CELL_SIZE = 100;
	
	private Group myGroup;
	private XMLParser parser;
	private Rules rules;
	private int rows;
	private int cols;
	private NodeList stateList;
	private File info;
	private Cell[][] myCells;

	public Grid(File setupInfo) {
		myGroup = new Group();
		
		info = setupInfo;
		parser = new XMLParser(info);	
		rules = Rules.getRules(parser.getRuleName());
		rows = parser.getGridRows();
		cols = parser.getGridColumns();
		stateList = parser.getInitialStates();
		
//		Just a test to see if it parses correctly		
//		for(int i = 0; i < stateList.getLength(); i++){
//			System.out.println(stateList.item(i).getTextContent());
//		}
	}
	
	/**
	 * Accesses the Group displaying all the cells.
	 * Group will be updated when simulation changes frame.
	 * @return the Group
	 */
	public Group getGroup() {
		return myGroup;
	}
	
	public void nextFrame() {
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				myCells[row][col].calculateFutureState();
			}
		}
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				myCells[row][col].refreshState();;
			}
		}
		updateGroup();
	}
	
	private Rules getRules() {
		// TODO Unimplemented method
		return null;
	}
	
	private void initializeArray() {
		// TODO Unimplemented method
	}
	
	private void passNeighbors() {
		// TODO Unimplemented method
	}
	
	private void updateGroup() {
		myGroup.getChildren().clear();
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				Node n = myCells[row][col].getCurrentState().getStateNode();
				n.setLayoutX(row*CELL_SIZE);
				n.setLayoutY(col*CELL_SIZE);
				myGroup.getChildren().add(n);
			}
		}
	}

}
