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
	private CandyTest frame;
	private JLabel icon;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton debugBtn;

	public HomePanel(CandyTest frame) {
		super();
		this.frame = frame;
		setLayout(null);
		icon = new JLabel();
		icon.setIcon(new ImageIcon(getClass().getResource("/images/icon06.png")));
		icon.setBounds(122, 5, 256, 256);
		btn1 = new JButton(frame.rb.getString("newgame"));
		btn1.setBounds(190, 265, 120, 30);
		btn1.setMnemonic(KeyEvent.VK_N);
		btn1.setBackground(Color.PINK);
		btn1.addActionListener(this);
		btn1.setActionCommand("newgame");
		debugBtn = new JButton(frame.rb.getString("debug") + "off");
		debugBtn.setBounds(315, 265, 160, 30);
		debugBtn.setMnemonic(KeyEvent.VK_D);
		debugBtn.addActionListener(this);
		debugBtn.setActionCommand("debug");
		btn2 = new JButton(frame.rb.getString("continue"));
		btn2.setBounds(190, 300, 120, 30);
		btn2.setEnabled(false);
		btn2.setMnemonic(KeyEvent.VK_C);
		btn2.setBackground(Color.PINK);
		btn2.addActionListener(this);
		btn2.setActionCommand("continue");
		btn3 = new JButton(frame.rb.getString("exit"));
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
		add(debugBtn);
		add(btn2);
		add(btn3);
		add(btn4);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "newgame":
			frame.remove(frame.homePanel);
			btn2.setEnabled(true);
			frame.add(frame.gamePanel);
			frame.gamePanel.loadStage(0);
			frame.gamePanel.setSpTime(3);
			frame.gamePanel.gameStart();
			frame.repaint();
			break;
		case "continue":
			frame.remove(frame.homePanel);
			frame.add(frame.gamePanel);
			frame.repaint();
			break;
		case "exit":
			int choose = JOptionPane.showConfirmDialog(this,
					frame.rb.getString("leavemsg"), frame.rb.getString("exit"),
					JOptionPane.YES_NO_OPTION);
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
					frame.rb = ResourceBundle.getBundle("message",
							Locale.TAIWAN);
					break;
				case "English":
					frame.rb = ResourceBundle.getBundle("message",
							Locale.ENGLISH);
					break;
				}
				btn1.setText(frame.rb.getString("newgame"));
				btn2.setText(frame.rb.getString("continue"));
				btn3.setText(frame.rb.getString("exit"));
				if (!frame.debug)
					debugBtn.setText(frame.rb.getString("debug") + "off");
				else
					debugBtn.setText(frame.rb.getString("debug") + "on");
				frame.gamePanel.getStageLabel().setText(
						frame.rb.getString("stage")
								+ frame.gamePanel.getStage());
				frame.gamePanel.getBtn1().setText(
						frame.rb.getString("sp") + frame.gamePanel.getSpTime());
				frame.gamePanel.getBtn1().setToolTipText(
						frame.rb.getString("sptip"));
				frame.gamePanel.getBtn2().setText(frame.rb.getString("pause"));
				frame.gamePanel.getGoalText().setText(
						frame.rb.getString("target"));
				frame.gamePanel.getMovesText().setText(
						frame.rb.getString("moves"));
				frame.gamePanel.getScoreText().setText(
						frame.rb.getString("score"));
			}
			break;
		case "debug":
			if (frame.debug) {
				frame.debug = false;
				debugBtn.setText(frame.rb.getString("debug") + "off");
			} else {
				frame.debug = true;
				debugBtn.setText(frame.rb.getString("debug") + "on");
			}
			break;
		}
	}
}
