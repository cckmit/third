package org.opoo.apps.conversion.v2.doc.event;

import java.io.File;

import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.v2.doc.DocConversionArtifactType;
import org.opoo.apps.conversion.v2.doc.DocRequestEvent;

public class ThumbnailRequestEvent extends DocRequestEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4044887260163666023L;

	public ThumbnailRequestEvent(ConversionRevision revision, int page, File pdf) {
		super(DocRequestEvent.Type.ThumbnailRequest, new DocRequestEvent.Request(revision, DocConversionArtifactType.Thumbnail, page, pdf));
	}
	
	public File getPdfFile(){
		return getSource().getInputFile();
	}
}
