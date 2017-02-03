package model;
import xml.XMLParser;

public class LifeRules extends Rules {
	XMLParser configuration;
	
	public LifeRules(XMLParser configuration){
		this.configuration = configuration;
	}
	
	public State getStartingState(String stateText){
		return new LifeState(configuration, stateText);
	}
	
	public LifeState getNewState(Neighborhood neighborhood) { 
		int neighborsAlive = getNumNeighborsAlive(neighborhood);
		boolean isAlive = getAlive(neighborhood.getCenter());
		
		if(isAlive){
			if (neighborsAlive >= 2 && neighborsAlive <= 3) return new LifeState(configuration, "alive");
			else return new LifeState(configuration, "dead");
		}
		else{
			if(neighborsAlive == 3) return new LifeState(configuration, "alive");
			else return new LifeState(configuration, "dead");
		}
		
	}
	
	private int getNumNeighborsAlive(Neighborhood neighborhood){
		int numAlive = 0;
		for(Cell neighbor : neighborhood.getNeighbors().values()){
			if(neighbor != null && neighbor.getCurrentState().getValue().equals(LifeState.ALIVE))
				numAlive++;
		}
		return numAlive;
	}
	
	private boolean getAlive(Cell cell){
		return (cell != null && cell.getCurrentState().getValue().equals(LifeState.ALIVE));
	}

}