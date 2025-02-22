package src;
import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
	private Controller controller;
	private JFrame frame;
	JMenuItem spielItem;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private Nutzer nutzer;
	public View(Controller controller) {
		this.controller = controller;
		frame = new JFrame("Worttrainer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout(10, 10));

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		JPanel homePanel = new JPanel(new GridLayout(2,1));
		JLabel schriftLabel = new JLabel("WILLKOMMEN BEIM WORTTRAINER!" , SwingConstants.CENTER);
		homePanel.add(schriftLabel);
		Font fontW = new Font("Arial", Font.PLAIN, 50);
		schriftLabel.setFont(fontW);
		homePanel.setBackground(new Color(100, 149, 237));

		//JPanel userPanel = new JPanel();
		//userPanel.add(new JLabel("")); // TEST
		JPanel userPanel = new Nutzer(controller); // Nutzer-Panel einfügen, mit Chatty umgeschreiben für Testzwecke

		JPanel quizPanel = new JPanel();
		quizPanel.add(new JLabel("")); // TEST

		JPanel spielPanel = new JPanel();
		spielPanel.add(new JLabel("")); // TEST


		mainPanel.add(homePanel, "home");
		mainPanel.add(userPanel, "benutzer");
		mainPanel.add(quizPanel, "quiz");
		mainPanel.add(spielPanel, "spiel");

		JMenuBar menuBar = new JMenuBar();
		JMenu spiel = new JMenu("Spiel");
		spiel.setPreferredSize(new Dimension(80, 100));
		Font font = new Font("Arial", Font.PLAIN, 20);
		spiel.setFont(font);
		JMenu benutzer = new JMenu("Benutzer");
		benutzer.setPreferredSize(new Dimension(100, 100));

		benutzer.setFont(font);
		JMenu quiz = new JMenu("Quiz");
		quiz.setPreferredSize(new Dimension(50, 100));

		quiz.setFont(font);

		JMenu haus = new JMenu("Home");
		haus.setPreferredSize(new Dimension(70, 100));
		haus.setFont(font);
		JMenuItem hausItem = new JMenuItem("Home");
		hausItem.setMnemonic('H');
		hausItem.addActionListener(controller);
		hausItem.setActionCommand("home");
		haus.add(hausItem);
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

		JMenuItem benutzerItem = new JMenuItem("Profil");
		benutzerItem.setMnemonic('P');
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
		menuBar.add(haus);
		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.setPreferredSize(new Dimension(frame.getWidth(), 75));


		frame.add(menuPanel, BorderLayout.NORTH);
		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
		public void showCard(String name) {
			cardLayout.show(mainPanel, name);
		}
}
