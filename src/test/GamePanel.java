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
	Color bg = new Color(224, 224, 224);
	Color tile = new Color(128, 192, 128);

	public GamePanel(CandyTest frame) {
		this.frame = frame;
		setLayout(null);
		setBounds(50, 0, frame.grid * frame.width, 630);
		setBackground(bg);
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

	// setting for frame.grid = 55
	int b = 6;
	int c = 11;
	int b2 = b / 2;
	int a = c - b2;

	public void paintComponent(Graphics g) { // paint game tile
		super.paintComponent(g);
		int x = frame.width;
		int y = frame.height;
		g.setColor(tile);
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				if (frame.mark[j][i] == 1) {
					g.fillRoundRect(i * frame.grid, j * frame.grid, frame.grid,
							frame.grid, frame.grid * b / c, frame.grid * b / c);
					if (i != x - 1 && frame.mark[j][i + 1] == 1) {
						g.fillRect(i * frame.grid + frame.grid * a / c, j
								* frame.grid, frame.grid * b / c, frame.grid
								* b2 / c);
						g.fillRect(i * frame.grid + frame.grid * a / c, j
								* frame.grid + frame.grid * a / c, frame.grid
								* b / c, frame.grid * b2 / c);
					}
					if (j != y - 1 && frame.mark[j + 1][i] == 1) {
						g.fillRect(i * frame.grid, j * frame.grid + frame.grid
								* a / c, frame.grid * b2 / c, frame.grid * b
								/ c);
						g.fillRect(i * frame.grid + frame.grid * a / c, j
								* frame.grid + frame.grid * a / c, frame.grid
								* b2 / c, frame.grid * b / c);
					}
				} else {
					if (i == 0) {
						if (j == 0)
							paintRD(g, i, j);
						else if (j == y - 1)
							paintRU(g, i, j);
						else {
							paintRD(g, i, j);
							paintRU(g, i, j);
						}
					} else if (i == x - 1) {
						if (j == 0)
							paintLD(g, i, j);
						else if (j == y - 1)
							paintLU(g, i, j);
						else {
							paintLD(g, i, j);
							paintLU(g, i, j);
						}
					} else {
						if (j == 0) {
							paintRD(g, i, j);
							paintLD(g, i, j);
						} else if (j == y - 1) {
							paintRU(g, i, j);
							paintLU(g, i, j);
						} else {
							paintRD(g, i, j);
							paintRU(g, i, j);
							paintLD(g, i, j);
							paintLU(g, i, j);
						}
					}
					g.setColor(bg);
					g.fillRoundRect(i * frame.grid, j * frame.grid, frame.grid,
							frame.grid, frame.grid * b / c, frame.grid * b / c);
					g.setColor(tile);
				}
	}

	public boolean unblockL(int i, int j) {
		return frame.mark[j][i - 1] == 1;
	}

	public boolean unblockR(int i, int j) {
		return frame.mark[j][i + 1] == 1;
	}

	public boolean unblockU(int i, int j) {
		return frame.mark[j - 1][i] == 1;
	}

	public boolean unblockD(int i, int j) {
		return frame.mark[j + 1][i] == 1;
	}

	public void paintRD(Graphics g, int i, int j) {
		if (unblockR(i, j) && unblockD(i, j))
			g.fillRect(i * frame.grid + frame.grid * a / c, j * frame.grid
					+ frame.grid * a / c, frame.grid * b2 / c, frame.grid * b2
					/ c);
	}

	public void paintRU(Graphics g, int i, int j) {
		if (unblockR(i, j) && unblockU(i, j))
			g.fillRect(i * frame.grid + frame.grid * a / c, j * frame.grid,
					frame.grid * b2 / c, frame.grid * b2 / c);
	}

	public void paintLD(Graphics g, int i, int j) {
		if (unblockL(i, j) && unblockD(i, j))
			g.fillRect(i * frame.grid, j * frame.grid + frame.grid * a / c,
					frame.grid * b2 / c, frame.grid * b2 / c);
	}

	public void paintLU(Graphics g, int i, int j) {
		if (unblockL(i, j) && unblockU(i, j))
			g.fillRect(i * frame.grid, j * frame.grid, frame.grid * b2 / c,
					frame.grid * b2 / c);
	}
}
