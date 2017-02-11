package model;

import java.util.ArrayList;
import java.util.Collection;

public class RectangularNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent local cell coordinates
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalAdjacentCoordinates() {
		Collection<Coordinate> adjacent = new ArrayList<Coordinate>();
		adjacent.add(new Coordinate(-1, 0)); // Above
		adjacent.add(new Coordinate(1, 0));  // Below
		adjacent.add(new Coordinate(0, -1)); // To the left
		adjacent.add(new Coordinate(0, 1));  // To the right
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal local cell coordinates
	 * (Edges are not fully connected)
	 */
	public Collection<Coordinate> getLocalDiagonalCoordinates() {
		Collection<Coordinate> diagonal = new ArrayList<Coordinate>();
		diagonal.add(new Coordinate(-1, -1)); // Top Left
		diagonal.add(new Coordinate(1, -1));	// Bottom left
		diagonal.add(new Coordinate(1, 1)); 	// Bottom right
		diagonal.add(new Coordinate(-1, 1));	// Top right
		return diagonal;
	}
	
}
