package model;

import java.io.File;

public abstract class Rules {
	private static FireRules fireRules;

	public static Rules getRules(String name) {
		if(name.equals("Spreading of Fire")){
			return fireRules;
		}
		return null;
	}
	
	public abstract State getStartingState(String stateText, int rows, int cols);
	
	public abstract State getNewState(State[][] states, File setupInfo);

}
