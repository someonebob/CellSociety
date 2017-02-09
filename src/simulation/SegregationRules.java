package simulation;

import java.util.ArrayList;
import java.util.Random;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

public class SegregationRules extends Rules{
	public static final String VACANT = "vacant";
	public static final String PERCENT_THRESHOLD_PARAMETER = "percentThreshold";
	private XMLParser configuration;
	private double percentThreshold;
	private Cell me;
	
	public SegregationRules(XMLParser configuration){
		this.configuration = configuration;
		this.percentThreshold = Double.parseDouble(configuration.getParameter(PERCENT_THRESHOLD_PARAMETER));
	}
	
	public State getStartingState(String stateText) {
		return new State(configuration, stateText);
	}

	public State getNewState(Neighborhood neighborhood) {
		me = neighborhood.getCenter();
			
		if(!isVacant(me) && getPercentAlike(neighborhood) < (percentThreshold/100)) 
			swap(me, getNearestEmptyHome(neighborhood));
		return me.getCurrentState(); // Will be overridden if cells are swapped
	}
	
	private Cell getNearestEmptyHome(Neighborhood neighborhood){
		ArrayList<Cell> possibleHomes = new ArrayList<Cell>(); // Semi-Random search (looks at neighbors first)
		ArrayList<Cell> alreadySearched = new ArrayList<Cell>();
		for(Cell other : neighborhood.getNeighbors(Neighborhood.ALL_INDICES)){
			if(other != null) possibleHomes.add(other);
		}
		while (!possibleHomes.isEmpty()){
			Cell home = possibleHomes.get((new Random()).nextInt(possibleHomes.size()));
			possibleHomes.remove(home);
			alreadySearched.add(home);
			
			if(isVacant(home) && home != me && !home.futureStateIsLocked()) return home;
			else{
				for(Cell nextPossible : home.getNeighborhood().getNeighbors(Neighborhood.ALL_INDICES)){
					if(nextPossible != null && !alreadySearched.contains(nextPossible) && nextPossible != me) {
						possibleHomes.add(nextPossible);
					}
				}
			}
		}
		return me; // Only gets to this if all other homes occupied
	}
	
	private void swap(Cell cell1, Cell cell2){
		cell1.setFutureState(cell2.getCurrentState());
		cell2.setFutureState(cell1.getCurrentState());
	}
	
	private double getPercentAlike(Neighborhood neighborhood){
		double numNeighbors = 0;
		double numAlike = 0;
		
		for(Cell other : neighborhood.getNeighbors(Neighborhood.ALL_INDICES)){
			if(other != null && !isVacant(other)){
				numNeighbors++;
				if (isAlike(me, other)) numAlike++;
			}
		}
		if(numNeighbors == 0) return 1;
		return numAlike / numNeighbors;
	}
	
	private boolean isAlike(Cell cell1, Cell cell2){
		return cell1.getCurrentState().getValue().equals(cell2.getCurrentState().getValue());		
	}
	
	private boolean isVacant(Cell cell){
		return cell.getCurrentState().getValue().equals(VACANT);
	}
}
