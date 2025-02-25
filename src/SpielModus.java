// File: src/SpielModus.java
package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SpielModus extends JPanel implements ActionListener {
	private Controller controller;
	private String wort;
	private Set<Character> gerateneBuchstaben;
	private int fehlversuche;
	private int maxFehlversuche;
	// GUI components
	private JLabel wortAnzeige;
	private JTextField buchstabenEingabe;
	private JButton ratenButton;
	private JButton neustartButton;
	private JLabel fehlversucheLabel;
	private JPanel hangmanPanel;
	// Word list loaded from file for specific level
	private List<String> wordList;
	// Default level used for Hangman words
	private String defaultLevel = "A1";

	public SpielModus(Controller controller) {
		this.controller = controller;
		this.maxFehlversuche = SettingsManager.getHangmanFehlversuche();
		setLayout(new BorderLayout(20, 20));
		setBackground(Color.WHITE);

		loadWords();
		wort = chooseRandomWord();
		gerateneBuchstaben = new HashSet<>();
		fehlversuche = 0;

		JLabel titel = new JLabel("Hangman-Spiel", SwingConstants.CENTER);
		titel.setFont(new Font("Verdana", Font.BOLD, 24));
		titel.setForeground(new Color(50, 50, 150));
		add(titel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
		centerPanel.setBackground(Color.WHITE);
		wortAnzeige = new JLabel(getAngezeigtesWort(), SwingConstants.CENTER);
		wortAnzeige.setFont(new Font("Verdana", Font.BOLD, 32));
		centerPanel.add(wortAnzeige, BorderLayout.NORTH);

		hangmanPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				zeichneHangman(g, fehlversuche);
			}
		};
		hangmanPanel.setPreferredSize(new Dimension(200, 300));
		hangmanPanel.setBackground(Color.WHITE);
		centerPanel.add(hangmanPanel, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
		bottomPanel.setBackground(Color.WHITE);
		fehlversucheLabel = new JLabel("Fehlversuche: 0 / " + maxFehlversuche, SwingConstants.CENTER);
		fehlversucheLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		bottomPanel.add(fehlversucheLabel, BorderLayout.NORTH);

		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.setBackground(Color.WHITE);
		buchstabenEingabe = new JTextField(2);
		buchstabenEingabe.setFont(new Font("Verdana", Font.PLAIN, 20));

		ratenButton = new JButton("Raten");
		ratenButton.setFont(new Font("Verdana", Font.BOLD, 16));
		ratenButton.setBackground(new Color(100, 150, 250));
		ratenButton.setForeground(Color.WHITE);
		ratenButton.setFocusPainted(false);
		ratenButton.addActionListener(this);

		neustartButton = new JButton("Neustarten");
		neustartButton.setFont(new Font("Verdana", Font.BOLD, 16));
		neustartButton.setBackground(new Color(220, 100, 100));
		neustartButton.setForeground(Color.WHITE);
		neustartButton.setFocusPainted(false);
		neustartButton.addActionListener(e -> spielZuruecksetzen());

		inputPanel.add(new JLabel("Buchstabe:"));
		inputPanel.add(buchstabenEingabe);
		inputPanel.add(ratenButton);
		inputPanel.add(neustartButton);
		bottomPanel.add(inputPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void loadWords() {
		Map<String, List<String>> levelWords = new HashMap<>();
		String currentLevel = "";
		try (BufferedReader br = new BufferedReader(new FileReader("src/hangman_words.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty()) continue;
				if (line.endsWith(":")) {
					currentLevel = line.substring(0, line.length() - 1).trim();
				} else {
					levelWords.computeIfAbsent(currentLevel, k -> new ArrayList<>()).add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		wordList = levelWords.getOrDefault(defaultLevel, new ArrayList<>());
		if (wordList.isEmpty()) {
			wordList.add("DEFAULT");
		}
	}

	private String chooseRandomWord() {
		Random rand = new Random();
		return wordList.get(rand.nextInt(wordList.size())).toUpperCase();
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

	private void zeichneHangman(Graphics g, int fehlversuche) {
		int x = 50;
		int y = 150;
		g.drawLine(x, y, x + 100, y);
		g.drawLine(x + 50, y, x + 50, y - 150);
		g.drawLine(x + 50, y - 150, x + 100, y - 150);
		g.drawLine(x + 100, y - 150, x + 100, y - 120);
		if (fehlversuche > 0) g.drawOval(x + 75, y - 120, 50, 50);
		if (fehlversuche > 1) g.drawLine(x + 100, y - 70, x + 100, y - 20);
		if (fehlversuche > 2) g.drawLine(x + 100, y - 60, x + 80, y - 40);
		if (fehlversuche > 3) g.drawLine(x + 100, y - 60, x + 120, y - 40);
		if (fehlversuche > 4) g.drawLine(x + 100, y - 20, x + 80, y);
		if (fehlversuche > 5) g.drawLine(x + 100, y - 20, x + 120, y);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eingabe = buchstabenEingabe.getText().toUpperCase();
		if (eingabe.length() != 1 || !Character.isLetter(eingabe.charAt(0))) {
			JOptionPane.showMessageDialog(this, "Bitte geben Sie einen gültigen Buchstaben ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		char buchstabe = eingabe.charAt(0);
		if (gerateneBuchstaben.contains(buchstabe)) {
			JOptionPane.showMessageDialog(this, "Sie haben diesen Buchstaben bereits geraten.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		gerateneBuchstaben.add(buchstabe);
		if (!wort.contains(String.valueOf(buchstabe))) {
			fehlversuche++;
		}
		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);
		hangmanPanel.repaint();

		if (fehlversuche >= maxFehlversuche) {
			JOptionPane.showMessageDialog(this, "Sie haben verloren! Das Wort war: " + wort, "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);
			spielZuruecksetzen();
		} else if (!wortAnzeige.getText().contains("_")) {
			JOptionPane.showMessageDialog(this, "Herzlichen Glückwunsch! Sie haben das Wort erraten: " + wort, "Spiel gewonnen", JOptionPane.INFORMATION_MESSAGE);
			spielZuruecksetzen();
		}
		buchstabenEingabe.setText("");
	}

	private void spielZuruecksetzen() {
		gerateneBuchstaben.clear();
		fehlversuche = 0;
		wort = chooseRandomWord();
		wortAnzeige.setText(getAngezeigtesWort());
		fehlversucheLabel.setText("Fehlversuche: 0 / " + maxFehlversuche);
		hangmanPanel.repaint();
	}
}