package simulation;

import model.State;
import xml.XMLParser;

public class SegregationState extends State{
	public static final String AGENT1 = "agent1";
	public static final String AGENT2 = "agent2";
	public static final String VACANT = "vacant";

	public SegregationState(XMLParser configuration, String stateString){
		super(configuration, stateString);
	}
	
	
}
