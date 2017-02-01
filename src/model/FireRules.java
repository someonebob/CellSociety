package model;

import java.io.File;
import java.util.Random;

import xml.XMLParser;

public class FireRules extends Rules {
	
	private static final String FIELD = "probCatch";
	
	private State state;
	private double probFire;
	
	public FireRules(File setupInfo) {
		XMLParser parser = new XMLParser(setupInfo);
		probFire = Integer.parseInt(parser.getParameters(FIELD))/100;	//change from percentage to probability
	}

	@Override
	public State getStartingState(String stateText) {
		state = new FireStates(stateText);
		return state;
	}

	@Override
	public State getNewState(State[][] states) {
		
		Random chance = new Random();
		
		
		return null;
	}

}
