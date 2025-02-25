// File: src/SettingsManager.java
package src;

public class SettingsManager {
    private static int hangmanFehlversuche = 6; // Default value
    private static String sprachniveau = "A1"; // Default value

    public static int getHangmanFehlversuche() {
        return hangmanFehlversuche;
    }

    public static void setHangmanFehlversuche(int fehlversuche) {
        hangmanFehlversuche = fehlversuche;
    }

    public static String getSprachniveau() {
        return sprachniveau;
    }

    public static void setSprachniveau(String niveau) {
        sprachniveau = niveau;
    }
}