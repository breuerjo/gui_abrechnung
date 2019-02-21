package application;

import javafx.beans.property.SimpleStringProperty;

public class RechnungDataStrom {
	public SimpleStringProperty zeitraum = new SimpleStringProperty();
	public SimpleStringProperty zaehler = new SimpleStringProperty();
	public SimpleStringProperty alt = new SimpleStringProperty();
	public SimpleStringProperty neu = new SimpleStringProperty();
	public SimpleStringProperty differenz = new SimpleStringProperty();
	public SimpleStringProperty faktor = new SimpleStringProperty();
	public SimpleStringProperty menge = new SimpleStringProperty();
	
	
	public String getZeitraum() {
		return zeitraum.get();
	}

	public void setZeitraum(String zeitraum) {
		this.zeitraum = new SimpleStringProperty(zeitraum);
	}
	
	public String getZaehler() {
		return zaehler.get();
	}

	public void setZaehler(String zaehler) {
		this.zaehler = new SimpleStringProperty(zaehler);
	}
	
	public String getAlt() {
		return alt.get();
	}

	public void setAlt(String alt) {
		this.alt = new SimpleStringProperty(alt);
	}
	
	public String getNeu() {
		return neu.get();
	}

	public void setNeu(String neu) {
		this.neu = new SimpleStringProperty(neu);
	}
	
	public String getDifferenz() {
		return differenz.get();
	}
	
	public void setDifferenz(String differenz) {
		this.differenz = new SimpleStringProperty(differenz);
	}
	
	public String getFaktor() {
		return faktor.get();
	}

	public void setFaktor(String faktor) {
		this.faktor = new SimpleStringProperty(faktor);
	}
	
	public String getMenge() {
		return menge.get();
	}

	public void setMenge(String menge) {
		this.menge = new SimpleStringProperty(menge);
	}
	
	
}
