package src;
import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
	private Controller controller;
	private JFrame frame;
	JMenuItem spielItem;
	private JPanel mainPanel;
	private CardLayout cardLayout;
	public View(Controller controller) {
		this.controller = controller;
		//panel = new JPanel(new BorderLayout(10,10));
		setTitle("Worttrainer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(new BorderLayout(10,10));
		//panel.setVisible(true);
		//frame.add(panel);



		CardLayout cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		mainPanel.setLayout(new GridLayout(2,1));
		mainPanel.setBackground(new Color(100, 149, 237));
		JLabel willkommen = new JLabel("WILLKOMMEN ZUM WORTTRAINER", SwingConstants.CENTER);
		mainPanel.setFont(new Font("Arial", Font.PLAIN, 50));
		mainPanel.add(willkommen);

		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("Benutzerprofil")); // TEST!!!

		mainPanel.add(userPanel);

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
		spielItem.setActionCommand("spiel");
		spielItem2.addActionListener(controller);
		spielItem2.setActionCommand("spiel2");

		JMenuItem quizItem = new JMenuItem("Quiz Starten");
		quizItem.setMnemonic('Q');
		JMenuItem quizItem2 = new JMenuItem("Beenden");
		quizItem2.setMnemonic('B');
		quiz.add(quizItem);
		quiz.add(quizItem2);
		quizItem.addActionListener(controller);
		quizItem.setActionCommand("quiz");
		quizItem2.addActionListener(controller);
		quizItem2.setActionCommand("quiz2");

		JMenuItem benutzerItem = new JMenuItem("Nutzerprofil");
		benutzerItem.setMnemonic('N');

		JMenuItem benutzerItem2 = new JMenuItem("Fortschritt");
		benutzerItem2.setMnemonic('F');
		benutzer.add(benutzerItem);
		benutzer.add(benutzerItem2);
		benutzerItem.addActionListener(controller);
		benutzerItem.setActionCommand("benutzer");
		benutzerItem2.addActionListener(controller);
		benutzerItem2.setActionCommand("benutzer2");


		menuBar.add(spiel);
		menuBar.add(benutzer);
		menuBar.add(quiz);

		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.setPreferredSize(new Dimension(getWidth(), 75));

		add(menuPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}
}
