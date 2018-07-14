package com.globalone.tsvweilheimapp.xmladapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

public class SpielplanXmlDomParser {

	static final String KEY_SITE = "site";
	static final String KEY_NAME = "name";
	static final String KEY_LINK = "link";
	static final String KEY_ABOUT = "about";
	static final String KEY_IMAGE_URL = "image";

	/*
	 * High level method that will read the xml file and parse it 
	 * into a List of StackSite objects.
	 */
	
	
	public static List<Spielplan> getStackSitesFromFile(Context ctx){
		List<Spielplan> stackSites = new ArrayList<Spielplan>();
		
		String xml = readFile(ctx); // getting XML
		Document doc = getDomElement(xml); // getting DOM element
		 
		NodeList nl = doc.getElementsByTagName(KEY_SITE);
		 
		// looping through all site nodes <site>
		for (int i = 0; i < nl.getLength(); i++) {
			Spielplan curStackSite = new Spielplan();
			Element e = (Element) nl.item(i);
			
			/*curStackSite.setName(getValue(e,KEY_NAME));
			curStackSite.setLink(getValue(e,KEY_LINK));
			curStackSite.setAbout(getValue(e,KEY_ABOUT));
			curStackSite.setImgUrl(getValue(e,KEY_IMAGE_URL));
			*/
			//Log.i("StackSites", curStackSite.getName());
			stackSites.add(curStackSite);
		}
		
		return stackSites;
	}
	
	//Helper method to read the file and return its text.
	private static String readFile(Context ctx){
		String xml = "";
		try {
			FileInputStream fis = ctx.openFileInput("Spielplan.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String line;
			while((line = reader.readLine()) != null){
				xml += line;
				xml += "\n";
			}
			//Log.i("StackSites", xml);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return xml;
	}
	
	//Get a value from an XML Element.
	private static String getValue(Element item, String str) {
	    NodeList n = item.getElementsByTagName(str);
	    return getElementValue(n.item(0));
	}
	
	//Get an Element Value from an XML Node object.
	private static String getElementValue( Node elem ) {
		 StringBuilder value = new StringBuilder();
         Node child;
         if( elem != null){
             if (elem.hasChildNodes()){
                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                     if( child.getNodeType() == Node.TEXT_NODE  ){
                    	 value.append(child.getNodeValue());
                         
                     }
                 }
                 return value.toString();
             }
         }
         return "";
	  } 
	
	//Get dom Document object from raw text.
	private static Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is); 
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
                // return DOM
            return doc;
    }
}