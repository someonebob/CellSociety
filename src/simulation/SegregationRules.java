package simulation;

import java.util.ArrayList;
import java.util.Random;

import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLException;
import xml.XMLParser;


public class SegregationRules extends Rules{
	public static final String VACANT = "vacant";
	public static final String PERCENT_THRESHOLD_PARAMETER = "percentThreshold";
	private XMLParser configuration;
	private double percentThreshold;
	private Cell me;
	
	public SegregationRules(XMLParser configuration) throws NumberFormatException, XMLException{
		this.configuration = configuration;
		this.percentThreshold = Double.parseDouble(configuration.getParameter(PERCENT_THRESHOLD_PARAMETER));
	}
	
	public State getStartingState(String stateText) {
		return new State(configuration, stateText);
	}
	
	public State getDefaultState(){
		return new State(configuration, VACANT);
	}

	public State getNewState(Neighborhood neighborhood) {
		me = neighborhood.getCenter();
			
		if(!isVacant(me) && getPercentAlike(neighborhood) < (percentThreshold/100)) 
			swap(me, getNearestEmptyHome(neighborhood));
		return me.getCurrentState(); // Will be overridden if cells are swapped
	}
	
	/**
	 * Uses semi-random-breadth first search to find nearest empty home.
	 * If no empty homes exists, returns inital home.
	 */
	private Cell getNearestEmptyHome(Neighborhood neighborhood){
		ArrayList<Cell> possibleHomes = new ArrayList<Cell>(); // Semi-Random search (looks at neighbors first)
		ArrayList<Cell> alreadySearched = new ArrayList<Cell>();
		for(Cell other : neighborhood.getNeighbors()){
			if(other != null) possibleHomes.add(other);
		}
		while (!possibleHomes.isEmpty()){
			Cell home = possibleHomes.get((new Random()).nextInt(possibleHomes.size()));
			possibleHomes.remove(home);
			alreadySearched.add(home);
			
			if(isVacant(home) && home != me && !home.futureStateIsLocked()) return home;
			else{
				for(Cell nextPossible : home.getNeighborhood().getNeighbors()){
					if(nextPossible != null && !alreadySearched.contains(nextPossible) && nextPossible != me) {
						possibleHomes.add(nextPossible);
					}
				}
			}
		}
		return me; // Only gets to this if all other homes occupied
	}
	
	/**
	 * Swaps position of two cells
	 * (future states of cells can only be manually set once)
	 */
	private void swap(Cell cell1, Cell cell2){
		cell1.setFutureState(cell2.getCurrentState());
		cell2.setFutureState(cell1.getCurrentState());
	}
	
	/**
	 * Iterates through a Cell's neighborhood
	 * and returns the percent of non-null neighbors 
	 * that are the same as center cell
	 */
	private double getPercentAlike(Neighborhood neighborhood){
		double numNeighbors = 0;
		double numAlike = 0;
		
		for(Cell other : neighborhood.getNeighbors()){
			if(other != null && !isVacant(other)){
				numNeighbors++;
				if (isAlike(me, other)) numAlike++;
			}
		}
		if(numNeighbors == 0) return 1;
		return numAlike / numNeighbors;
	}
	
	/**
	 * Compares the state value of two cells
	 */
	private boolean isAlike(Cell cell1, Cell cell2){
		return cell1.getCurrentState().getValue().equals(cell2.getCurrentState().getValue());		
	}
	
	/**
	 * Checks if cell's state is vacant
	 */
	private boolean isVacant(Cell cell){
		return cell.getCurrentState().getValue().equals(VACANT);
	}
}
