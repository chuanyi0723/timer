import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerPanel extends JPanel implements ActionListener {
	private Timer t;
	int x;

	TimerPanel() {
		t = new Timer(200, this);
		x = 0;
		t.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(x, 80, 40, 40);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == t) {
			x += 10;
			repaint();
			if (x == 260) {
				t.stop();
				System.exit(0);
			}
		}
	}
}
