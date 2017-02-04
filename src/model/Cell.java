package model;

public class Cell {
	private Rules rules;
	private State currentState, futureState;
	private Neighborhood neighborhood;
	
	public Cell(Rules rules, State startingState){
		this.rules = rules;
		this.currentState = startingState;
		this.futureState = startingState;
	}
	
	/**
	 * Sets cell's neighborhood
	 * @param neighborhood
	 */
	public void setNeighborhood(Neighborhood neighborhood){
		this.neighborhood= neighborhood;
	}
	
	public void calculateFutureState(){
		futureState = rules.getNewState(neighborhood);		
	}
	
	public void refreshState(){
		currentState = futureState;
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	@Override
	public String toString() {
		return "[Cell: " + getCurrentState().getValue() + "]";
	}
}
