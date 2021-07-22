package org.opoo.apps.dv.office.converter;

import java.util.List;

import org.opoo.apps.dv.ConversionMetaData;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.office.OfficeConversionMetaData;

public abstract class OfficeConverter implements Converter {

	public final void convert(ConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers) {
		convert((OfficeConversionMetaData) cm, inputFile, partNumbers);
	}
	
	protected abstract void convert(OfficeConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers);
}
