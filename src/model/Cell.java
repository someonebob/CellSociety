/**
- * Creates a Cellular Automaton. Cell has knowledge of rules
 * and its neighbors, but not the entire grid of cells.
 * @author DhruvKPatel
 */

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
	 * If already locked, will not change state, but will return false
	 * If not already locked, will change state, lock, and return true
	 * @param nextState
	 */
	public boolean setFutureState(State nextState){
		if(!futureStateIsLocked){
			this.futureState = nextState;
			futureStateIsLocked = true;	// This overrides "calculateFutureState()" until next turn to prevent blending of frames
			return true;
		}
		else{
			return false;
		}		
	}
	
	/**
	 * Returns Cell's lock status (true if future state locked)
	 * @return future state is locked
	 */
	public boolean futureStateIsLocked(){
		return futureStateIsLocked;
	}
	
	/**
	 * Calculates Cell's next state according to intrinsic rule set.
	 * Does not update current state until cell is refreshed.
	 */
	public void calculateFutureState(){
		State possibleFuture = rules.getNewState(neighborhood);
		if(!futureStateIsLocked) this.futureState = possibleFuture;
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
	 * Returns cell's current state formatted for printing
	 * @return cell's current state formatted for printing
	 */
	@Override
	public String toString() {
		return "[Cell: " + getCurrentState().getValue() + "]";
	}
}
