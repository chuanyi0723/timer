package test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickedListener implements MouseListener {
	private GamePanel parent;
	private static Candy from, to;

	ClickedListener(GamePanel gamePanel) {
		super();
		parent = gamePanel;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO
		if (parent.getStep() > 0 && !Candy.isMoving()) {
			if (!Candy.isPress()) {
				Candy.setPress(true);
				from = (Candy) arg0.getSource();
				parent.setPrev(from);
				from.highlight();
			} else {
				Candy.setPress(false);
				from.repaint();
				to = (Candy) arg0.getSource();
				if (to.isBehind(from)) {
					if (parent.canSwap(from, to))
						parent.moveTo(from, to);
					else
						parent.invalidMove(from, to);
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
}
