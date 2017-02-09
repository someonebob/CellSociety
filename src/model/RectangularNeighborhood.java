package model;

import java.util.ArrayList;
import java.util.Collection;

public class RectangularNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent cells
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Cell> getAdjacent() {
		Collection<Cell> adjacent = new ArrayList<Cell>();
		adjacent.add(this.get(-1, 0)); // Above
		adjacent.add(this.get(1, 0));  // Below
		adjacent.add(this.get(0, -1)); // To the left
		adjacent.add(this.get(0, 1));  // To the right
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal cells
	 * (Edges are not fully connected)
	 */
	public Collection<Cell> getDiagonal() {
		Collection<Cell> diagonal = new ArrayList<Cell>();
		diagonal.add(this.get(-1, -1)); // Top Left
		diagonal.add(this.get(1, -1));	// Bottom left
		diagonal.add(this.get(1, 1)); 	// Bottom right
		diagonal.add(this.get(-1, 1));	// Top right
		return diagonal;
	}
	
}
