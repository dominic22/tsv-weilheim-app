package tsvweilheimapp.globalone.com.tsvweilheim.xmladapter;


/*
 * Data object that holds all of our information about a StackExchange Site.
 */
public class StackSite {
	
	String Liga;
	String Datum;
	String VereinHeim;
	String VereinGast;
	String ToreHeim;
	String ToreGast;
	
	
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

	
	
	
	
	//Tabelle
	String Platz;
	String Verein;
	String Spiele;
	String Siege;
	String Unentschieden;
	String Niederlagen;
	String ToreGeschossen;
	String ToreErhalten;
	String PunktePlus;
	String PunkteMinus;
	
	public String getPunktePlus() {
		return PunktePlus;
	}
	public void setPunktePlus(String punktePlus) {
		PunktePlus = punktePlus;
	}
	public String getPunkteMinus() {
		return PunkteMinus;
	}
	public void setPunkteMinus(String punkteMinus) {
		PunkteMinus = punkteMinus;
	}
	private String link;
	
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPlatz() {
		return Platz;
	}
	public void setPlatz(String platz) {
		Platz = platz;
	}
	public String getVerein() {
		return Verein;
	}
	public void setVerein(String verein) {
		Verein = verein;
	}
	public String getSpiele() {
		return Spiele;
	}
	public void setSpiele(String spiele) {
		Spiele = spiele;
	}
	public String getSiege() {
		return Siege;
	}
	public void setSiege(String siege) {
		Siege = siege;
	}
	public String getUnentschieden() {
		return Unentschieden;
	}
	public void setUnentschieden(String unentschieden) {
		Unentschieden = unentschieden;
	}
	public String getNiederlagen() {
		return Niederlagen;
	}
	public void setNiederlagen(String niederlagen) {
		Niederlagen = niederlagen;
	}
	public String getToreGeschossen() {
		return ToreGeschossen;
	}
	public void setToreGeschossen(String toreGeschossen) {
		ToreGeschossen = toreGeschossen;
	}
	public String getToreErhalten() {
		return ToreErhalten;
	}
	public void setToreErhalten(String toreErhalten) {
		ToreErhalten = toreErhalten;
	}
	
	
}

/*	private String name;
	private String link;
	private String about;
	private String imgUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "StackSite [name=" + name + ", link=" + link + ", about="
				+ about + ", imgUrl=" + imgUrl + "]";
	}
}*/
