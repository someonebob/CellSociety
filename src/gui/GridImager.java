package gui;

import java.io.File;
import javafx.scene.Group;
import model.Grid;
import xml.XMLException;

/**
 * Creates a graphical representation of the Grid of cells for CellSociety.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public abstract class GridImager {
	
	private Group myGroup;
	private Grid myGrid;
	private File mySetup;
	
	/**
	 * Sets up the Grid and Group for the simulation
	 * @param setupInfo the XML file containing setup information
	 * @param width the width of the grid representation on screen.
	 * @param height the height of the grid representation on screen.
	 * @param edgeType the edge style (finite, toroidal, infinite) of the simulation.
	 */
	public GridImager(File setupInfo, double width, double height, String edgeType) {
		myGroup = new Group();
		myGrid = makeGrid(setupInfo, edgeType);
		mySetup = setupInfo;
		setCellSize(width, height);
		updateGroup(false);
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
	 * Accesses the Grid that this GridImager displays.
	 * @return the Grid
	 */
	public Grid getGrid() {
		return myGrid;
	}
	
	/**
	 * refreshes the state of the Grid, then updates the Group.
	 */
	public void nextFrame(boolean outline) {
		myGrid.nextFrame();
		updateGroup(outline);
	}
	
	/**
	 * Resets the grid to initial conditions.
	 * @param outline true if the cells are outlined in black.
	 * @param edgeType the edge style of the grid.
	 */
	public void reset(boolean outline, String edgeType) {
		myGrid = makeGrid(mySetup, edgeType);
		updateGroup(outline);

	}
	
	/**
	 * Makes a specific grid based on the shape of the cell.
	 * @param setupInfo File containing setup information.
	 * @param edgeType the edge style of the grid.
	 * @return the Grid.
	 */
	public abstract Grid makeGrid(File setupInfo, String edgeType);
		
	/**
	 * Updates the cell size in the grid display to fit the specified total grid dimensions.
	 * @param gridHeight the height of the display space for the grid.
	 * @param gridWidth the width of the display space for the grid.
	 */
	public abstract void setCellSize(double gridHeight, double gridWidth);
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 * @param outline true if the cells are outlined in black.
	 * @throws XMLException 
	 */
	public abstract void updateGroup(boolean outline);
	
}