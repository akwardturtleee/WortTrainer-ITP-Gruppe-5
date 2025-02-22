package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Nutzer extends JPanel {
    private JTextField nameField, emailField, alterField;
    private JComboBox<String> sprachlevelBox;
    private JButton speichernButton;
    private JRadioButton yesButton, noButton;
    private ButtonGroup schulbesuchGroup;
    private Controller controller;
    private JLabel schulbesuchLabel;

    public Nutzer(Controller controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Alter
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Alter:"), gbc);
        alterField = new JTextField(5);
        gbc.gridx = 1;
        add(alterField, gbc);

        // Sprachlevel
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Sprachlevel:"), gbc);
        String[] levels = {"A1", "A2", "B1", "B2", "C1", "C2"};
        sprachlevelBox = new JComboBox<>(levels);
        gbc.gridx = 1;
        add(sprachlevelBox, gbc);

        // E-Mail
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("E-Mail:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Schulbesuch
        schulbesuchLabel = new JLabel("Ich besuche eine Schule, wo ich aktiv am Deutschunterricht teilnehme.");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(schulbesuchLabel, gbc);

        yesButton= new JRadioButton("Ja");
        noButton = new JRadioButton("Nein");
        schulbesuchGroup = new ButtonGroup();
        schulbesuchGroup.add(yesButton);
        schulbesuchGroup.add(noButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(yesButton);
        radioPanel.add(noButton);

        gbc.gridy = 5;
        add(radioPanel, gbc);

        schulbesuchLabel.setVisible(false);
        radioPanel.setVisible(false);
        sprachlevelBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLevel = (String) sprachlevelBox.getSelectedItem();
                if (selectedLevel.equals("A1") || selectedLevel.equals("A2") || selectedLevel.equals("B1") || selectedLevel.equals("B2")) {
                    schulbesuchLabel.setVisible(true);
                    radioPanel.setVisible(true);
                } else {
                    schulbesuchLabel.setVisible(false);
                    radioPanel.setVisible(false);
                    schulbesuchGroup.clearSelection();
                }
            }
        });

        // Speichern-Button
        speichernButton = new JButton("Speichern");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(speichernButton, gbc);

        speichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speichernNutzer();
            }
        });
    }

    private void speichernNutzer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String alterText = alterField.getText().trim();
        String sprachlevel = (String) sprachlevelBox.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie Ihren Namen ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int alter = Integer.parseInt(alterText);
            if (alter <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie ein gültiges Alter ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige E-Mail-Adresse ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Nutzerdaten erfolgreich gespeichert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.+]+@[\\w-]+\\.[a-z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }
}

