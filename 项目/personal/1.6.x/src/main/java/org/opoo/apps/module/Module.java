package org.opoo.apps.module;


/**
 * 
 * ģ��ӿڡ�
 * �Զ���ģ��Ӧ��ʵ������ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <T>
 */
public interface Module<T> {

	/**
	 * ģ���ʼ����
	 */
	void init();
	
	/**
	 * ģ�����١�
	 */
	void destroy();
	
}
