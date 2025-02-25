// File: src/Fragenpool.java
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
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.endsWith(":")) {
                    currentLevel = line.substring(0, line.length() - 1).trim();
                } else if (line.startsWith("- Frage")) { // supports both Frage1 and Frage2
                    // Extract question text and answer split by "->"
                    String content = line.substring(line.indexOf(":") + 1).trim();
                    String[] parts = content.split("->");
                    String frageText = parts[0].trim();
                    String antwortText = parts.length > 1 ? parts[1].trim() : "";
                    fragen.add(new Frage(frageText, antwortText, currentLevel));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File src/textfile.txt not found.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    public List<Frage> getFragen() {
        return fragen;
    }
}