package org.opoo.apps.dv.converter;

import java.util.List;

import org.opoo.apps.dv.ConversionMetaData;


/**
 * �ļ�ת����������Officeת����PDF2SWF��������Ƶת����rmvb to flv��ת������
 * 
 * @author lcj
 */
public interface Converter {

	/**
	 * ִ���ļ�ת����
	 * �����ת��Office�ļ���һ��Ҫָ��ҳ�루�ֶΣ����ϡ�
	 * 
	 * @param cm Ҫת���Ķ����Ԫ����
	 * @param inputFile �����ļ�
	 * @param partNumbers ҳ�루�ֶΣ�����
	 */
	 void convert(ConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers);
	 
}
