// File: src/QuizModus.java
package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

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
	private JButton restartButton;
	private JLabel feedbackLabel;
	private JLabel linkLabel;
	private JProgressBar progressBar;
	private Controller controller;
	// Store the current image URL to use on click
	private String currentImageUrl;

	public QuizModus(Controller controller, Fragenpool fragenpool, String selectedLevel) {
		this.controller = controller;
		String level = selectedLevel != null ? selectedLevel : SettingsManager.getSprachniveau();
		// Count and filter questions based on the language level
		int count = 0;
		for (Frage f : fragenpool.getFragen()) {
			if (f.getFrageTyp().equalsIgnoreCase(level)) {
				count++;
			}
		}
		fragen = new Frage[count];
		int i = 0;
		for (Frage f : fragenpool.getFragen()) {
			if (f.getFrageTyp().equalsIgnoreCase(level)) {
				fragen[i++] = f;
			}
		}
		shuffleArray(fragen);
		this.aktuelleFrageIndex = 0;
		this.punkte = 0;
		this.richtigeAntworten = 0;
		this.falscheAntworten = 0;
		this.quizBeendet = false;
		currentImageUrl = null;

		setLayout(new BorderLayout(20, 20));
		setBackground(Color.WHITE);

		frageLabel = new JLabel("", SwingConstants.CENTER);
		frageLabel.setFont(new Font("Verdana", Font.BOLD, 22));
		frageLabel.setForeground(new Color(50, 50, 150));

		antwortFeld = new JTextField(20);
		antwortFeld.setFont(new Font("Verdana", Font.PLAIN, 18));
		antwortFeld.addActionListener(this);

		bestaetigenButton = new JButton("Best\u00e4tigen");
		bestaetigenButton.setFont(new Font("Verdana", Font.BOLD, 16));
		bestaetigenButton.setBackground(new Color(100, 150, 250));
		bestaetigenButton.setForeground(Color.WHITE);
		bestaetigenButton.setFocusPainted(false);
		bestaetigenButton.addActionListener(this);

		restartButton = new JButton("Neustarten");
		restartButton.setFont(new Font("Verdana", Font.BOLD, 16));
		restartButton.setBackground(new Color(220, 100, 100));
		restartButton.setForeground(Color.WHITE);
		restartButton.setFocusPainted(false);
		restartButton.addActionListener(e -> resetQuiz());

		feedbackLabel = new JLabel("", SwingConstants.CENTER);
		feedbackLabel.setFont(new Font("Verdana", Font.ITALIC, 18));
		feedbackLabel.setForeground(Color.DARK_GRAY);

		linkLabel = new JLabel("", SwingConstants.CENTER);
		linkLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		linkLabel.setForeground(Color.BLUE);
		linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentImageUrl != null && Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI(currentImageUrl));
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(QuizModus.this, "Failed to open link: " + ex.getMessage());
					}
				}
			}
		});

		progressBar = new JProgressBar(0, fragen.length);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Verdana", Font.BOLD, 14));
		progressBar.setForeground(new Color(0, 150, 0));

		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.add(antwortFeld);
		inputPanel.add(bestaetigenButton);
		inputPanel.add(restartButton);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(progressBar, BorderLayout.NORTH);
		bottomPanel.add(feedbackLabel, BorderLayout.CENTER);
		bottomPanel.add(inputPanel, BorderLayout.SOUTH);

		add(frageLabel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		add(linkLabel, BorderLayout.CENTER);

		naechsteFrage();
	}

	private void naechsteFrage() {
		if (aktuelleFrageIndex < fragen.length) {
			Frage aktuelleFrage = fragen[aktuelleFrageIndex];
			frageLabel.setText("Frage " + (aktuelleFrageIndex + 1) + ": " + aktuelleFrage.getInhalt());
			if (aktuelleFrage.getImageUrl() != null) {
				linkLabel.setText("<html><a href='#'>" + aktuelleFrage.getImageUrl() + "</a></html>");
				currentImageUrl = aktuelleFrage.getImageUrl();
			} else {
				linkLabel.setText("");
				currentImageUrl = null;
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
		return "Quiz abgeschlossen!\nPunkte: " + punkte +
				"\nRichtige Antworten: " + richtigeAntworten +
				"\nFalsche Antworten: " + falscheAntworten;
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
				updateProgressBar();
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
			protected Void doInBackground() {
				int newProgress = progressBar.getValue() + 1;
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

	public void resetQuiz() {
		shuffleArray(fragen);
		aktuelleFrageIndex = 0;
		punkte = 0;
		richtigeAntworten = 0;
		falscheAntworten = 0;
		quizBeendet = false;
		progressBar.setValue(0);
		feedbackLabel.setText("");
		antwortFeld.setText("");
		naechsteFrage();
	}
}