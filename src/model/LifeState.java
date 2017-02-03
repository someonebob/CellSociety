package model;

import xml.XMLParser;

public class LifeState extends State {

	private boolean alive;
	public static final String ALIVE = "alive";
	private static final String DEAD = "dead";
	
	public LifeState(XMLParser configuration, String value){
		super(configuration, value);
		alive = value.equals(ALIVE); // deafults to dead
	}
}
