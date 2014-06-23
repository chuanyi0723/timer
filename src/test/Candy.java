package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Candy extends JLabel {
	private static boolean moving;
	private static boolean press;
	private static int grid;

	public static int getGrid() {
		return grid;
	}

	public static boolean isMoving() {
		return moving;
	}

	public static boolean isPress() {
		return press;
	}

	public static void setGrid(int grid) {
		Candy.grid = grid;
	}

	public static void setMoving(boolean moving) {
		Candy.moving = moving;
	}

	public static void setPress(boolean press) {
		Candy.press = press;
	}

	private boolean del;
	private final int type;
	private final Color[] colors = { Color.BLUE, Color.RED, Color.GREEN,
			Color.ORANGE, Color.YELLOW, Color.CYAN };
	private Polygon polygon = new Polygon();

	public Candy(int type, int x, int y) {
		super();
		this.del = false;
		this.type = type;
		setBounds(x * grid, y * grid, grid, grid);
	}

	/** draw Candy */
	public void draw(Graphics g, int type) {
		polygon.reset();
		switch (type) {
		case 0:
			// square
			g.fillRect((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 1:
			// circle
			g.fillOval((int) (grid * 0.2), (int) (grid * 0.2),
					(int) (grid * 0.6), (int) (grid * 0.6));
			break;
		case 2:
			// triangle
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.2));
			polygon.addPoint((int) (grid * 0.15), (int) (grid * 0.8));
			polygon.addPoint((int) (grid * 0.85), (int) (grid * 0.8));
			g.fillPolygon(polygon);
			break;
		case 3:
			// hexagon
			polygon.addPoint((int) (grid * 0.3), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.7), (int) (grid * 0.15));
			polygon.addPoint((int) (grid * 0.9), (int) (grid * 0.5));
			polygon.addPoint((int) (grid * 0.7), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.3), (int) (grid * 0.85));
			polygon.addPoint((int) (grid * 0.1), (int) (grid * 0.5));
			g.fillPolygon(polygon);
			break;
		case 4:
			// star
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.14));
			polygon.addPoint((int) (grid * 0.59), (int) (grid * 0.42));
			polygon.addPoint((int) (grid * 0.88), (int) (grid * 0.42));
			polygon.addPoint((int) (grid * 0.64), (int) (grid * 0.59));
			polygon.addPoint((int) (grid * 0.73), (int) (grid * 0.86));
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.7));
			polygon.addPoint((int) (grid * 0.27), (int) (grid * 0.86));
			polygon.addPoint((int) (grid * 0.36), (int) (grid * 0.59));
			polygon.addPoint((int) (grid * 0.12), (int) (grid * 0.42));
			polygon.addPoint((int) (grid * 0.41), (int) (grid * 0.42));
			g.fillPolygon(polygon);
			break;
		case 5:
			// pentagon
			polygon.addPoint((int) (grid * 0.5), (int) (grid * 0.14));
			polygon.addPoint((int) (grid * 0.88), (int) (grid * 0.42));
			polygon.addPoint((int) (grid * 0.73), (int) (grid * 0.86));
			polygon.addPoint((int) (grid * 0.27), (int) (grid * 0.86));
			polygon.addPoint((int) (grid * 0.12), (int) (grid * 0.42));
			g.fillPolygon(polygon);
			break;
		}
	}

	/** highlight selected Candy */
	public void highlight() {
		Graphics g = getGraphics();
		g.setColor(Color.RED);
		g.drawRect((int) (grid * 0.1), (int) (grid * 0.1), (int) (grid * 0.8),
				(int) (grid * 0.8));
	}

	public boolean isBehind(Candy c) {
		Point p = getLocation();
		Point pc = c.getLocation();
		return Math.abs(p.x - pc.x) + Math.abs(p.y - pc.y) == grid;
	}

	public boolean isBlock() {
		return type == -1;
	}

	public boolean isDel() {
		return del;
	}

	public boolean isSameType(Candy c) {
		return type == c.type;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isBlock())
			g.setColor(colors[type]);
		draw(g, type);
	}

	public void setDel(boolean del) {
		this.del = del;
	}
}
