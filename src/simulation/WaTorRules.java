package simulation;

import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

public class WaTorRules extends Rules {
	
	XMLParser config;
	
	public WaTorRules(XMLParser configuration) {
		config = configuration;
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(config, stateText);
	}

	@Override
	public State getNewState(Neighborhood neighbors) {
		return new WaTorState(config, neighbors.getCenter().getCurrentState().getValue());
	}

}
