package org.opoo.apps.dvi.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.opoo.apps.AppsObject;
import org.opoo.apps.AppsObjectLoader;
import org.opoo.apps.dv.ConvertableObject;
import org.opoo.apps.dv.provider.ConvertibleObjectProvider;
import org.opoo.apps.dv.provider.ConvertibleObjectProviderFactory;
import org.opoo.apps.dvi.ConversionArtifact;
import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.ConversionManager;
import org.opoo.apps.dvi.ConversionMetaData;
import org.opoo.apps.dvi.ConversionStorageService;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractConversionManager	implements ConversionManager {
	
	protected final Logger log = Logger.getLogger(getClass());
	
	protected ConversionStorageService conversionStorageService;
	protected ConvertibleObjectProviderFactory coProviderFactory;
	protected AppsObjectLoader appsObjectLoader;
	
	@Required
	public void setConversionStorageService(ConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}

	@Required
	public void setConvertibleObjectProviderFactory(
			ConvertibleObjectProviderFactory coProviderFactory) {
		this.coProviderFactory = coProviderFactory;
	}

	@Required
	public void setAppsObjectLoader(AppsObjectLoader appsObjectLoader) {
		this.appsObjectLoader = appsObjectLoader;
	}

	
	public ConversionMetaData getConversionMetaData(long id) {
		return conversionStorageService.getMetaData(id);
	}

	public ConversionMetaData getConversionMetaData(int convertableObjectType, long convertableObjectId, int version) {
		return conversionStorageService.getMetaData(convertableObjectType, convertableObjectId, version);
	}

	public int getConversionMetaDataCount() {
		return conversionStorageService.getMetaDataCount();
	}

	public int getConversionMetaDataErrorCount(String filter) {
		return conversionStorageService.getMetaDataErrorCount(filter);
	}

	public ConvertableObject getConvertableObject(ConversionMetaData cm) {
		try {
			AppsObject appsObject = appsObjectLoader.getAppsObject(cm.getConvertableObjectType(), cm.getConvertableObjectId());

            if (appsObject instanceof ConvertableObject) {
                ConvertableObject co = (ConvertableObject) appsObject;
                return coProviderFactory.hasRegisteredProvider(co) ? 
                		coProviderFactory.get(co).getVersion(cm.getRevisionNumber()) : null;
            }
        }
        catch (Exception e) {
            log.error("Error loading a convertable object for conversion metadata " + cm, e);
        }
        return null;
	}

	public ConversionArtifact getConversionArtifact(ConversionMetaData cm, ConversionArtifactType type, int partNumber) {
		return conversionStorageService.getArtifact(cm, type, partNumber);
	}

	public InputStream getConversionArtifactBody(ConversionArtifact ca) throws IOException {
		// put this into a ComversionManagerProxy some day???
        ConversionMetaData cm = getConversionMetaData(ca.getConversionMetadataID());

        if (cm == null) {
            return null;
        }

        ConvertableObject co = getConvertableObject(cm);
        if (co == null) {
            return null;
        }

        if (!coProviderFactory.hasRegisteredProvider(co) || !coProviderFactory.get(co).isAllowedToConvert()) {
        	//TODO UnauthorizedException
            throw new IllegalStateException(String.format("Current user has no permssion to request the conversion artifact of type %s  " +
                        "for page %d of the document %s", ca.getType().getName(), ca.getPartNumber(), cm.getFilename()));                 
        }
        return conversionStorageService.getArtifactBody(ca);
	}


	public void deleteAll(ConversionMetaData cm) {
		conversionStorageService.deleteAll(cm);
	}

	public ConversionMetaData getConversionMetaData(ConvertableObject co) {
		ConvertibleObjectProvider provider = coProviderFactory.get(co);
		if(provider != null){
			return getConversionMetaData(co.getObjectType(), co.getId(), provider.getRevisionNumber());
		}
		return null;
	}
	
	public boolean isAllowedToConvert(ConvertableObject co) {
		if (!isConversionEnabled()) {
            return false;
        }

        if (!isConvertable(co)) {
            return false;   
        }

        if (!coProviderFactory.hasRegisteredProvider(co)) {
            return false;
        }
        return coProviderFactory.get(co).isAllowedToConvert();
	}
}
