package gui;

import java.io.File;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Coordinate;
import model.Grid;
import xml.XMLException;

/**
 * GridImager subclass used to image Grids containing squares.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class SquareGridImager extends GridImager {
	
	private double sideLength;
	
	public SquareGridImager(File setupInfo, double width, double height, String edgeType)  {
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
	public void updateGroup(boolean outline) {
		getGroup().getChildren().clear();
		for(Coordinate c : getGrid().getCoordinates()) {
			Rectangle r = new Rectangle(sideLength, sideLength);
			try {
				r.setFill(Color.web(getGrid().getCell(c).getCurrentState().getColor()));
			} catch (XMLException e) {
				new ExceptionHandler(e);
			}
			r.setX(c.getCol()*sideLength);
			r.setY(c.getRow()*sideLength);
			if(outline){
				r.setStroke(Color.BLACK);
			}
			getGroup().getChildren().add(r);
		}
	}

}
