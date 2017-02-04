package simulation;

import model.Neighborhood;
import model.Rules;
import model.RulesException;
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
		String currentValue = neighbors.getCenter().getCurrentState().getValue();
		String newValue = "";
		if(currentValue.equals("empty")) {
			
		}
		else if(currentValue.equals("fish")) {
			
		}
		else if(currentValue.equals("shark")) {
			
		}
		else {
			throw new RulesException("Value of current state of given cell not recognized");
		}
		
		return new WaTorState(config, newValue);
	}

}
