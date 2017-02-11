package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Coordinate;
import model.Grid;

/**
 * GridImager subclass used to image Grids containing triangles.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class TriangleGridImager extends GridImager {
	
	private double sideLength;

	public TriangleGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}
	
	/**
	 * Chooses correct grid type depending on Grid Imager type
	 * @param setupInfo setup file for grid
	 */
	@Override
	public Grid makeGrid(File setupInfo) {
		return new Grid(setupInfo, "triangular");
	}

	@Override
	public void setCellSize(double gridHeight, double gridWidth) {
		sideLength = gridHeight/getGrid().getRows()/Math.cos(Math.toRadians(30));
		if(sideLength > gridWidth/((double)getGrid().getCols() + 0.5)) {
			sideLength = gridWidth/((double)getGrid().getCols() + 0.5);
		}
	}

	@Override
	protected void updateGroup(Group group) {
		group.getChildren().clear();
		for(Coordinate c : getGrid().getCoordinates()) {
			Polygon p = makeTriangle(c.getRow(), c.getCol(), (c.getRow()+1)%4 > 1, c.getRow()%2 == 0);
			p.setFill(Color.web(getGrid().getCell(c).getCurrentState().getColor()));
			p.setOnMouseClicked(e -> {
				System.out.println(c);
			});
			group.getChildren().add(p);
		}
	}
	
	private Polygon makeTriangle(int row, int col, boolean leftShift, boolean pointUp) {
		double mainX = col*sideLength + ((leftShift)? sideLength/2 : 0);
		double mainY = (row/2)*sideLength*Math.cos(Math.toRadians(30));
		double middleCornerX = mainX + sideLength / 2;
		double height = sideLength*Math.cos(Math.toRadians(30));
		if(pointUp) {
			return new Polygon(mainX, mainY, mainX + sideLength, mainY, middleCornerX, mainY + height);
		}
		else {
			return new Polygon(mainX, mainY + height, mainX + sideLength, mainY + height, middleCornerX, mainY);
		}
	}

}
