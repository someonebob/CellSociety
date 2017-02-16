// This entire file is part of my masterpiece.
// Nathaniel Brooke

package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Coordinate;
import model.Grid;
import xml.XMLException;

/**
 * Creates a graphical representation of the Grid of cells for CellSociety.
 * 
 * This class is part of my masterpiece because it exemplifies how I used
 * polymorphism to tackle adding new features to the project.  By creating an
 * abstract GridImager class, I consolidated code that would otherwise have been
 * duplicated when adding the ability to render hexagonal and triangular grids.  
 * 
 * In my first commit, found at 
 * https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team02/commit/ba3cd6e6959be60c3e2d12bf03ba743f558283bd
 * my main change was to remove the abstract method updateGroup from the subclasses
 * of GridImager, and instead make it a private method in GridImager itself.  I then
 * created a new abstract method getShape which is called from updateGroup and creates
 * either a triangle, square, or hexagon depending on the subclass you use.  I did this
 * because the code for the updateGroup method in each subclass was nearly identical.  
 * The only difference between the subclasses was the shape that was generated within
 * the loop in UpdateGrid.  Now, all the duplicated code has been consolidated into one
 * method and only the pieces of code critically different between the subclasses of
 * GridImager are separated out, implemented in the definitions of abstract methods
 * defined in the concrete subclasses.  
 * 
 * My second commit was just a merge with master to get the branch properly set up, and
 * my final commit will be to commit this comment to the branch before I submit my
 * masterpiece as a merge request.  
 * 
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public abstract class GridImager {
	
	private Group myGroup;
	private Grid myGrid;
	private File mySetup;
	
	/**
	 * Sets up the Grid and Group for the simulation.
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
	 * Resets the grid and its graphical representation to initial conditions.
	 * @param outline true if the cells are outlined in black.
	 * @param edgeType the edge style of the grid.
	 */
	public void reset(boolean outline, String edgeType) {
		myGrid = makeGrid(mySetup, edgeType);
		updateGroup(outline);

	}
	
	/**
	 * Updates the Nodes held in the Group used for animation.
	 * @param outline true if the cells are outlined in black.
	 */
	private void updateGroup(boolean outline) {
		myGroup.getChildren().clear();
		for(Coordinate c : myGrid.getCoordinates()) {
			Shape s = getShape(c.getRow(), c.getCol());
			try {
				s.setFill(Color.web(myGrid.getCell(c).getCurrentState().getColor()));
			} catch (XMLException e) {
				new ExceptionHandler(e);
			}
			if(outline){
				s.setStroke(Color.BLACK);
			}
			getGroup().getChildren().add(s);
		}
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
	 * Creates a shape specified by the subclass of GridImager used that will fill the
	 * specified row and column location in the view of the grid. 
	 * @param row in the Grid of the cell that the shape represents.
	 * @param col in the Grid of the cell that the shape represents.
	 * @return a Shape correctly positioned to represent the cell at the given row and column.
	 */
	public abstract Shape getShape(int row, int col);
	
}