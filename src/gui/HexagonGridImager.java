package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Coordinate;
import model.Grid;
import xml.XMLException;

/**
 * GridImager subclass used to image Grids containing hexagons.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class HexagonGridImager extends GridImager {
	
	private double sideLength;

	public HexagonGridImager(File setupInfo, double width, double height, String edgeType) throws XMLException {
		super(setupInfo, width, height, edgeType);
	}
	
	/**
	 * Chooses correct grid type depending on Grid Imager type
	 * @param setupInfo setup file for grid
	 * @throws XMLException 
	 */
	@Override
	public Grid makeGrid(File setupInfo, String edgeType) throws XMLException {
		return new Grid(setupInfo, "hexagonal", edgeType);
	}

	@Override
	public void setCellSize(Grid grid, double gridHeight, double gridWidth) {
		sideLength = gridHeight/(grid.getRows()*1.5 + 0.5);
		if(sideLength > gridWidth/(grid.getCols()*2*Math.cos(Math.toRadians(30)) + 0.5)) {
			sideLength = gridWidth/(grid.getCols()*2*Math.cos(Math.toRadians(30)) + 0.5);
		}
	}

	@Override
	public void updateGroup(Group group, Grid grid, boolean outline) throws XMLException {
		group.getChildren().clear();
		for(Coordinate c : grid.getCoordinates()) {
			Polygon p = makeHexagon(c.getRow(), c.getCol(), Math.abs(c.getRow()%2) == 1);
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
	
	private Polygon makeHexagon(int row, int col, boolean leftShift) {
		double hexagonWidth = sideLength*2*Math.cos(Math.toRadians(30));
		double mainX = col*hexagonWidth + ((leftShift)? hexagonWidth/2 : 0);
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
