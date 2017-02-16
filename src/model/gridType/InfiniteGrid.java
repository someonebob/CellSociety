package model.gridType;

import java.io.File;
import java.util.ArrayList;

import model.Cell;
import model.Coordinate;
import model.Grid;
import model.Rules;
import xml.XMLException;

public class InfiniteGrid extends Grid {
	
	public InfiniteGrid(File setupInfo, String neighborhoodType) throws XMLException {
		super(setupInfo, neighborhoodType);
	}
	
	public void onNextFrame(Rules myRules){
		ArrayList<Coordinate> newCellPositions = new ArrayList<Coordinate>();
		for(Coordinate globalCellPos: this.getCoordinates()){
			Cell current = this.getCell(globalCellPos);
			if(this.isOnEdge(globalCellPos) && !current.getCurrentState().equals(myRules.getDefaultState())){ // Grid expansion is not affected by cells of default state
				for(Coordinate localNeighborPos : current.getNeighborhood().getLocalNeighborCoordinates()){
					if(current.getNeighborhood().get(localNeighborPos) == null){
						newCellPositions.add(globalCellPos.add(localNeighborPos));
					}
				}
			}
		}
		
		for(Coordinate newCellPos : newCellPositions){
			this.putCell(newCellPos, new Cell(myRules, myRules.getDefaultState()));
		}
		
		this.calculateGridSize();	// This is the only reason these methods are public in grid. (So edge types can use them)
		this.fillEmptyCells();
		this.passNeighbors();
	}
}
