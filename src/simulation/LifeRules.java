/**
 * Rule class for Game of Life Simulation
 */
package simulation;
import model.Cell;
import model.Neighborhood;
import model.Rules;
import model.State;
import xml.XMLParser;

public class LifeRules extends Rules {
	public static final String ALIVE = "alive";
	private static final String DEAD = "dead";
	XMLParser configuration;
	
	public LifeRules(XMLParser configuration){
		this.configuration = configuration;
	}
	
	public State getStartingState(String stateText){
		return new State(configuration, stateText);
	}
	
	public State getNewState(Neighborhood neighborhood) { 
		int neighborsAlive = getNumNeighborsAlive(neighborhood);
		boolean isAlive = getAlive(neighborhood.getCenter());
		if(isAlive){
			if (neighborsAlive >= 2 && neighborsAlive <= 3) {
				return new State(configuration, ALIVE);
			}
			else {
				return new State(configuration, DEAD);
			}
		}
		else{
			if(neighborsAlive == 3) {
				return new State(configuration, ALIVE);
			}
			else {
				return new State(configuration, DEAD);
			}
		}
		
	}
	
	private int getNumNeighborsAlive(Neighborhood neighborhood){
		int numAlive = 0;
		for(Cell neighbor : neighborhood.getNeighbors(Neighborhood.ALL_INDICES)){
			if(neighbor != null){
				if(neighbor.getCurrentState().getValue().equals(ALIVE))
					numAlive++;
			}
		}
		return numAlive;
	}
	
	private boolean getAlive(Cell cell){
		return (cell != null && cell.getCurrentState().getValue().equals(ALIVE));
	}

}