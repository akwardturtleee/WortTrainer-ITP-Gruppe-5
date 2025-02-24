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
	private Controller controller;

	public QuizModus(Controller controller, Fragenpool fragenpool) {
		this.controller = controller;
		this.fragen = fragenpool.getFragen().toArray(new Frage[0]);
		this.aktuelleFrageIndex = 0;
		this.punkte = 0;
		this.richtigeAntworten = 0;
		this.falscheAntworten = 0;
		this.quizBeendet = false;

		setLayout(new BorderLayout());

		frageLabel = new JLabel("Frage: ");
		frageLabel.setFont(new Font("Arial", Font.BOLD, 20));
		frageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		antwortFeld = new JTextField(20);
		bestaetigenButton = new JButton("Antwort bestätigen");
		bestaetigenButton.addActionListener(this);

		feedbackLabel = new JLabel("");
		feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel inputPanel = new JPanel();
		inputPanel.add(antwortFeld);
		inputPanel.add(bestaetigenButton);

		add(frageLabel, BorderLayout.NORTH);
		add(inputPanel, BorderLayout.CENTER);
		add(feedbackLabel, BorderLayout.SOUTH);

		naechsteFrage();
	}

	private void naechsteFrage() {
		if (aktuelleFrageIndex < fragen.length) {
			Frage aktuelleFrage = fragen[aktuelleFrageIndex];
			frageLabel.setText("Frage: " + aktuelleFrage.getInhalt());
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