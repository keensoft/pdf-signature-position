package es.keensoft.pdf.signature.position.util;

import com.itextpdf.text.Rectangle;

import es.keensoft.pdf.signature.position.beans.SignaturePosition;

public class Util {

	public static Rectangle positionToRectangle(SignaturePosition position) {
		Rectangle rectangle = new Rectangle(position.getLowerLeftX(), position.getLowerLeftY(), position.getUpperRightX(), position.getUpperRightY());
		return rectangle;
	}

	public static void updatePosition(SignaturePosition position, Rectangle rectangle) {
		position.setLowerLeftX(rectangle.getLeft());
		position.setLowerLeftY(rectangle.getBottom());
		position.setUpperRightX(rectangle.getRight());
		position.setUpperRightY(rectangle.getTop());
	}

}
