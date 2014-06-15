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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (type) {
		case 0:
			g.setColor(Color.BLUE);
			g.fillRect((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 1:
			g.setColor(Color.RED);
			g.fillOval((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 2:
			Polygon polygon1 = new Polygon();
			g.setColor(Color.GREEN);
			polygon1.addPoint((int) (grid * 0.5), (int) (grid * 0.1));
			polygon1.addPoint((int) (grid * 0.1), (int) (grid * 0.8));
			polygon1.addPoint((int) (grid * 0.9), (int) (grid * 0.8));
			// g.drawPolygon(polygon1);
			g.fillPolygon(polygon1);
			break;
		case 3:
			Polygon polygon2 = new Polygon();
			g.setColor(Color.ORANGE);
			polygon2.addPoint((int) (grid * 0.3), (int) (grid * 0.15));
			polygon2.addPoint((int) (grid * 0.7), (int) (grid * 0.15));
			polygon2.addPoint((int) (grid * 0.9), (int) (grid * 0.5));
			polygon2.addPoint((int) (grid * 0.7), (int) (grid * 0.85));
			polygon2.addPoint((int) (grid * 0.3), (int) (grid * 0.85));
			polygon2.addPoint((int) (grid * 0.1), (int) (grid * 0.5));
			g.fillPolygon(polygon2);
			break;
		case 4:
			Polygon polygon3 = new Polygon();
			g.setColor(Color.YELLOW);
			polygon3.addPoint((int) (grid * 0.5), (int) (grid * 0.15));
			polygon3.addPoint((int) (grid * 0.6), (int) (grid * 0.35));
			polygon3.addPoint((int) (grid * 0.9), (int) (grid * 0.35));
			polygon3.addPoint((int) (grid * 0.7), (int) (grid * 0.58));
			polygon3.addPoint((int) (grid * 0.8), (int) (grid * 0.85));
			polygon3.addPoint((int) (grid * 0.5), (int) (grid * 0.75));
			polygon3.addPoint((int) (grid * 0.2), (int) (grid * 0.85));
			polygon3.addPoint((int) (grid * 0.3), (int) (grid * 0.58));
			polygon3.addPoint((int) (grid * 0.1), (int) (grid * 0.35));
			polygon3.addPoint((int) (grid * 0.4), (int) (grid * 0.35));
			g.fillPolygon(polygon3);
			break;
		case 5:
			Polygon polygon4 = new Polygon();
			g.setColor(Color.CYAN);
			polygon4.addPoint((int) (grid * 0.5), (int) (grid * 0.15));
			polygon4.addPoint((int) (grid * 0.9), (int) (grid * 0.35));
			polygon4.addPoint((int) (grid * 0.8), (int) (grid * 0.85));
			polygon4.addPoint((int) (grid * 0.2), (int) (grid * 0.85));
			polygon4.addPoint((int) (grid * 0.1), (int) (grid * 0.35));
			g.fillPolygon(polygon4);
			break;
		}
	}
}
