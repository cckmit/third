package org.opoo.apps.dv;

import java.io.Serializable;


/**
 * 转换过程中生成的部件，用以记录转换的每一个部分的详细情况，如 PDF 的一页，一张缩略图等，
 * 视频的第 N 部分等。
 * 
 * <p>通常部件由 conversionMetaDataID, type, partNumber 三个属性唯一标识，如果在集群环境中，
 * 还必须加上 instanceID, 则可唯一区分。
 * 
 * <p>该接口的实现类必须实现 equals(),hashCode()等方法。
 * @author lcj
 *
 * @param <T> 部件类型
 */
public interface ConversionArtifact<T extends ConversionArtifactType> extends Serializable {

	/**
	 * @return 实例ID
	 */
	String getInstanceID();

	/**
	 * @return 转换元数据ID
	 */
	long getConversionMetadataID();

	/**
	 * @return 部件类型
	 */
	T getType();
	
	/**
	 * @return 部件编号、分段索引、页码等
	 */
	int getPartNumber();

	String getFilename();

	String getContentType();

	long getLength();
	
	/**
	 * 用来存储部件对应的文件信息时的键字符换，必须和其他部件能区分。
	 * @return key
	 */
	String getStorageKey();
	
	/*
	boolean equals(Object o);

	int hashCode();
	*/
}
