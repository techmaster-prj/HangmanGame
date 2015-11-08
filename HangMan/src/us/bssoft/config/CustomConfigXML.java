package us.bssoft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class CustomConfigXML {
	int bestLevel;
	Context context;
	public CustomConfigXML(Context c) {
		context = c;
		try {
        	File file = new File(context.getFilesDir(),"config.xml");
        	if(!file.exists()){
        		file.createNewFile();
        		FileOutputStream fileos= context.openFileOutput("config.xml", Context.MODE_PRIVATE);
    			XmlSerializer xmlSerializer = Xml.newSerializer();              
    			StringWriter writer = new StringWriter();
    			xmlSerializer.setOutput(writer);
    			xmlSerializer.startDocument("UTF-8",true);
    			xmlSerializer.startTag(null, "Config");
    			xmlSerializer.startTag(null, "BestLevel");
    			xmlSerializer.text("1");
    			xmlSerializer.endTag(null,"BestLevel");      
    			xmlSerializer.endTag(null, "Config");
    			xmlSerializer.endDocument();
    			xmlSerializer.flush();
    			String dataWrite=writer.toString();
    			fileos.write(dataWrite.getBytes());
    			fileos.close();
        	}
        	else{
        		readXML();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getBestLevel() {
		return bestLevel;
	}
	
	public void readXML() {
        try {
        	FileInputStream is = context.openFileInput("config.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ConfigXML myXMLHandler = new ConfigXML();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
            bestLevel = myXMLHandler.getBestLevel();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void writeXML(int n) {
		if(n > bestLevel)
			bestLevel = n;
		try {
			FileOutputStream fileos= context.openFileOutput("config.xml", Context.MODE_PRIVATE);
			XmlSerializer xmlSerializer = Xml.newSerializer();              
			StringWriter writer = new StringWriter();
			xmlSerializer.setOutput(writer);
			xmlSerializer.startDocument("UTF-8",true);
			xmlSerializer.startTag(null, "Config");
			xmlSerializer.startTag(null, "BestLevel");
			xmlSerializer.text(""+bestLevel);
			xmlSerializer.endTag(null,"BestLevel");           
			xmlSerializer.endTag(null, "Config");
			xmlSerializer.endDocument();
			xmlSerializer.flush();
			String dataWrite=writer.toString();
			fileos.write(dataWrite.getBytes());
			fileos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
