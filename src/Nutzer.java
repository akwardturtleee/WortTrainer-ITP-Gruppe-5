package src;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;


public class Nutzer extends JFrame {
    private String name;
    private int alter;
    private String sprachlevel;
    private String email;
    private boolean schulbesuch;
    private String[] levelOptionen;


    public Nutzer() {
        // Name abfragen
        name = JOptionPane.showInputDialog("Bitte geben Sie hier Ihren Namen ein:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte Name eingeben, es darf nicht leer sein!");
            return;
        }
        JOptionPane.showMessageDialog(null, "Willkommen " + name + "!");

        // Alter abfragen
        String alterEingabe = JOptionPane.showInputDialog("Bitte geben Sie hier Ihr Alter an:");
        if (alterEingabe == null || alterEingabe.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Alter an!");
            return;
        }
        try {
            alter = Integer.parseInt(alterEingabe);
            if (alter <= 0) {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie ein gültiges Alter ein! :");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Zahl ein! :");
            return;
        }
        JOptionPane.showMessageDialog(null, "Sie sind " + alter + " Jahre alt."); // Alter korrekt anzeigen

        // Sprachlevel abfragen
        String sprachlevelEingabe = JOptionPane.showInputDialog("Bitte geben Sie Ihr jetziges Sprachlevel an (A1 - C2):");
        if (sprachlevelEingabe == null || sprachlevelEingabe.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Sprachlevel an! :");
            return;
        }
        sprachlevelEingabe = sprachlevelEingabe.trim().toUpperCase();

        if (sprachlevelEingabe.equals("A1") || sprachlevelEingabe.equals("A2") || sprachlevelEingabe.equals("B1") || sprachlevelEingabe.equals("B2") || sprachlevelEingabe.equals("C1") || sprachlevelEingabe.equals("C2")) {
            sprachlevel = sprachlevelEingabe; // gültig
        } else {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein gültiges Sprachlevel an:\nA1\nA2\nB1\nB2\nC1\nC2");
            return;
        }
        // E-Mail
        String email = JOptionPane.showInputDialog("Bitte geben Sie Ihre E-Mail-Adresse ein! :");
        if (email == null || !isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige E-Mail-Adresse ein.");
        } else {
            JOptionPane.showMessageDialog(null, "Die E-Mail-Adresse ist gültig.");
        }

        // Schulbesuch bei A1-B1 abfragen
        if (sprachlevel.equals("A1") || sprachlevel.equals("A2") || sprachlevel.equals("B1")) {
            int schulAntwort = JOptionPane.showConfirmDialog(
                    null,
                    "Gehen Sie noch zur Schule, um Ihre Deutschkenntnisse zu verbessern?",
                    "Schulbesuch",
                    JOptionPane.YES_NO_OPTION
            );
            schulbesuch = (schulAntwort == JOptionPane.YES_OPTION);
        }
    }
    // E-Mail-Validierung
    private static boolean isValidEmail (String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }
}
