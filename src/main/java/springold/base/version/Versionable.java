package springold.base.version;

import java.util.Date;


/**
 * �����Ǵ��а汾��ʶ�Ľӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Versionable {
	
	/**
	 * ��ȡ����ʱ�䡣
	 * @return
	 */
	Date getCreationTime();
	/**
	 * ���ô���ʱ�䡣
	 * @param creationTime
	 */
	void setCreationTime(Date creationTime);
	/**
	 * ��ȡ�޸�ʱ�䡣
	 * @return
	 */
	Date getModificationTime();
	/**
	 * �����޸�ʱ�䡣
	 * @param modificationTime
	 */
	void setModificationTime(Date modificationTime);

}
