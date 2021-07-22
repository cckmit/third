package org.opoo.apps.dv.office.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.converter.GenericOfficeConverter.ConversionCommand;
import org.opoo.apps.util.FileUtils;

public class TestOfficeConversionStorageServiceImpl extends
		OfficeConversionStorageServiceImpl {

	protected InputStream getArtifactFile2(OfficeConversionArtifact artifact) throws IOException {
		File file = buildFile(artifact);
		return FileUtils.newFileInputStream(file);
	}

	protected boolean saveArtifactFile2(OfficeConversionArtifact ca, File file) throws IOException {
		File target = buildFile(ca);
		if(target.exists()){
			return false;
		}else{
			FileUtils.copyFileToFile(file, target);
			return target.exists() && target.length() > 0;
		}
	}

	private File buildFile(OfficeConversionArtifact ca){
		ConversionCommand command = ConversionCommand.getConversionCommand(ca.getType());
		String s = String.format("%s-%s-%s.%s", ca.getInstanceID(), ca.getConversionMetadataID(), ca.getPage(), command.getOutExt());
		return new File("D:\\temp\\fs\\" + s);
	}
}
