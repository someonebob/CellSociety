package simulation.ForagingAnts;

import java.util.ArrayList;
import java.util.Collection;

import model.State;
import xml.XMLParser;

public class ForagingAntState extends State {
	public static final int MAX_PHERAMONES = 5;
	
	private int foodPheramones, homePheramones;
	private XMLParser configuration;
	private boolean isObstacle = false;
	private boolean hasFood = false;
	private String value;
	private Collection<Ant> ants = new ArrayList<Ant>();

	public ForagingAntState(XMLParser configuration, String stateText, int foodPheramones, int homePheramones){
		super(configuration, stateText);
		if(stateText.equals(ForagingAntRules.OBSTACLE)) isObstacle = true; 
		this.configuration = configuration;
		this.foodPheramones = foodPheramones;
		this.homePheramones = homePheramones;
		this.value = stateText;
	}
	
	public ForagingAntState(ForagingAntState s){
		this(s.configuration, s.getValue(), s.foodPheramones, s.homePheramones);
	}
	
	public String getValue(){
		if(ants.size() > 0) return ForagingAntRules.ANT;
		return this.value;
	}
	
	public XMLParser getConfiguration(){
		return this.configuration;
	}
	public int getPheramones(String pheramoneType){
		if(pheramoneType.equals(ForagingAntRules.FOOD)) return this.foodPheramones;
		else if(pheramoneType.equals(ForagingAntRules.HOME)) return this.homePheramones;
		else return 0;
	}
	
	public void dropPheramones(String pheramoneType, int numDropped){
		setPheramones(pheramoneType, getPheramones(pheramoneType) + numDropped);
	}

	public void setPheramones(String pheramoneType, int numSet){
		if(numSet <= MAX_PHERAMONES){
			if(pheramoneType.equals(ForagingAntRules.FOOD)) foodPheramones = numSet;
			else if(pheramoneType.equals(ForagingAntRules.HOME)) homePheramones = numSet;
		}
		else{
			if(pheramoneType.equals(ForagingAntRules.FOOD)) foodPheramones = MAX_PHERAMONES;
			else if(pheramoneType.equals(ForagingAntRules.HOME)) homePheramones = MAX_PHERAMONES;
		}
	}
	
	public boolean hasFood(){
		return this.hasFood;
	}
	
	public void setHasFood(boolean has){
		this.hasFood = has;
	}
	
	public boolean isAnObstacle(){
		return isObstacle;
	}
	
	public Collection<Ant> getAnts(){
		return ants;
	}
	
	public void addAnt(Ant a){
		ants.add(a);
	}
	
	public void removeAnt(Ant a){
		ants.remove(a);
	}
}
