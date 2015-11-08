package us.bssoft.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

public class DataXML extends DefaultHandler{
	boolean currentElement = false;
    String currentValue = "";
    
    int TotalLevel;
    Data data;
    ArrayList<Data> list;
 
    public int getTotalLevel() {
		return this.TotalLevel;
	}
    
    public ArrayList<Data> getList() {
        return list;
    }
 
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
 
        currentElement = true;
 
        if (qName.equals("Data")) {
            list = new ArrayList<Data>();
        } else if (qName.equals("item")) {
            data = new Data();
        }
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
 
        currentElement = false;
        if (qName.equalsIgnoreCase("TotalLevel"))
        	TotalLevel = Integer.parseInt(currentValue.trim());
        else if (qName.equalsIgnoreCase("name"))
        	data.setName(currentValue.trim());
        else if (qName.equalsIgnoreCase("level"))
        	data.setLevel(Integer.parseInt(currentValue.trim()));
        else if (qName.equalsIgnoreCase("item"))
            list.add(data);
        currentValue = "";
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
 
        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }
    }
}
