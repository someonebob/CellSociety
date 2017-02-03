package model;

public class Object {
	private Rules rules;
	private State currentState, futureState;
	private Object[][] neighborCells;
	
	public Object(Rules rules, State startingState){
		this.rules = rules;
		this.currentState = startingState;
		this.futureState = startingState;
		this.neighborCells = new Object[3][3];
	}
	
	public void setNeighbors(Object[][] neighbors){
		neighborCells = neighbors;
	}
	
	public void calculateFutureState(){
		futureState = rules.getNewState(getNeighborStates());		
	}
	
	public void refreshState(){
		currentState = futureState;
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	private State[][] getNeighborStates(){
		State[][] neighborStates = new State[3][3];
		
		for(int r = 0; r < neighborCells.length; r++){
			for(int c = 0; c < neighborCells[0].length; c++){
				if(neighborCells[r][c] != null && neighborCells[r][c].getCurrentState() != null)
					neighborStates[r][c] = neighborCells[r][c].getCurrentState();
			}
		}
		
		return neighborStates;
	}
}
