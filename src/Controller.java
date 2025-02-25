// File: src/Controller.java
package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
	private final View view;
	private SpielModus spielModus;
	private QuizModus quizModus;
	private Fragenpool fragenpool;
	private SettingsPanel settingsPanel;

	public Controller() {
		fragenpool = new Fragenpool();
		spielModus = new SpielModus(this);
		quizModus = new QuizModus(this, fragenpool, "A1");
		settingsPanel = new SettingsPanel();
		view = new View(this, spielModus, quizModus, settingsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "home":
				view.showCard("home");
				break;
			case "quiz":
				view.showCard("quiz");
				break;
			case "quiz2":
				quizModus.resetQuiz();
				view.showCard("home");
				break;
			case "spiel":
				view.showCard("spiel");
				break;
			case "spiel2":
				view.showCard("home");
				break;
			case "settings":
				view.showCard("settings");
				break;
			case "infos":
				JOptionPane.showMessageDialog(view, "Dies ist die WortTrainer-Anwendung. Version 1.0", "Information", JOptionPane.INFORMATION_MESSAGE);
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Controller());
	}
}