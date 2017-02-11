package model;

import java.util.ArrayList;
import java.util.Collection;

public class TriangularNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent local cell coordinates
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalAdjacentCoordinates() {
		Collection<Coordinate> adjacent = new ArrayList<Coordinate>();
		adjacent.add(new Coordinate(0, 1)); 	// Directly right
		adjacent.add(new Coordinate(0, -1)); 	// Directly left
		adjacent.add(new Coordinate(1, 0)); 	// Directly below
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal local cell coordinates
	 * (Edges are not fully connected)
	 */
	public Collection<Coordinate> getLocalDiagonalCoordinates() {
		Collection<Coordinate> diagonal = new ArrayList<Coordinate>();
		diagonal.add(new Coordinate(-1, 0)); 	// Directly above
		diagonal.add(new Coordinate(-1, -1)); 	// Top left
		diagonal.add(new Coordinate(-1, 1)); 	// Top right
		diagonal.add(new Coordinate(0, -2));	// Middle, two left
		diagonal.add(new Coordinate(0, 2)); 	// Middle, two right
		diagonal.add(new Coordinate(1, -2));	// Bottom, two left
		diagonal.add(new Coordinate(1, -1));	// Bottom, one left
		diagonal.add(new Coordinate(1, 1));		// Bottom, one right
		diagonal.add(new Coordinate(1, 2));		// Bottom, two right
		return diagonal;
	}
	
}
