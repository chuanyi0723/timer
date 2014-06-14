package test;

import java.awt.Color;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Candy extends JLabel {
	private final int type;
	private boolean del;
	private static boolean press = false;
	private static int grid;

	//Icon icon0 = new ImageIcon("image/icon06.png");

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
		setOpaque(true);
		switch (type) {
		case -1:
			setText("X");
			setBackground(Color.BLACK);
			break;
		case 0:
			setText("0");
			setBackground(Color.BLUE);
			//setIcon(icon0);
			break;
		case 1:
			setText("1");
			setBackground(Color.RED);
			break;
		case 2:
			setText("2");
			setBackground(Color.GREEN);
			break;
		case 3:
			setText("3");
			setBackground(Color.ORANGE);
			break;
		case 4:
			setText("4");
			setBackground(Color.YELLOW);
			break;
		case 5:
			setText("5");
			setBackground(Color.CYAN);
			break;
		}
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
}
