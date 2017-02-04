package simulation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.w3c.dom.Element;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;
/**
 * Rules for Spreading Fire
 * @author Jesse
 *
 */
public class FireRules extends Rules {
	
	private static final String FIELD = "probCatch";
	
	private double probFire;
	private XMLParser parser;
	
	/**
	 * Sets up the specific parameters for the Spreading Fire simulation
	 * @param setupInfo
	 */
	public FireRules(XMLParser parser) {
		this.parser = parser;
		probFire = Integer.parseInt(this.parser.getParameter(FIELD))/100;		
	}

	@Override
	public State getStartingState(String stateText) {
		return new State(parser, stateText);
	}

	@Override
	public State getNewState(Neighborhood hood) {
		Map<String, Cell> neighbors = hood.getNeighbors();
		neighbors.get(Neighborhood.NW).getCurrentState();
		return null;
	}
	

}
