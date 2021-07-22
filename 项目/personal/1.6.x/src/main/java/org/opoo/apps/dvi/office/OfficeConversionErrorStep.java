package org.opoo.apps.dvi.office;

import java.io.Serializable;
import java.util.Date;

public interface OfficeConversionErrorStep extends Serializable {
	
	public OfficeConversionStep getStep();

	public Date getModificationDate();

	public String getMessage();

	public long getConversionMetaDataID();
}
