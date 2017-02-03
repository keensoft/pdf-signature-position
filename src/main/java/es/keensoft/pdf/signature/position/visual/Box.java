package es.keensoft.pdf.signature.position.visual;

import java.awt.Color;

import com.itextpdf.text.Rectangle;

public class Box {
	private int x;
	private int y;
	private int height;
	private int width;
	private Color color;

	public Box(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public Box() {		
	}

	public void transformPosition(Rectangle r, int pageHeight, int pageWidth) {
		int top = (int) r.getTop();
		int left = (int) r.getLeft();
		int bottom = (int) r.getBottom();
		int right = (int) r.getRight();

		x = left;
		y = pageHeight - top;
		height = top - bottom;
		width = right - left;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
