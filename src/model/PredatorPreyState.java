package model;

import gui.Grid;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PredatorPreyState extends State {
	
	private int currentState; // 0 = empty, 1 = fish, 2 = shark
	private int moved;
	private boolean eaten;

	public PredatorPreyState(int state) {
		currentState = state;
		moved = 0;
		eaten = false;
	}

	@Override
	public Node getStateNode() {
		if(currentState == 0) {
			return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.GREEN);
		}
		if(currentState == 1) {
			return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.BLUE);
		}
		if(currentState == 2) {
			return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.GRAY);
		}
		return null;
	}
	
	public void setState(int newState) {
		currentState = newState;
	}
	
	public int getStateValue() {
		return currentState;
	}
	
	public void moveToState(int newOccupant) {
		moved = newOccupant;
	}
	
	public void eat() {
		eaten = true;
	}
	
	public int getMoveIn() {
		return moved;
	}
	
	public boolean isEaten() {
		return eaten;
	}

}