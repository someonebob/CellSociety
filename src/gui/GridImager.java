package gui;

import java.io.File;
import javafx.scene.Group;
import model.Grid;

/**
 * Creates a graphical representation of the Grid of cells for CellSociety.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public abstract class GridImager {
	
	private Group myGroup;
	private Grid myGrid;
	
	/**
	 * Sets up the Grid and Group for the simulation
	 * @param setupInfo the XML file containing setup information
	 * @param width the width of the grid representation on screen.
	 * @param height the height of the grid representation on screen.
	 */
	public GridImager(File setupInfo, double width, double height, String edgeType) {
		myGroup = new Group();
		myGrid = makeGrid(setupInfo, edgeType);
		updateGroup(myGroup, myGrid, false);
		setCellSize(myGrid, width, height);
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
	public void nextFrame(boolean outline) {
		myGrid.nextFrame();
		updateGroup(myGroup, myGrid, outline);
	}
	
	/**
	 * Makes a specific grid based on the shape of the cell.
	 * @param setupInfo File containing setup information.
	 * @return the Grid.
	 */
	public abstract Grid makeGrid(File setupInfo, String edgeType);
		
	/**
	 * Updates the cell size in the grid display to fit the specified total grid dimensions.
	 * @param grid the Grid being used
	 * @param gridHeight the height of the display space for the grid.
	 * @param gridWidth the width of the display space for the grid.
	 */
	public abstract void setCellSize(Grid grid, double gridHeight, double gridWidth);
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 * @param group the GridImager's group.
	 * @param grid the Grid being used.
	 */
	public abstract void updateGroup(Group group, Grid grid, boolean outline);
}