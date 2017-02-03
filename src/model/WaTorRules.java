package model;

import xml.XMLParser;

public class WaTorRules extends Rules {
	
	public WaTorRules(XMLParser config) {
		
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(stateText);
	}

	@Override
	public State getNewState(Neighborhood neighbors) {
		return new WaTorState(((WaTorState)neighbors.getCenter().getCurrentState()).getValue());
	}

}
