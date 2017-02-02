package model;

import java.io.File;
import java.util.ArrayList;

public class PredatorPreyRules extends Rules {
	
	private int step;

	public PredatorPreyRules(File setupInfo) {
	}

	@Override
	public State getStartingState(String stateText) {
		System.out.println(stateText);
		return new PredatorPreyState(Integer.parseInt(stateText));
	}

	@Override
	public State getNewState(State[][] states) {
		PredatorPreyState[][] pStates = (PredatorPreyState[][])states;
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
	
	private PredatorPreyState sharksEat(PredatorPreyState[][] states) {
		if(states[1][1].getStateValue() == 2) { // It's a Shark
			ArrayList<PredatorPreyState> food = getAdjacentStates(states, 1);
			if(!food.isEmpty()) {
				food.get((int)(Math.random()*food.size())).eat();
			}
		}
		return states[1][1];

	}
	
	private PredatorPreyState thingsMove(PredatorPreyState[][] states) {
		ArrayList<PredatorPreyState> emptySpots = getAdjacentStates(states, 0);
		if(states[1][1].getStateValue() == 1 && !states[1][1].isEaten()) { // It's an uneaten Fish
			emptySpots.get((int)(Math.random()*emptySpots.size())).moveToState(1);
		}
		if(states[1][1].getStateValue() == 2 && noAdjacentState(states, 1)) { // It's a hungry shark
			emptySpots.get((int)(Math.random()*emptySpots.size())).moveToState(2);
		}
		return states[1][1];
	}
	
	private PredatorPreyState makeNewStates(PredatorPreyState[][] states) {
		int newOccupant = states[1][1].getStateValue();
		if(states[1][1].getMoveIn() != 0) {
			newOccupant = states[1][1].getMoveIn();
		}
		if(states[1][1].isEaten()) {
			newOccupant = 0;
		}
		return new PredatorPreyState(newOccupant);
	}
	
	
	private ArrayList<PredatorPreyState> getAdjacentStates(PredatorPreyState[][] states, int find) {
		ArrayList<PredatorPreyState> foundStates = new ArrayList<PredatorPreyState>();
		if(states[0][1].getStateValue() == find) {
			foundStates.add(states[0][1]);
		}
		if(states[1][0].getStateValue() == find) {
			foundStates.add(states[1][0]);
		}
		if(states[1][2].getStateValue() == find) {
			foundStates.add(states[1][2]);
		}
		if(states[2][1].getStateValue() == find) {
			foundStates.add(states[2][1]);
		}
		return foundStates;
	}

	private boolean noAdjacentState(PredatorPreyState[][] states, int find) {
	return states[0][1].getStateValue() != find
			&& states[1][0].getStateValue() != find
			&& states[1][2].getStateValue() != find
			&& states[2][1].getStateValue() != find;
	}


}
