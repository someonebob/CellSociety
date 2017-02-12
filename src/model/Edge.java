package model;

import java.util.TreeMap;

/**
 * Superclass structure for special edge types, which has two acessible points in grid control loop
 * This is the default edge type, which does nothing extra on method calls.
 * @author DhruvKPatel
 *
 */
public class Edge {
	public void onNextGridFrame(Grid myGrid, TreeMap<Coordinate, Cell> myCells, Rules myRules){};		// This must be a TreeMap, because comparables used to distinguish coordinates (no hashcode implemented)
	public void onPassGridNeighbors(Grid myGrid, TreeMap<Coordinate, Cell> myCells, Rules myRules){};
}
