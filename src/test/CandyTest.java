package test;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CandyTest extends JFrame {
	ResourceBundle rb = ResourceBundle.getBundle("message");
	HomePanel homePanel;
	GamePanel gamePanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CandyTest f = new CandyTest();
				f.setVisible(true);
			}
		});
	}

	public CandyTest() {
		setTitle("CandyTest");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 660);
		setResizable(false);
		setLocationRelativeTo(null);
		homePanel = new HomePanel(this);
		homePanel.setBounds(50, 0, 500, getHeight() - 30);
		add(homePanel);
		gamePanel = new GamePanel(this);
		gamePanel.setBounds(52, 5,
				gamePanel.getT_GRID() * gamePanel.getT_WIDTH(),
				getHeight() - 28);
	}
}