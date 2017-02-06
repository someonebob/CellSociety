package simulation;

import java.util.ArrayList;
import java.util.Random;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

/**
 * Rules for Spreading Fire
 * 
 * @author Jesse
 *
 */
public class FireRules extends Rules {
	private static final String FIELD = "probCatch";

	private XMLParser configuration;
	private double probFire;

	/**
	 * Sets up the specific parameters for the Spreading Fire simulation
	 * 
	 * @param setupInfo
	 */
	public FireRules(XMLParser configuration) {
		this.configuration = configuration;
		probFire = Integer.parseInt(this.configuration.getParameter(FIELD)) / 100.0;
	}

	@Override
	public State getStartingState(String stateText) {
		return new FireState(configuration, stateText);
	}

	@Override
	public FireState getNewState(Neighborhood hood) {
		Random rand = new Random();

		if (isTree(hood)) {
			if (neighborBurning(hood)) {
				if (rand.nextDouble() <= probFire) {
					return new FireState(configuration, FireState.BURNING);
				} else {
					return new FireState(configuration, FireState.TREE);
				}
			} else {
				return new FireState(configuration, FireState.TREE);
			}
		}

		if (isBurning(hood)) {
			return new FireState(configuration, FireState.EMPTY);
		}

		return new FireState(configuration, FireState.EMPTY);
	}

	private boolean isTree(Neighborhood hood) {
		return hood.getCenter().getCurrentState().getValue().equals(FireState.TREE);
	}

	private boolean isBurning(Neighborhood hood) {
		return hood.getCenter().getCurrentState().getValue().equals(FireState.BURNING);
	}

	private boolean neighborBurning(Neighborhood hood) {
		for (FireState s : getStatesAround(hood)) {
			if (s.getValue().equals(FireState.BURNING)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<FireState> getStatesAround(Neighborhood hood) {
		ArrayList<FireState> states = new ArrayList<FireState>();
		if (hood.get(0, 1) != null)
			states.add((FireState) hood.get(0, 1).getCurrentState());
		if (hood.get(1, 0) != null)
			states.add((FireState) hood.get(1, 0).getCurrentState());
		if (hood.get(1, 2) != null)
			states.add((FireState) hood.get(1, 2).getCurrentState());
		if (hood.get(2, 1) != null)
			states.add((FireState) hood.get(2, 1).getCurrentState());
		return states;
	}

}
