package org.opoo.apps.dv.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.opoo.apps.AppsObject;
import org.opoo.apps.AppsObjectLoader;
import org.opoo.apps.dv.ConversionArtifact;
import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.ConversionManager;
import org.opoo.apps.dv.ConversionMetaData;
import org.opoo.apps.dv.ConversionStatus;
import org.opoo.apps.dv.ConversionStep;
import org.opoo.apps.dv.ConversionStorageService;
import org.opoo.apps.dv.ConvertableObject;
import org.opoo.apps.dv.provider.ConvertibleObjectProvider;
import org.opoo.apps.dv.provider.ConvertibleObjectProviderFactory;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractConversionManager
		<M extends ConversionMetaData, A extends ConversionArtifact<T>, 
		T extends ConversionArtifactType, S extends ConversionStatus,
		P extends ConversionStep,
		C extends ConversionStorageService<M,A,T>>
		implements ConversionManager<M, A, T, S, P> {
	
	protected final Logger log = Logger.getLogger(getClass());
	
	protected C conversionStorageService;
	protected ConvertibleObjectProviderFactory coProviderFactory;
	protected AppsObjectLoader appsObjectLoader;
	
	@Required
	public void setConversionStorageService(C conversionStorageService) {
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

	
	public M getConversionMetaData(long id) {
		return conversionStorageService.getMetaData(id);
	}

	public M getConversionMetaData(int convertableObjectType, long convertableObjectId, int version) {
		return conversionStorageService.getMetaData(convertableObjectType, convertableObjectId, version);
	}

	public int getConversionMetaDataCount() {
		return conversionStorageService.getMetaDataCount();
	}

	public int getConversionMetaDataErrorCount(String filter) {
		return conversionStorageService.getMetaDataErrorCount(filter);
	}

	public ConvertableObject getConvertableObject(M cm) {
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

	public A getConversionArtifact(M cm, T type, int partNumber) {
		return conversionStorageService.getArtifact(cm, type, partNumber);
	}

	public InputStream getConversionArtifactBody(A ca) throws IOException {
		// put this into a ComversionManagerProxy some day???
        M cm = getConversionMetaData(ca.getConversionMetadataID());

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


	public void deleteAll(M cm) {
		conversionStorageService.deleteAll(cm);
	}

	public M getConversionMetaData(ConvertableObject co) {
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
