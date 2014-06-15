package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	CandyTest frame;

	public GamePanel(CandyTest frame) {
		this.frame = frame;
		setLayout(null);
		setBounds(0, 0, 500, 660);
		Font f = new Font("Arial", Font.BOLD, 24);
		JLabel l1 = new JLabel("Target");
		l1.setFont(f);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(5, 550, 160, 30);
		JLabel l2 = new JLabel("Moves");
		l2.setFont(f);
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setBounds(170, 550, 160, 30);
		JLabel l3 = new JLabel("Score");
		l3.setFont(f);
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setBounds(335, 550, 160, 30);
		add(l1);
		add(l2);
		add(l3);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = frame.width;
		int y = frame.height;
		g.setColor(new Color(0, 64, 0, 128));
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				if (frame.mark[j][i] == 1)
					g.fillRoundRect(i * frame.grid, j * frame.grid, frame.grid,
							frame.grid, frame.grid / 4, frame.grid / 4);
	}
}
