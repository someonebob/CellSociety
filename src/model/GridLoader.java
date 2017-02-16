package model;

import java.io.File;

import model.gridType.InfiniteGrid;
import model.gridType.ToroidalGrid;
import xml.XMLException;

/**
 * Loader for grid edge types.
 * If new edge type is added, add corresponding keyword and class here
 * @author DhruvKPatel
 */

/*
 * FOR MY MASTERPIECE, START HERE. (Dhruv K Patel)
 * 
 * My masterpiece demonstrates my knowledge of polymorphism and class hierarchies. 
 * 
 * To implement different grid edge types, I first created an "Edge" super-class,
 * in which its children represented different types of edges. Although it also
 * implemented class hierarchies, it did not intuitively make sense. Grids with 
 * different edge types are inherently still grids, and they should be treated 
 * like specific types of grids. This is why I decided to change the design of
 * my code so that the grids with different edge types were childrens of the grid object.
 * 
 * I created two empty public methods inside the Grid class. They are linked in two
 * key points in the rest of the grid class which are essential to creating the 
 * right grid structures. 
 * 
 * This public methods are reimplemented in subclasses redefined by each gridType. 
 * In this case, I implemented them in "InfiniteGrid" and "ToroidalGrid"
 * 
 * Because I placed these methods in specific locations in grid, I will
 * be able to expand on almost any feasible type of Grid, and because the
 * grid edge types are now part of grid subclasses, it is very conveniant to
 * access Grid's public methods.
 * 
 * 
 * This specific gridLoader class is used to do the "dirty work" for grids. It returns the 
 * correct grid instance in terms of the String "edgeType". Take a look at my changes
 * made in the sub-classes of GridImager, to see how I used this class.
 */
public class GridLoader {
	public Grid getGrid(File setupInfo, String neighborhoodType, String edgeType) throws XMLException{
		if(edgeType != null){
			if(edgeType.equals("Infinite")) return new InfiniteGrid(setupInfo, neighborhoodType);
			if(edgeType.equals("Toroidal")) return new ToroidalGrid(setupInfo, neighborhoodType);
		}
		return new Grid(setupInfo, neighborhoodType);
	}
}
