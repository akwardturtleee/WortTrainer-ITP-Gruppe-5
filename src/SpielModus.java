package src;

import java.util.ArrayList;
import java.util.List;

public class SpielModus {
	private String aktuellesWort;
	private int versuche;
	private List<Character> errateneBuchstaben; // Verwende eine Liste von Characters
	private int ungueltigeVersuche;
	private Fragenpool fragenpool;

	public SpielModus() {
		this.errateneBuchstaben = new ArrayList<>(); // Initialisiere die Liste
		this.versuche = 0;
		this.ungueltigeVersuche = 0;
	}

	public void starteSpiel(String wort) {
		this.aktuellesWort = wort;
		this.errateneBuchstaben.clear(); // Leere die Liste der erratenen Buchstaben
		this.versuche = 0;
		this.ungueltigeVersuche = 0;
		System.out.println("Spiel gestartet! Das Wort hat " + aktuellesWort.length() + " Buchstaben.");
	}

	public boolean pruefeBuchstabe(char buchstabe) {
		if (aktuellesWort.toLowerCase().indexOf(buchstabe) >= 0) {
			errateneBuchstaben.add(buchstabe); // Füge den Buchstaben zur Liste hinzu
			return true;
		} else {
			versuche++;
			return false;
		}
	}

	public boolean istWortErraten() {
		for (char buchstabe : aktuellesWort.toLowerCase().toCharArray()) {
			if (!errateneBuchstaben.contains(buchstabe)) {
				return false;
			}
		}
		return true;
	}

	public void zeigeSpielstand() {
		StringBuilder sb = new StringBuilder();
		for (char buchstabe : aktuellesWort.toLowerCase().toCharArray()) {
			if (errateneBuchstaben.contains(buchstabe)) {
				sb.append(buchstabe).append(" ");
			} else {
				sb.append("_ ");
			}
		}
		System.out.println("Aktueller Spielstand: " + sb.toString());
	}

	public boolean spielBeenden() {
		System.out.println("Spiel beendet. Versuche: " + versuche);
		return istWortErraten();
	}
}