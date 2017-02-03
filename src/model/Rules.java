package model;

public abstract class Rules {
	
	public abstract State getStartingState(String stateText);
	
	public abstract State getNewState(Neighborhood neighborhood);

}
