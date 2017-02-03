package model;

import java.io.File;

import xml.XMLParser;

public abstract class Rules {

	public static Rules getRules(File setupInfo) {
		XMLParser parser = new XMLParser(setupInfo);	
		String name = parser.getRuleName();
		
		if(name.equals("Spreading of Fire")) {
			return new FireRules(setupInfo);
		}
		if(name.equals("Wa-Tor")) {
			return new WaTorRules(setupInfo);
		}
		if(name.equals("Game of Life")) {
			return new LifeRules(setupInfo);
		}
		return null;
	}
	
	public abstract State getStartingState(String stateText);
	
	public abstract State getNewState(State[][] states);

}
