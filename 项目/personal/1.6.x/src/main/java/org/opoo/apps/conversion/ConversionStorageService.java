package org.opoo.apps.conversion;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;


public interface ConversionStorageService {

	/**
	 * 根据ID查询转换文件。
	 * @param revisionId
	 * @return
	 */
	ConversionRevision getRevision(long revisionId);
	
//	/**
//	 * 根据原始文件查询转换文件。
//	 * @param object
//	 * @return
//	 */
//	ConversionRevision getRevision(ConvertableObject object);
	
	ConversionRevision getRevision(int objectType, long objectId, int revisionNumber);

	/**
	 * 创建转换文件。
	 * @param objectType 相关对象的类型
	 * @param objectId 相关对象的ID
	 * @param filename 文件名
	 * @param realMimeType 文件类型
	 * @param contentLength 文件大小
	 * @param numberOfPages 文档页数/视频时长等
	 * @param revisionNumber 版本号
	 * @param metadata 头信息
	 * @return 保存后的对象
	 */
	ConversionRevision saveRevision(int objectType, long objectId, 
			String filename, String realMimeType, long contentLength, 
			int revisionNumber,
			int numberOfPages, String metadata);
	
	//ConversionRevision saveRevision(ConvertableObject object, int revisionNumber, String metadata);
	
	
	/**
	 * 
	 * @param rev
	 */
	void updateRevision(ConversionRevision rev);
	
	
	/**
	 * 删除所有错误步骤信息。
	 * @param revision
	 */
	void deleteErrorSteps(ConversionRevision revision);
	
	/**
	 * 保存步骤信息。
	 * @param revisionId
	 * @param type
	 * @param message
	 * @return
	 */
	ConversionErrorStep saveErrorStep(long revisionId, ConversionArtifactType type, String message);


	ConversionArtifact getArtifact(ConversionRevision revision, ConversionArtifactType type, int page);

	/**
	 * 
	 * @param revisionId
	 * @param type
	 * @param page
	 * @param filename
	 * @param contentType
	 * @param length
	 * @param file
	 * @return
	 */
	ConversionArtifact saveArtifact(long revisionId, ConversionArtifactType type,
			int page, String filename, String contentType, long length, File file) throws Exception;

	/**
	 * 
	 * @param a
	 * @return
	 */
	InputStream getArtifactStream(ConversionArtifact a);

	/**
	 * 
	 * @param revision
	 * @param type
	 * @return
	 */
	int getArtifactCount(ConversionRevision revision, ConversionArtifactType type);
	
	void deleteAll(ConversionRevision rev);
	
	int getRevisionCount();
	
	int getErrorRevisionCount();
	
	List<Long> getErrorRevisionIds(ResultFilter filter);
}
