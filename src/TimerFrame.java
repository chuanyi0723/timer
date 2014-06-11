import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TimerFrame extends JFrame {
	TimerPanel p = new TimerPanel();

	TimerFrame() {
		setTitle("Timer");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(p);
	}
}