package tsvweilheimapp.globalone.com.tsvweilheim.xmladapter;

public class Spielplan {

	String Datum;
	String Uhrzeit;
	String Heim;
	String Gast;
	String SpielberichtURL;

	public String getDatum() {
		return Datum;
	}
	public void setDatum(String datum) {
		Datum = datum;
	}
	public String getUhrzeit() {
		return Uhrzeit;
	}
	public void setUhrzeit(String uhrzeit) {
		Uhrzeit = uhrzeit;
	}
	public String getHeim() {
		return Heim;
	}
	public void setHeim(String heim) {
		Heim = heim;
	}
	public String getGast() {
		return Gast;
	}
	public void setGast(String gast) {
		Gast = gast;
	}
	public String getSpielberichtURL() {
		return SpielberichtURL;
	}
	public void setSpielberichtURL(String spielberichtURL) {
		SpielberichtURL = spielberichtURL;
	}
}
