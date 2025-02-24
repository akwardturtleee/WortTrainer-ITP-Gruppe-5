package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Fragenpool {
    private List<Frage> fragen;

    public Fragenpool() {
        fragen = new ArrayList<>();
        ladeFragenAusDatei("src/textfile.txt");
    }

    private void ladeFragenAusDatei(String dateiname) {
        try (BufferedReader br = new BufferedReader(new FileReader(dateiname))) {
            String line;
            String currentLevel = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.endsWith(":")) {
                    currentLevel = line.substring(0, line.length() - 1).trim();
                } else if (line.startsWith("- Frage:")) {
                    String content = line.substring(9).trim();
                    // Split the line by the delimiter "->"
                    String[] parts = content.split("->");
                    String frageText = parts[0].trim();
                    String antwortText = parts.length > 1 ? parts[1].trim() : "";
                    // Create a Frage instance with the cleaned text and current level.
                    fragen.add(new Frage(frageText, antwortText, currentLevel));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fehler: Die Datei " + dateiname + " wurde nicht gefunden.");
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    public List<Frage> getFragen() {
        return fragen;
    }
}