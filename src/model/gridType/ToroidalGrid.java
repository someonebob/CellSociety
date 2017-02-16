package model.gridType;

import java.io.File;

import model.Cell;
import model.Coordinate;
import model.Grid;
import model.Rules;
import xml.XMLException;

/*
 * This class reimplements a method in the grid class,
 * making it so that the grid is toroidal
 */
public class ToroidalGrid extends Grid {

	public ToroidalGrid(File setupInfo, String neighborhoodType) throws XMLException {
		super(setupInfo, neighborhoodType);
	}

	@Override
	public void onPassNeighbors(Rules myRules){
		for(Coordinate globalCellPos: this.getCoordinates()){
			Cell current = this.getCell(globalCellPos);
			for(Coordinate localNeighborPos: current.getNeighborhood().getLocalNeighborCoordinates()){
				if(current.getNeighborhood().get(localNeighborPos) == null){	// This means that we must replace this with the toroidal neighbor
					Coordinate toroidNeighborPos = globalCellPos.add(localNeighborPos);

					int newRow = toroidNeighborPos.getRow(), newCol = toroidNeighborPos.getCol();
					
					if(toroidNeighborPos.getRow() >= this.getRows()) newRow = 0; // bottom -> top
					else if(toroidNeighborPos.getRow() <= -1) newRow = this.getRows() - 1; // top -> bottom

					if(toroidNeighborPos.getCol() >= this.getCols()) newCol = 0; // right -> left
					else if(toroidNeighborPos.getCol() <= -1) newCol = this.getCols() - 1; // left -> right
					
					toroidNeighborPos = new Coordinate(newRow, newCol);
					current.getNeighborhood().setLocally(this.getCell(toroidNeighborPos), localNeighborPos);
				}
			}
		}
	}
}
