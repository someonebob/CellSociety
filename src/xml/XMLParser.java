package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Handles XML Parsing
 * @author Jesse
 *
 */
public class XMLParser {
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {"name", "dimension", "state", "parameter", "stateDef"});
	public static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();

	private File info;
	private String ruleName;
	private int gridRows, gridColumns;
	private NodeList parameter;
	private Document xmlDocument;
	private NodeList stateList;
	
	/**
	 * Allows the file to be used throughout the class
	 * @param setupInfo contains the File needed to be parsed
	 */
	public XMLParser(File setupInfo){
		info = setupInfo;
	}
	
	/**
	 * Returns the name of the simulation used to choose the specific Rule subclass
	 * @return name of simulation
	 */
	public String getRuleName(){
		ruleName = getAttribute(getRootElement(), DATA_FIELDS.get(0));
		
		return ruleName;
	}
	
	/**
	 * Returns the dimension for rows
	 * @return number of rows
	 */
	public int getGridRows(){
		gridRows = Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(0).getTextContent());
		
		return gridRows;
	}
	/**
	 * Returns the dimension for columns
	 * @return number of columns
	 */
	public int getGridColumns(){
		gridColumns = Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(1).getTextContent());

		return gridColumns;
	}
	
	/**
	 * Returns a NodeList of all the initial states of the simulation
	 * @return list of states
	 */
	public NodeList getInitialStates(){
		stateList = getRootElement().getElementsByTagName(DATA_FIELDS.get(2));

		return stateList;
	}
	
	/**
	 * Returns a String corresponding to the input parameter
	 * @param tagName
	 * @return
	 */
	public String getParameter(String tagName){
		return getRootElement().getElementsByTagName(tagName).item(0).getTextContent();
	}
	
	/**
	 * Finds a color given a state name
	 */
	public String getStateColor(String stateName){
		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));

		for(int i = 0; i < stateDefinitions.getLength(); i++){
			String currentStateName = stateDefinitions.item(i).getNodeValue();
			String color = stateDefinitions.item(i).getAttributes().getNamedItem("color").getNodeValue();
			if(currentStateName.equals(stateName)) return color;
		}
		throw new XMLException("State definition not found", stateName);
	}
	
	/**
	 * Finds a state name given a state reference name
	 */
	public String getStateName(String stateRef){
		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));

		for(int i = 0; i < stateDefinitions.getLength(); i++){
			String currentStateName = stateDefinitions.item(i).getNodeValue();
			String ref = stateDefinitions.item(i).getAttributes().getNamedItem("ref").getNodeValue();
			if(ref.equals(stateRef)) return currentStateName;
		}
		throw new XMLException("State definition not found", stateRef);
	}
	

	/**
	 * Gets the attribute of the element
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String getAttribute (Element element, String attributeName) {
        return element.getAttribute(attributeName);
    }
	
	private Element getRootElement () {
        try {
            DOCUMENT_BUILDER.reset();
            xmlDocument = DOCUMENT_BUILDER.parse(info);
            Element root = xmlDocument.getDocumentElement();
            return root;
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }
	
	/**
	 * Creates document builder with the necessary exceptions
	 * @return
	 */
	private static DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }

}
