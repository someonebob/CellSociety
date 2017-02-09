package model;

import java.util.HashMap;

public class NeighborhoodLoader {
	public static Neighborhood getNeighborhood(String neighborhoodType){
		HashMap<String, Neighborhood> neighborhoods = new HashMap<String, Neighborhood>();
		
		neighborhoods.put("rectangular", new RectangularNeighborhood());
		neighborhoods.put("triangular", new TriangularNeighborhood());
		neighborhoods.put("hexagonal", new HexagonalNeighborhood());
		
		return neighborhoods.get(neighborhoodType);
	}
}
