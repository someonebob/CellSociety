/**
 * Foraging ants simulation rule set
 * @author DhruvKPatel
 * http://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf
 */
package simulation.ForagingAnts;

import java.util.Random;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

public class ForagingAntRules extends Rules {
	public static final String HOME = "home";
	public static final String FOOD = "food";
	public static final String EMPTY_FOOD = "empty-food";
	public static final String EMPTY = "empty";
	public static final String OBSTACLE = "obstacle";
	public static final String ANT = "ant";

	
	private XMLParser configuration;
	
	public ForagingAntRules(XMLParser configuration){
		this.configuration = configuration;
	}
	
	@Override
	public State getStartingState(String stateText) {
		ForagingAntState start  = new ForagingAntState(configuration, stateText, 0, 0);
		if(stateText.equals(HOME)){
			start.dropPheramones(HOME, ForagingAntState.MAX_PHERAMONES);
			Random r = new Random();
			if((r.nextDouble()) <= 0.3333) start.addAnt(new Ant(stateText));
		}
		else if(stateText.equals(FOOD)){
			start.dropPheramones(FOOD, ForagingAntState.MAX_PHERAMONES);
			start.setHasFood(true);
		}
		return start;
	}

	@Override
	public State getDefaultState() {
		return getStartingState(EMPTY);
	}

	@Override
	public State getNewState(Neighborhood neighborhood) {
		ForagingAntState currentState = (ForagingAntState)(neighborhood.getCenter().getCurrentState());
		if(currentState.getValue().equals(FOOD) && !currentState.hasFood()) neighborhood.getCenter().setFutureState(getStartingState(EMPTY_FOOD)); // If empty, change color
		for(Ant a: (currentState.getAnts())){
			a.forage(neighborhood.getCenter());
		}		
		return currentState; // All state changes are done within ant class and overrided.
	}
}
