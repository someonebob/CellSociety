package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LifeRules extends Rules {
	private LifeStates[][] lstates;
	private Map<Integer, Integer> neighborMap;
	
	public LifeRules(File setupInfo){
		
	}

	@Override
	public State getStartingState(String stateText) {
		return new LifeStates(Integer.parseInt(stateText));
	}

	@Override
	public State getNewState(State[][] states) {
		convertStates(states);
		setupMap(lstates);
		LifeStates state = lstates[1][1];
		int stateValue = state.getStateValue();
		
		//current state is dead
		if(stateValue == 0){
			//exactly 3 live neighbors
			if(neighborMap.get(1) == 3){
				state.setStateValue(1);
			}
		}
		
		//current state is live
		if(stateValue == 1){
			//less than 2 live neighbors
			if(neighborMap.get(1) < 2){
				//dies to underpopulation
				state.setStateValue(0);
			}
			//more than 3 live neighbors
			if(neighborMap.get(1) > 3){
				//dies to overpopulation
				state.setStateValue(0);
			}
		}
		//else stay the same
		return state;
	}
	
	/**
	 * Converts from abstract class states to Life States to allow specific methods of Life States
	 * @param states
	 */
	private void convertStates(State[][] states){
		lstates = new LifeStates[3][3];
		
		for(int i = 0; i < lstates.length; i++){
			for(int j = 0; j < lstates.length; j++){
				lstates[i][j] = (LifeStates)states[i][j];
			}
		}
	}
	
	private void setupMap(LifeStates[][] lstates){
		neighborMap = new HashMap<Integer, Integer>();

		for(int i = 0; i < lstates.length; i++){
			for(int j = 0; j < lstates.length; j++){				
				if(i == 1 && j == 1){
					continue;
				}
				
				if(neighborMap.containsKey(lstates[i][j].getStateValue())){
					neighborMap.put(lstates[i][j].getStateValue(), neighborMap.get(lstates[i][j].getStateValue()) + 1);
				}else{
					neighborMap.put(lstates[i][j].getStateValue(), 1);
				}
			}
		}
	}
	

}
