/**
 * Parent class for all cell states. Can be used directly or 
 * extended to increase functionality. Pairs with Rules class
 * to create simulation.
 * 
 * @author DhruvKPatel
 */

package model;
import xml.XMLParser;
public class State {	
	
	private String value;
	private XMLParser configuration;
	
	/**
	 * Constructs a state with a configuration and value
	 * @param configuration parser that gives state access to all necessary parameters
	 * @param value defines state for comparison with other states in "Rules"
	 */
	public State(XMLParser configuration, String value){
		this.configuration = configuration;
		this.value = value;	
	}
	
	/**
	 * Returns color code associated with state 
	 * @return string color of state found in configuration file
	 */
	public String getColor(){
		return configuration.getStateColor(value);
	}
	
	/**
	 * Returns state's defined value
	 * @return state's defined value
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Returns state's defined value
	 * @return state's defined value
	 */
	
	@Override
	public String toString() {
		return getValue();
	}	
}