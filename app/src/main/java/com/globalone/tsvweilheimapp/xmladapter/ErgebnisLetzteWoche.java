package com.globalone.tsvweilheimapp.xmladapter;

public class ErgebnisLetzteWoche {

	//NOCH UNBENUTZT WIRD BIS JETZT ALLES UEBER DIE STACKSITE GEMACHT!!
	
	String Liga;
	String Datum;
	String VereinHeim;
	String VereinGast;
	String ToreHeim;
	String ToreGast;
	String SpielberichtURL;

	
	public String getLiga() {
		return Liga;
	}
	public void setLiga(String liga) {
		Liga = liga;
	}
	public String getDatum() {
		return Datum;
	}
	public void setDatum(String datum) {
		Datum = datum;
	}
	public String getVereinHeim() {
		return VereinHeim;
	}
	public void setVereinHeim(String vereinHeim) {
		VereinHeim = vereinHeim;
	}
	public String getVereinGast() {
		return VereinGast;
	}
	public void setVereinGast(String vereinGast) {
		VereinGast = vereinGast;
	}
	public String getToreHeim() {
		return ToreHeim;
	}
	public void setToreHeim(String toreHeim) {
		ToreHeim = toreHeim;
	}
	public String getToreGast() {
		return ToreGast;
	}
	public void setToreGast(String toreGast) {
		ToreGast = toreGast;
	}
	public String getSpielberichtURL() {
		return SpielberichtURL;
	}
	public void setSpielberichtURL(String spielberichtURL) {
		SpielberichtURL = spielberichtURL;
	}
}
