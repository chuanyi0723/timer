package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class CandyTest extends JFrame implements ActionListener, MouseListener {
	JPanel homePanel;
	GamePanel gamePanel;
	final int type = 6;
	final int width = 9;
	final int height = 9;
	final int grid = 55;
	int stage;
	final int finalStage = 3;
	int[] categories;
	int score;
	int goal = 1000;
	int initStep;
	int step;
	// test
	// int[][] temp = new int[][] { { 0, 1, 2, 3, 4 }, { 5, 0, 1, 2, 3 },
	// { 4, 0, 1, 2, 0 }, { 3, 4, 5, 1, 1 }, { 2, 3, 4, 2, 0 } };
	int[][] mark = new int[][] { { 0, 1, 1, 0, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 1, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 1, 0, 0, 0, 1, 1, 0 } };
	JLabel stageLabel = new JLabel();
	JLabel scoreLabel = new JLabel();
	JLabel goalLabel = new JLabel();
	JLabel stepLabel = new JLabel();
	Candy[][] candies = new Candy[width][height];
	Timer timer;
	JButton homeBtn1 = new JButton("New Game");
	JButton homeBtn2 = new JButton("Continue");
	JButton homeBtn3 = new JButton("Exit");
	JButton gameBtn = new JButton("Pause");
	JLabel homeIcon = new JLabel();

	public static void main(String[] args) {
		CandyTest f = new CandyTest();
		f.setVisible(true);
		// TODO check random type
		// int[] counter = new int[6];
		// for (int i = 0;i<20000;i++) {
		// int r = f.newCandy();
		// counter[r]++;
		// }
		// for (int i = 0;i<6;i++)
		// System.out.println(i+" "+counter[i]);
	}

	public CandyTest() {
		setTitle("CandyTest");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 660);
		setLocationRelativeTo(null);
		homePanel = new JPanel();
		homePanel.setLayout(null);
		homePanel.setBounds(50, 0, 500, getHeight() - 30);
		homeIcon.setIcon(new ImageIcon("image/icon06.png"));
		// homeIcon.setIcon(new
		// ImageIcon(getClass().getResource("/image/icon06.png")));
		homeIcon.setBounds(122, 5, 256, 256);
		homeBtn1.setBounds(190, 265, 120, 30);
		homeBtn1.setBackground(Color.PINK);
		homeBtn1.addActionListener(this);
		homeBtn1.setMnemonic(KeyEvent.VK_N);
		homeBtn1.setActionCommand("start");
		homeBtn2.setBounds(190, 300, 120, 30);
		homeBtn2.setBackground(Color.PINK);
		homeBtn2.setMnemonic(KeyEvent.VK_C);
		homeBtn2.setEnabled(false);
		homeBtn2.addActionListener(this);
		homeBtn2.setActionCommand("continue");
		homeBtn3.setBounds(190, 335, 120, 30);
		homeBtn3.setBackground(Color.PINK);
		homeBtn3.setMnemonic(KeyEvent.VK_E);
		homeBtn3.addActionListener(this);
		homeBtn3.setActionCommand("exit");
		homePanel.add(homeIcon);
		homePanel.add(homeBtn1);
		homePanel.add(homeBtn2);
		homePanel.add(homeBtn3);
		add(homePanel);
		Candy.setGrid(grid);
		gamePanel = new GamePanel(this);
		Font f1 = new Font("Arial", Font.BOLD, 24);
		Font f2 = new Font("Arial", Font.BOLD, 16);
		stageLabel.setFont(f1);
		stageLabel.setBounds(5, 510, 160, 30);
		stageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setFont(f2);
		scoreLabel.setBounds(335, 590, 160, 30);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		goalLabel.setFont(f2);
		goalLabel.setBounds(5, 590, 160, 30);
		goalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stepLabel.setFont(f2);
		stepLabel.setBounds(170, 590, 160, 30);
		stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameBtn.setBounds(375, 510, 80, 30);
		gameBtn.setForeground(Color.WHITE);
		gameBtn.setBackground(Color.BLACK);
		gameBtn.setMnemonic(KeyEvent.VK_P);
		gameBtn.addActionListener(this);
		gameBtn.setActionCommand("pause");
		gamePanel.add(stageLabel);
		gamePanel.add(goalLabel);
		gamePanel.add(stepLabel);
		gamePanel.add(scoreLabel);
		gamePanel.add(gameBtn);
		// gameStart();
		// add(gamePanel);
	}

	public void loadStage(int n) {
		stage = n;
		StageSetting s = new StageSetting(n);
		categories = s.getCategories();
		goal = s.getGoalGrade();
		goalLabel.setText(String.valueOf(goal));
		initStep = s.getInitStep();
		mark = s.getMark();
	}

	public void gameStart() {
		Candy.setPress(false);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				if (candies[i][j] != null)
					gamePanel.remove(candies[i][j]);
				candies[i][j] = null;
			}
		do {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Candy c;
					if (mark[j][i] == 1)
						c = new Candy(newCandy(), i, j);
					// c= new Candy(temp[j][i], i, j);
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
		scoreLabel.setText(String.valueOf(score));
		step = initStep;
		stepLabel.setText(String.valueOf(step));
		stageLabel.setText("Stage " + stage);
		goalLabel.setText(String.valueOf(goal));
		gamePanel.repaint();
	}

	public int newCandy() {
		int len = categories.length;
		int rand = (int) (Math.random() * len);
		return categories[rand];
	}

	Candy cLast, c;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (step > 0) {
			if (!Candy.isPressed()) {
				cLast = (Candy) arg0.getSource();
				if (!cLast.isBlock()) {
					// System.out.println(cLast.getType() + " "
					// + cLast.getLocation().x / grid + " "
					// + cLast.getLocation().y / grid);
					// Point p = cLast.getLocation();
					// System.out.println(p.x + " " + p.y);
					// cLast.setForeground(Color.RED);
					Graphics g = cLast.getGraphics();
					g.setColor(Color.RED);
					g.drawRect((int) (grid * 0.1), (int) (grid * 0.1),
							(int) (grid * 0.8), (int) (grid * 0.8));
					Candy.setPress(true);
				}
			} else {
				// cLast.setForeground(Color.BLACK);
				cLast.repaint();
				Candy.setPress(false);
				c = (Candy) arg0.getSource();
				if (!c.isBlock() && c.isBehind(cLast)) {
					if (canSwap(c, cLast)) {
						// swap(c, cLast);
						moveTo(c, cLast); // swap than eliminating
						// step--;
						// stepLabel.setText("Move: " + step);
					} else {
						invalidMove(c, cLast);
					}
				}
				// ////////////////////
				// erase();
				// if (step <= 0) {
				// if (score >= goal)
				// System.out.println("Clear " + score);
				// else
				// System.out.println("Fail");
				// return;
				// } else if (!hasMove()) {
				// JOptionPane.showMessageDialog(this, "No move! Refresh");
				// do {
				// for (int k = 0; k < 100; k++) {
				// int t = width * height;
				// int a = (int) (Math.random() * t);
				// int b = (int) (Math.random() * t);
				// int x1 = a / height;
				// int y1 = a % height;
				// int x2 = b / height;
				// int y2 = b % height;
				// if (!canSwap(candies[x1][y1], candies[x2][y2])
				// && !candies[x1][y1].isBlock()
				// && !candies[x2][y2].isBlock())
				// swap(candies[x1][y1], candies[x2][y2]);
				// }
				// } while (!hasMove());
				// }
				// ///////////////////
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
						// System.out.println(60 * (eliminate - 2) * base); //
						// score
						score += 60 * (eliminate - 2) * base;
						scoreLabel.setText(String.valueOf(score));
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
						// System.out.println(60 * (eliminate - 2) * base); //
						// score
						score += 60 * (eliminate - 2) * base;
						scoreLabel.setText(String.valueOf(score));
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
		timer = new Timer(20, this);
		timer.setActionCommand("move");
		from = c1;
		x1 = c1.getLocation().x;
		y1 = c1.getLocation().y;
		to = c2;
		x = x2 = c2.getLocation().x;
		y = y2 = c2.getLocation().y;
		dx = (x1 - x2) / grid * 5;
		dy = (y1 - y2) / grid * 5;
		timer.start();
	}

	int dir;

	public void invalidMove(Candy c1, Candy c2) {
		// isMoving = true;
		timer = new Timer(20, this);
		timer.setActionCommand("invalid");
		from = c1;
		x1 = c1.getLocation().x;
		y1 = c1.getLocation().y;
		to = c2;
		x = x2 = c2.getLocation().x;
		y = y2 = c2.getLocation().y;
		dx = (x1 - x2) / grid * 5;
		dy = (y1 - y2) / grid * 5;
		dir = 0;
		timer.start();
	}

	int d;

	public void drop(Candy c1, Candy c2) {
		// TODO
		timer = new Timer(20, this);
		timer.setActionCommand("drop");
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
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getActionCommand().equals("start")) {
			remove(homePanel);
			loadStage(0);
			gameStart();
			if (!homeBtn2.isEnabled())
				homeBtn2.setEnabled(true);
			add(gamePanel);
			gamePanel.repaint();
		} else if (arg0.getActionCommand().equals("continue")) {
			remove(homePanel);
			add(gamePanel);
			gamePanel.repaint();
		} else if (arg0.getActionCommand().equals("exit")) {
			int choose = JOptionPane.showConfirmDialog(this, "Are you sure?",
					"Exit", JOptionPane.YES_NO_OPTION);
			if (choose == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if (arg0.getActionCommand().equals("pause")) {
			String[] options = { "Resume", "Restart", "Home" };
			JLabel msg = new JLabel("Game pause");
			msg.setHorizontalAlignment(SwingConstants.CENTER);
			int x = JOptionPane.showOptionDialog(this, msg, "Pause",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[2]);
			if (x == JOptionPane.YES_OPTION) {
			} else if (x == JOptionPane.NO_OPTION)
				gameStart();
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
				timer.stop();
			}
		} else if (arg0.getActionCommand().equals("invalid")) {
			if (dir == 0) {
				x1 -= dx;
				y1 -= dy;
				x2 += dx;
				y2 += dy;
				from.setLocation(x1, y1);
				to.setLocation(x2, y2);
				if (x1 == x && y1 == y)
					dir = 1;
			} else {
				x1 += dx;
				y1 += dy;
				x2 -= dx;
				y2 -= dy;
				from.setLocation(x1, y1);
				to.setLocation(x2, y2);
				if (x2 == x && y2 == y)
					timer.stop();
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
				timer.stop();
				step--;
				stepLabel.setText(String.valueOf(step));
				erase();
				if (step <= 0) {
					JLabel msg;
					if (score >= goal) {
						msg = new JLabel("Your score: " + score);
						msg.setHorizontalAlignment(SwingConstants.CENTER);
						JOptionPane.showMessageDialog(this, msg, "Clear",
								JOptionPane.PLAIN_MESSAGE);
						if (stage < finalStage)
							loadStage(stage + 1);
						else
							stage++;
						if (stage <= finalStage)
							gameStart();
					} else {
						msg = new JLabel("Challenge again?");
						int choose = JOptionPane.showConfirmDialog(this, msg,
								"Fail", JOptionPane.YES_NO_OPTION);
						switch (choose) {
						case JOptionPane.YES_OPTION:
							gameStart();
							break;
						case JOptionPane.NO_OPTION:
							remove(gamePanel);
							gameStart();
							add(homePanel);
							homePanel.repaint();
							break;
						}
					}
				} else if (!hasMove()) {
					JOptionPane.showMessageDialog(this, "No move! Refresh");
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
							// TODO has problem
							// moveTo(candies[x1][y1], candies[x2][y2]);
						}
					} while (!hasMove());
				}
			}
		}
	}
}
