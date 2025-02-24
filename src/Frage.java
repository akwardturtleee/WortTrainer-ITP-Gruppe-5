package src;

public class Frage {
	private String frageID;
	private String frageTyp;
	private String inhalt;
	private String richtigeAntwort;
	private int punkte;
	private String imagePath; // field for image association

	public Frage(String inhalt, String richtigeAntwort, String frageTyp) {
		// Remove marker from displayed text if present.
		if (inhalt.contains("(siehe Bild)")) {
			inhalt = inhalt.replace("(siehe Bild)", "").trim();
		}
		this.inhalt = inhalt;
		this.richtigeAntwort = richtigeAntwort;
		this.frageTyp = frageTyp;
		this.punkte = 0;
		// Create a simple mapping: use the answer as an image filename in the 'images' folder.
		if(richtigeAntwort != null && !richtigeAntwort.isEmpty()){
			this.imagePath = "images/" + richtigeAntwort.toLowerCase() + ".jpg";
		} else {
			this.imagePath = null;
		}
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

	public String getImagePath() {
		return imagePath;
	}
}