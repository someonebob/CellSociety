package model;

/**
 * Holds a coordinate pair.
 * @author Nathaniel
 */
public class Coordinate implements Comparable<Coordinate> {
	int row, col;

	/**
	 * Creates a new coordinate pair.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Gets the x coordinate
	 * @return x
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the y coordinate
	 * @return y
	 */
	public int getCol() {
		return col;
	}

	@Override
	public int compareTo(Coordinate other) {
		int diff = this.row - other.row;
		if(diff == 0) {
			diff = this.col - other.col;
		}
		return diff;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Coordinate) {
			Coordinate other = (Coordinate)o;
			return this.row == other.row && this.col == other.col;
		}
		return false;	
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}