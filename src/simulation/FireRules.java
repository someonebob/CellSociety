package simulation;

import java.util.Random;

import model.Cell;
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
	
	private static final String BURNING = "burning";
	private static final String EMPTY = "empty";
	private static final String TREE = "tree";

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
		return new State(configuration, stateText);
	}

	@Override
	public State getNewState(Neighborhood hood) {
		Random rand = new Random();

		if (isTree(hood)) {
			if (neighborBurning(hood)) {
				if (rand.nextDouble() <= probFire) {
					return new State(configuration, BURNING);
				}
			} else {
				return new State(configuration, TREE);
			}
		}

		if (isBurning(hood)) {
			return new State(configuration, EMPTY);
		}

		return new State(configuration, EMPTY);
	}

	private boolean isTree(Neighborhood hood) {
		return hood.getCenter().getCurrentState().getValue().equals(TREE);
	}

	private boolean isBurning(Neighborhood hood) {
		return hood.getCenter().getCurrentState().getValue().equals(BURNING);
	}

	private boolean neighborBurning(Neighborhood hood) {
		for (Cell c : hood.getAdjacentCells()) {
			if (c.getCurrentState().getValue().equals(BURNING)) {
				return true;
			}
		}
		return false;
	}

}
