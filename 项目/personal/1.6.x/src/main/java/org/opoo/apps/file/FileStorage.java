package org.opoo.apps.file;

import java.io.File;
import java.io.InputStream;

/**
 * �ļ��洢����
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface FileStorage {
    /**
     * �����ļ��Ķ��������ݣ���������Ҫ�����ļ���ʽת����
     * ����ļ���ʽ������ת����ת�������ظ�ʽ��Ϣ�����õ����ص� {@link Details} �����С�
     *
     * @param is �ļ���
     * @param fileName �ļ���
     * @param contentType �ļ�����
     * @param id �ļ�ID
	 * @return �ļ��洢����ϸ��Ϣ
	 */
	Details store(InputStream is, String fileName, String contentType, Long id) throws FileStorageException;

	/**
	 * �����ļ��Ķ��������ݣ���������Ҫ�����ļ���ʽת����
	 * ����ļ���ʽ������ת����ת�������ظ�ʽ��Ϣ�����õ����ص� {@link Details} �����С�
	 *
	 * @param is �ļ���
	 * @param fileName �ļ���
	 * @param contentType �ļ�����
	 * @param id �ļ�ID
	 * @param protectedFormat �ܱ����ĸ�ʽ�������ת���ܱ�����ʽ���������ֵΪ {@link Details#PROTECTED_FORMAT_NONE}��
	 *                        ���ֵΪ <code>null</code>�����ʾȡ FileStoreManager �����õ� <code>protectedFormat</code> ��ֵ������ pdf��
	 * @param readableFormat ֻ����ʽ�������ת��ֻ����ʽ���������ֵΪ {@link Details#READABLE_FORMAT_NONE}��
	 *                        ���ֵΪ <code>null</code>�����ʾȡ FileStoreManager �����õ� <code>readableFormat</code> ��ֵ������ swf��
	 * @return �ļ��洢����ϸ��Ϣ
     * @throws FileStorageException
     */
	Details store(InputStream is, String fileName, String contentType, Long id,
				  String protectedFormat, String readableFormat) throws FileStorageException;
	
	/**
     * �����ļ��Ķ��������ݣ���������Ҫ�����ļ���ʽת����
     * ����ļ���ʽ������ת����ת�������ظ�ʽ��Ϣ�����õ����ص� {@link Details} �����С�
	 * 
	 * @param tmpFile �ϴ����ļ��洢����ʱ�ļ���
	 * @param fileName �ϴ����ļ������ơ�
	 * @param contentType �ļ����͡�
     * @param id �ļ�ID
	 * @return �ļ��洢����ϸ��Ϣ
	 */
	Details store(File tmpFile, String fileName, String contentType, Long id) throws FileStorageException;

    /**
     * �����ļ��Ķ��������ݣ���������Ҫ�����ļ���ʽת����
     * ����ļ���ʽ������ת����ת�������ظ�ʽ��Ϣ�����õ����ص� {@link Details} �����С�
     *
     * @param tmpFile �ϴ����ļ��洢����ʱ�ļ���
     * @param fileName �ϴ����ļ������ơ�
     * @param contentType �ļ����͡�
     * @param id �ļ�ID
     * @param protectedFormat �ܱ����ĸ�ʽ�������ת���ܱ�����ʽ���������ֵΪ {@link Details#PROTECTED_FORMAT_NONE}��
     *                        ���ֵΪ <code>null</code>�����ʾȡ FileStoreManager �����õ� <code>protectedFormat</code> ��ֵ������ pdf��
     * @param readableFormat ֻ����ʽ�������ת��ֻ����ʽ���������ֵΪ {@link Details#READABLE_FORMAT_NONE}��
     *                        ���ֵΪ <code>null</code>�����ʾȡ FileStoreManager �����õ� <code>readableFormat</code> ��ֵ������ swf��
     * @return �ļ��洢����ϸ��Ϣ
     * @throws FileStorageException
     */
    Details store(File tmpFile, String fileName, String contentType, Long id,
                  String protectedFormat, String readableFormat) throws FileStorageException;
	
	/**
	 * ��ȡһ���Ѿ�������ļ��������ļ���������
	 * Ĭ�϶�ȡԭʼ�ļ���
	 * 
	 * @param details �ļ��洢����ϸ��Ϣ
	 * @return �ļ����ݵ�������
	 */
	InputStream fetch(Details details) throws FileStorageException;
	
	/**
	 * ��ȡһ���Ѿ�������ļ���ָ����ʽ�������ļ���������
	 * 
	 * @param details �ļ��洢����ϸ��Ϣ
	 * @param format ��ȡ�ض���ʽ���ļ���
	 * @return �ļ����ݵ�������
	 * 
	 * @throws FileStorageException ��ָ����ʽ���ļ�������ʱ�������쳣��
	 */
	InputStream fetch(Details details, String format) throws FileStorageException;
	
	
	/**
	 * ɾ��һ���ļ���
	 * @param details �ļ��洢����ϸ��Ϣ
	 */
	void delete(Details details) throws FileStorageException;
}
