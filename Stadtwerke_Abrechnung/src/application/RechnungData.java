package application;

import javafx.beans.property.SimpleStringProperty;

public class RechnungData {
	public SimpleStringProperty zeitraum_von = new SimpleStringProperty();
	public SimpleStringProperty zeitraum_bis = new SimpleStringProperty();
	public SimpleStringProperty gesamtbetrag = new SimpleStringProperty();

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
	
	public String getGesamtbetrag() {
		return gesamtbetrag.get();
	}

	public void setGesamtbetrag(String gesamtbetrag) {
		this.gesamtbetrag = new SimpleStringProperty(gesamtbetrag);
	}
	
	
}
