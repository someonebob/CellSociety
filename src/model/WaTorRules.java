package model;

import java.io.File;
import java.util.ArrayList;

public class WaTorRules extends Rules {
	
	private int step;

	public WaTorRules(File setupInfo) {
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(Integer.parseInt(stateText));
	}

	@Override
	public State getNewState(State[][] states) {
		WaTorState[][] pStates = new WaTorState[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				pStates[i][j] = (WaTorState)states[i][j];
			}
		}
		if(step == 0) {
			step++;
			return sharksEat(pStates);
		}
		if(step == 1) {
			step++;
			return thingsMove(pStates);
		}
		if(step == 2) {
			step = 0;
			return makeNewStates(pStates);
		}
		return null;
	}
	
	private WaTorState sharksEat(WaTorState[][] states) {
		if(states[1][1].getStateValue() == 2) { // It's a Shark
			ArrayList<WaTorState> food = getAdjacentStates(states, 1);
			if(!food.isEmpty()) {
				food.get((int)(Math.random()*food.size())).eat();
			}
		}
		return states[1][1];

	}
	
	private WaTorState thingsMove(WaTorState[][] states) {
		ArrayList<WaTorState> emptySpots = getAdjacentStates(states, 0);
		if(states[1][1].getStateValue() == 1 && !states[1][1].isEaten()) { // It's an uneaten Fish
			emptySpots.get((int)(Math.random()*emptySpots.size())).moveToState(1);
		}
		if(states[1][1].getStateValue() == 2 && noAdjacentState(states, 1)) { // It's a hungry shark
			emptySpots.get((int)(Math.random()*emptySpots.size())).moveToState(2);
		}
		return states[1][1];
	}
	
	private WaTorState makeNewStates(WaTorState[][] states) {
		int newOccupant = states[1][1].getStateValue();
		if(states[1][1].getMoveIn() != 0) {
			newOccupant = states[1][1].getMoveIn();
		}
		if(states[1][1].isEaten()) {
			newOccupant = 0;
		}
		return new WaTorState(newOccupant);
	}
	
	
	private ArrayList<WaTorState> getAdjacentStates(WaTorState[][] states, int find) {
		ArrayList<WaTorState> foundStates = new ArrayList<WaTorState>();
		if(states[0][1] != null && states[0][1].getStateValue() == find) {
			foundStates.add(states[0][1]);
		}
		if(states[0][1] != null && states[1][0].getStateValue() == find) {
			foundStates.add(states[1][0]);
		}
		if(states[0][1] != null && states[1][2].getStateValue() == find) {
			foundStates.add(states[1][2]);
		}
		if(states[0][1] != null && states[2][1].getStateValue() == find) {
			foundStates.add(states[2][1]);
		}
		return foundStates;
	}

	private boolean noAdjacentState(WaTorState[][] states, int find) {
	return states[0][1] != null && states[0][1].getStateValue() != find
			&& states[0][1] != null && states[1][0].getStateValue() != find
			&& states[0][1] != null && states[1][2].getStateValue() != find
			&& states[0][1] != null && states[2][1].getStateValue() != find;
	}


}
