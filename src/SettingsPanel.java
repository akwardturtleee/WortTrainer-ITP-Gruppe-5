// File: src/SettingsPanel.java
package src;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsPanel extends JPanel {
    private JComboBox<String> sprachlevelBox;
    private JTextArea textFileEditor;
    private JTextArea hangmanWordsEditor;

    public SettingsPanel() {
        setLayout(new BorderLayout(10,10));

        // Title at the top
        JLabel titleLabel = new JLabel("Einstellungen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Content panel for form and editors
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Settings form panel
        JPanel settingsForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        settingsForm.add(new JLabel("Sprachniveau:"));
        String[] levels = {"A1", "A2", "B1", "B2", "C1", "C2"};
        sprachlevelBox = new JComboBox<>(levels);
        settingsForm.add(sprachlevelBox);
        contentPanel.add(settingsForm);

        // Editors panel
        JPanel editorPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Editor for textfile.txt
        JPanel textFilePanel = new JPanel(new BorderLayout());
        textFilePanel.setBorder(BorderFactory.createTitledBorder("Edit textfile.txt"));
        textFileEditor = new JTextArea(10, 40);
        loadFileContent("src/textfile.txt", textFileEditor);
        textFilePanel.add(new JScrollPane(textFileEditor), BorderLayout.CENTER);
        editorPanel.add(textFilePanel);

        // Editor for hangman_words.txt
        JPanel hangmanWordsPanel = new JPanel(new BorderLayout());
        hangmanWordsPanel.setBorder(BorderFactory.createTitledBorder("Edit hangman_words.txt"));
        hangmanWordsEditor = new JTextArea(10, 40);
        loadFileContent("src/hangman_words.txt", hangmanWordsEditor);
        hangmanWordsPanel.add(new JScrollPane(hangmanWordsEditor), BorderLayout.CENTER);
        editorPanel.add(hangmanWordsPanel);

        contentPanel.add(editorPanel);
        add(contentPanel, BorderLayout.CENTER);

        // Save button at the bottom
        JButton saveButton = new JButton("Speichern");
        saveButton.addActionListener(e -> saveSettings());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadFileContent(String filePath, JTextArea textArea) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            textArea.setText(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSettings() {
        String sprachlevel = (String) sprachlevelBox.getSelectedItem();
        SettingsManager.setSprachniveau(sprachlevel);
        saveFileContent("src/textfile.txt", textFileEditor.getText());
        saveFileContent("src/hangman_words.txt", hangmanWordsEditor.getText());
    }

    private void saveFileContent(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}