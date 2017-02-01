package gui;

import java.io.File;

import org.w3c.dom.NodeList;

import javafx.scene.Group;
import model.Rules;
import xml.XMLParser;

public class Grid {
	
	private Group myGroup;
	private XMLParser parser;
	private Rules rules;
	private int rows;
	private int cols;
	private NodeList stateList;
	private File info;

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
		// TODO Unimplemented method
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
		// TODO Unimplemented method
	}

}
