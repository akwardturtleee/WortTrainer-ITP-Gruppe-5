package src;
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
		spiel.setMnemonic('S');
		JMenu benutzer = new JMenu("Benutzer");
		benutzer.setPreferredSize(new Dimension(100,100));
		benutzer.setMnemonic('B');
		benutzer.setFont(font);
		JMenu quiz = new JMenu("Quiz");
		quiz.setPreferredSize(new Dimension(50,100));
		quiz.setMnemonic('Q');
		quiz.setFont(font);

		spielItem = new JMenuItem("Starten");
		JMenuItem spielItem2 = new JMenuItem("Beenden");
		spiel.add(spielItem);
		spiel.add(spielItem2);
		spielItem.addActionListener(controller);
		spielItem.setActionCommand("spiel");
		spielItem2.addActionListener(controller);
		spielItem2.setActionCommand("spiel2");

		JMenuItem quizItem = new JMenuItem("Starten");
		JMenuItem quizItem2 = new JMenuItem("Beenden");
		quiz.add(quizItem);
		quiz.add(quizItem2);
		quizItem.addActionListener(controller);
		quizItem.setActionCommand("quiz");
		quizItem2.addActionListener(controller);
		quizItem2.setActionCommand("quiz2");

		menuBar.add(spiel);
		menuBar.add(benutzer);
		menuBar.add(quiz);
		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));





		frame.add(menuPanel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
