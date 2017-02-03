package model;

import java.io.File;
import java.util.Random;

import org.w3c.dom.Element;

import xml.XMLParser;
/**
 * Rules for Spreading Fire
 * @author Jesse
 *
 */
public class FireRules extends Rules {
	
	private static final String FIELD = "probCatch";
	private static final String ATTRIBUTE = "name";
	
	private double probFire;
	private FireStates[][] fstates;
	
	/**
	 * Sets up the specific parameters for the Spreading Fire simulation
	 * @param setupInfo
	 */
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
		return new FireStates(Integer.parseInt(stateText));
	}

	@Override
	public State getNewState(State[][] states) {
		convertStates(states);
		
		Random chance = new Random();
		//current state is a tree
		if(fstates[1][1].getStateValue() == 1){
			//adjacent state is burning
			if(neighborBurning(fstates)){
				//random number is under the value of probFire
				if(chance.nextDouble() < probFire){
					//next state burning
					fstates[1][1].setStateValue(2);
				}
			}
		}
		//current state is burning
		if(fstates[1][1].getStateValue() == 2){
			//next state burned
			fstates[1][1].setStateValue(0);
			
		}
		//if current state is empty, stay the same
		return fstates[1][1];
	}
	
	/**
	 * Converts from abstract class states to Fire States to allow specific methods of Fire States
	 * @param states
	 */
	private void convertStates(State[][] states){
		fstates = new FireStates[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				fstates[i][j] = (FireStates)states[i][j];
			}
		}
	}
	
	/**
	 * Checks if any neighboring states are burning
	 * @param fstates
	 * @return
	 */
	private boolean neighborBurning(FireStates[][] fstates){
		return fstates[0][1].getStateValue() == 2 || fstates[1][0].getStateValue() == 2 || fstates[1][2].getStateValue() == 2 || fstates[2][1].getStateValue() == 2;
	}

}
