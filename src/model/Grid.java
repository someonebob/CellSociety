package model;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
		XMLParser configuration = new XMLParser(setupInfo);
		initializeArray(configuration);
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
		System.out.println("---------------------grid line 43");
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
	 * Initializes the Map of Coordinates and Cells.
	 * Passes each Cell its rules and starting state.
	 * @param setupInfo the File containing the size, rules, and starting states.
	 */
	private void initializeArray(XMLParser configuration) {
		Rules rules = new RulesLoader(configuration).getRules();
		Map<Coordinate, String> stateReference = configuration.getInitialStates();
		myCells = new TreeMap<Coordinate, Cell>();	
		
		for(Coordinate c: stateReference.keySet()){
			if(c.row + 1 > numRows) numRows = c.row + 1;
			if(c.col + 1 > numCols) numCols = c.col + 1;
			State state = rules.getStartingState(stateReference.get(c));			
			myCells.put(c, new Cell(rules, state));
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
					neighbors.set(myCells.get(nbrLoc), nRow + 1, nCol + 1);
				}
			}
			myCells.get(c).setNeighborhood(neighbors);
		}
	}

}
