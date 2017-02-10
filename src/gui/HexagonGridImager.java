package gui;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Coordinate;

/**
 * GridImager subclass used to image Grids containing hexagons.
 * @author Nathaniel Brooke
 * @version 02-10-2017
 */
public class HexagonGridImager extends GridImager {
	
	private double sideLength;

	public HexagonGridImager(File setupInfo, double width, double height) {
		super(setupInfo, width, height);
	}

	@Override
	public void setCellSize(double gridHeight, double gridWidth) {
		sideLength = gridHeight/(getGrid().getRows()*1.5 + 0.5);
		if(sideLength > gridWidth/(getGrid().getCols()*2*Math.cos(Math.toRadians(30)) + 0.5)) {
			sideLength = gridWidth/(getGrid().getCols()*2*Math.cos(Math.toRadians(30)) + 0.5);
		}
	}

	@Override
	protected void updateGroup(Group group) {
		group.getChildren().clear();
		for(Coordinate c : getGrid().getCoordinates()) {
			Polygon p = makeHexagon(c.getRow(), c.getCol(), c.getRow()%2 == 1);
			p.setFill(Color.web(getGrid().getCell(c).getCurrentState().getColor()));
			p.setOnMouseClicked(e -> {
				System.out.println(c);
			});
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
