package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JesseLifeStates extends State {
	private int currentstate;
	
	public JesseLifeStates(int state){
		currentstate = state;
	}

	@Override
	public Node getStateNode() {
		if(currentstate == 0){
			return new Rectangle(1, 1, Color.WHITE);
		}
		if(currentstate == 1){
			return new Rectangle(1, 1, Color.BLACK);
		}
		return null;
	}
	
	public void setStateValue(int state){
		currentstate = state;
	}
	
	public int getStateValue(){
		return currentstate;
	}

}
