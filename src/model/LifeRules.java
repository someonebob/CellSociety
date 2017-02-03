package model;

public class LifeRules extends Rules {

//	public State getNewState(State[][] states) {
//		return null;
//	}
	
	public State getStartingState(String stateText) {
		return new LifeState(stateText);
	}
	
	public LifeState getNewState(State[][] states) { // Parameter input casting *find a way to get rid of this
		int neighborsAlive = getNumAlive(getNeighborStates(states));
		boolean isAlive = getMyState(states).isAlive();
		
		if(isAlive){
			if (neighborsAlive >= 2 && neighborsAlive <= 3) return new LifeState("alive");
			else return new LifeState("dead");
		}
		else{
			if(neighborsAlive == 3) return new LifeState("alive");
			else return new LifeState("dead");
		}
		
	}
	
	private int getNumAlive(LifeState[][] states){
		int numAlive = 0;
		for(LifeState[] stateRow: states){
			for(LifeState potentialState: stateRow){
				if(potentialState != null && potentialState instanceof LifeState && ((LifeState) potentialState).isAlive()){
					numAlive++;
				}
			}
		}
		return numAlive;
	}
	
	
	private LifeState getMyState(State[][] neighborhood){
		return (LifeState) neighborhood[2][2];
	}
	
	private LifeState[][] getNeighborStates(State[][] neighborhood){
		LifeState[][] neighbors = new LifeState[3][3];
		for(int r = 0; r < neighbors.length; r++){
			for(int c = 0; c < neighbors[0].length; c++){
				if(r != 2 || c != 2) neighbors[r][c] = (LifeState) neighborhood[r][c];
			}
		}
		return neighbors;
	}
}