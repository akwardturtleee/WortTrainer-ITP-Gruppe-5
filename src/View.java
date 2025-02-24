package src;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame {
    private Controller controller;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JProgressBar progressBar;

    public View(Controller controller) {
        this.controller = controller;
        frame = new JFrame("WortTrainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Home Panel
        JPanel homePanel = new JPanel(new GridLayout(2, 1));
        JLabel schriftLabel = new JLabel("WILLKOMMEN BEIM WORTTRAINER!", SwingConstants.CENTER);
        schriftLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 48));
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

        // Create menu bar and menus
        JMenuBar menuBar = new JMenuBar();

        // Define a common square dimension
        Dimension squareSize = new Dimension(75, 75);

        // Home menu as a house icon with embedded text, without a menu item.
        JMenu home = new JMenu();
        home.setPreferredSize(squareSize);
        home.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
        home.setHorizontalTextPosition(SwingConstants.CENTER);
        home.setVerticalTextPosition(SwingConstants.CENTER);
        home.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Use HTML to center both the icon and label within the square.
        home.setText("<html><div style='text-align:center;'>&#8962;<br>Home</div></html>");
        // Add a mouse listener to trigger home navigation on click.
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCard("home");
            }
        });
        menuBar.add(home);

        // Spiel menu with centered text using HTML.
        JMenu spiel = new JMenu();
        spiel.setPreferredSize(squareSize);
        spiel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
        spiel.setHorizontalTextPosition(SwingConstants.CENTER);
        spiel.setVerticalTextPosition(SwingConstants.CENTER);
        spiel.setAlignmentY(Component.CENTER_ALIGNMENT);
        spiel.setText("<html><div style='text-align:center;'>Spiel</div></html>");
        // Add items if needed or add a mouse listener similar to home for direct navigation.
        // Here we add menu items as before.
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
        menuBar.add(spiel);

        // Quiz menu with centered text using HTML.
        JMenu quiz = new JMenu();
        quiz.setPreferredSize(squareSize);
        quiz.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
        quiz.setHorizontalTextPosition(SwingConstants.CENTER);
        quiz.setVerticalTextPosition(SwingConstants.CENTER);
        quiz.setAlignmentY(Component.CENTER_ALIGNMENT);
        quiz.setText("<html><div style='text-align:center;'>Quiz</div></html>");
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
        menuBar.add(quiz);

        // Add glue to push further menus to the right
        menuBar.add(Box.createHorizontalGlue());

        // Settings menu using a gear icon with centered text.
        JMenu settingsMenu = new JMenu();
        settingsMenu.setPreferredSize(squareSize);
        settingsMenu.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
        settingsMenu.setHorizontalTextPosition(SwingConstants.CENTER);
        settingsMenu.setVerticalTextPosition(SwingConstants.CENTER);
        settingsMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        settingsMenu.setText("<html><div style='text-align:center;'>&#9881;<br>Einst.</div></html>");
        JMenuItem settingsItem = new JMenuItem("Einstellungen");
        settingsItem.setActionCommand("settings");
        settingsItem.addActionListener(controller);
        settingsMenu.add(settingsItem);
        JMenuItem infosItem = new JMenuItem("Infos");
        infosItem.setActionCommand("infos");
        infosItem.addActionListener(controller);
        settingsMenu.add(infosItem);
        menuBar.add(settingsMenu);

        // User menu with a user icon using HTML for centered text.
        JMenu userMenu = new JMenu();
        userMenu.setPreferredSize(squareSize);
        userMenu.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
        userMenu.setHorizontalTextPosition(SwingConstants.CENTER);
        userMenu.setVerticalTextPosition(SwingConstants.CENTER);
        userMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
        userMenu.setText("<html><div style='text-align:center;'>&#128100; Profil</div></html>");
        JMenuItem profileItem = new JMenuItem("Profil");
        profileItem.setMnemonic('P');
        profileItem.setActionCommand("benutzer");
        profileItem.addActionListener(controller);
        JMenuItem progressItem = new JMenuItem("Fortschritt");
        progressItem.setMnemonic('F');
        progressItem.setActionCommand("benutzer2");
        progressItem.addActionListener(controller);
        userMenu.add(profileItem);
        userMenu.add(progressItem);
        menuBar.add(userMenu);

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