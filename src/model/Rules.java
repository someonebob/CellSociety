package model;

/**
 * Abstract rules set
 * All subclasses must implement this method. 
 * 
 * If cell state handling is more intricate than just returning a state for every condition,
 * you can manually set the cells future state, and this will override the "getNewState" method.
 * e.g. Segregation
 */
public abstract class Rules {
	
	public abstract State getStartingState(String stateText);
	
	public abstract State getDefaultState();
	
	public abstract State getNewState(Neighborhood neighborhood);
}
