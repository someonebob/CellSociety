package simulation.ForagingAnts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Cell;
import model.Coordinate;

public class Ant {
	private boolean hasFoodItem;
	
	// Parameters
	private final int NUM_ORIENTATION_CHOICES = 3;
	private final double K = 0.001;
	private final double N = 10.0;
	
	private Coordinate orientation;	
	private ForagingAntState futureState, currentState;
	private Cell currentCell, futureCell;
	private String location;
	
	public Ant(String location){
		this.hasFoodItem = false;
		setLocation(location);
	}
	
	public String getLocation(){
		return location;
	}
	
	public boolean hasFood(){
		return this.hasFoodItem;
	}
	
	public void setLocation(String loc){
		this.location = loc;
	}
	
	public void setHasFood(boolean has){
		this.hasFoodItem = has;
	}
	
	public void forage(Cell current){
		currentCell = current;
		currentState = new ForagingAntState(getState(currentCell));
		System.out.println(currentState);
		if(hasFood()){	// Ant-Return-To-Nest
			this.find(ForagingAntRules.HOME);
			this.dropPheramones(ForagingAntRules.FOOD);
			if(this.getLocation().equals(ForagingAntRules.HOME)){
				this.setHasFood(false);
				currentState.setHasFood(true);
			}
		}
		else{	// Ant-Find-Food-Source
			this.find(ForagingAntRules.FOOD);
			this.dropPheramones(ForagingAntRules.HOME);
		}
		
		currentCell.setFutureState(currentState);
		if(futureCell != null) futureCell.setFutureState(futureState);
		
	}

	private void find(String lure) {
		if(!getLocation().equals(lure) && getLocation().equals(ForagingAntRules.EMPTY)){
			orientation = this.getLocationWithMaxPheramones(lure);		
		}
		Collection<Coordinate> newLocationChoices = this.getClosestLocations(orientation, currentCell.getNeighborhood().getLocalNeighborCoordinates(), NUM_ORIENTATION_CHOICES);
		Coordinate possible = this.selectLocation(newLocationChoices);
		if(possible != null){
			Collection<Coordinate> backupChoices = currentCell.getNeighborhood().getLocalNeighborCoordinates();
			possible = this.selectLocation(backupChoices);
		}
		
		if(possible != null){
			futureCell = currentCell.getNeighborhood().get(possible);
			orientation = possible;
			futureState = new ForagingAntState(getState(currentCell.getNeighborhood().get(possible)));
			moveTo(possible);
		}
		
		possible = orientation;
		moveTo(possible);
	}

	private void dropPheramones(String type){
		if(this.location.equals(type)){
			currentState.setPheramones(type, ForagingAntState.MAX_PHERAMONES);
		}
		else{
			int d = ForagingAntState.MAX_PHERAMONES - 2 - getState(currentCell).getPheramones(type);
			if(d > 0) currentState.setPheramones(type, d);
		}		
	}
	
	private void moveTo(Coordinate newPos){
		futureState.addAnt(this);
		currentState.removeAnt(this);
	}
	
	public Coordinate getLocationWithMaxPheramones(String pheramoneType){
		int maxPheramones = 0;
		Coordinate maxLoc = new Coordinate(0, 0);
		
		for(Coordinate neighborLoc : currentCell.getNeighborhood().getLocalNeighborCoordinates()){
			Cell neighbor = currentCell.getNeighborhood().get(neighborLoc);
			ForagingAntState neighborState = getState(neighbor);
			
			if(neighborState.getPheramones(pheramoneType) > maxPheramones){
				maxPheramones++;
				maxLoc = neighborLoc;
			}
		}
		return maxLoc;
	}
	
	public ForagingAntState getState(Cell neighbor){
		return (ForagingAntState)neighbor.getCurrentState();
	}
	
	public Collection<Coordinate> getClosestLocations(Coordinate currentLocation, Collection<Coordinate> neighbors, int newLength){
		class distanceComparator implements Comparator<Coordinate> {
			@Override
			public int compare(Coordinate c1, Coordinate c2) {
				Coordinate diff = c1.subtract(c2);
				return (int) Math.sqrt(Math.pow(diff.getCol(), 2) + Math.pow(diff.getRow(), 2));
			}
			
		}
		List<Coordinate> allLocations = new ArrayList<Coordinate>(neighbors);
		Collection<Coordinate> closestLocations = new ArrayList<Coordinate>();
		Collections.sort(allLocations, new distanceComparator());
		for(int i = 0; i < newLength; i++){
			closestLocations.add(allLocations.get(i));
		}
		return closestLocations;
	}

	
	public Coordinate selectLocation(Collection<Coordinate> locSet){
		long seed = System.nanoTime();
		Map<Coordinate, Double> choicesWithWeights = new HashMap<Coordinate, Double>();
		for(Coordinate c: locSet){
			if(currentCell.getNeighborhood().get(c).getCurrentState() == null) continue;
			double pheremones = getState(currentCell.getNeighborhood().get(c)).getPheramones(ForagingAntRules.FOOD);
			choicesWithWeights.put(c, Math.pow((K + pheremones), N));
		}
		while(choicesWithWeights.size() > 0){
			Coordinate possible = this.getWeightedRandom(choicesWithWeights, new Random());
			ForagingAntState possibleState = getState(currentCell.getNeighborhood().get(possible));
			if(!possibleState.isAnObstacle() && possibleState.getAnts().size() >= 1) 
				return possible;
			choicesWithWeights.remove(possible);		
		}
		for(Coordinate loc: locSet){
			if(!getState(currentCell.getNeighborhood().get(loc)).isAnObstacle()) return loc;
		}
		return null;
	}
	
	/**
	 * This was taken from: http://stackoverflow.com/questions/6737283/weighted-randomness-in-java
	 * Gets weighted random of any Map
	 * @param weights
	 * @param random
	 *
	 */
	public static <E> E getWeightedRandom(Map<E, Double> weights, Random random) {
	    E result = null;
	    double bestValue = Double.MAX_VALUE;
	    for (E element : weights.keySet()) {
	        double value = -Math.log(random.nextDouble()) / weights.get(element);

	        if (value < bestValue) {
	            bestValue = value;
	            result = element;
	        }
	    }
	    return result;
	}
}
