package es.keensoft.pdf.signature.position;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Rectangle;

import es.keensoft.pdf.signature.position.beans.SignaturePosition;
import es.keensoft.pdf.signature.position.parser.DocumentParser;
import es.keensoft.pdf.signature.position.parser.ParsedPage;
import es.keensoft.pdf.signature.position.util.Util;
import es.keensoft.pdf.signature.position.visual.VisualRepresentation;

public class PdfSignaturePosition {
	private final Logger log = LoggerFactory.getLogger(PdfSignaturePosition.class);
	private final String DEFAULT_PROPERTIES = "signature-pdf-position.properties";
	private final int MAX_CANDIDATES = 50;
	private final SignaturePosition defaultPosition = loadDefaultPositionFromProperties();

	public SignaturePosition getNextPosition(InputStream document, SignaturePosition desiredPosition) throws IOException {
		SignaturePosition position = defaultPosition.merge(desiredPosition);
		log.trace("Position (default): {}", defaultPosition);
		log.trace("Position (desired): {}", desiredPosition);
		log.debug("Position (initial): {}", position);

		int page = position.getPage();
		float margin = position.getMargin();
		Rectangle signatureRectangle = Util.positionToRectangle(position);

		ParsedPage signaturePage = DocumentParser.parseSignaturePage(document, page);
		log.debug("Signature page ({}): {}", page, signaturePage);
		VisualRepresentation vr = new VisualRepresentation(signaturePage);
		vr.addBox(signatureRectangle, Color.RED);

		Rectangle collision = checkCollision(signatureRectangle, signaturePage);
		boolean outInRight = checkOutInRight(signatureRectangle, signaturePage, margin);
		boolean outInTop = checkOuthInTop(signatureRectangle, signaturePage, margin);
		int candidate = 1;
		while (collision != null || outInRight || outInTop) {
			if (candidate == MAX_CANDIDATES)
				throw new RuntimeException("Maximum candidates reached (" + candidate + ").");

			updatePosition(signatureRectangle, collision, outInRight, outInTop, margin);
			collision = checkCollision(signatureRectangle, signaturePage);
			outInRight = checkOutInRight(signatureRectangle, signaturePage, margin);
			outInTop = checkOuthInTop(signatureRectangle, signaturePage, margin);

			vr.addBox(signatureRectangle, Color.ORANGE);
			candidate++;
		}

		vr.addBox(signatureRectangle, Color.GREEN);
		vr.draw();

		Util.updatePosition(position, signatureRectangle);
		log.debug("Position (final) ({} candidates): {}", candidate, position);
		return position;
	}

	protected void updatePosition(Rectangle signatureRectangle, Rectangle collision, boolean outInRight, boolean outInTop, float margin) {
		if (collision != null) {
			movePositionRight(signatureRectangle, margin);
		} else if (outInRight) {
			movePositionLineUp(signatureRectangle, margin);
		} else if (outInTop) {
			movePositionLineBottom(signatureRectangle, margin);
		}
	}

	private void movePositionLineBottom(Rectangle signatureRectangle, float margin) {
		float height = signatureRectangle.getHeight();
		float width = signatureRectangle.getWidth();

		signatureRectangle.setTop(height + margin);
		signatureRectangle.setLeft(margin);
		signatureRectangle.setBottom(margin);
		signatureRectangle.setRight(width + margin);
	}

	private boolean checkOuthInTop(Rectangle signatureRectangle, ParsedPage signaturePage, float margin) {
		return (signatureRectangle.getTop() + margin) > signaturePage.getHeight();
	}

	private boolean checkOutInRight(Rectangle signatureRectangle, ParsedPage signaturePage, float margin) {
		return (signatureRectangle.getRight() + margin) > signaturePage.getWidth();
	}

	private void movePositionLineUp(Rectangle signatureRectangle, float margin) {
		float height = signatureRectangle.getHeight();
		float width = signatureRectangle.getWidth();
		signatureRectangle.setTop(signatureRectangle.getTop() + height + margin);
		signatureRectangle.setLeft(margin);
		signatureRectangle.setBottom(signatureRectangle.getTop() - height);
		signatureRectangle.setRight(signatureRectangle.getLeft() + width);
	}

	private void movePositionRight(Rectangle signatureRectangle, float margin) {
		float width = signatureRectangle.getWidth();
		signatureRectangle.setLeft(signatureRectangle.getLeft() + width + margin);
		signatureRectangle.setRight(signatureRectangle.getRight() + width + margin);
	}

	private Rectangle checkCollision(Rectangle signatureRectangle, ParsedPage signaturePage) {
		List<Rectangle> signaturesPosition = signaturePage.getSignaturesPosition();
		for (Rectangle rectangle : signaturesPosition) {
			if (overlap(rectangle, signatureRectangle))
				return rectangle;
		}

		return null;
	}

	// http://www.geeksforgeeks.org/find-two-rectangles-overlap/
	private boolean overlap(Rectangle rectangle, Rectangle signatureRectangle) {
		if (rectangle.getLeft() > signatureRectangle.getRight() || signatureRectangle.getLeft() > rectangle.getRight())
			return false;
		if (rectangle.getTop() < signatureRectangle.getBottom() || signatureRectangle.getTop() < rectangle.getBottom())
			return false;
		return true;
	}

	private SignaturePosition loadDefaultPositionFromProperties() {
		try {
			Properties properties = new Properties();
			properties.load(PdfSignaturePosition.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES));

			SignaturePosition position = new SignaturePosition();
			position.setPage(Integer.parseInt(properties.getProperty("signature.pdf.position.default.page")));
			position.setLowerLeftX(Float.parseFloat(properties.getProperty("signature.pdf.position.default.lowerLeftX")));
			position.setLowerLeftY(Float.parseFloat(properties.getProperty("signature.pdf.position.default.lowerLeftY")));
			position.setUpperRightX(Float.parseFloat(properties.getProperty("signature.pdf.position.default.upperRightX")));
			position.setUpperRightY(Float.parseFloat(properties.getProperty("signature.pdf.position.default.upperRightY")));
			position.setMargin(Float.parseFloat(properties.getProperty("signature.pdf.position.default.margin")));
			return position;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}