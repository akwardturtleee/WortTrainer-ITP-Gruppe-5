package src;
import java.util.*;

public class Frage {
	private String frageID;
	private String frageTyp;
	private String inhalt;
	private String richtigeAntwort; // New field for the correct answer
	private int punkte;

	public Frage(String inhalt, String richtigeAntwort, String frageTyp) {
		this.inhalt = inhalt;
		this.richtigeAntwort = richtigeAntwort;
		this.frageTyp = frageTyp;
		this.punkte = 0;
	}

	public String getInhalt() {
		return inhalt;
	}

	public String getRichtigeAntwort() {
		return richtigeAntwort;
	}

	public void setRichtigeAntwort(String richtigeAntwort) {
		this.richtigeAntwort = richtigeAntwort;
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
}