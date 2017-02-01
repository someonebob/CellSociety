package gui;

import java.io.File;

import org.w3c.dom.NodeList;

import javafx.scene.Group;
import javafx.scene.Node;
import model.Cell;
import model.Rules;
import model.State;
import xml.XMLParser;

public class Grid {
	
	public static final int CELL_SIZE = 100;
	
	private Group myGroup;
	private Cell[][] myCells;


	/**
	 * Initializes the Group and 2D Array of Cells.
	 * @param setupInfo the File containing setup information
	 * including grid size, rules, and starting states.
	 */
	public Grid(File setupInfo) {
		myGroup = new Group();
		initializeArray(setupInfo);
		passNeighbors();
		updateGroup();
	}
	
	/**
	 * Accesses the Group displaying all the cells.
	 * Group will be updated when simulation changes frame.
	 * @return the Group
	 */
	public Group getGroup() {
		return myGroup;
	}
	
	/**
	 * Updates every cell.
	 * Calculates the future state of every cell, then
	 * refreshes the state of every cell, then
	 * updates the Group.
	 */
	public void nextFrame() {
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				myCells[row][col].calculateFutureState();
			}
		}
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				myCells[row][col].refreshState();
			}
		}
		updateGroup();
	}
	
	/**
	 * Initializes the main 2D array of Cells.
	 * Passes each Cell its rules and starting state.
	 * @param setupInfo the File containing the size, rules, and starting states.
	 */
	private void initializeArray(File setupInfo) {
		XMLParser parser = new XMLParser(setupInfo);	
		Rules rules = Rules.getRules(parser.getRuleName());
		NodeList stateList = parser.getInitialStates();
		myCells = new Cell[parser.getGridRows()][parser.getGridColumns()];
		
		int count = 0;
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				String stateText = stateList.item(count++).toString();
				State state = rules.getStartingState(stateText, row, col);
				myCells[row][col] = new Cell(rules, state);
			}
		}
	}
	
	/**
	 * Passes each Cell in the 2D array the cells directly next to it.
	 */
	private void passNeighbors() {
		for(int row = 0; row < myCells.length; row++) {
			for(int col = 0; col < myCells[0].length; col++) {
				Cell[][] neighbors = new Cell[3][3];
				for(int nRow = 0; nRow < neighbors.length; nRow++) {
					for(int nCol = 0; nCol < neighbors[0].length; nCol++) {
						try {
							neighbors[nRow][nCol] = myCells[nRow + row - 1][nCol + col - 1];
						}
						catch (ArrayIndexOutOfBoundsException e) {
							neighbors[nRow][nCol] = null;
						}
					}
				}
				myCells[row][col].setNeighbors(neighbors);
			}
		}
	}
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 */
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
