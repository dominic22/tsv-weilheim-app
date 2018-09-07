package com.globalone.tsvweilheimapp.xmladapter;

public class Tabelle {
//NOCH UNBENUTZT WIRD BIS JETZT ALLES UEBER DIE STACKSITE GEMACHT!!

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
