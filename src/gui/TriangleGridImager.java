// This entire file is part of my masterpiece.
// Nathaniel Brooke

package gui;

import java.io.File;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import model.Grid;
import xml.XMLException;

/**
 * GridImager subclass used to image Grids containing triangles.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class TriangleGridImager extends GridImager {
	
	private double sideLength;

	public TriangleGridImager(File setupInfo, double width, double height, String edgeType) {
		super(setupInfo, width, height, edgeType);
	}
	
	@Override
	public Grid makeGrid(File setupInfo, String edgeType) {
		try {
			return new Grid(setupInfo, "triangular", edgeType);
		} catch (XMLException e) {
			new ExceptionHandler(e).exit();
			return null;
		}	}

	@Override
	public void setCellSize(double gridHeight, double gridWidth) {
		sideLength = gridHeight/getGrid().getRows()/Math.cos(Math.toRadians(30));
		if(sideLength > gridWidth/((double)getGrid().getCols() + 0.5)) {
			sideLength = gridWidth/((double)getGrid().getCols() + 0.5);
		}
	}
	
	@Override
	public Shape getShape(int row, int col) {
		return makeTriangle(row, col);
	}

	/**
	 * Generates a triangle at the specified row and column, flipping
	 * every other triangle position to allow the triangles to properly mesh.
	 * @param row the row position of the triangle in the grid.
	 * @param col the column position of the triangle in the grid.
	 * @return a Polygon in the position and shape of the specified triangle.
	 */
	private Polygon makeTriangle(int row, int col) {
		double mainX = col*sideLength/2;
		double mainY = row*sideLength*Math.cos(Math.toRadians(30));
		double middleCornerX = mainX + sideLength / 2;
		double height = sideLength*Math.cos(Math.toRadians(30));
		if(Math.abs((col + row)%2) == 0) {
			return new Polygon(mainX, mainY, mainX + sideLength, mainY, middleCornerX, mainY + height);
		}
		else {
			return new Polygon(mainX, mainY + height, mainX + sideLength, mainY + height, middleCornerX, mainY);
		}
	}

}
