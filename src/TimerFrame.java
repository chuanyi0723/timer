import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerFrame extends JFrame implements ActionListener {
	TimerPanel p;
	Timer t;

	TimerFrame() {
		setTitle("Timer");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new TimerPanel();
		t = new Timer(100, this);
		t.start();
		add(p);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == t) {
			int x = p.getPosX() + 10;
			p.setPosX(x);
			repaint();
			if (x == 260) {
				t.stop();
				System.exit(0);
			}
		}
	}
}