package simulation;

import model.State;
import xml.XMLParser;

public class FireState extends State {
	
	public static final String EMPTY = "empty";
	public static final String TREE = "tree";
	public static final String BURNING = "burning";


	public FireState(XMLParser configuration, String value) {
		super(configuration, value);
	}

}
