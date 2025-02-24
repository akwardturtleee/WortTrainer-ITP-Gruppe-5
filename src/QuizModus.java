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
	private JProgressBar progressBar; // Fortschrittsbalken
	private Controller controller;

	public QuizModus(Controller controller, Fragenpool fragenpool, String selectedLevel) {
		this.controller = controller;
		this.fragen = fragenpool.getFragen().stream()
				.filter(f -> f.getFrageTyp().equalsIgnoreCase(selectedLevel))
				.toArray(Frage[]::new);
		shuffleArray(fragen);
		this.aktuelleFrageIndex = 0;
		this.punkte = 0;
		this.richtigeAntworten = 0;
		this.falscheAntworten = 0;
		this.quizBeendet = false;

		setLayout(new BorderLayout(20, 20));
		setBackground(Color.WHITE);

		frageLabel = new JLabel("", SwingConstants.CENTER);
		frageLabel.setFont(new Font("Verdana", Font.BOLD, 22));
		frageLabel.setForeground(new Color(50, 50, 150));

		antwortFeld = new JTextField(20);
		antwortFeld.setFont(new Font("Verdana", Font.PLAIN, 18));
		antwortFeld.addActionListener(this);

		bestaetigenButton = new JButton("Bestätigen");
		bestaetigenButton.setFont(new Font("Verdana", Font.BOLD, 16));
		bestaetigenButton.setBackground(new Color(100, 150, 250));
		bestaetigenButton.setForeground(Color.WHITE);
		bestaetigenButton.setFocusPainted(false);
		bestaetigenButton.addActionListener(this);

		feedbackLabel = new JLabel("", SwingConstants.CENTER);
		feedbackLabel.setFont(new Font("Verdana", Font.ITALIC, 18));
		feedbackLabel.setForeground(Color.DARK_GRAY);

		imageLabel = new JLabel("", SwingConstants.CENTER);

		// Fortschrittsbalken initialisieren
		progressBar = new JProgressBar(0, fragen.length);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Verdana", Font.BOLD, 14));
		progressBar.setForeground(new Color(0, 150, 0)); // Grün für Fortschritt

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(antwortFeld);
		inputPanel.add(bestaetigenButton);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(progressBar, BorderLayout.NORTH); // Fortschrittsbalken hinzufügen
		bottomPanel.add(feedbackLabel, BorderLayout.CENTER);
		bottomPanel.add(inputPanel, BorderLayout.SOUTH);

		add(frageLabel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		naechsteFrage();
	}

	private void naechsteFrage() {
		if (aktuelleFrageIndex < fragen.length) {
			Frage aktuelleFrage = fragen[aktuelleFrageIndex];
			frageLabel.setText("Frage " + (aktuelleFrageIndex + 1) + ": " + aktuelleFrage.getInhalt());

			if (aktuelleFrage.getImagePath() != null) {
				ImageIcon icon = new ImageIcon(aktuelleFrage.getImagePath());
				Image scaledImage = icon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH);
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
				feedbackLabel.setForeground(new Color(0, 128, 0));
				richtigeAntworten++;
				punkte += 10;
				updateProgressBar(); // Fortschritt erhöhen
			} else {
				feedbackLabel.setText("Falsch! Die richtige Antwort war: " + aktuelleFrage.getRichtigeAntwort());
				feedbackLabel.setForeground(Color.RED);
				falscheAntworten++;
			}

			aktuelleFrageIndex++;
			antwortFeld.setText("");
			naechsteFrage();
		}
	}

	private void updateProgressBar() {
		SwingWorker<Void, Integer> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() throws Exception {
				int currentProgress = progressBar.getValue();
				int newProgress = currentProgress + 1; // Erhöhe Fortschritt um 1
				publish(newProgress);
				return null;
			}

			@Override
			protected void process(java.util.List<Integer> chunks) {
				for (int value : chunks) {
					progressBar.setValue(value);
				}
			}
		};
		worker.execute();
	}

	private void shuffleArray(Frage[] array) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = (int) (Math.random() * (i + 1));
			Frage temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}
}
