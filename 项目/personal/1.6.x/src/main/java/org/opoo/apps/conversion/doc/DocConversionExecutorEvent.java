package org.opoo.apps.conversion.doc;

import java.io.File;
import java.io.Serializable;

import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.event.v2.Event;


public class DocConversionExecutorEvent extends Event<DocConversionExecutorEvent.Type, DocConversionExecutorEvent.Payload> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5093741735169810719L;
	
	public DocConversionExecutorEvent(Type eventType, Payload payload) {
		super(eventType, payload);
	}

	public static enum Type{
		PdfGenerated, WebPreviewGenerated, ThumbnailGenerated, TextExtracted
	}
	
	public static class Payload extends DocConversionContext implements Serializable{
		private static final long serialVersionUID = -7577029155316837955L;
		private File generatedFile;
		private String message;
		private ConversionArtifactType type;
		private int page;
		public Payload(DocConversionContext context, ConversionArtifactType type, int page, File generatedFile) {
			super(context.getRevision(), context.getFile(), context.getPdf());
			this.type = type;
			this.page = page;
			this.generatedFile = generatedFile;
		}
		
		public Payload(DocConversionContext context, ConversionArtifactType type, int page, String message) {
			super(context.getRevision(), context.getFile(), context.getPdf());
			this.type = type;
			this.page = page;
			this.message = message;
		}
		public File getGeneratedFile() {
			return generatedFile;
		}
		public String getMessage() {
			return message;
		}
		public ConversionArtifactType getType() {
			return type;
		}
		public int getPage() {
			return page;
		}
	}
}
