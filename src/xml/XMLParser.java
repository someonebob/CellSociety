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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import model.Coordinate;

/**
 * Handles XML Parsing
 * 
 * @author Jesse
 *
 */
public class XMLParser {
	public static final List<String> DATA_FIELDS = Arrays
			.asList(new String[] { "name", "dimension", "state", "parameter", "stateDef" });
	public static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
	public static final ResourceBundle RESOURCES = ResourceBundle.getBundle("resourcefiles/XML");

	private File info;
	private int gridRows, gridColumns;
	private Document xmlDocument;

	/**
	 * Allows the file to be used throughout the class
	 * 
	 * @param setupInfo
	 *            contains the File needed to be parsed
	 */
	public XMLParser(File setupInfo) {
		info = setupInfo;
	}

	public void setParameter(String tagName, String newParam) throws TransformerException {
		getRootElement().getElementsByTagName(tagName).item(0).setTextContent(newParam);
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = new StreamResult(info);
		transformer.transform(source, result);
	}
	
	public void setColor(String newColor) throws TransformerException{
		NodeList nodelist = getRootElement().getElementsByTagName("scheme").item(0).getChildNodes();
		NodeList statelist = getRootElement().getElementsByTagName("stateDef");
		NodeList colorlist = null;
		
		for(int i = 0; i < nodelist.getLength(); i++){
			if(nodelist.item(i).getNodeName().equals(newColor)){
				colorlist = nodelist.item(i).getChildNodes(); 			
			}
		}
		
		for(int j = 0; j < statelist.getLength(); j++){
			for(int k = 0; k < colorlist.getLength(); k++){
				if(statelist.item(j).getTextContent().equals(colorlist.item(k).getNodeName())){
					statelist.item(j).getAttributes().getNamedItem("color").setTextContent(colorlist.item(k).getTextContent());
				}
			}
		}
		
		TransformerFactory transformerfactory = TransformerFactory.newInstance();
		Transformer transformer = transformerfactory.newTransformer();
		DOMSource source = new DOMSource(xmlDocument);
		StreamResult result = new StreamResult(info);
		transformer.transform(source, result);
	}

	/**
	 * Returns a String corresponding to the input parameter
	 * 
	 * @param tagName
	 * @return
	 */
	public String getParameter(String tagName) {
		try {
			return getRootElement().getElementsByTagName(tagName).item(0).getTextContent();
		} catch (Exception e) {
			throw new XMLException(e, "Invalid parameter requested");
		}
	}

	public NodeList getParameters(String tagName) {
		try {
			return getRootElement().getElementsByTagName(tagName).item(0).getChildNodes();
		} catch (Exception e) {
			throw new XMLException(e, "Invalid parameter requested");
		}
	}

	public String getParameterAttribute(String tagName, String attribute) {
		try {
			Element element = (Element) getRootElement().getElementsByTagName(tagName).item(0);
			return getAttribute(element, attribute);
		} catch (Exception e) {
			throw new XMLException(e, "Invalid parameter requested");
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
	public String getStateColor(String stateName) {
		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
		for (int i = 0; i < stateDefinitions.getLength(); i++) {
			String currentStateName = stateDefinitions.item(i).getTextContent();
			String color = getAttribute((Element) stateDefinitions.item(i), "color");
			if (currentStateName.equals(stateName))
				return color;
		}
		throw new XMLException("State definition not found", stateName);
	}
	
	/**
	 * Returns a NodeList of all the initial states of the simulation
	 * 
	 * @return list of states
	 */
	public Map<Coordinate, String> getInitialStates() {
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
			for(int row = 0; row < rows; row++){
				for(int col = 0; col < cols; cols++){					
					stateTextGrid.put(new Coordinate(row, col), getRootElement().getElementsByTagName(DATA_FIELDS.get(4)).item(rand.nextInt(1)).getTextContent());
				}
			}
		}
		
		return stateTextGrid;
	}

	/**
	 * Finds a state name given a state reference name
	 * 
	 * @param stateRef
	 *            Shortcut state reference value defined in xml file "stateDef"
	 *            tag
	 */
	private String getStateName(String stateRef) {
		NodeList stateDefinitions = getRootElement().getElementsByTagName(DATA_FIELDS.get(4));
		for (int i = 0; i < stateDefinitions.getLength(); i++) {
			String currentStateName = stateDefinitions.item(i).getTextContent();
			String ref = getAttribute((Element) stateDefinitions.item(i), "ref");
			if (ref.equals(stateRef)) {
				return currentStateName;
			}
		}
		throw new XMLException("State definition not found", stateRef);
	}
	
	/**
	 * Returns the dimension for rows
	 * 
	 * @return number of rows
	 */
	private int getGridRows() {
		gridRows = Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(0).getTextContent());

		return gridRows;
	}

	/**
	 * Returns the dimension for columns
	 * 
	 * @return number of columns
	 */
	private int getGridColumns() {
		gridColumns = Integer
				.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(1).getTextContent());
		return gridColumns;
	}

	/**
	 * Gets the attribute of the element
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public String getAttribute(Element element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	private Element getRootElement() {
		try {
			DOCUMENT_BUILDER.reset();
			xmlDocument = DOCUMENT_BUILDER.parse(info);
			Element root = xmlDocument.getDocumentElement();
			return root;
		} catch (SAXException | IOException e) {
			throw new XMLException(e);
		}
	}

	/**
	 * Creates document builder with the necessary exceptions
	 * 
	 * @return
	 */
	private static DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}

}