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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, MouseListener {
	private final boolean debug = false; // track candy eliminate & fall down
	private CandyTest frame;
	private final Color bg = new Color(224, 224, 224);
	private final Color tile = new Color(128, 192, 128);
	private final int T_WIDTH = 9;
	private final int T_HEIGHT = 9;
	private final int T_GRID = 55;
	private final int finalStage = 3;
	private int base;
	private int stage;
	private int goal;
	private int initStep;
	private int step;
	private int score;
	private int[] categories;
	private int[][] mark;
	private JLabel stageLabel;
	// private JButton btn1;
	private JButton btn2;
	private JLabel goalText;
	private JLabel goalLabel;
	private JLabel movesText;
	private JLabel movesLabel;
	private JLabel scoreText;
	private JLabel scoreLabel;
	private final int delay = 20;
	private final int delta = T_GRID / 5;
	private Candy prev, cur;
	private Timer timer;
	// Candy swap variables
	private Candy from, to;
	private Point p1, p2;
	private int x; // initial location of to
	private int y;
	private int dx; // change in one period
	private int dy;
	private int dir;
	// tile painting variables
	// setting for T_GRID = 55
	private final int arcB = 6; // must even
	private final int arcC = 11; // T_GRID % c must be 0
	private final int arcB2 = arcB / 2;
	private final int arcA = arcC - arcB2;

	@SuppressWarnings("unused")
	private List<Candy> droplist = new ArrayList<Candy>();
	@SuppressWarnings("unused")
	private List<Point> pointlist = new ArrayList<Point>();
	@SuppressWarnings("unused")
	private int len;

	public GamePanel(CandyTest frame) {
		super();
		this.frame = frame;
		setLayout(null);
		setBackground(bg);
		Font f1 = new Font("Arial", Font.BOLD, 24);
		Font f2 = new Font("Arial", Font.BOLD, 16);
		Candy.setGrid(T_GRID);
		loadStage(0);
		stageLabel = new JLabel(frame.rb.getString("stage") + stage);
		stageLabel.setFont(f1);
		stageLabel.setBounds(5, 510, 160, 30);
		stageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// btn1 = new JButton(frame.rb.getString(""));
		btn2 = new JButton(frame.rb.getString("pause"));
		btn2.setBounds(375, 510, 80, 30);
		btn2.setForeground(Color.WHITE);
		btn2.setBackground(Color.BLACK);
		btn2.setMnemonic(KeyEvent.VK_P);
		btn2.addActionListener(this);
		btn2.setActionCommand("pause");
		goalText = new JLabel(frame.rb.getString("target"));
		goalText.setFont(f1);
		goalText.setHorizontalAlignment(SwingConstants.CENTER);
		goalText.setBounds(5, 550, 160, 30);
		goalLabel = new JLabel();
		goalLabel.setFont(f2);
		goalLabel.setBounds(5, 590, 160, 30);
		goalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		movesText = new JLabel(frame.rb.getString("moves"));
		movesText.setFont(f1);
		movesText.setHorizontalAlignment(SwingConstants.CENTER);
		movesText.setBounds(170, 550, 160, 30);
		movesLabel = new JLabel();
		movesLabel.setFont(f2);
		movesLabel.setBounds(170, 590, 160, 30);
		movesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreText = new JLabel(frame.rb.getString("score"));
		scoreText.setFont(f1);
		scoreText.setHorizontalAlignment(SwingConstants.CENTER);
		scoreText.setBounds(335, 550, 160, 30);
		scoreLabel = new JLabel();
		scoreLabel.setFont(f2);
		scoreLabel.setBounds(335, 590, 160, 30);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(stageLabel);
		// add(btn1);
		add(btn2);
		add(goalText);
		add(goalLabel);
		add(movesText);
		add(movesLabel);
		add(scoreText);
		add(scoreLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "sp":
			// for btn1
			break;
		case "pause":
			String[] options = { frame.rb.getString("resume"),
					frame.rb.getString("restart"), frame.rb.getString("home") };
			JLabel msg = new JLabel(frame.rb.getString("gamepause"));
			msg.setHorizontalAlignment(SwingConstants.CENTER);
			int choose = JOptionPane.showOptionDialog(this, msg,
					frame.rb.getString("pause"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
			if (choose == 0) {
			} else if (choose == 1)
				gameStart();
			else if (choose == 2) {
				frame.remove(frame.gamePanel);
				frame.add(frame.homePanel);
				frame.homePanel.repaint();
			}
			break;
		}
	}

	private void addCandies() {
		// TODO Animation
		for (int i = 0; i < T_WIDTH; i++)
			for (int j = 0; j < T_HEIGHT; j++)
				if (!hasCandy(i, j)) {
					Candy c = new Candy(newCandy(), i, j);
					add(c);
					c.addMouseListener(this);
				}
		/*
		 * timer = new Timer(delay, new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { timer.stop(); } }); timer.start();
		 */
		repaint();
		eliminate();
	}

	private boolean canSwap(Candy c1, Candy c2) {
		if (c1.isBlock() || c2.isBlock())
			return false;
		Candy ctmp1, ctmp2;
		boolean canSwap = false;
		int eliminate;
		swap(c1, c2);
		for (int i = 0; i < T_WIDTH - 2 && !canSwap; i++)
			for (int j = 0; j < T_HEIGHT && !canSwap; j++) {
				ctmp1 = getCandy(i, j);
				if (ctmp1.isBlock())
					continue;
				if (i > 0) {
					ctmp2 = getCandy(i - 1, j);
					if (ctmp1.isSameType(ctmp2))
						continue;
				}
				int k = i + 1;
				eliminate = 1;
				while (k < T_WIDTH) {
					ctmp2 = getCandy(k, j);
					if (!ctmp1.isSameType(ctmp2))
						break;
					eliminate++;
					k++;
				}
				if (eliminate > 2)
					canSwap = true;
			}
		for (int i = 0; i < T_WIDTH && !canSwap; i++)
			for (int j = 0; j < T_HEIGHT - 2 && !canSwap; j++) {
				ctmp1 = getCandy(i, j);
				if (ctmp1.isBlock())
					continue;
				if (j > 0) {
					ctmp2 = getCandy(i, j - 1);
					if (ctmp1.isSameType(ctmp2))
						continue;
				}
				int k = j + 1;
				eliminate = 1;
				while (k < T_HEIGHT) {
					ctmp2 = getCandy(i, k);
					if (!ctmp1.isSameType(ctmp2))
						break;
					eliminate++;
					k++;
				}
				if (eliminate > 2)
					canSwap = true;
			}
		swap(c1, c2);
		return canSwap;
	}

	private void eliminate() {
		Candy c1, c2;
		int eliminate;
		boolean chain = false;
		for (int i = 0; i < T_WIDTH - 2; i++)
			for (int j = 0; j < T_HEIGHT; j++) {
				c1 = getCandy(i, j);
				if (c1.isBlock())
					continue;
				if (i > 0) {
					c2 = getCandy(i - 1, j);
					if (c1.isSameType(c2))
						continue;
				}
				int k = i + 1;
				eliminate = 1;
				while (k < T_WIDTH) {
					c2 = getCandy(k, j);
					if (!c1.isSameType(c2))
						break;
					eliminate++;
					k++;
				}
				if (eliminate > 2) {
					for (k = 0; k < eliminate; k++)
						getCandy(i + k, j).setDel(true);
					score += 60 * (eliminate - 2) * base;
					scoreLabel.setText(String.valueOf(score));
					chain = true;
				}
			}
		for (int i = 0; i < T_WIDTH; i++)
			for (int j = 0; j < T_HEIGHT - 2; j++) {
				c1 = getCandy(i, j);
				if (c1.isBlock())
					continue;
				if (j > 0) {
					c2 = getCandy(i, j - 1);
					if (c1.isSameType(c2))
						continue;
				}
				int k = j + 1;
				eliminate = 1;
				while (k < T_HEIGHT) {
					c2 = getCandy(i, k);
					if (!c1.isSameType(c2))
						break;
					eliminate++;
					k++;
				}
				if (eliminate > 2) {
					for (k = 0; k < eliminate; k++)
						getCandy(i, j + k).setDel(true);
					score += 60 * (eliminate - 2) * base;
					scoreLabel.setText(String.valueOf(score));
					chain = true;
				}
			}
		if (chain) {
			if (debug)
				JOptionPane.showMessageDialog(null, base, "before eliminate",
						JOptionPane.INFORMATION_MESSAGE);
			for (int i = 0; i < T_WIDTH; i++)
				for (int j = 0; j < T_HEIGHT; j++)
					if (getCandy(i, j).isDel())
						remove(getCandy(i, j));
			System.gc();
			repaint();
			if (debug)
				JOptionPane.showMessageDialog(null, base, "after eliminate",
						JOptionPane.INFORMATION_MESSAGE);
			base++;
			fall();
		} else {
			Candy.setMoving(false);
		}
	}

	private void fall() {
		// TODO Animation
		for (int i = 0; i < T_WIDTH; i++)
			for (int j = T_HEIGHT - 1; j >= 0; j--)
				if (!hasCandy(i, j))
					for (int k = j - 1; k >= 0; k--) {
						if (hasCandy(i, k)) {
							Candy c = getCandy(i, k);
							if (!c.isBlock()) {
								moveTo(c, i, j);
								break;
							}
						}
					}
		/*
		 * timer = new Timer(delay, new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { timer.stop(); } }); timer.start();
		 */
		if (debug)
			JOptionPane.showMessageDialog(null, "candies fall");
		repaint();
		addCandies();
	}

	void gameStart() {
		Candy.setPress(false);
		do {
			for (int i = 0; i < T_WIDTH; i++)
				for (int j = 0; j < T_HEIGHT; j++)
					if (hasCandy(i, j))
						remove(getCandy(i, j));
			for (int i = 0; i < T_WIDTH; i++)
				for (int j = 0; j < T_HEIGHT; j++) {
					Candy candy;
					if (mark[j][i] == 1) {
						candy = new Candy(newCandy(), i, j);
						candy.addMouseListener(this);
					} else
						candy = new Candy(-1, i, j);
					add(candy);
				}
			tileInit();
		} while (!hasMove());
		stageLabel.setText(frame.rb.getString("stage") + stage);
		goalLabel.setText(String.valueOf(goal));
		step = initStep;
		movesLabel.setText(String.valueOf(step));
		score = 0;
		scoreLabel.setText(String.valueOf(score));
		repaint();
	}

	public JButton getBtn2() {
		return btn2;
	}

	private Candy getCandy(int i, int j) {
		if (hasCandy(i, j))
			return (Candy) getComponentAt(T_GRID * i, T_GRID * j);
		else
			return null;
	}

	public JLabel getGoalText() {
		return goalText;
	}

	public JLabel getMovesText() {
		return movesText;
	}

	public JLabel getScoreText() {
		return scoreText;
	}

	public int getStage() {
		return stage;
	}

	public JLabel getStageLabel() {
		return stageLabel;
	}

	public int getT_GRID() {
		return T_GRID;
	}

	public int getT_WIDTH() {
		return T_WIDTH;
	}

	private boolean hasCandy(int i, int j) {
		return getComponentAt(T_GRID * i, T_GRID * j).getClass()
				.getSimpleName().equals("Candy");
	}

	private boolean hasMove() {
		Candy c1, c2;
		for (int i = 0; i < T_WIDTH - 1; i++)
			for (int j = 0; j < T_HEIGHT; j++) {
				c1 = (Candy) getComponentAt(T_GRID * i, T_GRID * j);
				c2 = (Candy) getComponentAt(T_GRID * (i + 1), T_GRID * j);
				if (canSwap(c1, c2))
					return true;
			}
		for (int i = 0; i < T_WIDTH; i++)
			for (int j = 0; j < T_HEIGHT - 1; j++) {
				c1 = (Candy) getComponentAt(T_GRID * i, T_GRID * j);
				c2 = (Candy) getComponentAt(T_GRID * i, T_GRID * (j + 1));
				if (canSwap(c1, c2))
					return true;
			}
		return false;
	}

	private void invalidMove(Candy c1, Candy c2) {
		from = c1;
		to = c2;
		p1 = c1.getLocation();
		p2 = c2.getLocation();
		x = p2.x;
		y = p2.y;
		dx = (p1.x - p2.x) / delta;
		dy = (p1.y - p2.y) / delta;
		dir = 0;
		timer = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dir == 0) {
					p1.x -= dx;
					p1.y -= dy;
					p2.x += dx;
					p2.y += dy;
					from.setLocation(p1);
					to.setLocation(p2);
					if (p1.x == x && p1.y == y)
						dir = 1;
				} else {
					p1.x += dx;
					p1.y += dy;
					p2.x -= dx;
					p2.y -= dy;
					from.setLocation(p1);
					to.setLocation(p2);
					if (p2.x == x && p2.y == y)
						timer.stop();
				}
			}
		});
		timer.start();
	}

	void loadStage(int stage) {
		this.stage = stage;
		StageSetting s = new StageSetting(stage);
		categories = s.getCategories();
		mark = s.getMark();
		goal = s.getGoal();
		initStep = s.getInitStep();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (step > 0 && !Candy.isMoving()) {
			if (!Candy.isPress()) {
				Candy.setPress(true);
				prev = (Candy) arg0.getSource();
				prev.highlight();
			} else {
				Candy.setPress(false);
				prev.repaint();
				cur = (Candy) arg0.getSource();
				if (cur.isBehind(prev)) {
					if (canSwap(cur, prev))
						moveTo(cur, prev);
					else
						invalidMove(cur, prev);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	private void moveTo(Candy c1, Candy c2) {
		from = c1;
		to = c2;
		p1 = c1.getLocation();
		p2 = c2.getLocation();
		x = p2.x;
		y = p2.y;
		dx = (p1.x - p2.x) / delta;
		dy = (p1.y - p2.y) / delta;
		timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p1.x -= dx;
				p1.y -= dy;
				p2.x += dx;
				p2.y += dy;
				from.setLocation(p1);
				to.setLocation(p2);
				if (p1.x == x && p1.y == y) {
					timer.stop();
					base = 1;
					repaint();
					eliminate();
					step--;
					movesLabel.setText(String.valueOf(step));
					if (step == 0)
						stageEnd();
					else if (!hasMove()) {
						JOptionPane.showMessageDialog(frame,
								frame.rb.getString("nomovemsg"));
						shuffle();
					}
				}
			}
		});
		Candy.setMoving(true);
		timer.start();
	}

	private void moveTo(Candy c, int i, int j) {
		c.setLocation(T_GRID * i, T_GRID * j);
	}

	private int newCandy() {
		Random rand = new Random();
		int len = categories.length;
		return categories[rand.nextInt(len)];
	}

	@Override
	protected void paintComponent(Graphics g) { // paint game tile
		super.paintComponent(g);
		g.setColor(tile);
		for (int i = 0; i < T_WIDTH; i++)
			for (int j = 0; j < T_HEIGHT; j++)
				if (mark[j][i] == 1) {
					g.fillRoundRect(i * T_GRID, j * T_GRID, T_GRID, T_GRID,
							T_GRID * arcB / arcC, T_GRID * arcB / arcC);
					if (i != T_WIDTH - 1 && mark[j][i + 1] == 1) {
						g.fillRect(i * T_GRID + T_GRID * arcA / arcC, j
								* T_GRID, T_GRID * arcB / arcC, T_GRID * arcB2
								/ arcC);
						g.fillRect(i * T_GRID + T_GRID * arcA / arcC, j
								* T_GRID + T_GRID * arcA / arcC, T_GRID * arcB
								/ arcC, T_GRID * arcB2 / arcC);
					}
					if (j != T_HEIGHT - 1 && mark[j + 1][i] == 1) {
						g.fillRect(i * T_GRID, j * T_GRID + T_GRID * arcA
								/ arcC, T_GRID * arcB2 / arcC, T_GRID * arcB
								/ arcC);
						g.fillRect(i * T_GRID + T_GRID * arcA / arcC, j
								* T_GRID + T_GRID * arcA / arcC, T_GRID * arcB2
								/ arcC, T_GRID * arcB / arcC);
					}
				} else {
					if (i == 0) {
						if (j == 0)
							paintRD(g, i, j);
						else if (j == T_HEIGHT - 1)
							paintRU(g, i, j);
						else {
							paintRD(g, i, j);
							paintRU(g, i, j);
						}
					} else if (i == T_WIDTH - 1) {
						if (j == 0)
							paintLD(g, i, j);
						else if (j == T_HEIGHT - 1)
							paintLU(g, i, j);
						else {
							paintLD(g, i, j);
							paintLU(g, i, j);
						}
					} else {
						if (j == 0) {
							paintRD(g, i, j);
							paintLD(g, i, j);
						} else if (j == T_HEIGHT - 1) {
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
					g.fillRoundRect(i * T_GRID, j * T_GRID, T_GRID, T_GRID,
							T_GRID * arcB / arcC, T_GRID * arcB / arcC);
					g.setColor(tile);
				}
	}

	private void paintLD(Graphics g, int i, int j) {
		if (unblockL(i, j) && unblockD(i, j))
			g.fillRect(i * T_GRID, j * T_GRID + T_GRID * arcA / arcC, T_GRID
					* arcB2 / arcC, T_GRID * arcB2 / arcC);
	}

	private void paintLU(Graphics g, int i, int j) {
		if (unblockL(i, j) && unblockU(i, j))
			g.fillRect(i * T_GRID, j * T_GRID, T_GRID * arcB2 / arcC, T_GRID
					* arcB2 / arcC);
	}

	private void paintRD(Graphics g, int i, int j) {
		if (unblockR(i, j) && unblockD(i, j))
			g.fillRect(i * T_GRID + T_GRID * arcA / arcC, j * T_GRID + T_GRID
					* arcA / arcC, T_GRID * arcB2 / arcC, T_GRID * arcB2 / arcC);
	}

	private void paintRU(Graphics g, int i, int j) {
		if (unblockR(i, j) && unblockU(i, j))
			g.fillRect(i * T_GRID + T_GRID * arcA / arcC, j * T_GRID, T_GRID
					* arcB2 / arcC, T_GRID * arcB2 / arcC);
	}

	private void shuffle() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int wh = T_WIDTH * T_HEIGHT;
		while (!hasMove()) {
			for (int i = 0; i < 100; i++) {
				int a = rand.nextInt(wh);
				int b = rand.nextInt(wh);
				Point pA = new Point(a / T_HEIGHT, a % T_HEIGHT);
				Point pB = new Point(b / T_HEIGHT, b % T_HEIGHT);
				Candy cA = getCandy(pA.x, pA.y);
				Candy cB = getCandy(pB.x, pB.y);
				if (!canSwap(cA, cB) && !cA.isBlock() && !cB.isBlock())
					swap(cA, cB);
			}
		}
		repaint();
	}

	private void stageEnd() {
		JLabel msg;
		if (score >= goal) {
			msg = new JLabel(frame.rb.getString("clearmsg") + score);
			msg.setHorizontalAlignment(SwingConstants.CENTER);
			JOptionPane.showMessageDialog(this, msg,
					frame.rb.getString("gameclear"), JOptionPane.PLAIN_MESSAGE);
			if (stage < finalStage)
				loadStage(stage + 1);
			else
				stage++;
			if (stage <= finalStage)
				gameStart();
		} else {
			msg = new JLabel(frame.rb.getString("failedmsg"));
			msg.setHorizontalAlignment(SwingConstants.CENTER);
			int choose = JOptionPane.showConfirmDialog(this, msg,
					frame.rb.getString("gamefailed"),
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			switch (choose) {
			case JOptionPane.YES_OPTION:
				gameStart();
				break;
			case JOptionPane.NO_OPTION:
				frame.remove(frame.gamePanel);
				gameStart();
				frame.add(frame.homePanel);
				frame.homePanel.repaint();
				break;
			}
		}
	}

	private void swap(Candy c1, Candy c2) {
		Point p1 = c1.getLocation();
		Point p2 = c2.getLocation();
		c1.setLocation(p2);
		c2.setLocation(p1);
	}

	private void tileInit() {
		Candy c1, c2;
		boolean chain = true;
		int eliminate;
		while (chain) {
			chain = false;
			for (int i = 0; i < T_WIDTH - 2; i++)
				for (int j = 0; j < T_HEIGHT; j++) {
					c1 = getCandy(i, j);
					if (c1.isBlock())
						continue;
					if (i > 0) {
						c2 = getCandy(i - 1, j);
						if (c1.isSameType(c2))
							continue;
					}
					int k = i + 1;
					eliminate = 1;
					while (k < T_WIDTH) {
						c2 = getCandy(k, j);
						if (!c1.isSameType(c2))
							break;
						eliminate++;
						k++;
					}
					if (eliminate > 2) {
						for (k = 0; k < eliminate; k++)
							getCandy(i + k, j).setDel(true);
						chain = true;
					}
				}
			for (int i = 0; i < T_WIDTH; i++)
				for (int j = 0; j < T_HEIGHT - 2; j++) {
					c1 = getCandy(i, j);
					if (c1.isBlock())
						continue;
					if (j > 0) {
						c2 = getCandy(i, j - 1);
						if (c1.isSameType(c2))
							continue;
					}
					int k = j + 1;
					eliminate = 1;
					while (k < T_HEIGHT) {
						c2 = getCandy(i, k);
						if (!c1.isSameType(c2))
							break;
						eliminate++;
						k++;
					}
					if (eliminate > 2) {
						for (k = 0; k < eliminate; k++)
							getCandy(i, j + k).setDel(true);
						chain = true;
					}
				}
			if (chain) {
				// eliminate candies
				for (int i = 0; i < T_WIDTH; i++)
					for (int j = 0; j < T_HEIGHT; j++)
						if (getCandy(i, j).isDel())
							remove(getCandy(i, j));
				// candies fall down
				for (int i = 0; i < T_WIDTH; i++)
					for (int j = T_HEIGHT - 1; j >= 0; j--)
						if (!hasCandy(i, j))
							for (int k = j - 1; k >= 0; k--) {
								if (hasCandy(i, k)) {
									Candy c = getCandy(i, k);
									if (!c.isBlock()) {
										moveTo(c, i, j);
										break;
									}
								}
							}
				// add candies
				for (int i = 0; i < T_WIDTH; i++)
					for (int j = 0; j < T_HEIGHT; j++)
						if (!hasCandy(i, j)) {
							Candy c = new Candy(newCandy(), i, j);
							add(c);
							c.addMouseListener(this);
						}
			}
		}
	}

	private boolean unblockD(int i, int j) {
		return mark[j + 1][i] == 1;
	}

	private boolean unblockL(int i, int j) {
		return mark[j][i - 1] == 1;
	}

	private boolean unblockR(int i, int j) {
		return mark[j][i + 1] == 1;
	}

	private boolean unblockU(int i, int j) {
		return mark[j - 1][i] == 1;
	}
}
