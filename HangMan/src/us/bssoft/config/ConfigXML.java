package us.bssoft.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.R.bool;

import java.util.ArrayList;

public class ConfigXML extends DefaultHandler{
	boolean currentElement = false;
    String currentValue = "";
    
    int BestLevel;//, CurrentLevel;
    public int getBestLevel() {
		return this.BestLevel;
	}

 
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
 
        currentElement = true;
 
        
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
 
        currentElement = false;
        if (qName.equalsIgnoreCase("BestLevel"))
        	BestLevel = Integer.parseInt(currentValue.trim());
        currentValue = "";
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
 
        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }
    }
}
