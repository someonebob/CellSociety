package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Coordinate;
import model.Grid;

/**
 * GridImager subclass used to image Grids containing squares.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class SquareGridImager extends GridImager {
	
	private double cellSize; // Side length of square
	
	public SquareGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}
	
	@Override
	public void setCellSize(Grid grid, double gridWidth, double gridHeight) {
		cellSize = gridHeight/grid.getRows();
		if(cellSize > gridWidth/grid.getCols()) {
			cellSize = gridWidth/grid.getCols();
		}
	}
	
	@Override
	protected void updateGroup(Group group, Grid grid) {
		group.getChildren().clear();
		for(Coordinate c : grid.getCoordinates()) {
			Rectangle r = new Rectangle(cellSize, cellSize);
			r.setFill(Color.web(grid.getCell(c).getCurrentState().getColor()));
			r.setX(c.getCol()*cellSize);
			r.setY(c.getRow()*cellSize);
			group.getChildren().add(r);
		}
	}

}
