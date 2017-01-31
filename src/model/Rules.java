package model;

public abstract class Rules {

	public static Rules getRules(String name) {
		// TODO Unimplemented method
		return null;
	}
	
	public abstract State getStartingState(String stateText);
	
	public abstract State getNewState(Cell[][] states);

}
