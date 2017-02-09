/**
 * Creates a 3x3 neighborhood of cells, with main cell in center 
 * and neighbors surrounding in all directions
 * 
 * @author DhruvKPatel
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Neighborhood {
	private Cell[][] neighborhood;
	public static final int[] ADJACENT_INDICES = { 1, 3, 5, 7 };
	public static final int[] ALL_INDICES = { 0, 1, 2, 3, 5, 6, 7, 8 };

	/**
	 * Constructs a new empty neighborhood of null cells. To add a cell, use
	 * "set" method
	 */
	public Neighborhood() {
		neighborhood = new Cell[3][3];
	}

	/**
	 * Sets cell at certain coordinate in neighborhood.
	 * 
	 * @param row
	 * @param col
	 * @param cell
	 */
	public void set(Cell cell, int row, int col) {
		if (inRange(row, col)) {
			neighborhood[row][col] = cell;
		} else {
			throw new IndexOutOfBoundsException("Neighborhood coordinates out of range");
		}
	}

	/**
	 * Gets cell at certain coordinate in neighborhood
	 */
	public Cell get(int row, int col) {
		return neighborhood[row][col];
	}

	/**
	 * Returns a map of all neighbor cells. Map keys are cardinal directions
	 * relative to center cell.
	 * 
	 * @return collection of neighboring cells (can be null)
	 */
	public List<Cell> getNeighbors(int[] type) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int r = 0; r < neighborhood.length; r++) {
			for (int c = 0; c < neighborhood[0].length; c++) {
				cells.add(get(r, c));
			}
		}

		List<Cell> neighbors = new ArrayList<>();
		for (int i = 0; i < type.length; i++) {
			neighbors.add(cells.get(type[i]));
		}

		return neighbors;
	}

	/**
	 * Returns center cell
	 * 
	 * @return center cell
	 */
	public Cell getCenter() {
		return neighborhood[1][1];
	}

	/**
	 * Checks if coordinates are in range of neighborhood
	 * 
	 * @param row
	 * @param col
	 * @return coordinates in range
	 */
	private boolean inRange(int row, int col) {
		return row >= 0 && row < neighborhood.length && col >= 0 && col < neighborhood[0].length;
	}

	/**
	 * Returns a printable string describing the neighborhood of cells
	 * 
	 * @return a printable string describing the neighborhood of cells
	 */
	@Override
	public String toString() {
		String output = "";
		for (int row = 0; row < 3; row++) {
			output += "\n";
			for (int col = 0; col < 3; col++) {
				if (neighborhood[row][col] != null) {
					output += neighborhood[row][col].toString() + " ";
				} else {
					output += "***null***";
				}
			}
		}
		return output.substring(1);
	}
}
