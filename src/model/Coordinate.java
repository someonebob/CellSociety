package model;

/**
 * Holds a coordinate pair.
 * @author Nathaniel
 */
public class Coordinate implements Comparable<Coordinate> {
	private int row, col;

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
	 * Constructs a copy of a coordinate
	 */
	public Coordinate(Coordinate c){
		this(c.getRow(), c.getCol());
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

	
	/**
	 * Returns sum of two coordinates
	 * add rows, add columns
	 */
	public Coordinate add(Coordinate other){
		return new Coordinate(this.getRow() + other.getRow(), this.getCol() + other.getCol());
	}

	/**
	 * Returns difference of two coordinates.
	 * Parameter is assumed right side of subtraction
	 */
	public Coordinate subtract(Coordinate other){
		return add(new Coordinate(-other.getRow(), -other.getCol()));
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