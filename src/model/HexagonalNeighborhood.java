package model;

import java.util.ArrayList;
import java.util.Collection;

public class HexagonalNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent local cell coordinates
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalAdjacentCoordinates() {
		Collection<Coordinate> adjacent = new ArrayList<Coordinate>();
		adjacent.add(new Coordinate(-1, 0)); 	// Directly above
		adjacent.add(new Coordinate(-1, -1)); 	// Top left
		adjacent.add(new Coordinate(-1, 1)); 	// Top right
		adjacent.add(new Coordinate(0, -1)); 	// Directly left
		adjacent.add(new Coordinate(0, 1)); 	// Directly right
		adjacent.add(new Coordinate(1, 0)); 	// Directly below
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal local cell coordinates
	 * (Edges are not fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalDiagonalCoordinates() {
		Collection<Coordinate> diagonal = new ArrayList<Coordinate>();
		// Hexagonal tesselations have no diagonal neighbors.
		return diagonal;
	}
	
}
