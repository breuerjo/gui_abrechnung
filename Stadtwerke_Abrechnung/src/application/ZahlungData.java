package application;

import javafx.beans.property.SimpleStringProperty;

public class ZahlungData {
    public SimpleStringProperty menge_strom = new SimpleStringProperty();
    public SimpleStringProperty menge_erdgas = new SimpleStringProperty();
    public SimpleStringProperty menge_wasser = new SimpleStringProperty();
    public SimpleStringProperty menge_abwasser = new SimpleStringProperty();
    public SimpleStringProperty zeitraum = new SimpleStringProperty();
    
	public SimpleStringProperty getMenge_strom() {
		return menge_strom;
	}
	public void setMenge_strom(SimpleStringProperty menge_strom) {
		this.menge_strom = menge_strom;
	}
	public SimpleStringProperty getMenge_erdgas() {
		return menge_erdgas;
	}
	public void setMenge_erdgas(SimpleStringProperty menge_erdgas) {
		this.menge_erdgas = menge_erdgas;
	}
	public SimpleStringProperty getMenge_wasser() {
		return menge_wasser;
	}
	public void setMenge_wasser(SimpleStringProperty menge_wasser) {
		this.menge_wasser = menge_wasser;
	}
	public SimpleStringProperty getMenge_abwasser() {
		return menge_abwasser;
	}
	public void setMenge_abwasser(SimpleStringProperty menge_abwasser) {
		this.menge_abwasser = menge_abwasser;
	}
	public SimpleStringProperty getZeitraum() {
		return zeitraum;
	}
	public void setZeitraum(SimpleStringProperty zeitraum) {
		this.zeitraum = zeitraum;
	}
    
    
}
