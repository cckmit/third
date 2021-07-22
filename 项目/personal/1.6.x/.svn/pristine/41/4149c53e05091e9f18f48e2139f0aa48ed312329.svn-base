package org.opoo.apps.file;

import java.io.File;
import java.io.InputStream;

/**
 * 文件存储器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface FileStorage {
    /**
     * 保存文件的二进制数据，并根据需要进行文件格式转换。
     * 如果文件格式进行了转换，转换后的相关格式信息会设置到返回的 {@link Details} 对象中。
     *
     * @param is 文件流
     * @param fileName 文件名
     * @param contentType 文件类型
     * @param id 文件ID
	 * @return 文件存储的详细信息
	 */
	Details store(InputStream is, String fileName, String contentType, Long id) throws FileStorageException;

	/**
	 * 保存文件的二进制数据，并根据需要进行文件格式转换。
	 * 如果文件格式进行了转换，转换后的相关格式信息会设置到返回的 {@link Details} 对象中。
	 *
	 * @param is 文件流
	 * @param fileName 文件名
	 * @param contentType 文件类型
	 * @param id 文件ID
	 * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
	 *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
	 * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
	 *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
	 * @return 文件存储的详细信息
     * @throws FileStorageException
     */
	Details store(InputStream is, String fileName, String contentType, Long id,
				  String protectedFormat, String readableFormat) throws FileStorageException;
	
	/**
     * 保存文件的二进制数据，并根据需要进行文件格式转换。
     * 如果文件格式进行了转换，转换后的相关格式信息会设置到返回的 {@link Details} 对象中。
	 * 
	 * @param tmpFile 上传的文件存储的临时文件。
	 * @param fileName 上传的文件的名称。
	 * @param contentType 文件类型。
     * @param id 文件ID
	 * @return 文件存储的详细信息
	 */
	Details store(File tmpFile, String fileName, String contentType, Long id) throws FileStorageException;

    /**
     * 保存文件的二进制数据，并根据需要进行文件格式转换。
     * 如果文件格式进行了转换，转换后的相关格式信息会设置到返回的 {@link Details} 对象中。
     *
     * @param tmpFile 上传的文件存储的临时文件。
     * @param fileName 上传的文件的名称。
     * @param contentType 文件类型。
     * @param id 文件ID
     * @param protectedFormat 受保护的格式。如果不转成受保护格式，设置这个值为 {@link Details#PROTECTED_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>protectedFormat</code> 的值，比如 pdf。
     * @param readableFormat 只读格式。如果不转成只读格式，设置这个值为 {@link Details#READABLE_FORMAT_NONE}；
     *                        如果值为 <code>null</code>，则表示取 FileStoreManager 中配置的 <code>readableFormat</code> 的值，比如 swf。
     * @return 文件存储的详细信息
     * @throws FileStorageException
     */
    Details store(File tmpFile, String fileName, String contentType, Long id,
                  String protectedFormat, String readableFormat) throws FileStorageException;
	
	/**
	 * 读取一个已经保存的文件，返回文件输入流。
	 * 默认读取原始文件。
	 * 
	 * @param details 文件存储的详细信息
	 * @return 文件数据的输入流
	 */
	InputStream fetch(Details details) throws FileStorageException;
	
	/**
	 * 读取一个已经保存的文件的指定格式，返回文件输入流。
	 * 
	 * @param details 文件存储的详细信息
	 * @param format 获取特定格式的文件。
	 * @return 文件数据的输入流
	 * 
	 * @throws FileStorageException 当指定格式的文件不存在时，产生异常。
	 */
	InputStream fetch(Details details, String format) throws FileStorageException;
	
	
	/**
	 * 删除一个文件。
	 * @param details 文件存储的详细信息
	 */
	void delete(Details details) throws FileStorageException;
}
