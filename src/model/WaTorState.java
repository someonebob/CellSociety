package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WaTorState extends State {
	
	private int currentState; // 0 = empty, 1 = fish, 2 = shark
	private int moved;
	private boolean eaten;

	public WaTorState(int state) {
		currentState = state;
		moved = 0;
		eaten = false;
	}

	@Override
	public Node getStateNode() {
		if(currentState == 0) {
			return new Rectangle(1, 1, Color.GREEN);
		}
		if(currentState == 1) {
			return new Rectangle(1, 1, Color.BLUE);
		}
		if(currentState == 2) {
			return new Rectangle(1, 1, Color.GRAY);
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
