package model;

import xml.XMLParser;

public class State {	
	
	private String value;
	private XMLParser configuration;
	
	public State(XMLParser configuration, String value){
		this.configuration = configuration;
		this.value = value;
		
	}
	
	public String getColor(){
		return configuration.getStateColor(value);
	}
	
	public String getValue(){
		return value;
	}
	
}