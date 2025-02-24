package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	// The constructor now accepts a language level (e.g. "A1", "B2", etc.) to filter questions.
	public QuizModus(Controller controller, Fragenpool fragenpool, String selectedLevel) {
		this.controller = controller;
		List<Frage> filteredList = fragenpool.getFragen()
				.stream()
				.filter(f -> f.getFrageTyp().equalsIgnoreCase(selectedLevel))
				.collect(Collectors.toList());
		Collections.shuffle(filteredList);
		this.fragen = filteredList.toArray(new Frage[0]);
		this.aktuelleFrageIndex = 0;
		this.punkte = 0;
		this.richtigeAntworten = 0;
		this.falscheAntworten = 0;
		this.quizBeendet = false;

		setLayout(new BorderLayout());

		frageLabel = new JLabel("", SwingConstants.CENTER);
		frageLabel.setFont(new Font("Arial", Font.BOLD, 20));

		antwortFeld = new JTextField(20);
		bestaetigenButton = new JButton("Antwort bestätigen");
		bestaetigenButton.addActionListener(this);

		feedbackLabel = new JLabel("", SwingConstants.CENTER);
		feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16));

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