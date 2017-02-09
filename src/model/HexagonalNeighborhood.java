package model;

import java.util.Collection;

public class HexagonalNeighborhood extends Neighborhood {

	/**
	 * Returns collection of Adjacent cells
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Cell> getAdjacent() {
		return this.getNeighbors();
	}

	/**
	 * Returns collection of Diagonal cells
	 * (Edges are not fully connected)
	 */
	public Collection<Cell> getDiagonal() {
		return null;
	}
	
}
