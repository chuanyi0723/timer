import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerPanel extends JPanel {
	private Timer t;
	private int posX;

	public Timer getT() {
		return t;
	}

	public void setT(Timer t) {
		this.t = t;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	TimerPanel() {
		posX = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(posX, 80, 40, 40);
	}
}
