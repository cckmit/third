package org.opoo.apps.dv.office.impl;

import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.AppsObject;
import org.opoo.apps.dv.office.OfficeConversionArtifact;

public class TestOfficeConversionManagerImpl extends OfficeConversionManagerImpl {

	@Override
	public boolean isOfficeIntegrationLicensed() {
		return true;
	}
	
	@Override
	public boolean isConversionEnabled(){
		return true;
	}
	
	@Override
	public boolean isConvertable(AppsObject appsObject){
		return true;
	}
	
	@Override
	public InputStream getConversionArtifactBody(OfficeConversionArtifact ca) throws IOException{
		return conversionStorageService.getArtifactBody(ca);
	}
}
