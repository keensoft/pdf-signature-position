package es.keensoft.pdf.signature.position.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.itextpdf.text.Rectangle;

import es.keensoft.pdf.signature.position.parser.ParsedPage;

public class VisualRepresentation {
	public static final String SYSTEM_PROPERTY_ENABLE_VISUAL = "signature.pdf.position.visual";
	private static boolean enabled = false;
	private List<Box> boxes;
	private int width;
	private int height;

	static {
		if (System.getProperty(SYSTEM_PROPERTY_ENABLE_VISUAL) != null) {
			enabled = true;
		}
	}

	public VisualRepresentation(ParsedPage page) {
		if (enabled) {
			boxes = new ArrayList<Box>();
			height = (int) page.getHeight();
			width = (int) page.getWidth();
			boxes.add(new Box(0, 0, width, height, Color.BLUE));

			List<Rectangle> signaturesPosition = page.getSignaturesPosition();
			for (Rectangle rectangle : signaturesPosition) {
				addBox(rectangle, Color.BLACK);
			}
		}
	}

	public void addBox(Rectangle rectangle, Color color) {
		if (enabled) {
			Box box = new Box();
			box.transformPosition(rectangle, height, width);
			box.setColor(color);
			boxes.add(box);
		}
	}

	public void draw() {
		if (enabled) {
			JFrame jframe;
			jframe = new JFrame();
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.setBounds(100, 100, width + 100, height + 100);
			jframe.add(new Canvas(boxes));
			jframe.setVisible(true);
		}
	}
}
