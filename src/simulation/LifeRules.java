/**
 * Rule class for Game of Life Simulation
 * @author DhruvKPatel
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
	
	public State getDefaultState(){
		return new State(configuration, DEAD);
	}
	
	/**
	 * Checks neighbors around with constraints 
	 * and returns life state depending on constraints.
	 */
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
	
	/**
	 * Counts the number of neighbors alive
	 * @param neighborhood
	 * @return integer value of neighbors alive
	 */
	private int getNumNeighborsAlive(Neighborhood neighborhood){
		int numAlive = 0;
	
		for(Cell neighbor : neighborhood.getNeighbors())
			if(getAlive(neighbor)) numAlive++;
		
		return numAlive;
	}
	
	/**
	 * Checks if cell is alive
	 * @param cell
	 */
	private boolean getAlive(Cell cell){
		return (cell != null && cell.getCurrentState().getValue().equals(ALIVE));
	}

}