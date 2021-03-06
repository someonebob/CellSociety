package model;

import java.util.ArrayList;
import java.util.List;

import simulation.FireRules;
import simulation.LifeRules;
import simulation.SegregationRules;
import simulation.WaTorRules;
import simulation.ForagingAnts.ForagingAntRules;
import xml.XMLException;
import xml.XMLParser;

/**
 * Loads a subclass of Rules to be used for a given simulation.
 * @author Nathaniel Brooke
 * @version 02-03-2017
 */
public class RulesLoader {
	
	private Rules matchingRules;

	/**
	 * Creates a list of all known subclasses of Rules.
	 * Finds the subclass specified in the configuration XML file.
	 * @param config the XML file containing configuration information.
	 * @throws XMLException 
	 */
	public RulesLoader(XMLParser config) throws XMLException {
		List<Rules> allRules = makeRulesList(config);	
		findRulesUsed(config, allRules);
	}
	
	/**
	 * Gets the subclass of Rules to be used in this simulation.
	 * @return the initialized Rules subclass.
	 */
	public Rules getRules() {
		return matchingRules;
	}

	/**
	 * Looks through the list of all subclasses of Rules.
	 * Returns the subclass specified in the configuration file.
	 * @param config the configuration XML file.
	 * @param options the list of all subclasses of Rules.
	 * @throws XMLException 
	 */
	private void findRulesUsed(XMLParser config, List<Rules> options) throws XMLException {
		String rulesName = config.getParameter("subclass");
		for(Rules r : options) {
			if(r.getClass().getName().equals(rulesName)) {
				matchingRules = r;
				return;
			}
		}
		throw new XMLException("Subclass of rules specified in XML file not found");
	}
	
	/**
	 * Generates a list of all known rules.
	 * @param config configuration file to be passed to Rules subclasses.
	 * @return List of Rules subclasses
	 */
	private List<Rules> makeRulesList(XMLParser config) {
		ArrayList<Rules> options = new ArrayList<Rules>();
		try {
			options.add(new FireRules(config));
		} catch (XMLException e) {}
		options.add(new LifeRules(config));
		try {
			options.add(new WaTorRules(config));
		} catch (XMLException e) {}
		try {
			options.add(new SegregationRules(config));
		} catch (XMLException e) {}
		options.add(new ForagingAntRules(config));
		return options;
	}	
}
