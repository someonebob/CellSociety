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
	private String GRID_TYPE_REFERENCE = "gridType";
	private Map<Coordinate, Cell> myCells;
	private int numRows, numCols;
	private int cyclesPerTick;
	private String gridType;

	/**
	 * Initializes the 2D Array of Cells.
	 * @param setupInfo the File containing setup information
	 * including grid size, rules, and starting states.
	 */
	public Grid(File setupInfo) {
		XMLParser config = new XMLParser(setupInfo);
		cyclesPerTick = Integer.parseInt(config.getParameter("cyclesPerTick"));
		gridType = config.getParameter(GRID_TYPE_REFERENCE);
		initializeArray(config);
		passNeighbors(config);
	}

	/**
	 * Updates every cell.
	 * Calculates the future state of every cell, then
	 * refreshes the state of every cell.
	 */
	public void nextFrame() {
		for(int i = 0; i < cyclesPerTick; i++) {
			for(Cell c : myCells.values()) {
				c.calculateFutureState();
			}
			for(Cell c : myCells.values()) {
				c.refreshState();
			}
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
	 * Initializes the Map of Coordinates and Cells.
	 * Passes each Cell its rules and starting state.
	 * @param setupInfo the File containing the size, rules, and starting states.
	 */
	private void initializeArray(XMLParser configuration) {
		Rules rules = new RulesLoader(configuration).getRules();
		Map<Coordinate, String> stateReference = configuration.getInitialStates();
		myCells = new TreeMap<Coordinate, Cell>();	
		
		for(Coordinate c: stateReference.keySet()){
			if(c.getRow() + 1 > numRows) numRows = c.getRow() + 1;
			if(c.getCol() + 1 > numCols) numCols = c.getCol() + 1;
			State state = rules.getStartingState(stateReference.get(c));
			myCells.put(c, new Cell(rules, state));
		}
	}
	
	/**
	 * Passes each Cell in the 2D array the cells directly next to it.
	 */
	private void passNeighbors(XMLParser configuration) {
		NeighborhoodLoader gridTypeSelector = new NeighborhoodLoader();

		for(Coordinate c : myCells.keySet()) {
			Neighborhood neighborhood = gridTypeSelector.getNeighborhood(gridType);
			
			neighborhood.setCenter(myCells.get(c), c);
			
			for(Coordinate neighborLocal : neighborhood.getLocalNeighborCoordinates()){
				Coordinate neighborGlobal = c.add(neighborLocal);
				neighborhood.set(myCells.get(neighborGlobal), neighborGlobal);
			}	
			myCells.get(c).setNeighborhood(neighborhood);
		}
		
		for(Coordinate c: myCells.keySet()){
			System.out.println(myCells.get(c).getNeighborhood().getLocalNeighborCoordinates() + "" +c);
		}
	}

}
