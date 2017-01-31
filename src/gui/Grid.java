package gui;

import javafx.scene.Group;
import model.Rules;

public class Grid {
	
	private Group myGroup;

	public Grid() {
		myGroup = new Group();
	}
	
	/**
	 * Accesses the Group displaying all the cells.
	 * Group will be updated when simulation changes frame.
	 * @return the Group
	 */
	public Group getGroup() {
		return myGroup;
	}
	
	public void nextFrame() {
		// TODO Unimplemented method
	}
	
	private Rules getRules() {
		// TODO Unimplemented method
		return null;
	}
	
	private void initializeArray() {
		// TODO Unimplemented method
	}
	
	private void passNeighbors() {
		// TODO Unimplemented method
	}
	
	private void updateGroup() {
		// TODO Unimplemented method
	}

}
