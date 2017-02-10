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
	
	private double cellSize; // Side length of equilateral triangle

	public TriangleGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}

	@Override
	public void setCellSize(Grid grid, double gridHeight, double gridWidth) {
		cellSize = gridHeight/grid.getRows()/Math.cos(Math.toRadians(30));
		if(cellSize > gridWidth/((double)grid.getCols() + 0.5)) {
			cellSize = gridWidth/((double)grid.getCols() + 0.5);
		}
	}

	@Override
	protected void updateGroup(Group group, Grid grid) {
		group.getChildren().clear();
		for(Coordinate c : grid.getCoordinates()) {
			Polygon p = makeTriangle(c.getRow(), c.getCol(), (c.getRow()+1)%4 > 1, c.getRow()%2 == 0);
			p.setFill(Color.web(grid.getCell(c).getCurrentState().getColor()));
			p.setOnMouseClicked(e -> {
				System.out.println(c);
			});
			group.getChildren().add(p);
		}
	}
	
	private Polygon makeTriangle(int row, int col, boolean leftShift, boolean pointUp) {
		double mainX = col*cellSize + ((leftShift)? cellSize/2 : 0);
		double mainY = (row/2)*cellSize*Math.cos(Math.toRadians(30));
		double middleCornerX = mainX + cellSize / 2;
		double height = cellSize*Math.cos(Math.toRadians(30));
		if(pointUp) {
			return new Polygon(mainX, mainY, mainX + cellSize, mainY, middleCornerX, mainY + height);
		}
		else {
			return new Polygon(mainX, mainY + height, mainX + cellSize, mainY + height, middleCornerX, mainY);
		}
	}

}
