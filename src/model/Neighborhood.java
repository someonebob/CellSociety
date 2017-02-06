package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a neighborhood of cells, with main cell in center and neighbors surrounding
 * @author DhruvKPatel
 */
public class Neighborhood {
		public static final String NW = "NW";
		public static final String N = "N";
		public static final String NE = "NE";
		public static final String W = "W";
		public static final String E = "E";
		public static final String SW = "SW";
		public static final String S = "S";
		public static final String SE = "SE";
		public static final List<String> LOCATIONS = Arrays.asList(new String[] {NW, N, NE, W, E, SW, S, SE});


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
			if(inRange(row, col)) {
				neighborhood[row][col] = cell;
			}
			else {
				throw new IndexOutOfBoundsException("Neighborhood coordinates out of range");
			}
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
		public Map<String, Cell> getNeighbors(){
			Map<String, Cell> neighbors = new HashMap<String, Cell>();
			
			int count = 0;
			for(int row = 0; row < neighborhood.length; row++)
				for(int col = 0; col < neighborhood[0].length; col++)
					if(row != 1 || col != 1){
						neighbors.put(LOCATIONS.get(count++), neighborhood[row][col]);
					}
			
			return neighbors;
		}
		
		/**
		 * Returns center cell
		 */
		public Cell getCenter(){
			return neighborhood[1][1];
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
		
		@Override
		public String toString() {
			String output = "";
			for(int row = 0; row < 3; row++) {
				output += "\n";
				for(int col = 0; col < 3; col++) {
					if(neighborhood[row][col] != null) {
						output += neighborhood[row][col].toString() + " ";
					}
					else {
						output += "***null***";
					}
				}
			}
			return output.substring(1);
		}
		
}
