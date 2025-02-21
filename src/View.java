package src;

import src.Controller;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
	private Controller controller;
	private JFrame frame;
	JMenuItem spielItem;
	public View(Controller controller) {
		this.controller = controller;
		frame = new JFrame("Worttrainer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout(10,10));

		JMenuBar menuBar = new JMenuBar();

		JMenu spiel = new JMenu("Spiel");
		spiel.setPreferredSize(new Dimension(80,100));
		Font font = new Font("Arial", Font.PLAIN, 20);
		spiel.setFont(font);

		JMenu benutzer = new JMenu("Benutzer");
		benutzer.setPreferredSize(new Dimension(100,100));

		benutzer.setFont(font);
		JMenu quiz = new JMenu("Quiz");
		quiz.setPreferredSize(new Dimension(50,100));

		quiz.setFont(font);

		spielItem = new JMenuItem("Starten");
		spielItem.setMnemonic('S');
		JMenuItem spielItem2 = new JMenuItem("Beenden");
		spielItem2.setMnemonic('B');
		spiel.add(spielItem);
		spiel.add(spielItem2);
		spielItem.addActionListener(controller);
		spielItem.setActionCommand("Spiel");
		spielItem2.addActionListener(controller);
		spielItem2.setActionCommand("Spiel2");

		JMenuItem quizItem = new JMenuItem("Quiz Starten");
		quizItem.setMnemonic('Q');
		JMenuItem quizItem2 = new JMenuItem("Beenden");
		quizItem2.setMnemonic('B');
		quiz.add(quizItem);
		quiz.add(quizItem2);
		quizItem.addActionListener(controller);
		quizItem.setActionCommand("Quiz");
		quizItem2.addActionListener(controller);
		quizItem2.setActionCommand("Quiz2");

		JMenuItem benutzerItem = new JMenuItem("Nutzerprofil");
		benutzerItem.setMnemonic('P');
		JMenuItem benutzerItem2 = new JMenuItem("Fortschritt");
		benutzerItem2.setMnemonic('F');
		benutzer.add(benutzerItem);
		benutzer.add(benutzerItem2);
		benutzerItem.addActionListener(controller);
		benutzerItem.setActionCommand("Benutzer");
		benutzerItem2.addActionListener(controller);
		benutzerItem2.setActionCommand("Benutzer2");


		menuBar.add(spiel);
		menuBar.add(benutzer);
		menuBar.add(quiz);
		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.setPreferredSize(new Dimension(frame.getWidth(), 75));

		JLabel schriftLabel = new JLabel("WILLKOMMEN BEIM WORTTRAINER!" , SwingConstants.CENTER);

		Font fontW = new Font("Arial", Font.PLAIN, 50);
		schriftLabel.setFont(fontW);
		JPanel schriftPanel = new JPanel(new GridLayout(2,1));
		schriftPanel.add(schriftLabel);

		schriftPanel.setBackground(new Color(100, 149, 237));


		frame.add(schriftPanel, BorderLayout.CENTER);
		frame.add(menuPanel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
