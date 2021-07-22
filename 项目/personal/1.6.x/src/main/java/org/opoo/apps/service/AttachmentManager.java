package org.opoo.apps.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.file.Details;
import org.opoo.ndao.support.ResultFilter;

public interface AttachmentManager {
	
	/**
	 * 根据条件查询附件信息。
	 * 
	 * @param rf
	 * @return
	 */
	List<Attachment> findAttachments(ResultFilter rf);
 	/**
	 * 保存附件。
	 * 从数据流中读取附件，并保存在附件处理系统中。
	 * 
	 * @param is
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	Attachment store(InputStream is, String fileName, String contentType);

    /**
     * 保存附件。
     * 从数据流中读取附件，并保存在附件处理系统中。并根据需要进行文件格式的转换。
     *
     * @param is
     * @param fileName
     * @param contentType
     * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
     * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
     * @return
     * @see org.opoo.apps.file.FileStorage#store(InputStream, String, String, Long, String, String)
     */
    Attachment store(InputStream is, String fileName, String contentType, String protectedFormat, String readableFormat);
	
	/**
	 * 保存附件。
	 * 从临时文件中读取附件，并保存在附件处理系统中。
	 * 
	 * @param tmpFile
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	Attachment store(File tmpFile, String fileName, String contentType);

    /**
     * 保存附件。
     * 从数据流中读取附件，并保存在附件处理系统中。并根据需要进行文件格式的转换。
     *
     * @param tmpFile
     * @param fileName
     * @param contentType
     * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
     * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
     * @return
     */
    Attachment store(File tmpFile, String fileName, String contentType, String protectedFormat, String readableFormat);
	
	/**
	 * 
	 * @param id
	 * @param os
	 */
	//void fetch(Long id, OutputStream os);
	
	/**
	 * 获取附件（原始格式）的数据流。
	 * 
	 * @param id
	 * @return
	 */
	InputStream fetch(Long id);
	
	/**
	 * 获取指定格式的附件。
	 * 
	 * @param id
	 * @param format
	 * @return
	 */
	InputStream fetch(Long id, String format);
	
	/**
	 * 获取指定的附件信息。
	 * 
	 * @param id
	 * @return
	 */
	Attachment getAttachment(long id);
	
	/**
	 * 删除指定的附件信息及附件数据（文件）。
	 * 
	 * @param id
	 */
	void removeAttachment(long id);
	
	/**
	 * 删除指定的附件信息及附件数据（文件）。
	 * 
	 * @param ids
	 */
	void removeAttachments(Long[] ids);
	
	/**
	 * 删除指定的附件信息及附件数据（文件）。
	 * 
	 * @param a
	 */
	void deleteAttachment(Attachment a);

	/**
	 * 更新现有的附件。
	 * @param id
	 * @param tmpFile
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	Attachment update(long id, File tmpFile, String fileName, String contentType);

    /**
     * 更新现有的附件。
     * @param id
     * @param tmpFile
     * @param fileName
     * @param contentType
     * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
     * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
     * @return
     */
    Attachment update(long id, File tmpFile, String fileName, String contentType, String protectedFormat, String readableFormat);


	/**
	 * 更新现有的附件。
	 * @param id
	 * @param is
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	Attachment update(long id, InputStream is, String fileName, String contentType);

    /**
     * 更新现有的附件。
     * @param id
     * @param is
     * @param fileName
     * @param contentType
     * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
     * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
     * @return
     */
    Attachment update(long id, InputStream is, String fileName, String contentType, String protectedFormat, String readableFormat);
}
