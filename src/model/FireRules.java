package model;

import java.io.File;
import java.util.Random;

import xml.XMLParser;

public class FireRules extends Rules {
	private State state;
	private String field = "probCatch";
	private double probFire;
	private XMLParser parser;

	@Override
	public State getStartingState(String stateText, int rows, int cols) {
		state = new FireStates(stateText, rows, cols);
		return state;
	}

	@Override
	public State getNewState(State[][] states, File setupInfo) {
		parser = new XMLParser(setupInfo);
		probFire = Integer.parseInt(parser.getParameters(field))/100;	//change from percentage to probability
		
		Random chance = new Random();
		
		
		return null;
	}

}
