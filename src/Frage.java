package src;

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
