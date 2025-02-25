package src;

import java.awt.Desktop;
import java.net.URI;

public class Frage {
	private String frageID;
	private String frageTyp;
	private String inhalt;
	private String richtigeAntwort;
	private int punkte;
	private String imageUrl; // Holds image URL when available

	public Frage(String inhalt, String richtigeAntwort, String frageTyp) {
		// In this rollback we ignore matching IDs and simply parse the question.
		// Remove any "(siehe Bild)" marker if present.
		if (inhalt.contains("(siehe Bild)")) {
			inhalt = inhalt.replace("(siehe Bild)", "").trim();
		}
		// If the question text contains a URL via "siehe Bild:" then extract it.
		if (inhalt.contains("siehe Bild:")) {
			int index = inhalt.indexOf("siehe Bild:");
			String textPart = inhalt.substring(0, index).trim();
			String urlPart = inhalt.substring(index + "siehe Bild:".length()).trim();
			// remove trailing punctuation if any
			if (urlPart.endsWith("?")) {
				urlPart = urlPart.substring(0, urlPart.length()-1).trim();
			}
			this.inhalt = textPart;
			this.imageUrl = urlPart;
		} else {
			this.inhalt = inhalt;
			this.imageUrl = null;
		}
		this.richtigeAntwort = richtigeAntwort;
		this.frageTyp = frageTyp;
		this.punkte = 0;
		this.frageID = null; // Matching IDs are no longer implemented
	}

	public String getFrageID() {
		return frageID;
	}

	public String getFrageTyp() {
		return frageTyp;
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

	public String getImageUrl() {
		return imageUrl;
	}
}