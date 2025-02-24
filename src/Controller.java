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
	private Nutzer nutzer;
	public Controller() {
        this.view = new View(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "spiel":
					if (spielModus == null) {
						view.showCard("spiel");
					} else {
						view.showCard("home");
					}
					break;
				case "spiel2", "quiz2","home":
					view.showCard("home");
					break;
				case "quiz":
					if (quizModus == null) {
						view.showCard("quiz");
					} else {
						view.showCard("home");
					}
					break;
                case "benutzer":
					if (nutzer == null) {
						view.showCard("benutzer");
					} else {
						view.showCard("home");
					}
					view.showCard("benutzer");

					break;
				case "benutzer2":
					view.showCard("benutzer2");
					break;
            }

	}
	public static void main(String[] args) {
		new Controller();
	}
}
