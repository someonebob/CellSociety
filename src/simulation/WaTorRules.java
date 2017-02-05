package simulation;

import java.util.ArrayList;
import java.util.Map;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

public class WaTorRules extends Rules {
	
	private XMLParser config;
	private int breed;
	private int starve;
	
	public WaTorRules(XMLParser configuration) {
		config = configuration;
		breed = Integer.parseInt(config.getParameter("breedTime"));
		starve = Integer.parseInt(config.getParameter("starveTime"));
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(config, stateText, 1, 0, 0);
	}

	@Override
	public State getNewState(Neighborhood neighbors) {
		int step = getCenterState(neighbors).getStep();
		int age = getCenterState(neighbors).getCellAge() + (3-step)/3;
		int noFood = getCenterState(neighbors).getNoFoodTime() + (3-step)/3;
		getCenterState(neighbors).incrStep();
		
		String value = neighbors.getCenter().getCurrentState().getValue();
		if(step == 1) {
			// Sharks Eat
			if(value.equals("shark")) {
				WaTorState eat = getStateOf("fish", neighbors);
				if(eat != null) {
					eat.setNextValue("empty");
					getCenterState(neighbors).eat();
				}
			}
			return neighbors.getCenter().getCurrentState();
		}
		else if(step == 3) {
			getCenterState(neighbors).setStep(0);
			// Everybody moves or reproduces or starves
			if((value.equals("shark") && (!getCenterState(neighbors).ate()
							|| getCenterState(neighbors).getCellAge() >= breed)) 
					|| value.equals("fish")) {
				WaTorState move = getStateOf("empty", neighbors);
				if(move != null) {
					if(!value.equals("shark") || noFood < starve) {
						// Not Starving
						move.setNextValue(value);
						move.setNoFoodTime(noFood);
					}
					if(age < breed) {
						// Not reproducing
						getCenterState(neighbors).setNextValue("empty");	
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
		else {
			// Update everything
			String nextVal = getCenterState(neighbors).getNextValue();
			if(nextVal != null) {
				return new WaTorState(config, nextVal, ++step, age, noFood);
			}
			else {
				return new WaTorState(config, getCenterState(neighbors).getValue(), ++step, age, noFood);
			}
		}
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
		Map<String, Cell> cells = neighbors.getNeighbors();
		ArrayList<WaTorState> states = new ArrayList<WaTorState>();
		if(cells.get(Neighborhood.N) != null) 
			states.add((WaTorState)cells.get(Neighborhood.N).getCurrentState());
		if(cells.get(Neighborhood.E) != null) 
			states.add((WaTorState)cells.get(Neighborhood.E).getCurrentState());
		if(cells.get(Neighborhood.W) != null) 
			states.add((WaTorState)cells.get(Neighborhood.W).getCurrentState());
		if(cells.get(Neighborhood.S) != null) 
			states.add((WaTorState)cells.get(Neighborhood.S).getCurrentState());
		return states;
	}


}
