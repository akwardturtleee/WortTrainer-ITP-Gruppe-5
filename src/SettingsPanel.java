package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsPanel extends JPanel { //erweitert JPanel und stellt ein Einstellungsmenü bereit.
    private JComboBox<String> sprachlevelBox; // Dropdown zur Auswahl des Sprachniveaus
    private JTextArea textFileEditor; // Textbereich zur Bearbeitung von textfile.txt
    private JTextArea hangmanWordsEditor; // Textbereich zur Bearbeitung von hangman_words.txt

    public SettingsPanel() { // Konstruktor für das Einstellungs-Panel
        setLayout(new BorderLayout(10,10)); // Setzt das Layout auf BorderLayout mit 10 Pixel Abstand

        JLabel titleLabel = new JLabel("Einstellungen", SwingConstants.CENTER); // Überschrift oben im Panel
        titleLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(); // Hauptinhaltspanel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertikale Anordnung
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Panel für das Sprachniveau-Formular
        settingsForm.add(new JLabel("Sprachniveau:"));
        String[] levels = {"A1", "A2", "B1", "B2", "C1", "C2"}; // Definiert die verfügbaren Sprachniveaus
        sprachlevelBox = new JComboBox<>(levels);
        settingsForm.add(sprachlevelBox);
        contentPanel.add(settingsForm);

        JPanel editorPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Panel für die beiden Texteditoren

        JPanel textFilePanel = new JPanel(new BorderLayout()); // Editor für textfile.txt
        textFilePanel.setBorder(BorderFactory.createTitledBorder("Edit textfile.txt"));
        textFileEditor = new JTextArea(10, 40);
        loadFileContent("src/textfile.txt", textFileEditor); // Dateiinhalt laden
        textFilePanel.add(new JScrollPane(textFileEditor), BorderLayout.CENTER);
        editorPanel.add(textFilePanel);

        JPanel hangmanWordsPanel = new JPanel(new BorderLayout()); // Editor für hangman_words.txt
        hangmanWordsPanel.setBorder(BorderFactory.createTitledBorder("Edit hangman_words.txt"));
        hangmanWordsEditor = new JTextArea(10, 40);
        loadFileContent("src/hangman_words.txt", hangmanWordsEditor); // Dateiinhalt laden
        hangmanWordsPanel.add(new JScrollPane(hangmanWordsEditor), BorderLayout.CENTER);
        editorPanel.add(hangmanWordsPanel);

        contentPanel.add(editorPanel);
        add(contentPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Speichern"); // Speichern-Button
        saveButton.addActionListener(e -> saveSettings()); // ActionListener für das Speichern
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadFileContent(String filePath, JTextArea textArea) { // Methode zum Laden des Dateiinhalts in ein JTextArea
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath))); // Dateiinhalt als String einlesen
            textArea.setText(content);
        } catch (IOException e) {
            e.printStackTrace(); // Fehler ausgeben, falls Datei nicht gelesen werden kann
        }
    }

    private void saveSettings() { // Methode zum Speichern der Einstellungen
        String sprachlevel = (String) sprachlevelBox.getSelectedItem(); // Gewähltes Sprachniveau abrufen
        SettingsManager.setSprachniveau(sprachlevel); // Speichert das Sprachniveau in den SettingsManager
        saveFileContent("src/textfile.txt", textFileEditor.getText()); // Speichert den Inhalt des ersten Editors
        saveFileContent("src/hangman_words.txt", hangmanWordsEditor.getText()); // Speichert den Inhalt des zweiten Editors
        JOptionPane.showMessageDialog(this, "Einstellungen wurden erfolgreich gespeichert.", // Zeigt eine Bestätigungsmeldung an
                "Speichern", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveFileContent(String filePath, String content) { // Methode zum Speichern des Inhalts eines JTextArea in eine Datei
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content); // Schreibt den Text in die Datei
        } catch (IOException e) {
            e.printStackTrace(); // Fehler ausgeben, falls Speichern fehlschlägt
        }
    }
}
