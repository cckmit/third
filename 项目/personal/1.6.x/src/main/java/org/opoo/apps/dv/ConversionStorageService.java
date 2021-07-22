package org.opoo.apps.dv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * ת���洢����
 * 
 * �ýӿڲ����Ⱪ¶���ݸ��µķ�����
 * 
 * @author lcj
 *
 * @param <M>
 * @param <A>
 * @param <T>
 */
public interface ConversionStorageService<M extends ConversionMetaData, A extends ConversionArtifact<T>, T extends ConversionArtifactType> {
	/**
	 * ���� Meta ʵ����
	 * @param meta �������
	 * @return �����meta�н�������Ч��id
	 */
	M saveMetaData(M meta);
	
	M updateMetaData(M meta);
	
	M getMetaData(long id);

	M getMetaData(int objectType, long objectId, int version);

	int getMetaDataCount();

	int getMetaDataErrorCount(String filter);
	
	A saveArtifact(A ca, File file) throws Exception;
	
	A getArtifact(M cm, T type, int partNumber);
	
	InputStream getArtifactBody(A artifact) throws IOException;

	int getArtifactCount(M cm,	T type);

	void deleteAll(M cm);
//
//	List<Long> getErrorConversionMetadataIDs(ResultFilter filter);
}
