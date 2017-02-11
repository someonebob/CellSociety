package model;

import java.util.HashMap;

public class NeighborhoodLoader {	
	public Neighborhood getNeighborhood(String gridType){
		HashMap<String, Neighborhood> neighborhoods = new HashMap<String, Neighborhood>();
		neighborhoods.put("square", new SquareNeighborhood());
		neighborhoods.put("triangular", new TriangularNeighborhood());
		neighborhoods.put("hexagonal", new HexagonalNeighborhood());
		return neighborhoods.get(gridType);
	}
}
