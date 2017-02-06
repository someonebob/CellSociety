package simulation;

import model.State;
import xml.XMLParser;

public class LifeState extends State {

	public static final String ALIVE = "alive";
	public static final String DEAD = "dead";
	
	public LifeState(XMLParser configuration, String value){
		super(configuration, value);
	}
}
