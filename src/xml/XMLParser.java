// This entire file is part of my masterpiece.
// Jesse Yue
// I believe this class is well designed because the API has powerful features that allow the user to do a lot of things but it also encapsulates the generation of the Document Builder so the user doesn't have the ability to go crazy and create a bunch of documents

package xml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import model.Coordinate;

/**
 * Handles XML Parsing
 * 
 * @author Jesse
 * @author Dhruv
 */
public class XMLParser {
	
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "name", "dimension", "state", "parameter", "stateDef", "scheme", "color", "ref" });
	public static final ResourceBundle RESOURCES = ResourceBundle.getBundle("resourcefiles/XML");
	public DocumentBuilder DOCUMENT_BUILDER;	

	private File info;
	private Document xmlDocument;

	/**
	 * Allows the file to be used throughout the class
	 * 
	 * @param setupInfo
	 *            contains the File needed to be parsed
	 * @throws XMLException 
	 */
	public XMLParser(File setupInfo) throws XMLException {
		DOCUMENT_BUILDER = getDocumentBuilder();
		info = setupInfo;
	}

	/**
	 * Returns a NodeList of all the initial states of the simulation
	 * 
	 * @return list of states
	 * @throws XMLException 
	 * @throws DOMException 
	 */
	public Map<Coordinate, String> getInitialStates() throws DOMException, XMLException {
		try{
			Map<Coordinate, String> stateTextGrid = new TreeMap<Coordinate, String>();
	
			boolean hasStates = false;
			NodeList allChildren = getRootElement().getChildNodes();
			for(int i = 0; i < allChildren.getLength(); i++){
				if(allChildren.item(i).getNodeName().equals(DATA_FIELDS.get(2))){
					hasStates = true;
				}
			}
			if(hasStates){
				String fullRef = getRootElement().getElementsByTagName(DATA_FIELDS.get(2)).item(0).getTextContent();
				String[] linesRef = fullRef.trim().split("\n");
				for (int row = 0; row < linesRef.length; row++) {
					String[] stateRef = linesRef[row].trim().split("\\s+");
					for (int col = 0; col < stateRef.length; col++) {
						stateTextGrid.put(new Coordinate(row, col), getStateName(stateRef[col]));
					}
				}
			}		
			else{
				int rows = getGridRows();
				int cols = getGridColumns();
				Random rand = new Random();
				
				NodeList stateDefs = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
				for(int row = 0; row < rows; row++){
					for(int col = 0; col < cols; col++){		
						stateTextGrid.put(new Coordinate(row, col), stateDefs.item(rand.nextInt(stateDefs.getLength())).getTextContent());
					}
				}
			}
			return stateTextGrid;
		} catch(Exception e){
			throw new XMLException("Initial state grid not defined");
		}
	}
	/**
	 * Allows the user to change the parameter from the GUI, if one exists
	 * @param tagName
	 * @param newParam
	 * @throws TransformerException
	 * @throws DOMException
	 * @throws XMLException
	 */
	public void setParameter(String tagName, String newParam) throws TransformerException, DOMException, XMLException {
		getRootElement().getElementsByTagName(tagName).item(0).setTextContent(newParam);
		transform();
	}
	/**
	 * Allows the user to change the color scheme from the GUI
	 * @param newColor
	 * @throws TransformerException
	 * @throws XMLException
	 */
	public void setColor(String newColor) throws TransformerException, XMLException{
		NodeList nodelist = getRootElement().getElementsByTagName(DATA_FIELDS.get(5)).item(0).getChildNodes();
		NodeList statelist = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
		NodeList colorlist = null;
		
		for(int i = 0; i < nodelist.getLength(); i++){
			if(nodelist.item(i).getNodeName().equals(newColor)){
				colorlist = nodelist.item(i).getChildNodes(); 			
			}
		}
		
		for(int j = 0; j < statelist.getLength(); j++){
			for(int k = 0; k < colorlist.getLength(); k++){
				if(statelist.item(j).getTextContent().equals(colorlist.item(k).getNodeName())){
					statelist.item(j).getAttributes().getNamedItem(DATA_FIELDS.get(6)).setTextContent(colorlist.item(k).getTextContent());
				}
			}
		}
		transform();
		
	}
	/**
	 * Returns a String corresponding to the input parameter
	 * 
	 * @param tagName
	 * @return
	 * @throws XMLException 
	 */
	public String getParameter(String tagName) throws XMLException {
		try {
			return getRootElement().getElementsByTagName(tagName).item(0).getTextContent();
		} catch (Exception e) {
			throw new XMLException("Invalid parameter requested: " + tagName);
		}
	}
	/**
	 * Returns the children of a parameter
	 * @param tagName
	 * @return
	 * @throws XMLException
	 */
	public NodeList getParameterChildren(String tagName) throws XMLException {
		try {
			return getRootElement().getElementsByTagName(tagName).item(0).getChildNodes();
		} catch (Exception e) {
			throw new XMLException("Invalid parameter requested: " + tagName);
		}
	}
	/**
	 * Returns the specified attribute of a parameter
	 * @param tagName
	 * @param attribute
	 * @return
	 * @throws XMLException
	 */
	public String getParameterAttribute(String tagName, String attribute) throws XMLException {
		try {
			Element element = (Element) getRootElement().getElementsByTagName(tagName).item(0);
			return getAttribute(element, attribute);
		} catch (Exception e) {
			throw new XMLException("Invalid parameter requested: " + tagName + ", " + attribute);
		}
	}
	/**
	 * Finds a color given a state name
	 * 
	 * @param stateName
	 *            full-length string definition of state name
	 * @exception XMLException
	 *                if state not found in file
	 */
	public String getStateColor(String stateName) throws XMLException {
		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
		for (int i = 0; i < stateDefinitions.getLength(); i++) {
			String currentStateName = stateDefinitions.item(i).getTextContent();
			String color = getAttribute((Element) stateDefinitions.item(i), DATA_FIELDS.get(6));
			if (currentStateName.equals(stateName))
				return color;
		}
		throw new XMLException("State definition not found: " + stateName);
	}
	/**
	 * Finds a state name given a state reference name
	 * 
	 * @param stateRef
	 *            Shortcut state reference value defined in xml file "stateDef"
	 *            tag
	 * @throws XMLException 
	 */
	public String getStateName(String stateRef) throws XMLException {

		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
		for (int i = 0; i < stateDefinitions.getLength(); i++) {
			String currentStateName = stateDefinitions.item(i).getTextContent();
			String ref = getAttribute((Element) stateDefinitions.item(i), DATA_FIELDS.get(7));
			if (ref.equals(stateRef)) {
				return currentStateName;
			}
		}
		throw new XMLException("State definition not found: " + stateRef);
	}
	/**
	 * Gets the attribute of the element
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	private String getAttribute(Element element, String attributeName) {
		return element.getAttribute(attributeName);
	}
	private Element getRootElement() throws XMLException {
		try {
			DOCUMENT_BUILDER.reset();
			xmlDocument = DOCUMENT_BUILDER.parse(info);
			Element root = xmlDocument.getDocumentElement();
			return root;
		} catch (SAXException | IOException e) {
			throw new XMLException("Root element not found in file");
		}
	}
	private int getGridRows() throws NumberFormatException, DOMException, XMLException {
		return Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(0).getTextContent());
	}
	private int getGridColumns() throws NumberFormatException, DOMException, XMLException {
		return Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(1).getTextContent());
	}
	private DocumentBuilder getDocumentBuilder() throws XMLException {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}
	private void transform() throws TransformerException{
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = new StreamResult(info);
		transformer.transform(source, result);
	}

}