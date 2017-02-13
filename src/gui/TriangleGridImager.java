package gui;

import java.io.File;

import javafx.scene.Group;
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

	public TriangleGridImager(File setupInfo, double width, double height, String edgeType) throws XMLException {
		super(setupInfo, width, height, edgeType);
	}
	
	/**
	 * Chooses correct grid type depending on Grid Imager type
	 * @param setupInfo setup file for grid
	 * @throws XMLException 
	 */
	@Override
	public Grid makeGrid(File setupInfo, String edgeType) throws XMLException {
		return new Grid(setupInfo, "triangular", edgeType);
	}

	@Override
	public void setCellSize(Grid grid, double gridHeight, double gridWidth) {
		sideLength = gridHeight/grid.getRows()/Math.cos(Math.toRadians(30));
		if(sideLength > gridWidth/((double)grid.getCols() + 0.5)) {
			sideLength = gridWidth/((double)grid.getCols() + 0.5);
		}
	}

	@Override
	public void updateGroup(Group group, Grid grid, boolean outline) throws XMLException {
		group.getChildren().clear();
		for(Coordinate c : grid.getCoordinates()) {
			Polygon p = makeTriangle(c.getRow(), c.getCol());
			p.setFill(Color.web(grid.getCell(c).getCurrentState().getColor()));
			p.setOnMouseClicked(e -> {
				System.out.println(c);
			});
			if(outline){
				p.setStroke(Color.BLACK);
			}
			group.getChildren().add(p);
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
