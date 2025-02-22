package src;
import java.util.*;


public class Frage {
	private String frageID;
	private String frageTyp;
	private String inhalt;
	private boolean richtig;
	private int punkte;

	public Frage(String inhalt, boolean richtig, String frageTyp) {
		this.inhalt = inhalt;
		this.richtig = richtig;
		this.frageTyp = frageTyp;
		this.punkte=0;
	}

	public String getInhalt() {
		return inhalt;
	}

	public boolean isRichtig() {
		return richtig;
	}

	public void setRichtig(boolean richtig) {
		this.richtig = richtig;
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	public String getFrageTyp() {
		return frageTyp;
	}

	public void setFrageTyp(String frageTyp) {
		this.frageTyp = frageTyp;
	}

	public String getFrageID() {
		return frageID;
	}

	public void frageHinzufuegen(String inhalt, boolean richtig, String frageTyp) {
		Frage frage = new Frage(inhalt, richtig, frageTyp);

	}
	public void frageBearbeiten(String frageID, String neuerInhalt, String neueAntwort) {
		this.frageID = frageID;
		this.inhalt = neuerInhalt;
		this.richtig = false;
	}
	public void frageLoeschen(String frageID) {

	}
}
