package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import model.Grid;
import model.Coordinate;

public class GridImager {
	
	private double cellWidth;
	private double cellHeight;
	private Group myGroup;
	private Grid myGrid;

	public GridImager(File setupInfo, double width, double height) {
		myGroup = new Group();
		myGrid = new Grid(setupInfo);
		cellWidth = width/myGrid.getRows();
		cellHeight = height/myGrid.getCols();
		updateGroup();
	}
	
	/**
	 * Accesses the Group displaying all the cells.
	 * Group will be updated when simulation changes frame.
	 * @return the Group
	 */
	public Group getGroup() {
		return myGroup;
	}
	
	/**
	 * refreshes the state of the Grid, then updates the Group.
	 */
	public void nextFrame() {
		myGrid.nextFrame();
		updateGroup();
	}
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 */
	private void updateGroup() {
		myGroup.getChildren().clear();
		for(Coordinate c : myGrid.getCoordinates()) {
			Rectangle r = (Rectangle)myGrid.getCell(c).getCurrentState().getStateNode();
			// TODO change pass of Rectangle
			r = new Rectangle(cellWidth, cellHeight, r.getFill());
			r.setX(c.getRow()*cellWidth);
			r.setY(c.getCol()*cellHeight);
			myGroup.getChildren().add(r);
		}
	}

}
