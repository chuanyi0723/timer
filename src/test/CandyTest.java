package test;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class CandyTest extends JFrame implements ActionListener, MouseListener {
	JPanel homePanel;
	JPanel gamePanel;
	final int type = 6;
	final int width = 5;
	final int height = 5;
	final int grid = 100;
	// int stage;
	int score;
	int goal = 1000;
	int step;
	// test
	int[][] temp = new int[][] { { 0, 1, 2, 3, 4 }, { 5, 0, 1, 2, 3 },
			{ 4, 0, 1, 2, 0 }, { 3, 4, 5, 1, 1 }, { 2, 3, 4, 2, 0 } };
	int[][] mark = new int[][] { { 0, 1, 1, 0, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 1, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 1, 0, 0, 0, 1, 1, 0 } };
	JLabel scoreLabel = new JLabel();
	JLabel goalLabel = new JLabel();
	JLabel stepLabel = new JLabel();
	Candy[][] candies = new Candy[width][height];
	Timer t;
	JButton homeBtn1 = new JButton("Start");
	JButton homeBtn2 = new JButton("Exit");
	JButton gameBtn = new JButton("Pause");

	public static void main(String[] args) {
		CandyTest f = new CandyTest();
		f.setVisible(true);
	}

	public CandyTest() {
		setTitle("CandyTest");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);
		setLocationRelativeTo(null);
		homePanel = new JPanel();
		homePanel.add(homeBtn1);
		homeBtn1.addActionListener(this);
		homeBtn1.setActionCommand("start");
		homePanel.add(homeBtn2);
		homeBtn2.addActionListener(this);
		homeBtn2.setActionCommand("exit");
		homePanel.setBounds(0, 0, 500, 700);
		add(homePanel);
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		Candy.setGrid(grid);
		// //// function gameStart()
		do {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Candy c;
					if (mark[j][i] == 1)
						// c = new Candy(newCandy(), i, j);
						c = new Candy(temp[j][i], i, j);
					else
						c = new Candy(-1, i, j);
					candies[i][j] = c;
					c.addMouseListener(this);
					gamePanel.add(c);
				}
			}
			erase();
		} while (!hasMove());
		score = 0;
		scoreLabel.setText("Score: " + score);
		step = 15;
		stepLabel.setText("Move: " + step);
		goalLabel.setText("Goal: " + goal);
		// //////
		scoreLabel.setBounds(50, 550, 200, 50);
		goalLabel.setBounds(50, 600, 400, 50);
		stepLabel.setBounds(250, 550, 200, 50);
		gameBtn.setBounds(300, 600, 100, 50);
		gameBtn.addActionListener(this);
		gameBtn.setActionCommand("pause");
		gamePanel.add(goalLabel);
		gamePanel.add(stepLabel);
		gamePanel.add(scoreLabel);
		gamePanel.add(gameBtn);
		gamePanel.setBounds(0, 0, 500, 700);
		// add(gamePanel);
	}

	public void gameStart() {
		do {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Candy c;
					if (mark[j][i] == 1)
						// c = new Candy(newCandy(), i, j);
						c = new Candy(temp[j][i], i, j);
					else
						c = new Candy(-1, i, j);
					candies[i][j] = c;
					c.addMouseListener(this);
					gamePanel.add(c);
				}
			}
			erase();
		} while (!hasMove());
		score = 0;
		scoreLabel.setText("Score: " + score);
		step = 15;
		stepLabel.setText("Move: " + step);
		goalLabel.setText("Goal: " + goal);
	}

	public void restart() {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				gamePanel.remove(candies[i][j]);
				candies[i][j] = null;
			}
		gameStart();
		gamePanel.repaint();
	}

	public int newCandy() {
		return (int) (Math.random() * type);
	}

	Candy cLast, c;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (step > 0) {
			if (!Candy.isPressed()) {
				cLast = (Candy) arg0.getSource();
				if (!cLast.isBlock()) {
					System.out.println(cLast.getType() + " "
							+ cLast.getLocation().x / grid + " "
							+ cLast.getLocation().y / grid);
					// Point p = cLast.getLocation();
					// System.out.println(p.x + " " + p.y);
					cLast.setForeground(Color.RED);
					Candy.setPress(true);
				}
			} else {
				cLast.setForeground(Color.BLACK);
				Candy.setPress(false);
				c = (Candy) arg0.getSource();
				if (!c.isBlock() && c.isBehind(cLast) && canSwap(c, cLast)) {
					// swap(c, cLast);
					moveTo(c, cLast);
				}
				// erase();

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void erase() {
		boolean chain = true;
		int base = 1;
		int eliminate;
		while (chain) {
			chain = false;
			for (int i = 0; i < width - 2; i++)
				for (int j = 0; j < height; j++) {
					if (candies[i][j].isBlock() || i > 0
							&& candies[i - 1][j].isSameType(candies[i][j]))
						continue;
					int k = i + 1;
					eliminate = 1;
					while (k < width) {
						if (candies[i][j].isSameType(candies[k][j]))
							eliminate++;
						else
							break;
						k++;
					}
					if (eliminate > 2) {
						for (k = 0; k < eliminate; k++)
							candies[i + k][j].setDel(true);
						System.out.println(60 * (eliminate - 2) * base); // score
						score += 60 * (eliminate - 2) * base;
						scoreLabel.setText("Score: " + score);
						chain = true;
					}
				}
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height - 2; j++) {
					if (candies[i][j].isBlock() || j > 0
							&& candies[i][j - 1].isSameType(candies[i][j]))
						continue;
					int k = j + 1;
					eliminate = 1;
					while (k < height) {
						if (candies[i][j].isSameType(candies[i][k]))
							eliminate++;
						else
							break;
						k++;
					}
					if (eliminate > 2) {
						for (k = 0; k < eliminate; k++)
							candies[i][j + k].setDel(true);
						System.out.println(60 * (eliminate - 2) * base); // score
						score += 60 * (eliminate - 2) * base;
						scoreLabel.setText("Score: " + score);
						chain = true;
					}
				}
			if (chain) {
				for (int i = 0; i < width; i++)
					for (int j = height - 1; j >= 0; j--)
						if (candies[i][j].isDeleted()) {
							gamePanel.remove(candies[i][j]);
							int k = j - 1;
							while (k >= 0) {
								if (!candies[i][k].isBlock()
										&& !candies[i][k].isDeleted()) {
									swap(candies[i][j], candies[i][k]);
									// moveTo(candies[i][j], candies[i][k]);
									// drop(candies[i][k], candies[i][j]);
									break;
								}
								k--;
							}
							if (k < 0) {
								candies[i][j] = new Candy(newCandy(), i, j);
								gamePanel.add(candies[i][j]);
								candies[i][j].addMouseListener(this);
							}
						}
				gamePanel.repaint();
			}
			base++;
		}
	}

	public boolean canSwap(Candy c1, Candy c2) {
		boolean canSwap = false;
		int eliminate;
		if (c1.isBlock() || c2.isBlock())
			return false;
		swap(c1, c2);
		for (int i = 0; i < width - 2 && !canSwap; i++)
			for (int j = 0; j < height && !canSwap; j++) {
				if (candies[i][j].isBlock() || i > 0
						&& candies[i - 1][j].isSameType(candies[i][j]))
					continue;
				int k = i + 1;
				eliminate = 1;
				while (k < width) {
					if (candies[i][j].isSameType(candies[k][j]))
						eliminate++;
					else
						break;
					k++;
				}
				if (eliminate > 2)
					canSwap = true;
			}
		for (int i = 0; i < width && !canSwap; i++)
			for (int j = 0; j < height - 2 && !canSwap; j++) {
				if (candies[i][j].isBlock() || j > 0
						&& candies[i][j - 1].isSameType(candies[i][j]))
					continue;
				int k = j + 1;
				eliminate = 1;
				while (k < height) {
					if (candies[i][j].isSameType(candies[i][k]))
						eliminate++;
					else
						break;
					k++;
				}
				if (eliminate > 2)
					canSwap = true;
			}
		swap(c1, c2);
		return canSwap;
	}

	public boolean hasMove() {
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height - 1; j++)
				if (canSwap(candies[i][j], candies[i + 1][j])
						|| canSwap(candies[i][j], candies[i][j + 1]))
					return true;
			if (canSwap(candies[i][height - 1], candies[i + 1][height - 1]))
				return true;
		}
		for (int j = 0; j < height - 1; j++)
			if (canSwap(candies[width - 1][j], candies[width - 1][j + 1]))
				return true;
		return false;
	}

	public void swap(Candy c1, Candy c2) {
		Point p1 = c1.getLocation();
		Point p2 = c2.getLocation();
		candies[p2.x / grid][p2.y / grid] = c1;
		c1.setLocation(p2.x, p2.y);
		candies[p1.x / grid][p1.y / grid] = c2;
		c2.setLocation(p1.x, p1.y);
	}

	int dx, dy, x1, x2, y1, y2, x, y;
	Candy from, to;

	public void moveTo(Candy c1, Candy c2) {
		// isMoving = true;
		t = new Timer(20, this);
		t.setActionCommand("move");
		from = c1;
		x1 = c1.getLocation().x;
		y1 = c1.getLocation().y;
		to = c2;
		x = x2 = c2.getLocation().x;
		y = y2 = c2.getLocation().y;
		dx = (x1 - x2) / grid * 5;
		dy = (y1 - y2) / grid * 5;
		t.start();
	}

	int d;

	public void drop(Candy c1, Candy c2) {
		t = new Timer(20, this);
		t.setActionCommand("drop");
		from = c1;
		x1 = c1.getLocation().x;
		y1 = c1.getLocation().y;
		to = c2;
		x = x2 = c2.getLocation().x;
		y = y2 = c2.getLocation().y;
		dx = (x1 - x2) / grid * 5;
		dy = (y1 - y2) / grid * 5;
		candies[x1 / grid][y1 / grid] = c2;
		d = grid;
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getActionCommand().equals("start")) {
			remove(homePanel);
			add(gamePanel);
			gamePanel.repaint();
		} else if (arg0.getActionCommand().equals("exit")) {
			int choose = JOptionPane.showConfirmDialog(this, "", "",
					JOptionPane.YES_NO_OPTION);
			if (choose == JOptionPane.OK_OPTION)
				System.exit(0);
		} else if (arg0.getActionCommand().equals("pause")) {
			String[] options = { "Resume", "Restart", "Exit" };
			int x = JOptionPane.showOptionDialog(this, "Please select one:",
					"Input", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			if (x == JOptionPane.YES_OPTION) {
			} else if (x == JOptionPane.NO_OPTION)
				restart();
			else if (x == JOptionPane.CANCEL_OPTION) {
				remove(gamePanel);
				add(homePanel);
				homePanel.repaint();
			}
		} else if (arg0.getActionCommand().equals("drop")) {
			x1 -= dx;
			y1 -= dy;
			d -= 5;
			from.setLocation(x1, y1);
			if (d == 0) {
				candies[x1 / grid][y1 / grid] = from;
				t.stop();
			}
		} else if (arg0.getActionCommand().equals("move")) {
			x1 -= dx;
			y1 -= dy;
			x2 += dx;
			y2 += dy;
			from.setLocation(x1, y1);
			to.setLocation(x2, y2);
			if (x1 == x && y1 == y) {
				candies[x1 / grid][y1 / grid] = from;
				candies[x2 / grid][y2 / grid] = to;
				t.stop();
				erase();
				step--;
				stepLabel.setText("Move: " + step);
				if (step <= 0) {
					if (score >= goal)
						System.out.println("Clear " + score);
					else
						System.out.println("Fail");
					return;
				} else if (!hasMove()) {
					do {
						for (int k = 0; k < 100; k++) {
							int t = width * height;
							int a = (int) (Math.random() * t);
							int b = (int) (Math.random() * t);
							int x1 = a / height;
							int y1 = a % height;
							int x2 = b / height;
							int y2 = b % height;
							if (!canSwap(candies[x1][y1], candies[x2][y2])
									&& !candies[x1][y1].isBlock()
									&& !candies[x2][y2].isBlock())
								swap(candies[x1][y1], candies[x2][y2]);
						}
					} while (!hasMove());
				}
			}
		}
	}
}