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
		if(hasFood()){	// Ant-Return-To-Nest
			this.find(current, ForagingAntRules.HOME);
			this.dropPheramones(current, ForagingAntRules.FOOD);
			if(this.getLocation().equals(HOME))
		}
		else{	// Ant-Find-Food-Source
			this.find(current, ForagingAntRules.FOOD);
			this.dropPheramones(current, ForagingAntRules.HOME);
		}
		
		current.setFutureState(currentState);
		//ADD OTHER STATE TOO
		
	}

	private void find(Cell current, String lure) {
		if(!getLocation().equals(lure) && getLocation().equals(ForagingAntRules.OTHER)){
			orientation = this.getLocationWithMaxPheramones(current, lure);		
		}
		Collection<Coordinate> newLocationChoices = this.getClosestLocations(orientation, current.getNeighborhood().getLocalNeighborCoordinates(), NUM_ORIENTATION_CHOICES);
		Coordinate possible = this.selectLocation(current, newLocationChoices);
		if(possible != null){
			Collection<Coordinate> backupChoices = current.getNeighborhood().getLocalNeighborCoordinates();
			possible = this.selectLocation(current, backupChoices);
		}
		
		if(possible != null){
			orientation = possible;
			currentState = new ForagingAntState(getState(current));
			futureState = new ForagingAntState(getState(current.getNeighborhood().get(possible)));
			moveTo(current, possible);
		}
	}

	private void dropPheramones(Cell current, String type){
		if(this.location.equals(type)){
			currentState.setPheramones(type, ForagingAntState.MAX_PHERAMONES);
		}
		else{
			int d = ForagingAntState.MAX_PHERAMONES - 2 - getState(current).getPheramones(type);
			if(d > 0) currentState.setPheramones(type, d);
		}		
	}
	
	private void moveTo(Cell current, Coordinate newPos){
		futureState.addAnt(this);
		currentState.removeAnt(this);
	}
	
	public Coordinate getLocationWithMaxPheramones(Cell currentLocation, String pheramoneType){
		int maxPheramones = 0;
		Coordinate maxLoc = new Coordinate(0, 0);
		
		for(Coordinate neighborLoc : currentLocation.getNeighborhood().getLocalNeighborCoordinates()){
			Cell neighbor = currentLocation.getNeighborhood().get(neighborLoc);
			ForagingAntState neighborState = getState(neighbor);
			
			if(neighborState.getPheramones(pheramoneType) > maxPheramones){
				maxPheramones++;
				maxLoc = neighborLoc;
			}
		}
		return maxLoc;
	}
	
	public ForagingAntState getState(Cell neighbor){
		if(neighbor.getCurrentState() instanceof ForagingAntState)
			return (ForagingAntState)neighbor.getCurrentState();
		return null;
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

	
	public Coordinate selectLocation(Cell current, Collection<Coordinate> locSet){
		long seed = System.nanoTime();
		Map<Coordinate, Double> choicesWithWeights = new HashMap<Coordinate, Double>();
		for(Coordinate c: locSet){
			double pheremones = getState(current.getNeighborhood().get(c)).getPheramones(ForagingAntRules.FOOD);
			choicesWithWeights.put(c, Math.pow((K + pheremones), N));
		}
		while(choicesWithWeights.size() > 0){
			Coordinate possible = this.getWeightedRandom(choicesWithWeights, new Random());
			ForagingAntState possibleState = getState(current.getNeighborhood().get(possible));
			if(!possibleState.isAnObstacle() && possibleState.getAnts().size() >= 1) 
				return possible;
			choicesWithWeights.remove(possible);		
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
