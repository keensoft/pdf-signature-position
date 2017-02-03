package es.keensoft.pdf.signature.position.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfReader;

public class DocumentParser {
	public static ParsedPage parseSignaturePage(InputStream document, int pageNumber) throws IOException {
		ParsedPage page = new ParsedPage();

		PdfReader pdf = new PdfReader(document);
		int totalPages = pdf.getNumberOfPages();
		if (pageNumber > totalPages)
			throw new RuntimeException("Requested page '" + pageNumber + "' but document only contains '" + totalPages + "' page(s).");

		Rectangle pageSize = pdf.getPageSize(pageNumber);
		page.setHeight(pageSize.getHeight());
		page.setWidth(pageSize.getWidth());

		AcroFields acroFields = pdf.getAcroFields();
		List<String> signatureNames = acroFields.getSignatureNames();
		for (String sn : signatureNames) {
			List<FieldPosition> fps = acroFields.getFieldPositions(sn);
			if (fps != null && fps.size() > 0) {
				FieldPosition fp = fps.get(0);				
				if (fp.page==pageNumber && fp.position.getWidth() != 0 && fp.position.getHeight() != 0) {					
					page.getSignaturesPosition().add(fp.position);
				}
			}
		}

		return page;
	}
}
