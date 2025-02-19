package src;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
	private JFrame frame;
	public View() {
		frame = new JFrame("Worttrainer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout(10,10));

		JMenuBar menuBar = new JMenuBar();

		JMenu beispiel1 = new JMenu("Beispiel1");
		JMenu beispiel2 = new JMenu("Beispiel2");
		JMenu beispiel3 = new JMenu("Beispiel3");

		JMenuItem beispiel1Item = new JMenuItem("FlorianHATeinen40cmBBC");

		beispiel1.add(beispiel1Item);

		menuBar.add(beispiel1);
		menuBar.add(beispiel2);
		menuBar.add(beispiel3);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}

}
