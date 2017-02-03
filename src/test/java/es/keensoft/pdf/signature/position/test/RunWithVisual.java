package es.keensoft.pdf.signature.position.test;

import java.io.FileInputStream;

import org.slf4j.impl.SimpleLogger;

import es.keensoft.pdf.signature.position.PdfSignaturePosition;
import es.keensoft.pdf.signature.position.beans.SignaturePosition;
import es.keensoft.pdf.signature.position.visual.VisualRepresentation;

public class RunWithVisual {

	public static void main(String[] args) {
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		System.setProperty(SimpleLogger.LOG_FILE_KEY, "System.out");
		System.setProperty(VisualRepresentation.SYSTEM_PROPERTY_ENABLE_VISUAL, "");

		try {
			FileInputStream fis = new FileInputStream("src/test/resources/test-document.pdf");
			PdfSignaturePosition psp = new PdfSignaturePosition();
		
			//Default position
//			SignaturePosition nextPosition = psp.getNextPosition(fis, null);
//			System.out.println(nextPosition);
			
			//Force top to bottom
			SignaturePosition position = new SignaturePosition(null, 500F, 700F, 600F, 800F, 20F);
			SignaturePosition nextPosition = psp.getNextPosition(fis, position);
			System.out.println(nextPosition);
			
			//Force new line
//			SignaturePosition position = new SignaturePosition(null, 100F, 50F, 200F, 150F, 10F);
//			SignaturePosition nextPosition = psp.getNextPosition(fis, position );
//			System.out.println(nextPosition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
