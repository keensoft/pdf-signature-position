package es.keensoft.pdf.signature.position.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.Rectangle;

public class ParsedPage {
	private float height;
	private float width;
	private List<Rectangle> signaturesLocation;

	public ParsedPage() {
		signaturesLocation = new ArrayList<Rectangle>();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public List<Rectangle> getSignaturesPosition() {
		return signaturesLocation;
	}

	@Override
	public String toString() {
		return "ParsedPage [height=" + height + ", width=" + width + ", signaturesLocation=" + Arrays.toString(signaturesLocation.toArray()) + "]";
	}

}
