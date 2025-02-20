package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller implements ActionListener {
	private final View view;
	private SpielModus spielModus;
	private QuizModus quizModus;
	private Fragenpool fragenpool;
	public Controller() {
        this.view = new View(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "spiel":
					spielModus = new SpielModus();
					break;
				case "spiel2":
					if (spielModus != null) {
						spielModus.spielBeenden();
					}
					break;
				case "quiz":
					quizModus = new QuizModus(fragenpool);
					break;
				case "quiz2":
					if (quizModus != null) {
						quizModus.beendeQuiz();
					}
					break;
				case "benutzer":
					// neues frame irgendwie öffnen
					break;
				case "benutzer2":
					// neues frame öffnen oder man schafft es das die Hauptseite wechselt
					break;
			}

	}
	public static void main(String[] args) {
		new Controller();
	}
}
