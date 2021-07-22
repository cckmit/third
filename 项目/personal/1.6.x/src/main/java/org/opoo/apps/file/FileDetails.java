package org.opoo.apps.file;


/**
 * �ļ��洢���顣
 * ���ļ����洢���ļ�ϵͳ�����ݿ���֮���봢���ϸ�������������ʾ��
 * �����ļ��洢��·�����洢�ĸ�ʽ�������ݿ��е�λ�õȵȡ�
 * 
 * @author Alex Lin
 *
 */
public class FileDetails implements Details, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String storeName;
	private String location;
	private String originalFormat;
	private String readableFormat;
	private String protectedFormat;
	private long fileSize;
	
	/**
	 * �ļ����ڵĴ洢����
	 * 
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
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
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * �ļ���ԭʼ��ʽ�����ļ���ԭʼ��׺��
	 * ����Ϊ�ա�
	 * @return the originalFormat
	 */
	public String getOriginalFormat() {
		return originalFormat;
	}
	/**
	 * @param originalFormat the originalFormat to set
	 */
	public void setOriginalFormat(String originalFormat) {
		this.originalFormat = originalFormat;
	}

	/**
	 * �ܱ������ļ���ʽ������ļ�������ָ�����ܱ����ĸ�ʽ����PDF�������ڴ洢�ǽ�
	 * �ļ�ת����ָ�����ܱ�����ʽ����pdf),�򽫸�ʽ��¼����������У���������ֵ
	 * Ϊnull��
	 * @return the protectedFormat
	 */
	public String getProtectedFormat() {
		return protectedFormat;
	}
	/**
	 * @param protectedFormat the protectedFormat to set
	 */
	public void setProtectedFormat(String protectedFormat) {
		this.protectedFormat = protectedFormat;
	}
	/**
	 * �ļ����Ķ���ʽ������ļ�������ָ�����Ķ���ʽ����swf�������ڴ洢ʱ���ļ�ת
	 * ������ָ�����Ķ���ʽ����swf�����򽫸�ʽ��¼����������У���������ֵΪnull��
	 * @return the readableFormat
	 */
	public String getReadableFormat() {
		return readableFormat;
	}
	/**
	 * @param readableFormat the readableFormat to set
	 */
	public void setReadableFormat(String readableFormat) {
		this.readableFormat = readableFormat;
	}
	/**
	 * �����ļ���С��
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * �����ļ���С��
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
