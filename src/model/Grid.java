package model;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.w3c.dom.DOMException;

import model.neighborhoods.NeighborhoodLoader;
import xml.XMLException;
import xml.XMLParser;

/**
 * Handles the grid of cells used in simulation.
 * @author Nathaniel Brooke
 * @version 2-01-2017
 * 
 * @author DhruvKPatel
 * @version 2-12-2017
 */
public class Grid {

	private TreeMap<Coordinate, Cell> myCells;
	private int numRows, numCols;
	private Coordinate min, max;
	private int cyclesPerTick;
	private String neighborhoodType;
	private Rules rules;
	private Edge edgeType;

	/**
	 * Initializes the 2D Array of Cells.
	 * @param setupInfo the File containing setup information
	 * @param shape of grid
	 * including grid size, rules, and starting states.
	 * @throws XMLException 
	 */
	public Grid(File setupInfo, String neighborhoodType, String edgeType) throws XMLException {
		XMLParser config = new XMLParser(setupInfo);
		
		this.cyclesPerTick = Integer.parseInt(config.getParameter("cyclesPerTick"));
		this.rules = new RulesLoader(config).getRules();

		this.neighborhoodType = neighborhoodType;
		this.edgeType = (new EdgeLoader()).getEdge(edgeType);
		
		initializeArray(config);
		passNeighbors();
	}
	
	/**
	 * Updates every cell.
	 * Calculates the future state of every cell, then
	 * refreshes the state of every cell.
	 */
	public void nextFrame() {
		for(int i = 0; i < cyclesPerTick; i++) {
			edgeType.onNextGridFrame(this, myCells, rules);
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
	 * @throws XMLException 
	 * @throws DOMException 
	 */
	private void initializeArray(XMLParser configuration) throws DOMException, XMLException {
		Map<Coordinate, String> stateReference = configuration.getInitialStates();
		myCells = new TreeMap<Coordinate, Cell>();	
		
		for(Coordinate c: stateReference.keySet()){
			State state = rules.getStartingState(stateReference.get(c));
			myCells.put(c, new Cell(rules, state));
		}
		calculateGridSize();
	}
	
	/**
	 * Passes each Cell in the 2D array the cells directly next to it.
	 */
	public void passNeighbors() {
		NeighborhoodLoader gridTypeSelector = new NeighborhoodLoader();

		for(Coordinate c : myCells.keySet()) {
			Neighborhood neighborhood = gridTypeSelector.getNeighborhood(neighborhoodType);
			
			neighborhood.setCenter(myCells.get(c), c);
			
			for(Coordinate neighborLocal : neighborhood.getLocalNeighborCoordinates()){
				Coordinate neighborGlobal = c.add(neighborLocal);
				neighborhood.set(myCells.get(neighborGlobal), neighborGlobal);
			}	
			myCells.get(c).setNeighborhood(neighborhood);
		}
		edgeType.onPassGridNeighbors(this, myCells, rules);
	}
	
	/**
	 * Resizes grid to encompass all cells in space.
	 * Note: cells can have negative coordinate, so min value is not assumed 0.
	 */
	public void calculateGridSize(){
		max = new Coordinate(0, 0);
		min = new Coordinate(0, 0);
		
		for(Coordinate c : myCells.keySet()){
			if(c.getRow() > max.getRow()) max = new Coordinate(c.getRow(), max.getCol());
			if(c.getCol() > max.getCol()) max = new Coordinate(max.getRow(), c.getCol());
			if(c.getRow() < min.getRow()) min = new Coordinate(c.getRow(), min.getCol());
			if(c.getCol() < min.getCol()) min = new Coordinate(min.getRow(), c.getCol());
		}
		
		numCols = max.getCol() - min.getCol() + 1;
		numRows = max.getRow() - min.getRow() + 1;
	}

	/**
	 * Given grid's intrinsic size constraints, if a cell is not contained in every coordinate possible,
	 * a cell with a default state will be added. This is helpful for infinite grids, but could be used
	 * for anything similar.
	 */
	public void fillEmptyCells(){
		for(int r = min.getRow(); r <= max.getRow(); r++){
			for(int c = min.getCol(); c <= max.getCol(); c++){
				Coordinate cellPos = new Coordinate(r, c);
				if(myCells.get(cellPos) == null) myCells.put(cellPos, new Cell(rules, rules.getDefaultState()));
			}
		}
	}
	
	/**
	 * Checks size constraints of grid with coordinate
	 * to see if coordinate is on edge of grid.
	 */
	public boolean isOnEdge(Coordinate c){
		if(c.getRow() == max.getRow()) return true; 	// Bottom
		if(c.getRow() == min.getRow()) return true;		// Top
		if(c.getCol() == max.getCol()) return true; 	// Right
		if(c.getCol() == min.getCol()) return true;		// Left
		return false;	
	}
	
	/**
	 * Returns map of states and number of cells with states.
	 * This method is currently only used for graphing states
	 */
	public Map<String, Integer> getStateQuantities(){
		Map<String, Integer> stateQuantities = new TreeMap<String, Integer>();

		for(Cell current: myCells.values()){
			String currentState = current.getCurrentState().getValue();
			if(!stateQuantities.containsKey(currentState)) stateQuantities.put(currentState, 1);
			else stateQuantities.put(currentState, stateQuantities.get(currentState) + 1);
		}
		return stateQuantities;
	}
}