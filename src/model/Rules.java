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
	
	/**
	 * Returns starting state given text identifier
	 * @param stateText
	 * @return starting State
	 */
	public abstract State getStartingState(String stateText);
	
	/**
	 * Returns default state depending on rule set.
	 * Note: default states do not affect infinite grid expanion
	 * @return default state
	 */
	public abstract State getDefaultState();
	
	/**
	 * Called every time grid gets a new frame.
	 * @param neighborhood
	 * @return newest state
	 */
	public abstract State getNewState(Neighborhood neighborhood);
}
