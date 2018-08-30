package tsvweilheimapp.globalone.com.tsvweilheim.xmladapter;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SitesXmlPullParser {

	// Letzte Woche
	static final String KEY_LETZTEWOCHE = "LetzteWoche";
	static final String KEY_LIGA = "Liga";
	static final String KEY_DATUM = "Datum";
	static final String KEY_VEREINHEIM = "VereinHeim";
	static final String KEY_VEREINGAST = "VereinGast";
	static final String KEY_TOREHEIM = "ToreHeim";
	static final String KEY_TOREGAST = "ToreGast";
	static final String KEY_SPIELBERICHT_URL = "BerichtUrl";

	// Tabelle
	static final String KEY_TABELLE = "TabelleUngerade";
	static final String KEY_TABELLE2 = "TabelleGerade";
	static final String KEY_PLATZ = "Platz";
	static final String KEY_VEREIN = "Verein";
	static final String KEY_SPIELE = "Spiele";
	static final String KEY_SIEGE = "Siege";

	static final String KEY_UNENTSCHIEDEN = "Unentschieden";
	static final String KEY_NIEDERLAGEN = "Niederlagen";
	static final String KEY_TOREGESCH = "ToreGeschossen";
	static final String KEY_TOREERHALTEN = "ToreErhalten";
	static final String KEY_PUNKTEPLUS = "PunktePlus";
	static final String KEY_PUNKTEMINUS = "PunkteMinus";
	static int lastIndex=0;

	public static List<StackSite> getStackSitesFromFile(Context ctx, int laufVar) {

		// List of StackSites that we will return
		List<StackSite> stackSites;

		stackSites = new ArrayList<StackSite>();

		// temp holder for current StackSite while parsing
		StackSite curStackSite = null;

		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis;
			BufferedReader reader = null;
			if (laufVar == 0) {
				fis = ctx.openFileInput("StackSites.xml");
				reader = new BufferedReader(new InputStreamReader(fis));
			} else if (laufVar == 1) {
				fis = ctx.openFileInput("StackSitesLetzteWoche.xml");
				reader = new BufferedReader(new InputStreamReader(fis));
			}
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
					if (laufVar == 0) {
						if (tagname.equalsIgnoreCase(KEY_TABELLE)) {
							// If we are starting a new <site> block we need
							// a new StackSite object to represent it
							curStackSite = new StackSite();
						} else if (tagname.equalsIgnoreCase(KEY_TABELLE2)) {
							// If we are starting a new <site> block we need
							// a new StackSite object to represent it
							curStackSite = new StackSite();
						}
					} else if (laufVar == 1) {
						if (tagname.equalsIgnoreCase(KEY_LETZTEWOCHE)) {
							// If we are starting a new <site> block we need
							// a new StackSite object to represent it
							curStackSite = new StackSite();

						}
					}

					if (xpp.isEmptyElementTag() && laufVar == 1)
						curText = "0".toString();
					else if (xpp.isEmptyElementTag() && laufVar == 0){
						
						curText = " ";//String.valueOf(lastIndex);//stackSites.size());
					}

					break;

				case XmlPullParser.TEXT:
					// grab the current text so we can use it in END_TAG event
					// curText = "  ";
					curText = xpp.getText();

					break;

				case XmlPullParser.END_TAG:

					// letzte WOche

					if (tagname.equalsIgnoreCase(KEY_LETZTEWOCHE)) {
						// if </site> then we are done with current Site
						// add it to the list.
						stackSites.add(curStackSite);

					} else if (tagname.equalsIgnoreCase(KEY_LIGA)) {
						// if </name> use setName() on curSite
						curStackSite.setLiga(curText);
					} else if (tagname.equalsIgnoreCase(KEY_DATUM)) {
						// if </name> use setName() on curSite
						curStackSite.setDatum(curText);
					} else if (tagname.equalsIgnoreCase(KEY_VEREINHEIM)) {
						// if </name> use setName() on curSite
						curStackSite.setVereinHeim(curText);
					} else if (tagname.equalsIgnoreCase(KEY_VEREINGAST)) {
						// if </name> use setName() on curSite
						curStackSite.setVereinGast(curText);
					} else if (tagname.equalsIgnoreCase(KEY_TOREHEIM)) {
						// if </name> use setName() on curSite
						curStackSite.setToreHeim(curText);
					} else if (tagname.equalsIgnoreCase(KEY_TOREGAST)) {
						// if </name> use setName() on curSite
						curStackSite.setToreGast(curText);
					} else if (tagname.equalsIgnoreCase(KEY_SPIELBERICHT_URL)) {
						// if </name> use setName() on curSite
						curStackSite.setSpielberichtURL(curText);
					}

					// Tabelle
					if (tagname.equalsIgnoreCase(KEY_TABELLE)) {
						// if </site> then we are done with current Site
						// add it to the list.
						stackSites.add(curStackSite);
					}
					if (tagname.equalsIgnoreCase(KEY_TABELLE2)) {
						// if </site> then we are done with current Site
						// add it to the list.
						stackSites.add(curStackSite);
					}

					else if (tagname.equalsIgnoreCase(KEY_PLATZ)) {
						// if </name> use setName() on curSite
						curStackSite.setPlatz(curText);
					} else if (tagname.equalsIgnoreCase(KEY_VEREIN)) {
						// if </link> use setLink() on curSite
						curStackSite.setVerein(curText);
					} else if (tagname.equalsIgnoreCase(KEY_SPIELE)) {
						// if </about> use setAbout() on curSite
						curStackSite.setSpiele(curText);
					} else if (tagname.equalsIgnoreCase(KEY_SIEGE)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setSiege(curText);
					} else if (tagname.equalsIgnoreCase(KEY_UNENTSCHIEDEN)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setUnentschieden(curText);
					} else if (tagname.equalsIgnoreCase(KEY_NIEDERLAGEN)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setNiederlagen(curText);
					} else if (tagname.equalsIgnoreCase(KEY_TOREGESCH)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setToreGeschossen(curText);
					} else if (tagname.equalsIgnoreCase(KEY_TOREERHALTEN)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setToreErhalten(curText);
					} else if (tagname.equalsIgnoreCase(KEY_PUNKTEPLUS)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setPunktePlus(curText);
					} else if (tagname.equalsIgnoreCase(KEY_PUNKTEMINUS)) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setPunkteMinus(curText);
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
