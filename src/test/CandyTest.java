package test;

import java.util.ResourceBundle;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CandyTest extends JFrame {
	public static void main(String[] args) {
		CandyTest f = new CandyTest();
		f.setVisible(true);
	}

	GamePanel gamePanel;
	HomePanel homePanel;
	ResourceBundle rb = ResourceBundle.getBundle("message");

	public CandyTest() {
		super("CandyTest");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setSize(600, 660);
		// setLocationRelativeTo(null);
		setBounds(100, 100, 600, 660);
		setResizable(false);
		homePanel = new HomePanel(this);
		homePanel.setBounds(50, 0, 500, getHeight() - 30);
		add(homePanel);
		gamePanel = new GamePanel(this);
		gamePanel.setBounds(52, 5,
				gamePanel.getT_GRID() * gamePanel.getT_WIDTH(),
				getHeight() - 28);
	}
}
