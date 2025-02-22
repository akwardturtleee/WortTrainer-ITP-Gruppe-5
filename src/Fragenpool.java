package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Fragenpool {
    private List <Frage> fragen;

    public Fragenpool() {
        fragen = new ArrayList<>();
        ladeFragenAusDatei("textfile.txt");
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
                    String frageText = line.substring(9).trim();
                    fragen.add(new Frage(frageText, false, currentLevel));
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

    public void frageHinzufuegen(String inhalt, boolean richtig, String frageTyp) {
        fragen.add(new Frage(inhalt, richtig, frageTyp));
    }

    public void frageBearbeiten(String frageID, String neuerInhalt, String neueAntwort) {
        for (Frage frage : fragen) {
            if (frage.getFrageID().equals(frageID)) {
                frage.frageBearbeiten(frageID, neuerInhalt, neueAntwort);
                return;
            }
        }
        System.err.println("Fehler: Frage mit ID " + frageID + " nicht gefunden.");
    }

    public void frageLoeschen(String frageID) {
        fragen.removeIf(frage -> frage.getFrageID().equals(frageID));
    }
}