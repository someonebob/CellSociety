package model;

import java.io.File;
import java.util.ArrayList;

public class WaTorRules extends Rules {
	
	public WaTorRules(File setupInfo) {
	}

	@Override
	public State getStartingState(String stateText) {
		return new WaTorState(Integer.parseInt(stateText));
	}

	@Override
	public State getNewState(Neighborhood neighbors) {
		
		return new WaTorState(((WaTorState)neighbors.getCenter().getCurrentState()).getValue());
	}

}
