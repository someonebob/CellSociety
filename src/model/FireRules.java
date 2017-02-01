package model;

import java.io.File;
import java.util.Random;

import org.w3c.dom.Element;

import xml.XMLParser;

public class FireRules extends Rules {
	
	private static final String FIELD = "probCatch";
	private static final String ATTRIBUTE = "name";
	
	private State state;
	private double probFire;
	
	public FireRules(File setupInfo) {
		XMLParser parser = new XMLParser(setupInfo);
		
		for(int i = 0; i < parser.getParameters().getLength(); i++){
			Element element = (Element) parser.getParameters().item(i);
			if(parser.getAttribute(element, ATTRIBUTE).equals(FIELD)){
				probFire = Integer.parseInt(parser.getParameters().item(0).getTextContent())/100;
			}
		}			
	}

	@Override
	public State getStartingState(String stateText) {
		state = new FireStates(stateText);
		return state;
	}

	@Override
	public State getNewState(State[][] states) {
		if(states.length != 3){
			return null;
		}
		
		Random chance = new Random();
		
		
		return null;
	}

}
