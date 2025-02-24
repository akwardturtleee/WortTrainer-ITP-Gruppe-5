package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Fragenpool {
    private List<Frage> fragen;

    public Fragenpool() {
        fragen = new ArrayList<>();
        ladeFragenAusDatei();
    }

    private void ladeFragenAusDatei() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/textfile.txt"))) {
            String line;
            String currentLevel = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                if (line.endsWith(":")) {
                    currentLevel = line.substring(0, line.length() - 1).trim();
                } else if (line.startsWith("- Frage:")) {
                    String frageText = line.substring(9).trim();
                    String antwortText = br.readLine().substring(10).trim(); // Read the next line for the answer
                    fragen.add(new Frage(frageText, antwortText, currentLevel));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fehler: Die Datei " + "src/textfile.txt" + " wurde nicht gefunden.");
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