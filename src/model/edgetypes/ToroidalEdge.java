package model.edgetypes;

import java.util.TreeMap;
import model.Cell;
import model.Coordinate;
import model.Edge;
import model.Grid;
import model.Rules;

/**
 * This class allows for toroidal interaction between cells
 * @author DhruvKPatel
 */
public class ToroidalEdge extends Edge {

	/**
	 * Call after neighbors are passed to cells in Grid.
	 * If a cell's neighbor is not in the grid size constraints, it will connect the neighbor to
	 * the corresponding toroidal cell on the other side.
	 */
	@Override
	public void onPassGridNeighbors(Grid myGrid, TreeMap<Coordinate, Cell> myCells, Rules rules) {
		for(Coordinate globalCellPos: myCells.keySet()){
			Cell current = myCells.get(globalCellPos);
			for(Coordinate localNeighborPos: current.getNeighborhood().getLocalNeighborCoordinates()){
				if(current.getNeighborhood().get(localNeighborPos) == null){	// This means that we must replace this with the toroidal neighbor
					Coordinate toroidNeighborPos = globalCellPos.add(localNeighborPos);

					int newRow = toroidNeighborPos.getRow(), newCol = toroidNeighborPos.getCol();
					
					if(toroidNeighborPos.getRow() >= myGrid.getRows()) newRow = 0; // bottom -> top
					else if(toroidNeighborPos.getRow() <= -1) newRow = myGrid.getRows() - 1; // top -> bottom

					if(toroidNeighborPos.getCol() >= myGrid.getCols()) newCol = 0; // right -> left
					else if(toroidNeighborPos.getCol() <= -1) newCol = myGrid.getCols() - 1; // left -> right
					
					toroidNeighborPos = new Coordinate(newRow, newCol);
					current.getNeighborhood().setLocally(myCells.get(toroidNeighborPos), localNeighborPos);
				}
			}
		}		
	}
}
