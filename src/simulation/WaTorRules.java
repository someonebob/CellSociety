package simulation;

import java.util.ArrayList;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLException;
import xml.XMLParser;

public class WaTorRules extends Rules {
	
	private static final String SHARK = "shark";
	private static final String FISH = "fish";
	private static final String EMPTY = "empty";
	
	private XMLParser config;
	private int breed;
	private int starve;
	
	public WaTorRules(XMLParser configuration) throws NumberFormatException, XMLException {
		config = configuration;
		breed = Integer.parseInt(config.getParameter("breedTime"));
		starve = Integer.parseInt(config.getParameter("starveTime"));
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(config, stateText, 1, 0, 0);
	}
	
	@Override
	public State getDefaultState(){
		return new WaTorState(config, EMPTY, 1, 0, 0);
	}

	@Override
	public State getNewState(Neighborhood neighbors) {
		int step = getCenterState(neighbors).getStep();
		int age = getCenterState(neighbors).getCellAge() + (3-step)/3;
		int noFood = getCenterState(neighbors).getNoFoodTime() + (3-step)/3;
		getCenterState(neighbors).incrStep();
		
		String value = neighbors.getCenter().getCurrentState().getValue();
		if(step == 1) {
			return sharksEat(neighbors, value);
		}
		else if(step == 2) {
			return updateStates(neighbors, step, age, noFood, false);
		}
		else if(step == 3) {
			getCenterState(neighbors).setStep(0);
			return moveReproduceDie(neighbors, age, noFood, value);
		}
		else {
			return updateStates(neighbors, step, age, noFood, true);
		}
	}

	private State updateStates(Neighborhood neighbors, int step, int age, int noFood, boolean shark) {
		// Don't update a shark if it ate and needs to not move
		if(!shark && getCenterState(neighbors).getValue().equals(SHARK)) {
			return getCenterState(neighbors);
		}
		// Update to next value if next value initialized
		String nextVal = getCenterState(neighbors).getNextValue();
		if(nextVal != null) {
			return new WaTorState(config, nextVal, ++step, age, noFood);
		}
		else {
			return new WaTorState(config, getCenterState(neighbors).getValue(), ++step, age, noFood);
		}
	}

	private State moveReproduceDie(Neighborhood neighbors, int age, int noFood, String value) {
		if((value.equals(SHARK) && (!getCenterState(neighbors).ate()
						|| getCenterState(neighbors).getCellAge() >= breed)) 
				|| value.equals(FISH)) {
			WaTorState move = getStateOf(EMPTY, neighbors);
			if(move != null) {
				if(!value.equals(SHARK) || noFood < starve) {
					// Not Starving
					move.setNextValue(value);
					move.setNoFoodTime(noFood);
				}
				if(age < breed) {
					// Not reproducing
					getCenterState(neighbors).setNextValue(EMPTY);	
					move.setCellAge(age);
				}
				else {
					// Reproducing, reset age
					getCenterState(neighbors).setCellAge(0);
					move.setCellAge(0);
					move.setNoFoodTime(0);
				}
			}
		}
		return neighbors.getCenter().getCurrentState();
	}

	private State sharksEat(Neighborhood neighbors, String value) {
		// Sharks Eat
		if(value.equals(SHARK)) {
			WaTorState eat = getStateOf(FISH, neighbors);
			if(eat != null) {
				eat.setNextValue(EMPTY);
				getCenterState(neighbors).eat();
			}
		}
		return neighbors.getCenter().getCurrentState();
	}
	
	private WaTorState getCenterState(Neighborhood neighbors) {
		return (WaTorState)neighbors.getCenter().getCurrentState();
	}
	
	private WaTorState getStateOf(String value, Neighborhood neighbors) {
		ArrayList<WaTorState> possible = new ArrayList<WaTorState>();
		for(WaTorState s : getStatesAround(neighbors)) {
			if(s.getValue().equals(value) && s.getNextValue() == null) {
				possible.add(s);
			}
		}
		if(possible.isEmpty()) return null;
		return possible.get((int)(Math.random()*possible.size()));
	}
	
	private ArrayList<WaTorState> getStatesAround(Neighborhood neighbors) {
		ArrayList<WaTorState> states = new ArrayList<WaTorState>();
		for(Cell neighbor : neighbors.getAdjacent()){
			if(neighbor != null) states.add((WaTorState) neighbor.getCurrentState());

		}
		return states;
	}
}
