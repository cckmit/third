package org.opoo.apps.dvi.converter;

import org.opoo.apps.dvi.ConversionArtifactType;

/**
 * ת����������
 * @author lcj
 *
 */
public interface ConverterFactory{

	/**
	 * ����ָ�����͵�ת������
	 * 
	 * @param type ����
	 * @return ת����
	 */
	Converter getConverter(ConversionArtifactType type);
}
