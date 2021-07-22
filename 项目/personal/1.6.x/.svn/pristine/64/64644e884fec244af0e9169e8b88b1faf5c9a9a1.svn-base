package org.opoo.apps.dv.office.event;

import java.io.File;
import java.io.Serializable;

import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStep;
import org.opoo.apps.event.v2.Event;


public class OfficeConversionEvent extends Event<OfficeConversionEvent.Type, OfficeConversionEvent.ConversionEventPayload> {
	private static final long serialVersionUID = 8142072427981647571L;

	public OfficeConversionEvent(Type type, ConversionEventPayload o){
		super(type, o);
	}
	
	public static enum Type {
		PDF_GENERATED, THUMBMATIL_PAGE_GENERATED, PREVIEW_PAGE_GENERATED, TEXT_INDEX_GENERATED, ERROR
	}
	
	public static final class ConversionEventPayload implements Serializable {
		private static final long serialVersionUID = -7915593741222974368L;
		private OfficeConversionArtifact conversionArtifact;
        private OfficeConversionMetaData conversionMetaData;
        private File generatedArtifactData;
        private OfficeConversionStep conversionStep;
        private String message;

        public ConversionEventPayload(OfficeConversionMetaData conversionMetaData,
        		OfficeConversionArtifact conversionArtifact, File generatedArtifactData) {
            this.conversionArtifact = conversionArtifact;
            this.conversionMetaData = conversionMetaData;
            this.generatedArtifactData = generatedArtifactData;

        }

        /**
         * In-case-of-an-error constructor
         *
         * @param conversionMetaData
         * @param conversionArtifact
         */
        public ConversionEventPayload(OfficeConversionMetaData conversionMetaData, 
        		OfficeConversionArtifact conversionArtifact, String message, OfficeConversionStep conversionStep ) {
            this.conversionArtifact = conversionArtifact;
            this.conversionMetaData = conversionMetaData;            
            this.message = message;
            this.conversionStep = conversionStep;
        }

        public OfficeConversionArtifact getConversionArtifact() {
            return conversionArtifact;
        }

        public OfficeConversionMetaData getConversionMetaData() {
            return conversionMetaData;
        }

        public File getGeneratedArtifactData() {
            return generatedArtifactData;
        }        

        public String getMessage() {
            return message;
        }

        public OfficeConversionStep getConversionStep() {
			return conversionStep;
		}

		@Override
        public String toString() {
            return "ConversionEventPayload{" + "conversionArtifact=" + conversionArtifact + ", conversionMetaData="
                    + conversionMetaData + ", generatedArtifactData=" + generatedArtifactData + ", message='" + message
                    + '\'' + ", conversionStep=" + conversionStep + '}';
        }
    }
}
