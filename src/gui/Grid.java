package gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import model.Cell;
import model.Rules;
import model.State;
import xml.XMLParser;

/**
 * Handles the grid of cells used in simulation.
 * @author Nathaniel Brooke
 * @version 2-01-2017
 */
public class Grid {
	
	/**
	 * Holds a coordinate pair.
	 * @author Nathaniel
	 */
	private class Coordinate implements Comparable<Coordinate> {
		int x, y;
		
		/**
		 * Creates a new coordinate pair.
		 * @param x coordinate
		 * @param y coordinate
		 */
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Gets the x coordinate
		 * @return x
		 */
		public int getX() {
			return x;
		}
		
		/**
		 * Gets the y coordinate
		 * @return y
		 */
		public int getY() {
			return y;
		}

		@Override
		public int compareTo(Coordinate other) {
			int diff = this.x - other.x;
			if(diff == 0) {
				diff = this.y - other.y;
			}
			return diff;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Coordinate) {
				Coordinate other = (Coordinate)o;
				return this.x == other.x && this.y == other.y;
			}
			return false;	
		}
	}
	
	public static final int CELL_SIZE = 100;
	
	private Group myGroup;
	private Map<Coordinate, Cell> myCells;


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
		for(Cell c : myCells.values()) {
			c.calculateFutureState();
		}
		for(Cell c : myCells.values()) {
				c.refreshState();
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
		Rules rules = Rules.getRules(setupInfo);
		NodeList stateList = parser.getInitialStates();
		myCells = new HashMap<Coordinate, Cell>();		
		int numRows = parser.getGridRows();
		int numCols = parser.getGridColumns();		
		int count = 0;
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				String stateText = stateList.item(count++).getTextContent(); //TODO this doesn't work
				State state = rules.getStartingState(stateText);
				myCells.put(new Coordinate(row, col), new Cell(rules, state));
			}
		}
	}
	
	/**
	 * Passes each Cell in the 2D array the cells directly next to it.
	 */
	private void passNeighbors() {
		for(Coordinate c : myCells.keySet()) {
				Cell[][] neighbors = new Cell[3][3];
				for(int nRow = 0; nRow < neighbors.length; nRow++) {
					for(int nCol = 0; nCol < neighbors[0].length; nCol++) {
						try {
							Coordinate nbrLoc = new Coordinate(c.getX() + nRow, c.getY() + nCol);
							neighbors[nRow][nCol] = myCells.get(nbrLoc);
						}
						catch (ArrayIndexOutOfBoundsException e) {
							neighbors[nRow][nCol] = null;
						}
					}
				}
				myCells.get(c).setNeighbors(neighbors);
		}
	}
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 */
	private void updateGroup() {
		myGroup.getChildren().clear();
		for(Coordinate c : myCells.keySet()) {
			Rectangle r = (Rectangle)myCells.get(c).getCurrentState().getStateNode();
			r.setX(c.getX()*CELL_SIZE);
			r.setY(c.getY()*CELL_SIZE);
			myGroup.getChildren().add(r);
		}
	}

}
