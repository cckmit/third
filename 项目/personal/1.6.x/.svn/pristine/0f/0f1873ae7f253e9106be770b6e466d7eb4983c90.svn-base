package org.opoo.apps.conversion.v2.doc.event;

import java.io.File;

import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.v2.doc.DocConversionArtifactType;
import org.opoo.apps.conversion.v2.doc.DocRequestEvent;

public class PdfRequestEvent extends DocRequestEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4044887260163666023L;

	public PdfRequestEvent(ConversionRevision revision, int page, File file) {
		super(DocRequestEvent.Type.PdfRequest, new DocRequestEvent.Request(revision, DocConversionArtifactType.Pdf, page, file));
	}
	
	public File getOriginalFile(){
		return getSource().getInputFile();
	}
}
