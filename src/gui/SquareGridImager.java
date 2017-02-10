package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Coordinate;

/**
 * GridImager subclass used to image Grids containing squares.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class SquareGridImager extends GridImager {
	
	private double sideLength;
	
	public SquareGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}
	
	@Override
	public void setCellSize(double gridWidth, double gridHeight) {
		sideLength = gridHeight/getGrid().getRows();
		if(sideLength > gridWidth/getGrid().getCols()) {
			sideLength = gridWidth/getGrid().getCols();
		}
	}
	
	@Override
	protected void updateGroup(Group group) {
		group.getChildren().clear();
		for(Coordinate c : getGrid().getCoordinates()) {
			Rectangle r = new Rectangle(sideLength, sideLength);
			r.setFill(Color.web(getGrid().getCell(c).getCurrentState().getColor()));
			r.setX(c.getCol()*sideLength);
			r.setY(c.getRow()*sideLength);
			group.getChildren().add(r);
		}
	}

}
