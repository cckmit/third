package org.opoo.apps.dvi;

import java.util.Date;

/**
 * ת��״̬��
 * @author lcj
 *
 */
public interface ConversionStatus {

	boolean isConverting();

	String getError();

	Date getConversionStartedTime();

	Date getLastConvertionProgressTime();

	long getConversionMetaDataID();
}
