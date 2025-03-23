package src;
 /*
  *Eine Klasse zur Verwaltung der Spieleinstellungen.
  *@author Sukhanmol Thabal
  *@version 24-03-2025
 */

public class SettingsManager { // Definiert die Klasse SettingsManager.

    // Speichert die maximale Anzahl an Fehlversuchen für das Hangman-Spiel. Standardwert: 6.
    private static int hangmanFehlversuche = 6;

    // Speichert das Sprachniveau des Spiels. Standardwert: "A1".
    private static String sprachniveau = "A1";

    // Gibt die aktuelle Anzahl der erlaubten Fehlversuche zurück.
    public static int getHangmanFehlversuche() {
        return hangmanFehlversuche;
    }

    // Setzt die Anzahl der erlaubten Fehlversuche auf einen neuen Wert.
    public static void setHangmanFehlversuche(int fehlversuche) {
        hangmanFehlversuche = fehlversuche;
    }

    // Gibt das aktuell eingestellte Sprachniveau zurück.
    public static String getSprachniveau() {
        return sprachniveau;
    }

    // Setzt das Sprachniveau auf einen neuen Wert.
    public static void setSprachniveau(String niveau) {
        sprachniveau = niveau;
    }
}
