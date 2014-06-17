package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Candy extends JLabel {
	private final int type;
	private boolean del;
	private static boolean press = false;
	private static int grid;
	private Color[] colors = { Color.BLUE, Color.RED, Color.GREEN,
			Color.ORANGE, Color.YELLOW, Color.CYAN };
	private Polygon polygon = new Polygon();

	public int getType() {
		return type;
	}

	public boolean isDeleted() {
		return del;
	}

	public void setDel(boolean b) {
		del = b;
	}

	public static boolean isPressed() {
		return press;
	}

	public static void setPress(boolean b) {
		press = b;
	}

	public static int getGrid() {
		return grid;
	}

	public static void setGrid(int grid) {
		Candy.grid = grid;
	}

	public Candy(int type, int x, int y) {
		this.type = type;
		setDel(false);
		setBounds(x * grid, y * grid, grid, grid);
	}

	public boolean isSameType(Candy c) {
		return type == c.type;
	}

	public boolean isBlock() {
		return type == -1;
	}

	public boolean isBehind(Candy c) {
		Point p = getLocation();
		Point pc = c.getLocation();
		return Math.abs(p.x - pc.x) + Math.abs(p.y - pc.y) == grid;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isBlock())
			g.setColor(colors[type]);
		draw(g, type);
	}

	public void draw(Graphics g, int type) {
		polygon.reset();
		switch (type) {
		case 0:
			g.fillRect((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 1:
			g.fillOval((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 2:
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.1));
			polygon.addPoint((int) (grid * 0.1), (int) (grid * 0.8));
			polygon.addPoint((int) (grid * 0.9), (int) (grid * 0.8));
			g.fillPolygon(polygon);
			break;
		case 3:
			polygon.addPoint((int) (grid * 0.3), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.7), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.9), (int) (grid * 0.5));
			polygon.addPoint((int) (grid * 0.7), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.3), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.1), (int) (grid * 0.5));
			g.fillPolygon(polygon);
			break;
		case 4:
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.6), (int) (grid * 0.35));
			polygon.addPoint((int) (grid * 0.9), (int) (grid * 0.35));
			polygon.addPoint((int) (grid * 0.7), (int) (grid * 0.58));
			polygon.addPoint((int) (grid * 0.8), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.75));
			polygon.addPoint((int) (grid * 0.2), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.3), (int) (grid * 0.58));
			polygon.addPoint((int) (grid * 0.1), (int) (grid * 0.35));
			polygon.addPoint((int) (grid * 0.4), (int) (grid * 0.35));
			g.fillPolygon(polygon);
			break;
		case 5:
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.9), (int) (grid * 0.35));
			polygon.addPoint((int) (grid * 0.8), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.2), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.1), (int) (grid * 0.35));
			g.fillPolygon(polygon);
			break;
		}
	}

	public void highlight() {
		Graphics g = getGraphics();
		g.setColor(Color.RED);
		g.drawRect((int) (grid * 0.1), (int) (grid * 0.1), (int) (grid * 0.8),
				(int) (grid * 0.8));
	}
}
