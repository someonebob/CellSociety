package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LifeState extends State {

	private boolean alive;
	private Color aliveColor = Color.BLACK;
	private Color deadColor = Color.WHITE;
	
	public LifeState(String startingState){
		if(startingState.equals("alive")) alive = true;
		else alive = false;
	}
	
	public Node getStateNode() {
		if(alive) return new Rectangle(0, 0, aliveColor);
		else return new Rectangle(0, 0, deadColor);
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void setAlive(boolean isAlive){
		alive = isAlive;
	}
}
