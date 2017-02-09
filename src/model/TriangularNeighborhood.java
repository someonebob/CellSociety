package model;

import java.util.ArrayList;
import java.util.Collection;

public class TriangularNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent cells
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Cell> getAdjacent() {
		Collection<Cell> adjacent = new ArrayList<Cell>();
		adjacent.add(this.get(0, 1)); 	// Directly right
		adjacent.add(this.get(0, -1)); 	// Directly left
		adjacent.add(this.get(1, 0)); 	// Directly below
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal cells
	 * (Edges are not fully connected)
	 */
	public Collection<Cell> getDiagonal() {
		Collection<Cell> diagonal = new ArrayList<Cell>();
		diagonal.add(this.get(-1, 0)); 	// Directly above
		diagonal.add(this.get(-1, -1)); // Top left
		diagonal.add(this.get(-1, 1)); 	// Top right
		diagonal.add(this.get(0, -2));	// Middle, two left
		diagonal.add(this.get(0, 2)); 	// Middle, two right
		diagonal.add(this.get(1, -2));// Bottom, two left
		diagonal.add(this.get(1, -1));// Bottom, one left
		diagonal.add(this.get(1, 1));// Bottom, one right
		diagonal.add(this.get(1, 2));// Bottom, two right
		return diagonal;
	}
}
