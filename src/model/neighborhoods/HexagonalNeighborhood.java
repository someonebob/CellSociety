package model.neighborhoods;

import java.util.ArrayList;
import java.util.Collection;

import model.Cell;
import model.Coordinate;
import model.Neighborhood;

public class HexagonalNeighborhood extends Neighborhood {
	private int columnwiseOffset;
	
	@Override
	public void setCenter(Cell cell, Coordinate c){ 	// If cell is on an odd row, the offset for neighbors will change
		super.setCenter(cell, c);
		columnwiseOffset = c.getRow()%2 == 1 ? 0 : -1;
	}
	
	/**
	 * Returns collection of Adjacent local cell coordinates
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalAdjacentCoordinates() {
		Collection<Coordinate> adjacent = new ArrayList<Coordinate>();
		adjacent.add(new Coordinate(-1, columnwiseOffset)); 	// Top left
		adjacent.add(new Coordinate(-1, columnwiseOffset + 1)); 	// Top right
		adjacent.add(new Coordinate(0, -1)); 	// left
		adjacent.add(new Coordinate(0, 1)); 	// right
		adjacent.add(new Coordinate(1, columnwiseOffset)); 	// bottom left
		adjacent.add(new Coordinate(1, columnwiseOffset + 1)); 	// bottom right
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
