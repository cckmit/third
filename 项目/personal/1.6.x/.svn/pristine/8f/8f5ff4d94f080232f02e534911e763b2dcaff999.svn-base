package org.opoo.apps.dvi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 转换存储服务。
 * 
 * 该接口不对外暴露内容更新的方法。
 * 
 * @author lcj
 *
 */
public interface ConversionStorageService{
	/**
	 * 保存 Meta 实例。
	 * @param meta 被保存的
	 * @return 保存后meta中将含有有效的id
	 */
	ConversionMetaData saveMetaData(ConversionMetaData meta);
	
	ConversionMetaData updateMetaData(ConversionMetaData meta);
	
	ConversionMetaData getMetaData(long id);

	ConversionMetaData getMetaData(int objectType, long objectId, int version);

	int getMetaDataCount();

	int getMetaDataErrorCount(String filter);
	
	ConversionArtifact saveArtifact(ConversionArtifact ca, File file) throws Exception;
	
	ConversionArtifact getArtifact(ConversionMetaData cm, ConversionArtifactType type, int partNumber);
	
	InputStream getArtifactBody(ConversionArtifact artifact) throws IOException;

	int getArtifactCount(ConversionMetaData cm,	ConversionArtifactType type);

	void deleteAll(ConversionMetaData cm);
//
//	List<Long> getErrorConversionMetadataIDs(ResultFilter filter);
}
