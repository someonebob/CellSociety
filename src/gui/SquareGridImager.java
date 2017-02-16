// This entire file is part of my masterpiece.
// Nathaniel Brooke

package gui;

import java.io.File;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Grid;
import xml.XMLException;

/**
 * GridImager subclass used to image Grids containing squares.
 * 
 * This code is part of my masterpiece because it clearly shows
 * the reason I created multiple subclasses of GridImager to handle
 * different styles of grid. 
 * 
 * I moved the updateGroup method from this class to grid because it 
 * was largely identical to the implementation of the same method in 
 * all other subclasses of Grid.  See the comment in GridImager for 
 * the link to the commit. 
 * 
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class SquareGridImager extends GridImager {
	
	private double sideLength;
	
	public SquareGridImager(File setupInfo, double width, double height, String edgeType)  {
		super(setupInfo, width, height, edgeType);
	}
	
	@Override
	public Grid makeGrid(File setupInfo, String edgeType) {
		try {
			return new Grid(setupInfo, "square", edgeType);
		} catch (XMLException e) {
			new ExceptionHandler(e).exit();
			return null;
		}
	}
	
	@Override
	public void setCellSize(double gridWidth, double gridHeight) {
		sideLength = gridHeight/getGrid().getRows();
		if(sideLength > gridWidth/getGrid().getCols()) {
			sideLength = gridWidth/getGrid().getCols();
		}
	}

	@Override
	public Shape getShape(int row, int col) {
		Rectangle r = new Rectangle(sideLength, sideLength);
		r.setX(col*sideLength);
		r.setY(row*sideLength);
		return r;
	}
	

}
