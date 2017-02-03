package model;

public class WaTorState extends State {
	
	private String currentState; // empty, "fish", or "shark"

	public WaTorState(String state) {
		currentState = state;
	}

	@Override
	public String getColor() {
		if(currentState.equals("shark")) {
			return "gray";
		}
		if(currentState.equals("fish")) {
			return "blue";
		}
		return "green";
	}
	
	@Override
	public String getValue() {
		return currentState;
	}

}
