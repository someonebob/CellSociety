package model;

public class Cell {
	private Rules rules;
	private State currentState, futureState;
	private Neighborhood neighborhood;
	private boolean futureStateIsLocked;
	
	/**
	 * Constructs a cell; assigns it an intrinsic rule set and starting state
	 * @param rules intrinsic rule set, depending on simulation
	 * @param startingState cell's initial state
	 */
	public Cell(Rules rules, State startingState){
		this.rules = rules;
		this.currentState = startingState;
		this.futureState = startingState;
		this.futureStateIsLocked = false;
	}
	
	/**
	 * Sets cell's neighborhood
	 * @param neighborhood
	 */
	public void setNeighborhood(Neighborhood neighborhood){
		this.neighborhood = neighborhood;
	}
	
	/**
	 * Allows you to manually set Cell's future state. 
	 * Overrides and locks intrinsic "calculateFutureState" until state is refreshed.
	 * @param nextState
	 */
	public void setFutureState(State nextState){
		this.futureState = nextState;
		futureStateIsLocked = true;
	}
	
	/**
	 * Calculates Cell's next state according to intrinsic rule set.
	 * Does not update current state until cell is refreshed.
	 */
	public void calculateFutureState(){
		if(!futureStateIsLocked)
			this.futureState = rules.getNewState(neighborhood);		
	}
	
	/**
	 * Set's cell's current state to its stored future state
	 */
	public void refreshState(){
		currentState = futureState;
		futureStateIsLocked = false;
	}
	
	/**
	 * Returns cell's current state
	 * @return cell's current state
	 */
	public State getCurrentState(){
		return currentState;
	}
	
	/**
	 * Returns cell's neighborhood of cells
	 * @return cell's neighborhood of cells
	 */
	public Neighborhood getNeighborhood() {
		return neighborhood;
	}
	
	/**
	 * Return's cell's state value
	 */
	@Override
	public String toString() {
		return "[Cell: " + getCurrentState().getValue() + "]";
	}
}
