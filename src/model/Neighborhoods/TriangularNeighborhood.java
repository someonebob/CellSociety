package model.Neighborhoods;

import java.util.ArrayList;
import java.util.Collection;

import model.Cell;
import model.Coordinate;
import model.Neighborhood;

public class TriangularNeighborhood extends Neighborhood {
	private boolean cellIsUpsideDown;
	private int rowwiseOffset;
	
	@Override
	public void setCenter(Cell cell, Coordinate c){
		super.setCenter(cell, c);
		cellIsUpsideDown = (c.getCol() + c.getRow()) % 2 == 0;
		rowwiseOffset = cellIsUpsideDown? -1 : 1;
	}
	
	/**
	 * Returns collection of Adjacent local cell coordinates
	 * (Edges are fully connected)
	 */
	@Override
	public Collection<Coordinate> getLocalAdjacentCoordinates() {
		Collection<Coordinate> adjacent = new ArrayList<Coordinate>();
		adjacent.add(new Coordinate(0, 1)); // right	
		adjacent.add(new Coordinate(0, -1)); // left	
		adjacent.add(new Coordinate(rowwiseOffset, 0)); // top or bottom (depending on flip state)
		return adjacent;
	}

	/**
	 * Returns collection of Diagonal local cell coordinates
	 * (Edges are not fully connected)
	 */
	public Collection<Coordinate> getLocalDiagonalCoordinates() {
		Collection<Coordinate> diagonal = new ArrayList<Coordinate>();
		// sides
		diagonal.add(new Coordinate(0, -2)); // left
		diagonal.add(new Coordinate(0, 2)); // right
		
		// 3-neighbored side
		diagonal.add(new Coordinate(rowwiseOffset, -1)); // left
		diagonal.add(new Coordinate(rowwiseOffset, 1)); // right
		diagonal.add(new Coordinate(rowwiseOffset, 0)); // center
		
		// 5-neighbored side
		diagonal.add(new Coordinate(-rowwiseOffset, -1)); // 1 left
		diagonal.add(new Coordinate(-rowwiseOffset, 1)); // 1 right
		diagonal.add(new Coordinate(-rowwiseOffset, -2)); // 2 left
		diagonal.add(new Coordinate(-rowwiseOffset, 2)); // 2 right
		diagonal.add(new Coordinate(-rowwiseOffset, 0)); // center
		return diagonal;
	}
	
}
