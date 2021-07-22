package org.opoo.apps.file;

public interface Details {

	String PROTECTED_FORMAT_NONE = "none";
    String READABLE_FORMAT_NONE = "none";

	/**
	 * �ļ����ڵĴ洢����
	 * 
	 * @return the storeName
	 */
	String getStoreName();
	
	/**
	 * �ļ��ڴ洢���е�λ�á�
	 * 
	 * ����Ǵ洢���ļ�ϵͳ�У�λ��ָ�ļ�����·���Ͳ����ļ���׺���ļ�����
	 * �� /2008/11/24/12345678, ��ʽ��/path-to-filesaved/filename-no-ext��
	 * 
	 * ����ڴ洢�����ݿ��ܣ�λ��ֵ�ļ�����������ݱ�����ƺͱ����ֶε����ƣ�
	 * �� /table_name/file_field��
	 * @return the location
	 */
	String getLocation();

	/**
	 * �ļ���ԭʼ��ʽ�����ļ���ԭʼ��׺��
	 * ����Ϊ�ա�
	 * @return the originalFormat
	 */
	String getOriginalFormat();

	/**
	 * �ܱ������ļ���ʽ������ļ�������ָ�����ܱ����ĸ�ʽ����PDF�������ڴ洢�ǽ�
	 * �ļ�ת����ָ�����ܱ�����ʽ����pdf),�򽫸�ʽ��¼����������У���������ֵ
	 * Ϊnull��
	 * @return the protectedFormat
	 */
	String getProtectedFormat();
	/**
	 * �ļ����Ķ���ʽ������ļ�������ָ�����Ķ���ʽ����swf�������ڴ洢ʱ���ļ�ת
	 * ������ָ�����Ķ���ʽ����swf�����򽫸�ʽ��¼����������У���������ֵΪnull��
	 * @return the readableFormat
	 */
	String getReadableFormat();
	
	
	/**
	 * �ļ���С��
	 * @return
	 */
	long getFileSize();
}
