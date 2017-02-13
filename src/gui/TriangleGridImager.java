package gui;

import java.io.File;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Coordinate;
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
	
	/**
	 * Chooses correct grid type depending on Grid Imager type
	 * @param setupInfo setup file for grid
	 * @throws XMLException 
	 */
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
	public void updateGroup(boolean outline) {
		getGroup().getChildren().clear();
		for(Coordinate c : getGrid().getCoordinates()) {
			Polygon p = makeTriangle(c.getRow(), c.getCol());
			try {
				p.setFill(Color.web(getGrid().getCell(c).getCurrentState().getColor()));
			} catch (XMLException e) {
				new ExceptionHandler(e);
			}
			if(outline){
				p.setStroke(Color.BLACK);
			}
			getGroup().getChildren().add(p);
		}
	}
	
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
