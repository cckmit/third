package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//import org.opoo.ndao.support.Pageable;

/**
 * @deprecated
 */
public interface ActionResult extends Serializable{//, Pageable {
	/**
	 * ������ϡ�
	 * ����ʵ���������������Ǳ��ύ��Ӧ��û�м��ϡ�
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List getRows();
	/**
	 * ��Ϣ��
	 * @return
	 */
	String getMessage();
	/**
	 * �����Ƿ�ɹ���
	 * @return
	 */
	boolean isSuccess();
	/**
	 * ���󼯺ϡ�
	 * ����Ӧ�ֶ����ƣ�ֵ��Ӧ������Ϣ��
	 * @return
	 */
	Map<String, String> getErrors();
	
	/**
	 * ��������
	 * @return
	 */
	Serializable getData();
	
	
	int getLimit();
	int getTotal();
	int getStart();
	String getDir();
	String getSort();
}
