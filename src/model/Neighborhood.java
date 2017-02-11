/**
 * Creates a 3x3 neighborhood of cells, with main cell in center 
 * and neighbors surrounding in all directions
 * 
 * @author DhruvKPatel
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public abstract class Neighborhood {
	private Map<Coordinate, Cell> neighborhood;
	public int xOffset, yOffset; // TODO SWITCH TO PRIVATE AFTER DEBUGGING
	
	/**
	 * Constructs a new empty neighborhood of null cells. To add a cell, use "set" method
	 */
	public Neighborhood(){
		neighborhood = new TreeMap<Coordinate, Cell>();
	}
	
	/**
	 * Sets input as "zero" cell. All neighbors will be 
	 * automatically put into new coordinate system
	 * centered at center cell
	 * @param cell
	 * @param row
	 * @param col
	 */
	public void setCenter(Cell cell, Coordinate c){
		xOffset = c.getCol();
		yOffset = c.getRow();
		neighborhood.put(new Coordinate(0, 0), cell);
	}
	
	/**
	 * Sets cell at certain coordinate in neighborhood.
	 * Input is in global coordinate; it will automatically
	 * put cell in local coordinates relative to center cell
	 * @param row
	 * @param c 
	 */
	public void set(Cell cell, Coordinate c){
		neighborhood.put(new Coordinate(c.getRow() - yOffset, c.getCol() - xOffset), cell);
	}
		
	/**
	 * Returns cell at certain coordinate in neighborhood
	 * relative to center cell
	 */
	public Cell get(Coordinate c){
		return neighborhood.get(c);
	}
	
	/**
	 * Returns center cell
	 * @return center cell
	 */
	public Cell getCenter(){
		return neighborhood.get(new Coordinate(0, 0));
	}	
	
	/**
	 * Returns collection of all neighbor cells
	 * Note: if cell is put into a coordinate that is 
	 * not valid in subclass diagonal or adjacent space. 
	 * @return collection of all neighbor cells
	 */
	public Collection<Cell> getNeighbors(){
		return this.getCellsFromCoordinates(this.getLocalNeighborCoordinates());
	}
	
	/**
	 * Returns collection of all adjacent cells
	 * @return collection of all adjacent cells
	 */
	public Collection<Cell> getAdjacent(){
		return this.getCellsFromCoordinates(this.getLocalAdjacentCoordinates());
	}
	
	/**
	 * Returns collection of all diagonal cells
	 * @return collection of all diagonal cells
	 */
	public Collection<Cell> getDiagonal(){
		return this.getCellsFromCoordinates(this.getLocalDiagonalCoordinates());
	}
	
	/**
	 * Returns a collection of local neighbor coordinates,
	 * where (0, 0) is the center cell
	 */
	public Collection<Coordinate> getLocalNeighborCoordinates(){

		Collection<Coordinate> allNeighbors = new ArrayList<Coordinate>(getLocalAdjacentCoordinates());				// Sum of known neighbors
		Collection<Coordinate> diagonalNeighbors = new ArrayList<Coordinate>(getLocalDiagonalCoordinates());
		allNeighbors.addAll(diagonalNeighbors);
		return allNeighbors;
	}
	
	/**
	 * Returns list of cells relating to coordinates
	 * @param Collection of coordinates
	 * @return Collection of cells
	 */
	private Collection<Cell> getCellsFromCoordinates(Collection<Coordinate> coordinates){
		Collection<Cell> cells = new ArrayList<Cell>();
		for(Coordinate c : coordinates)
			cells.add(this.get(c));
		return cells;
	}
	
	/**
	 * Returns a printable string describing the neighborhood of cells
	 * @return a printable string describing the neighborhood of cells
	 */
	@Override
	public String toString() {
		String output =  "Neighborhood of " + getCenter() + ":\n";
		for(Coordinate c : this.getLocalNeighborCoordinates())
			output += c + ": " + this.get(c) + "\n";
		return output + "\n";
	}	
	
	/**
	 * Returns collection of Adjacent cells
	 * (Edges are fully connected)
	 */
	public abstract Collection<Coordinate> getLocalAdjacentCoordinates();
	
	/**
	 * Returns collection of Diagonal cells
	 * (Edges are not fully connected)
	 */
	public abstract Collection<Coordinate> getLocalDiagonalCoordinates();
}
