package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.NodeList;

import xml.XMLParser;

/**
 * Handles the grid of cells used in simulation.
 * @author Nathaniel Brooke
 * @version 2-01-2017
 */
public class Grid {

	private Map<Coordinate, Cell> myCells;
	private int numRows, numCols;

	/**
	 * Initializes the 2D Array of Cells.
	 * @param setupInfo the File containing setup information
	 * including grid size, rules, and starting states.
	 */
	public Grid(File setupInfo) {
		initializeArray(setupInfo);
		passNeighbors();
	}

	/**
	 * Updates every cell.
	 * Calculates the future state of every cell, then
	 * refreshes the state of every cell.
	 */
	public void nextFrame() {
		for(Cell c : myCells.values()) {
			c.calculateFutureState();
		}
		for(Cell c : myCells.values()) {
			c.refreshState();
		}
	}

	/**
	 * Accesses a specific Cell in the Grid.
	 * @param coord the Coordinate indicating where the Cell is.
	 * @return the Cell.
	 */
	public Cell getCell(Coordinate coord) {
		return myCells.get(coord);
	}
	
	/**
	 * Gets a collection of all coordinates in the Grid.
	 * @return the coordinates.
	 */
	public Set<Coordinate> getCoordinates() {
		return myCells.keySet();
	}
	
	/**
	 * Gets the number of rows in the Grid.
	 * @return number of rows.
	 */
	public int getRows() {
		return numRows;
	}
	
	/**
	 * Gets the number of columns in the Grid.
	 * @return number of columns.
	 */
	public int getCols() {
		return numCols;
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
		numRows = parser.getGridRows();
		numCols = parser.getGridColumns();		
		int count = 0;
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				String stateText = stateList.item(count++).getTextContent();
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
			Neighborhood neighbors = new Neighborhood();
			for(int nRow = -1; nRow <= 1; nRow++) {
				for(int nCol = -1; nCol <= 1; nCol++) {
					Coordinate nbrLoc = new Coordinate(c.getRow() + nRow, c.getCol() + nCol);
					neighbors.set(myCells.get(nbrLoc), nRow, nCol);
				}
			}
			myCells.get(c).setNeighborhood(neighbors);
		}
	}

}
