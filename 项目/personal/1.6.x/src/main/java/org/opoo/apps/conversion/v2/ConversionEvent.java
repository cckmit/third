package org.opoo.apps.conversion.v2;

import java.io.File;
import java.io.Serializable;

import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.event.v2.Event;

public class ConversionEvent extends Event<ConversionEvent.Type, ConversionEvent.Context> {
	private static final long serialVersionUID = -1540293989568636358L;
	public ConversionEvent(Type eventType, Context source) {
		super(eventType, source);
	}
	public static enum Type{
		FileGenerated, Error, Completion;
	}
	public static class Context implements Serializable{
		private static final long serialVersionUID = 5250322540997382772L;
		private final ConversionRevision revision;
		private final File generatedFile;
		private final int page;
		private final ConversionArtifactType type;
		private String message;
		private boolean needToSave = true;
		
		public Context(ConversionRevision revision,
				ConversionArtifactType type, int page, File generatedFile) {
			super();
			this.revision = revision;
			this.type = type;
			this.generatedFile = generatedFile;
			this.page = page;
		}
		public Context(ConversionRevision revision,
				ConversionArtifactType type, int page, String message) {
			super();
			this.revision = revision;
			this.type = type;
			this.page = page;
			this.message = message;
			this.generatedFile = null;
		}

		public Context(ConversionRevision revision, ConversionArtifactType type,
				int page) {
			this.revision = revision;
			this.type = type;
			this.generatedFile = null;
			this.page = page;
		}
		public ConversionRevision getRevision() {
			return revision;
		}
		public File getGeneratedFile() {
			return generatedFile;
		}
		public ConversionArtifactType getType() {
			return type;
		}
		public int getPage() {
			return page;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public boolean isNeedToSave() {
			return needToSave;
		}
		public void setNeedToSave(boolean needToSave) {
			this.needToSave = needToSave;
		}
	}
	
}
