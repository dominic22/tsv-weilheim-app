package com.globalone.tsvweilheimapp.xmladapter;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SpielplanPullParser {

	// Spielpan
	static final String KEY_PARENT = "SpielplanChild";
	static final String KEY_DATUM = "Datum";
	static final String KEY_UHRZEIT = "Uhrzeit";
	static final String KEY_HEIM = "Heim";
	static final String KEY_GAST = "Gast";
	static final String KEY_SPIELBERICHT_URL = "BerichtUrl";

	

	public static List<Spielplan> getSpielplanFromFile(Context ctx) {

		// List of StackSites that we will return
		List<Spielplan> stackSites;

		stackSites = new ArrayList<Spielplan>();

		// temp holder for current StackSite while parsing
		Spielplan curStackSite = null;

		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis;
			BufferedReader reader = null;
			
				fis = ctx.openFileInput("Spielplan.xml");
				reader = new BufferedReader(new InputStreamReader(fis));
			
			// point the parser to our file.
			if (reader != null)
				xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();

			// Loop through pull events until we reach END_DOCUMENT
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Get the current tag

				String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
				case XmlPullParser.START_TAG:
					
						if (tagname.equalsIgnoreCase(KEY_PARENT)) {
							// If we are starting a new <site> block we need
							// a new StackSite object to represent it
							curStackSite = new Spielplan();
						} 
					
					break;

				case XmlPullParser.TEXT:
					// grab the current text so we can use it in END_TAG event
					// curText = "  ";
					curText = xpp.getText();

					break;

				case XmlPullParser.END_TAG:

					// letzte WOche

					if (tagname.equalsIgnoreCase(KEY_PARENT)) {
						// if </site> then we are done with current Site
						// add it to the list.
						stackSites.add(curStackSite);

					} else if (tagname.equalsIgnoreCase(KEY_DATUM)) {
						// if </name> use setName() on curSite
						curStackSite.setDatum(curText);
					} 
					else if (tagname.equalsIgnoreCase(KEY_UHRZEIT)) {
						// if </name> use setName() on curSite
						curStackSite.setUhrzeit(curText);
					} 
					 else if (tagname.equalsIgnoreCase(KEY_HEIM)) {
						// if </name> use setName() on curSite
						curStackSite.setHeim(curText);
					} 
					 else if (tagname.equalsIgnoreCase(KEY_GAST)) {
							// if </name> use setName() on curSite
							curStackSite.setGast(curText);
					}
					 else if (tagname.equalsIgnoreCase(KEY_SPIELBERICHT_URL)) {
							// if </name> use setName() on curSite
							curStackSite.setSpielberichtURL(curText);
					}

					break;
				default:
					break;
				}
				// move on to next iteration
				eventType = xpp.next();

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		// return the populated list.
		return stackSites;
	}
}
