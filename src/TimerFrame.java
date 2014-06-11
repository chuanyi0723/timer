import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerFrame extends JFrame implements ActionListener{
	TimerPanel p = new TimerPanel();

	TimerFrame() {
		setTitle("Timer");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.setT(new Timer(100, this));
		p.getT().start();
		add(p);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == p.getT()) {
			int x = p.getPosX() + 10;
			p.setPosX(x);
			repaint();
			if (x == 260) {
				p.getT().stop();
				System.exit(0);
			}
		}
	}
}