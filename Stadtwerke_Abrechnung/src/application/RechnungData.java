package application;

import javafx.beans.property.SimpleStringProperty;

public class RechnungData {
	public SimpleStringProperty nummer = new SimpleStringProperty();
	public SimpleStringProperty einstellungen = new SimpleStringProperty();
	public SimpleStringProperty zeitraum_von = new SimpleStringProperty();
	public SimpleStringProperty zeitraum_bis = new SimpleStringProperty();
	public SimpleStringProperty menge_strom = new SimpleStringProperty();
	public SimpleStringProperty menge_erdgas = new SimpleStringProperty();
	public SimpleStringProperty menge_wasser = new SimpleStringProperty();
	public SimpleStringProperty menge_abwasser = new SimpleStringProperty();
	public SimpleStringProperty gesamtbetrag = new SimpleStringProperty();
	
	public String getNummer() {
		return nummer.get();
	}

	public void setNummer(String nummer) {
		this.nummer = new SimpleStringProperty(nummer);
	}
	
	public String getEinstellungen() {
		return einstellungen.get();
	}

	public void setEinstellungen(String einstellungen) {
		this.einstellungen = new SimpleStringProperty(einstellungen);
	}
	
	public String getZeitraum_von() {
		return zeitraum_von.get();
	}

	public void setZeitraum_von(String zeitraum_von) {
		this.zeitraum_von = new SimpleStringProperty(zeitraum_von);
	}
	
	public String getZeitraum_bis() {
		return zeitraum_bis.get();
	}

	public void setZeitraum_bis(String zeitraum_bis) {
		this.zeitraum_bis = new SimpleStringProperty(zeitraum_bis);
	}
	
	public String getMenge_strom() {
		return menge_strom.get();
	}
	
	public void setMenge_strom(String menge_strom) {
		this.menge_strom = new SimpleStringProperty(menge_strom);
	}
	
	public String getMenge_erdgas() {
		return menge_erdgas.get();
	}

	public void setMenge_erdgas(String menge_erdgas) {
		this.menge_erdgas = new SimpleStringProperty(menge_erdgas);
	}
	
	public String getMenge_wasser() {
		return menge_wasser.get();
	}

	public void setMenge_wasser(String menge_wasser) {
		this.menge_wasser = new SimpleStringProperty(menge_wasser);
	}
	
	public String getMenge_abwasser() {
		return menge_abwasser.get();
	}
	
	public void setMenge_abwasser(String menge_abwasser) {
		this.menge_abwasser = new SimpleStringProperty(menge_abwasser);
	}
	
	
	public String getGesamtbetrag() {
		return gesamtbetrag.get();
	}

	public void setGesamtbetrag(String gesamtbetrag) {
		this.gesamtbetrag = new SimpleStringProperty(gesamtbetrag);
	}
	
	
}
