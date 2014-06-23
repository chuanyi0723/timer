package test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomePanel extends JPanel implements ActionListener {
	private CandyTest parent;
	private JButton btn1, btn2, btn3, btn4;
	private JLabel icon;

	// private JButton debugBtn;

	public HomePanel(CandyTest parent) {
		super();
		this.parent = parent;
		setLayout(null);
		icon = new JLabel();
		icon.setIcon(new ImageIcon(getClass().getResource("/images/icon06.png")));
		icon.setBounds(122, 5, 256, 256);
		btn1 = new JButton(parent.rb.getString("newgame"));
		btn1.setBounds(190, 265, 120, 30);
		btn1.setMnemonic(KeyEvent.VK_N);
		btn1.setBackground(Color.PINK);
		btn1.addActionListener(this);
		btn1.setActionCommand("newgame");
		// debugBtn = new JButton(frame.rb.getString("debug") + "off");
		// debugBtn.setBounds(315, 265, 160, 30);
		// debugBtn.setMnemonic(KeyEvent.VK_D);
		// debugBtn.addActionListener(this);
		// debugBtn.setActionCommand("debug");
		// add(debugBtn);
		btn2 = new JButton(parent.rb.getString("continue"));
		btn2.setBounds(190, 300, 120, 30);
		btn2.setEnabled(false);
		btn2.setMnemonic(KeyEvent.VK_C);
		btn2.setBackground(Color.PINK);
		btn2.addActionListener(this);
		btn2.setActionCommand("continue");
		btn3 = new JButton(parent.rb.getString("exit"));
		btn3.setBounds(190, 335, 120, 30);
		btn3.setMnemonic(KeyEvent.VK_E);
		btn3.setBackground(Color.PINK);
		btn3.addActionListener(this);
		btn3.setActionCommand("exit");
		btn4 = new JButton("Language");
		btn4.setBounds(190, 370, 120, 30);
		btn4.setMnemonic(KeyEvent.VK_L);
		btn4.setBackground(Color.PINK);
		btn4.addActionListener(this);
		btn4.setActionCommand("language");
		add(icon);
		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "newgame":
			parent.remove(parent.homePanel);
			btn2.setEnabled(true);
			parent.add(parent.gamePanel);
			parent.gamePanel.loadStage(1);
			parent.gamePanel.setSpTime(3);
			parent.gamePanel.gameStart();
			parent.repaint();
			break;
		case "continue":
			parent.remove(parent.homePanel);
			parent.add(parent.gamePanel);
			parent.repaint();
			break;
		case "exit":
			int choose = JOptionPane.showConfirmDialog(this,
					parent.rb.getString("leavemsg"),
					parent.rb.getString("exitT"), JOptionPane.YES_NO_OPTION);
			if (choose == JOptionPane.YES_OPTION)
				System.exit(0);
			break;
		case "language":
			String[] ss = { "Chinese", "English" };
			String s = (String) JOptionPane.showInputDialog(this,
					"Select language", "language", JOptionPane.PLAIN_MESSAGE,
					null, ss, ss[0]);
			if (s != null) {
				switch (s) {
				case "Chinese":
					parent.rb = ResourceBundle.getBundle("message",
							Locale.TAIWAN);
					break;
				case "English":
					parent.rb = ResourceBundle.getBundle("message",
							Locale.ENGLISH);
					break;
				}
				btn1.setText(parent.rb.getString("newgame"));
				btn2.setText(parent.rb.getString("continue"));
				btn3.setText(parent.rb.getString("exit"));
				// if (frame.gamePanel.isDebug())
				// debugBtn.setText(frame.rb.getString("debug") + "on");
				// else
				// debugBtn.setText(frame.rb.getString("debug") + "off");
				parent.gamePanel.getStageLabel().setText(
						parent.rb.getString("stage")
								+ parent.gamePanel.getStage());
				parent.gamePanel.getBtn1().setText(
						parent.rb.getString("sp")
								+ parent.gamePanel.getSpTime());
				parent.gamePanel.getBtn1().setToolTipText(
						parent.rb.getString("sptip"));
				parent.gamePanel.getBtn2()
						.setText(parent.rb.getString("pause"));
				parent.gamePanel.getGoalText().setText(
						parent.rb.getString("target"));
				parent.gamePanel.getMovesText().setText(
						parent.rb.getString("moves"));
				parent.gamePanel.getScoreText().setText(
						parent.rb.getString("score"));
			}
			break;
		// case "debug":
		// if (frame.gamePanel.isDebug()) {
		// frame.gamePanel.setDebug(false);
		// debugBtn.setText(frame.rb.getString("debug") + "off");
		// } else {
		// frame.gamePanel.setDebug(true);
		// debugBtn.setText(frame.rb.getString("debug") + "on");
		// }
		// break;
		}
	}
}
