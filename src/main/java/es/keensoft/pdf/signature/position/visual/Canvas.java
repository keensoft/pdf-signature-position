package es.keensoft.pdf.signature.position.visual;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

public class Canvas extends JComponent {
	private static final long serialVersionUID = 1L;

	private List<Box> boxes;

	public Canvas(List<Box> boxes) {
		this.boxes = boxes;
	}

	@Override
	public void paint(Graphics g) {
		for (Box box : boxes) {
			g.setColor(box.getColor());
			g.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
		}
	}

}
