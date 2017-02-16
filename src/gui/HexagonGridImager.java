// This entire file is part of my masterpiece.
// Nathaniel Brooke

package gui;

import java.io.File;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import model.Grid;
import xml.XMLException;

/**
 * GridImager subclass used to image Grids containing hexagons.
 * 
 * This code is part of my masterpiece because it is one of three
 * examples of my effort to use polymorphism and subclasses of GridImager
 * to implement different ways to view differently shaped grids.  
 * 
 * I moved the updateGroup method from this class to grid because it 
 * was largely identical to the implementation of the same method in 
 * all other subclasses of Grid.  See the comment in GridImager for 
 * the link to the commit. 
 * 
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class HexagonGridImager extends GridImager {
	
	private double sideLength;

	/**
	 * Initializes GridImager superclass.
	 */
	public HexagonGridImager(File setupInfo, double width, double height, String edgeType) {
		super(setupInfo, width, height, edgeType);
	}
	
	@Override
	public Grid makeGrid(File setupInfo, String edgeType) {
		try {
			return new Grid(setupInfo, "hexagonal", edgeType);
		} catch (XMLException e) {
			new ExceptionHandler(e).exit();
			return null;
		}
	}

	@Override
	public void setCellSize(double gridHeight, double gridWidth) {
		sideLength = gridHeight/(getGrid().getRows()*1.5 + 0.5);
		if(sideLength > gridWidth/(getGrid().getCols()*2*Math.cos(Math.toRadians(30)) + 0.5)) {
			sideLength = gridWidth/(getGrid().getCols()*2*Math.cos(Math.toRadians(30)) + 0.5);
		}
	}
	
	@Override
	public Shape getShape(int row, int col) {
		return makeHexagon(row, col, Math.abs(row%2) == 1);
	}
	
	/**
	 * Generates a hexagon at the specified row and column, optionally
	 * right shifted to mesh with other hexagons in different rows.
	 * @param row the row position of the hexagon in the grid.
	 * @param col the column position of the hexagon in the grid.
	 * @param rightShift true if this hexagon needs to be shifted right to fit in the grid.
	 * @return a Polygon in the position and shape of the specified hexagon.
	 */
	private Polygon makeHexagon(int row, int col, boolean rightShift) {
		double hexagonWidth = sideLength*2*Math.cos(Math.toRadians(30));
		double mainX = col*hexagonWidth + ((rightShift)? hexagonWidth/2 : 0);
		double mainY = row*sideLength*1.5;
		return new Polygon(
				mainX + hexagonWidth/2, mainY,
				mainX, mainY + sideLength*0.5, 
				mainX, mainY + sideLength*1.5,
				mainX + hexagonWidth/2, mainY + sideLength*2, 
				mainX + hexagonWidth, mainY + sideLength*1.5,
				mainX + hexagonWidth, mainY + sideLength*0.5
				);
	}

}
