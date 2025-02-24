package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class SpielModus extends JPanel implements ActionListener {
	private Controller controller;
	private String wort = "PROGRAMMIEREN";  // Das Wort, das der Benutzer erraten muss
	private Set<Character> gerateneBuchstaben = new HashSet<>();  // Set für bereits geratene Buchstaben
	private int fehlversuche = 0;  // Fehlversuche des Benutzers
	private final int maxFehlversuche = 6;  // Maximale Anzahl an Fehlversuchen

	// GUI-Komponenten
	private JLabel wortAnzeige;
	private JLabel fehlversucheLabel;
	private JTextField buchstabenEingabe;
	private JButton ratenButton;
	private JButton neustartButton;
	private JPanel hangmanPanel;

	public SpielModus(Controller controller) {
		this.controller = controller;
		setLayout(new BorderLayout(10, 10));

		// Panel für das Spiel mit GridLayout (3 Zeilen, 1 Spalte)
		JPanel spielPanel = new JPanel(new GridLayout(3, 1, 10, 10));

		// 1. Zeile: Titel
		JLabel titel = new JLabel("Hangman-Spiel", SwingConstants.CENTER);
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
		ratenButton = new JButton("Raten");
		ratenButton.setFont(new Font("Arial", Font.BOLD, 18));
		ratenButton.addActionListener(this);

		inputPanel.add(new JLabel("Buchstabe:"));
		inputPanel.add(buchstabenEingabe);
		inputPanel.add(ratenButton);

		spielPanel.add(inputPanel);

		add(spielPanel, BorderLayout.CENTER);

		// Unteres Panel für Fehlversuche und Neustart
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		fehlversucheLabel = new JLabel("Fehlversuche: 0 / 6", SwingConstants.CENTER);
		fehlversucheLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		bottomPanel.add(fehlversucheLabel);

		neustartButton = new JButton("Neustart");
		neustartButton.setFont(new Font("Arial", Font.BOLD, 18));
		neustartButton.addActionListener(e -> spielZurücksetzen());
		bottomPanel.add(neustartButton);

		add(bottomPanel, BorderLayout.SOUTH);

		// Panel für den Hangman-Zeichenbereich
		hangmanPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				zeichneHangman(g, fehlversuche);
			}
		};
		hangmanPanel.setPreferredSize(new Dimension(200, 300));  // Größe des Panels angepasst
		add(hangmanPanel, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eingabe = buchstabenEingabe.getText().toUpperCase();

		if (eingabe.length() != 1 || !Character.isLetter(eingabe.charAt(0))) {
			JOptionPane.showMessageDialog(this, "Bitte gib einen einzelnen Buchstaben ein!");
			return;
		}

		char buchstabe = eingabe.charAt(0);
		if (gerateneBuchstaben.contains(buchstabe)) {
			JOptionPane.showMessageDialog(this, "Dieser Buchstabe wurde bereits geraten!");
			return;
		}

		gerateneBuchstaben.add(buchstabe);

		if (!wort.contains(String.valueOf(buchstabe))) {
			fehlversuche++;
		}

		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);

		// Hangman-Grafik aktualisieren
		hangmanPanel.repaint();

		// Spielende prüfen
		if (fehlversuche >= maxFehlversuche) {
			JOptionPane.showMessageDialog(this, "Verloren! Das Wort war: " + wort);
			spielZurücksetzen();
		} else if (!getAngezeigtesWort().contains("_")) {
			JOptionPane.showMessageDialog(this, "Gewonnen! Das Wort war: " + wort);
			spielZurücksetzen();
		}

		buchstabenEingabe.setText("");
	}

	// Methode zur Darstellung des angezeigten Wortes
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

	// Methode zum Zurücksetzen des Spiels
	private void spielZurücksetzen() {
		gerateneBuchstaben.clear();
		fehlversuche = 0;
		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("Fehlversuche: 0 / " + maxFehlversuche);
		hangmanPanel.repaint();
	}

	// Methode zum Zeichnen des Hangman-Gesamtfortschritts
	private void zeichneHangman(Graphics g, int fehlversuche) {
		// Hangman-Grafik Schritt für Schritt basierend auf den Fehlversuchen
		int x = 50; // Ursprungspunkt
		int y = 150; // Ausgangspunkt für die Basis

		// Basis des Galgens
		g.drawLine(x, y, x + 100, y);

		// Vertikale Stange
		g.drawLine(x + 50, y, x + 50, y - 150);

		// Querbalken oben
		g.drawLine(x + 50, y - 150, x + 100, y - 150);

		// Seil
		g.drawLine(x + 100, y - 150, x + 100, y - 120);

		// Zeichnen des Hangman je nach Fehlversuchen
		if (fehlversuche > 0) g.drawOval(x + 75, y - 120, 50, 50); // Kopf
		if (fehlversuche > 1) g.drawLine(x + 100, y - 70, x + 100, y - 20); // Körper
		if (fehlversuche > 2) g.drawLine(x + 100, y - 60, x + 80, y - 40); // linker Arm
		if (fehlversuche > 3) g.drawLine(x + 100, y - 60, x + 120, y - 40); // rechter Arm
		if (fehlversuche > 4) g.drawLine(x + 100, y - 20, x + 80, y); // linkes Bein
		if (fehlversuche > 5) g.drawLine(x + 100, y - 20, x + 120, y ); // rechtes Bein

	}
}
