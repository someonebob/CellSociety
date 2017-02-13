package model.edgetypes;

import java.util.ArrayList;
import java.util.TreeMap;

import model.Cell;
import model.Coordinate;
import model.Edge;
import model.Grid;
import model.Rules;

/**
 * This edgetype allows for infinite grid expansion
 * @author DhruvKPatel
 *
 */
public class InfiniteEdge extends Edge {

	/**
	 * If a cell on the grid edge does not have a default state, its neighbors will be added to grid
	 * Then, the grid will resize to ensure that all rows and columns are even
	 * 
	 * Call in Grid before rest of grid frame is refreshed
	 */
	@Override
	public void onNextGridFrame(Grid myGrid, TreeMap<Coordinate, Cell> myCells, Rules myRules) {
		ArrayList<Coordinate> newCellPositions = new ArrayList<Coordinate>();
		for(Coordinate globalCellPos: myCells.keySet()){
			Cell current = myCells.get(globalCellPos);
			if(myGrid.isOnEdge(globalCellPos) && !current.getCurrentState().equals(myRules.getDefaultState())){ // Grid expansion is not affected by cells of default state
				for(Coordinate localNeighborPos : current.getNeighborhood().getLocalNeighborCoordinates()){
					if(current.getNeighborhood().get(localNeighborPos) == null){
						newCellPositions.add(globalCellPos.add(localNeighborPos));
					}
				}
			}
		}
		
		for(Coordinate newCellPos : newCellPositions){
			myCells.put(newCellPos, new Cell(myRules, myRules.getDefaultState()));
		}
		
		myGrid.calculateGridSize();	// This is the only reason these methods are public in grid. (So edge types can use them)
		myGrid.fillEmptyCells();
		myGrid.passNeighbors();
	}
}
