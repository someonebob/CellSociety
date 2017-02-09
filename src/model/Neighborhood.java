/**
 * Creates a 3x3 neighborhood of cells, with main cell in center 
 * and neighbors surrounding in all directions
 * 
 * @author DhruvKPatel
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Neighborhood {
	private HashMap<Coordinate, Cell> neighborhood;
	private int xOffset, yOffset;
	
	/**
	 * Constructs a new empty neighborhood of null cells. To add a cell, use "set" method
	 */
	public Neighborhood(){
		neighborhood = new HashMap<Coordinate, Cell>();
	}
	
	/**
	 * Sets input as "zero" cell. All neighbors will be 
	 * automatically put into new coordinate system
	 * centered at center cell
	 * @param cell
	 * @param row
	 * @param col
	 */
	public void setCenter(Cell cell, int row, int col){
		xOffset = col;
		yOffset = row;
		neighborhood.put(new Coordinate(0, 0), cell);
	}
	
	/**
	 * Sets cell at certain coordinate in neighborhood.
	 * Input is in global coordinate; it will automatically
	 * put cell in local coordinates relative to center cell
	 * @param row
	 * @param col
	 * @param cell
	 */
	public void set(Cell cell, int row, int col){
		neighborhood.put(new Coordinate(row - yOffset, col - xOffset), cell);
	}
		
	/**
	 * Returns cell at certain coordinate in neighborhood
	 * relative to center cell
	 * 
	 */
	public Cell get(int row, int col){
		return neighborhood.get(new Coordinate(row, col));
	}
	
	/**
	 * Returns a map of all neighbor cells.
	 * Map keys are cardinal directions relative to center cell.
	 * @return collection of neighboring cells (can be null)
	 */
	public Collection<Cell> getNeighbors(){
		Collection<Cell> neighbors = new ArrayList<Cell>();
		for(Coordinate position: neighborhood.keySet()){
			if(!position.equals(new Coordinate(0, 0))) neighbors.add(neighborhood.get(position));
		}
		return neighbors;
	}
	
	/**
	 * Returns center cell
	 * @return center cell
	 */
	public Cell getCenter(){
		return neighborhood.get(new Coordinate(0, 0));
	}	
	
	/**
	 * Returns a printable string describing the neighborhood of cells
	 * @return a printable string describing the neighborhood of cells
	 */
	@Override
	public String toString() {
		return "Neighborhood of " + getCenter();
	}	
}
