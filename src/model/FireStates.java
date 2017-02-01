package model;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FireStates extends State {
	private Rectangle empty;
	private Rectangle tree;
	private Rectangle burning;
	private String state;
	private List<String> possibleStates = Arrays.asList(new String[] {"empty", "tree", "burning"});
	private int rows, cols;
	private int width = 1280;
	private int height = 720;
	
	public FireStates(String state, int rows, int cols){
		this.state = state;
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public Node getStateNode() {
		if(state.equals(possibleStates.get(0))){
			return getEmptyState();
		}else if(state.equals(possibleStates.get(1))){
			return getTreeState();
		}else if(state.equals(possibleStates.get(2))){
			return getBurningState();
		}
		return null;
	}
	
	private Rectangle getEmptyState(){
		empty = new Rectangle(width/cols, height/rows, Color.YELLOW);
	
		return empty;
	}
	
	private Rectangle getTreeState(){
		tree = new Rectangle(width/cols, height/rows, Color.GREEN);

		return tree;
	}
	
	private Rectangle getBurningState(){
		burning = new Rectangle(width/cols, height/rows, Color.RED);

		return burning;
	}

}
