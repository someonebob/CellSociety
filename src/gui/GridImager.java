package gui;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Grid;
import model.Coordinate;
public class GridImager {
	
	private double cellSize;
	private Group myGroup;
	private Grid myGrid;
	public GridImager(File setupInfo, double width, double height) {
		myGroup = new Group();
		myGrid = new Grid(setupInfo);
		cellSize = height/myGrid.getRows();
		if(cellSize > width/myGrid.getCols()) {
			cellSize = width/myGrid.getCols();
		}
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
			Rectangle r = new Rectangle(cellSize, cellSize);
			r.setFill(Color.web(myGrid.getCell(c).getCurrentState().getColor()));
			r.setX(c.getCol()*cellSize);
			r.setY(c.getRow()*cellSize);
			myGroup.getChildren().add(r);
		}
	}
}