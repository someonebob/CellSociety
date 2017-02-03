package model;

import java.util.Arrays;
import java.util.List;

import gui.Grid;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FireStates extends State {
	private String state;
	private List<String> possibleStates = Arrays.asList(new String[] {"empty", "tree", "burning"});
	
	public FireStates(String state){
		this.state = state;

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
		return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.YELLOW);
	}
	
	private Rectangle getTreeState(){
		return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.GREEN);

	}
	
	private Rectangle getBurningState(){
		return new Rectangle(Grid.CELL_SIZE, Grid.CELL_SIZE, Color.RED);
	}

}