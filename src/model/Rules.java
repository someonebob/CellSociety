package model;

import java.io.File;

import xml.XMLParser;

public abstract class Rules {

	public static Rules getRules(XMLParser configuration) {
		String name = configuration.getRuleName();
		
		if(name.equals("Spreading of Fire")) {
			return new FireRules(configuration);
		}
		if(name.equals("Wa-Tor")) {
			return new WaTorRules(configuration);
		}
		if(name.equals("Game of Life")) {
			return new LifeRules(configuration);
		}
		return null;
	}
	
	public abstract State getStartingState(String stateText);
	
	public abstract State getNewState(Neighborhood neighborhood);

}
