package xml;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	private File info;
	private String ruleName;
	private int gridRows, gridColumns;
	private String parameter;
	private DocumentBuilder documentBuilder = getDocumentBuilder();
	private Document xmlDocument;
	private NodeList stateList;
	private List<String> dataFields = Arrays.asList(new String[] {"name", "dimension", "state"});
	
	public XMLParser(File setupInfo){
		info = setupInfo;
	}
	
	public String getRuleName(){
		ruleName = getAttribute(getRootElement(), dataFields.get(0));
		
		return ruleName;
	}
	
	public int getGridRows(){
		gridRows = Integer.parseInt(getRootElement().getElementsByTagName(dataFields.get(1)).item(0).getTextContent());
		
		return gridRows;
	}
	
	public int getGridColumns(){
		gridColumns = Integer.parseInt(getRootElement().getElementsByTagName(dataFields.get(1)).item(1).getTextContent());

		return gridColumns;
	}
	
	public NodeList getInitialStates(){
		stateList = getRootElement().getElementsByTagName(dataFields.get(2));

		return stateList;
	}
	
	public String getParameters(String tagName){
		parameter = getRootElement().getElementsByTagName(tagName).item(0).getTextContent();
		
		return parameter;
	}
	
	private Element getRootElement () {
        try {
            documentBuilder.reset();
            xmlDocument = documentBuilder.parse(info);
            Element root = xmlDocument.getDocumentElement();
            return root;
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }
	
	private String getAttribute (Element element, String attributeName) {
        return element.getAttribute(attributeName);
    }
	
	private String getTextValue (Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }
	
	private static DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }

}
