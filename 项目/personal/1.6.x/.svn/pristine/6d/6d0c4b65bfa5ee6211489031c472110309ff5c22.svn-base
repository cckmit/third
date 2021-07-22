package org.opoo.apps.conversion.v2.doc;

import java.io.File;
import java.io.Serializable;

import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.event.v2.Event;

public class DocRequestEvent extends Event<DocRequestEvent.Type, DocRequestEvent.Request> {
	private static final long serialVersionUID = -1372243234212690580L;

	public DocRequestEvent(Type eventType, Request source) {
		super(eventType, source);
	}

	public static enum Type{
		PdfRequest, PreviewRequest, ThumbnailRequest;
	}
	
	public static class Request implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8340432263298034812L;
		private final ConversionRevision revision;
		private final int page;
		private final ConversionArtifactType type;
		private final File inputFile;
		
		public Request(ConversionRevision revision,
				ConversionArtifactType type, int page, File inputFile) {
			super();
			this.revision = revision;
			this.type = type;
			this.page = page;
			this.inputFile = inputFile;
		}
		public ConversionRevision getRevision() {
			return revision;
		}
		public int getPage() {
			return page;
		}
		public ConversionArtifactType getType() {
			return type;
		}
		public File getInputFile() {
			return inputFile;
		}
	}
}
