// File: src/QuizModus.java
package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizModus extends JPanel implements ActionListener {
	private Frage[] fragen;
	private int aktuelleFrageIndex;
	private int punkte;
	private int richtigeAntworten;
	private int falscheAntworten;
	private boolean quizBeendet;

	private JLabel frageLabel;
	private JTextField antwortFeld;
	private JButton bestaetigenButton;
	private JLabel feedbackLabel;
	private JLabel imageLabel;
	private Controller controller;

	public QuizModus(Controller controller, Fragenpool fragenpool, String selectedLevel) {
		this.controller = controller;
		// Filter questions using a basic loop.
		int total = 0;
		for (int i = 0; i < fragenpool.getFragen().size(); i++) {
			Frage f = fragenpool.getFragen().get(i);
			if (f.getFrageTyp().equalsIgnoreCase(selectedLevel)) {
				total++;
			}
		}
		Frage[] filtered = new Frage[total];
		int index = 0;
		for (int i = 0; i < fragenpool.getFragen().size(); i++) {
			Frage f = fragenpool.getFragen().get(i);
			if (f.getFrageTyp().equalsIgnoreCase(selectedLevel)) {
				filtered[index++] = f;
			}
		}
		for (int i = filtered.length - 1; i > 0; i--) {
			int j = (int) (Math.random() * (i + 1));
			Frage temp = filtered[i];
			filtered[i] = filtered[j];
			filtered[j] = temp;
		}
		this.fragen = filtered;
		this.aktuelleFrageIndex = 0;
		this.punkte = 0;
		this.richtigeAntworten = 0;
		this.falscheAntworten = 0;
		this.quizBeendet = false;

		setLayout(new BorderLayout());
		frageLabel = new JLabel("", SwingConstants.CENTER);
		frageLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));

		antwortFeld = new JTextField(20);
		antwortFeld.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
		// Allow Enter key to trigger answer confirmation.
		antwortFeld.addActionListener(this);

		bestaetigenButton = new JButton("Antwort bestätigen");
		bestaetigenButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
		bestaetigenButton.addActionListener(this);

		feedbackLabel = new JLabel("", SwingConstants.CENTER);
		feedbackLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));

		imageLabel = new JLabel("", SwingConstants.CENTER);

		JPanel inputPanel = new JPanel();
		inputPanel.add(antwortFeld);
		inputPanel.add(bestaetigenButton);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(feedbackLabel, BorderLayout.NORTH);
		bottomPanel.add(inputPanel, BorderLayout.SOUTH);

		add(frageLabel, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		naechsteFrage();
	}

	private void naechsteFrage() {
		if (aktuelleFrageIndex < fragen.length) {
			Frage aktuelleFrage = fragen[aktuelleFrageIndex];
			frageLabel.setText(aktuelleFrage.getInhalt());
			if (aktuelleFrage.getImagePath() != null) {
				ImageIcon icon = new ImageIcon(aktuelleFrage.getImagePath());
				Image scaledImage = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(scaledImage));
			} else {
				imageLabel.setIcon(null);
			}
		} else {
			beendeQuiz();
		}
	}

	private void beendeQuiz() {
		if (!quizBeendet) {
			quizBeendet = true;
			JOptionPane.showMessageDialog(this, berechneStatistik(), "Quiz Beendet", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean pruefeAntwort(String eingabe, Frage frage) {
		return eingabe.trim().equalsIgnoreCase(frage.getRichtigeAntwort());
	}

	private String berechneStatistik() {
		return "Quiz abgeschlossen!\n" +
				"Punkte: " + punkte + "\n" +
				"Richtige Antworten: " + richtigeAntworten + "\n" +
				"Falsche Antworten: " + falscheAntworten;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (aktuelleFrageIndex < fragen.length) {
			Frage aktuelleFrage = fragen[aktuelleFrageIndex];
			String eingabe = antwortFeld.getText().trim();
			if (pruefeAntwort(eingabe, aktuelleFrage)) {
				feedbackLabel.setText("Richtig!");
				richtigeAntworten++;
				punkte += 10;
			} else {
				feedbackLabel.setText("Falsch!");
				falscheAntworten++;
			}
			aktuelleFrageIndex++;
			antwortFeld.setText("");
			naechsteFrage();
		}
	}
}