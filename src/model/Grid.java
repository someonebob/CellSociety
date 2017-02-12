package model;
import java.io.File;
import java.util.ArrayList;
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

	private TreeMap<Coordinate, Cell> myCells;
	private int numRows, numCols;
	private Coordinate min, max;
	private int cyclesPerTick;
	private String type;
	private Rules rules;

	/**
	 * Initializes the 2D Array of Cells.
	 * @param setupInfo the File containing setup information
	 * @param shape of grid
	 * including grid size, rules, and starting states.
	 */
	public Grid(File setupInfo, String type) {
		XMLParser config = new XMLParser(setupInfo);
		this.rules = new RulesLoader(config).getRules();
		cyclesPerTick = Integer.parseInt(config.getParameter("cyclesPerTick"));
		this.type = type;
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
//			this.connectNeighborsInfinite();
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
	private void passNeighbors() {
		NeighborhoodLoader gridTypeSelector = new NeighborhoodLoader();

		for(Coordinate c : myCells.keySet()) {
			Neighborhood neighborhood = gridTypeSelector.getNeighborhood(type);
			
			neighborhood.setCenter(myCells.get(c), c);
			
			for(Coordinate neighborLocal : neighborhood.getLocalNeighborCoordinates()){
				Coordinate neighborGlobal = c.add(neighborLocal);
				neighborhood.set(myCells.get(neighborGlobal), neighborGlobal);
			}	
			myCells.get(c).setNeighborhood(neighborhood);
		}
		
//		this.connectNeighborsToroidal();
	}
	
	private void calculateGridSize(){
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
	
	public void connectNeighborsToroidal(){		
		for(Coordinate globalCellPos: myCells.keySet()){
			Cell current = myCells.get(globalCellPos);
			for(Coordinate localNeighborPos: current.getNeighborhood().getLocalNeighborCoordinates()){
				if(current.getNeighborhood().get(localNeighborPos) == null){	// This means that we must replace this with the toroidal neighbor
					Coordinate toroidNeighborPos = globalCellPos.add(localNeighborPos);

					int newRow = toroidNeighborPos.getRow(), newCol = toroidNeighborPos.getCol();
					
					if(toroidNeighborPos.getRow() >= this.getRows()) newRow = 0; // bottom -> top
					else if(toroidNeighborPos.getRow() <= -1) newRow = getRows() - 1; // top -> bottom

					if(toroidNeighborPos.getCol() >= this.getCols()) newCol = 0; // right -> left
					else if(toroidNeighborPos.getCol() <= -1) newCol = this.getCols() - 1; // left -> right
					
					toroidNeighborPos = new Coordinate(newRow, newCol);
					current.getNeighborhood().setLocally(myCells.get(toroidNeighborPos), localNeighborPos);
				}
			}
		}
	}
	
	public void connectNeighborsInfinite(){
		ArrayList<Coordinate> newCellPositions = new ArrayList<Coordinate>();
		for(Coordinate globalCellPos: myCells.keySet()){
			Cell current = myCells.get(globalCellPos);
			if(isOnEdge(globalCellPos) && !current.getCurrentState().equals(rules.getDefaultState())){ // Grid expansion is not affected by cells of default state
				for(Coordinate localNeighborPos : current.getNeighborhood().getLocalNeighborCoordinates()){
					if(current.getNeighborhood().get(localNeighborPos) == null){
						newCellPositions.add(globalCellPos.add(localNeighborPos));
					}
				}
			}
		}
		
		for(Coordinate newCellPos : newCellPositions){
			myCells.put(newCellPos, new Cell(rules, rules.getDefaultState()));
		}
		
		calculateGridSize();
		fillEmptyCells();
		passNeighbors();
	}
	
	
	private void fillEmptyCells(){
		for(int r = min.getRow(); r <= max.getRow(); r++){
			for(int c = min.getCol(); c <= max.getCol(); c++){
				Coordinate cellPos = new Coordinate(r, c);
				if(myCells.get(cellPos) == null) myCells.put(cellPos, new Cell(rules, rules.getDefaultState()));
			}
		}
	}
	
	public boolean isOnEdge(Coordinate c){
		if(c.getRow() == max.getRow()) return true; 	// Bottom
		if(c.getRow() == min.getRow()) return true;		// Top
		if(c.getCol() == max.getCol()) return true; 	// Right
		if(c.getCol() == min.getCol()) return true;		// Left
		return false;	
	}
}
