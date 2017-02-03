package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * States for Spreading Fire
 * @author Jesse
 *
 */
public class FireStates extends State {
	private int currentstate; //0 = empty/burned 1 = tree 2 = burning
	
	public FireStates(int state){
		currentstate = state;
	}

	@Override
	public Node getStateNode() {
		if(currentstate == 0){
			return new Rectangle(1, 1, Color.YELLOW);
		}
		if(currentstate == 1){
			return new Rectangle(1, 1, Color.GREEN);
		}
		if(currentstate == 2){
			return new Rectangle(1, 1, Color.RED);
		}
		return null;
	}
	
	/**
	 * Sets the state to a new state
	 */
	public void setStateValue(int state){
		currentstate = state;
	}
	
	/**
	 * Returns the current state
	 * @return
	 */
	public int getStateValue(){
		return currentstate;
	}

}
