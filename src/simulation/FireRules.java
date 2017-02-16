// This entire file is part of my masterpiece.
// Jesse Yue
// I believe this class is well designed because it uses well named helper methods to make the logic clear and keeps that functionality encapsulated.
// The public methods are only what need to be public as directed by the super class.
package simulation;

import java.util.ArrayList;
import java.util.Random;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLException;
import xml.XMLParser;

/**
 * Rules for Spreading Fire
 * 
 * @author Jesse
 *
 */
public class FireRules extends Rules {
	
	private static final String FIELD = "probCatch";
	
	public static final String EMPTY = "empty";
	public static final String TREE = "tree";
	public static final String BURNING = "burning";

	private XMLParser configuration;
	private double probFire;

	/**
	 * Sets up the specific parameters for the Spreading Fire simulation
	 * 
	 * @param setupInfo
	 * @throws XMLException 
	 * @throws NumberFormatException 
	 */
	public FireRules(XMLParser configuration) throws NumberFormatException, XMLException {
		this.configuration = configuration;
		probFire = Integer.parseInt(this.configuration.getParameter(FIELD)) / 100.0;
	}

	@Override
	public State getStartingState(String stateText) {
		return new State(configuration, stateText);
	}
	
	@Override
	public State getDefaultState(){
		return new State(configuration, TREE);
	}

	@Override
	public State getNewState(Neighborhood hood) {
		return calculateState(hood);
	}
	
	private State calculateState(Neighborhood hood){
		Random rand = new Random();

		if (isTree(hood)) {
			if (neighborBurning(hood)) {
				if (rand.nextDouble() <= probFire) {
					return new State(configuration, BURNING);
				} else {
					return new State(configuration, TREE);
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
		for (State s : getStatesAround(hood)) {
			if (s.getValue().equals(BURNING)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<State> getStatesAround(Neighborhood hood) {
		ArrayList<State> states = new ArrayList<State>();
		for(Cell neighbor : hood.getAdjacent()){
			if(neighbor != null) states.add(neighbor.getCurrentState());

		}
		return states;
	}
}
