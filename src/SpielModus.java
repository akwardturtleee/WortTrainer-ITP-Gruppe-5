package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class SpielModus extends JPanel implements ActionListener {
	private String wort = "PROGRAMMIEREN"; // Beispielwort (kann später zufällig gewählt werden)
	private Set<Character> gerateneBuchstaben = new HashSet<>();
	private int fehlversuche = 0;
	private final int maxFehlversuche = 6;

	private JLabel wortAnzeige;
	private JLabel fehlversucheLabel;
	private JTextField buchstabenEingabe;
	private JButton ratenButton;
	private JButton neustartButton;

	public SpielModus(Controller controller) {
		setLayout(new BorderLayout());

		// Panel für das Spiel mit GridLayout (3 Zeilen, 1 Spalte)
		JPanel spielPanel = new JPanel(new GridLayout(3, 1, 10, 10));

		// 1. Zeile: Titel
		JLabel titel = new JLabel("🎯 Hangman-Spiel", SwingConstants.CENTER);
		titel.setFont(new Font("Arial", Font.BOLD, 26));
		spielPanel.add(titel);

		// 2. Zeile: Wortanzeige
		wortAnzeige = new JLabel(getAngezeigtesWort(), SwingConstants.CENTER);
		wortAnzeige.setFont(new Font("Arial", Font.BOLD, 32));
		spielPanel.add(wortAnzeige);

		// 3. Zeile: Steuerungsbereich (Eingabe + Button)
		JPanel inputPanel = new JPanel(new FlowLayout());
		buchstabenEingabe = new JTextField(2);
		buchstabenEingabe.setFont(new Font("Arial", Font.PLAIN, 20));
		ratenButton = new JButton("🔍 Raten");
		ratenButton.setFont(new Font("Arial", Font.BOLD, 18));
		ratenButton.addActionListener(this);

		inputPanel.add(new JLabel("Buchstabe:"));
		inputPanel.add(buchstabenEingabe);
		inputPanel.add(ratenButton);

		spielPanel.add(inputPanel);

		add(spielPanel, BorderLayout.CENTER);

		// Unteres Panel für Fehlversuche und Neustart
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		fehlversucheLabel = new JLabel("❌ Fehlversuche: 0 / " + maxFehlversuche, SwingConstants.CENTER);
		fehlversucheLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		bottomPanel.add(fehlversucheLabel);

		neustartButton = new JButton("🔄 Neustart");
		neustartButton.setFont(new Font("Arial", Font.BOLD, 18));
		neustartButton.addActionListener(e -> spielZurücksetzen());
		bottomPanel.add(neustartButton);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eingabe = buchstabenEingabe.getText().toUpperCase();

		if (eingabe.length() != 1 || !Character.isLetter(eingabe.charAt(0))) {
			JOptionPane.showMessageDialog(this, "⚠ Bitte gib einen einzelnen Buchstaben ein!");
			return;
		}

		char buchstabe = eingabe.charAt(0);
		if (gerateneBuchstaben.contains(buchstabe)) {
			JOptionPane.showMessageDialog(this, "⚠ Dieser Buchstabe wurde bereits geraten!");
			return;
		}

		gerateneBuchstaben.add(buchstabe);

		if (!wort.contains(String.valueOf(buchstabe))) {
			fehlversuche++;
		}

		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("❌ Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);

		// Spielende prüfen
		if (fehlversuche >= maxFehlversuche) {
			JOptionPane.showMessageDialog(this, "💀 Verloren! Das Wort war: " + wort);
			spielZurücksetzen();
		} else if (!getAngezeigtesWort().contains("_")) {
			JOptionPane.showMessageDialog(this, "🎉 Gewonnen! Das Wort war: " + wort);
			spielZurücksetzen();
		}

		buchstabenEingabe.setText("");
	}

	private String getAngezeigtesWort() {
		StringBuilder sb = new StringBuilder();
		for (char c : wort.toCharArray()) {
			if (gerateneBuchstaben.contains(c)) {
				sb.append(c).append(" ");
			} else {
				sb.append("_ ");
			}
		}
		return sb.toString().trim();
	}

	private void spielZurücksetzen() {
		gerateneBuchstaben.clear();
		fehlversuche = 0;
		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("❌ Fehlversuche: 0 / " + maxFehlversuche);
	}
}
