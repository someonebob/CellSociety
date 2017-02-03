package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Creates a neighborhood of cells, with main cell in center and neighbors surrounding
 * @author DhruvKPatel
 */
public class Neighborhood {

		private Cell[][] neighborhood;
		
		public Neighborhood(){
			neighborhood = new Cell[3][3];
		}
		
		/**
		 * Sets cell at certain coordinate in neighborhood.
		 * @param row
		 * @param col
		 * @param cell
		 */
		public void set(Cell cell, int row, int col){
			if(inRange(row, col))
				neighborhood[row][col] = cell;
			else throw new IndexOutOfBoundsException("Neighborhood coordinates out of range");
		}
		
		/**
		 * Gets cell at certain coordinate in neighborhood
		 */
		public Cell get(int row, int col){
			return neighborhood[row][col];
		}
		
		/**
		 * Returns a collection of all neighbor cells
		 */
		public Collection<Cell> getNeighbors(){
			Collection<Cell> neighbors = new ArrayList<Cell>();
			
			for(int row = 0; row < neighborhood.length; row++)
				for(int col = 0; col < neighborhood[0].length; col++)
					if(row != 2 || col != 2) neighbors.add(neighborhood[row][col]);
			
			return neighbors;
		}
		
		/**
		 * Returns center cell
		 */
		public Cell getCenter(){
			return neighborhood[2][2];
		}
		
		/**
		 * Checks if coordinates are in range of neighborhood
		 * @param row
		 * @param col
		 * @return coordinates in range
		 */
		private boolean inRange(int row, int col){
			return row >= 0 && row < neighborhood.length && col >= 0 && col < neighborhood[0].length;
		}
		
}
