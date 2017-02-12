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
	
	private double sideLength;
	
	public SquareGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}
	
	/**
	 * Chooses correct grid type depending on Grid Imager type
	 * @param setupInfo setup file for grid
	 */
	@Override
	public Grid makeGrid(File setupInfo) {
		return new Grid(setupInfo, "square");
	}
	
	@Override
	public void setCellSize(Grid grid, double gridWidth, double gridHeight) {
		sideLength = gridHeight/grid.getRows();
		if(sideLength > gridWidth/grid.getCols()) {
			sideLength = gridWidth/grid.getCols();
		}
	}
	
	@Override
	public void updateGroup(Group group, Grid grid, boolean outline) {
		group.getChildren().clear();
		for(Coordinate c : grid.getCoordinates()) {
			Rectangle r = new Rectangle(sideLength, sideLength);
			r.setFill(Color.web(grid.getCell(c).getCurrentState().getColor()));
			r.setX(c.getCol()*sideLength);
			r.setY(c.getRow()*sideLength);
			if(outline){
				r.setStroke(Color.BLACK);
			}
			group.getChildren().add(r);
		}
	}

}
