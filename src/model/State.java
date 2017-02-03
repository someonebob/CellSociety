package model;

public abstract class State {	
	
	private String myValue;
	
	public State(String value) {
		myValue = value;
	}
	
	public String getColor() {
		return null;
	}
	
	public String getValue() {
		return myValue;
	}
	
}