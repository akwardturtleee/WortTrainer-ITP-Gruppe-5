package src;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Controller controller;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JProgressBar progressBar;

    public View(Controller controller) {
        this.controller = controller;
        frame = new JFrame("Worttrainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Home Panel
        JPanel homePanel = new JPanel(new GridLayout(2, 1));
        JLabel schriftLabel = new JLabel("WILLKOMMEN BEIM WORTTRAINER!", SwingConstants.CENTER);
        schriftLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        homePanel.add(schriftLabel);
        homePanel.setBackground(new Color(100, 149, 237));

        // User Panel
        JPanel userPanel = new Nutzer(controller);
        userPanel.setBackground(new Color(100, 149, 237));

        // Quiz Panel with default language level "A1"
        JPanel quizPanel = new QuizModus(controller, new Fragenpool(), "A1");

        // Spiel Panel
        JPanel spielPanel = new SpielModus(controller);

        // Add panels to main panel using card layout
        mainPanel.add(homePanel, "home");
        mainPanel.add(userPanel, "benutzer");
        mainPanel.add(quizPanel, "quiz");
        mainPanel.add(spielPanel, "spiel");

        // Menu bar and menu items creation
        JMenuBar menuBar = new JMenuBar();

        JMenu home = new JMenu("Home");
        home.setPreferredSize(new Dimension(70, 100));
        home.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.setMnemonic('H');
        homeItem.setActionCommand("home");
        homeItem.addActionListener(controller);
        home.add(homeItem);

        JMenu spiel = new JMenu("Spiel");
        spiel.setPreferredSize(new Dimension(80, 100));
        spiel.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem spielItem = new JMenuItem("Starten");
        spielItem.setMnemonic('S');
        spielItem.setActionCommand("spiel");
        spielItem.addActionListener(controller);
        JMenuItem spielItem2 = new JMenuItem("Beenden");
        spielItem2.setMnemonic('B');
        spielItem2.setActionCommand("spiel2");
        spielItem2.addActionListener(controller);
        spiel.add(spielItem);
        spiel.add(spielItem2);

        JMenu benutzer = new JMenu("Benutzer");
        benutzer.setPreferredSize(new Dimension(100, 100));
        benutzer.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem benutzerItem = new JMenuItem("Profil");
        benutzerItem.setMnemonic('P');
        benutzerItem.setActionCommand("benutzer");
        benutzerItem.addActionListener(controller);
        JMenuItem benutzerItem2 = new JMenuItem("Fortschritt");
        benutzerItem2.setMnemonic('F');
        benutzerItem2.setActionCommand("benutzer2");
        benutzerItem2.addActionListener(controller);
        benutzer.add(benutzerItem);
        benutzer.add(benutzerItem2);

        JMenu quiz = new JMenu("Quiz");
        quiz.setPreferredSize(new Dimension(50, 100));
        quiz.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem quizItem = new JMenuItem("Quiz Starten");
        quizItem.setMnemonic('Q');
        quizItem.setActionCommand("quiz");
        quizItem.addActionListener(controller);
        JMenuItem quizItem2 = new JMenuItem("Beenden");
        quizItem2.setMnemonic('B');
        quizItem2.setActionCommand("quiz2");
        quizItem2.addActionListener(controller);
        quiz.add(quizItem);
        quiz.add(quizItem2);

        menuBar.add(home);
        menuBar.add(spiel);
        menuBar.add(benutzer);
        menuBar.add(quiz);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(frame.getWidth(), 75));

        // Progress bar at the bottom of the frame
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        // Assemble the frame
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(progressBar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void showCard(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void updateProgress(int progress) {
        progressBar.setValue(progress);
    }
}