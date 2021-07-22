package org.opoo.apps.module.dao;

import java.io.InputStream;
import java.util.List;


/**
 * ģ�����ݷ��ʽӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ModuleDao {

	/**
	 * ����ģ�顣
	 * 
	 * @param module ģ��Bean
	 * @return �Ѿ�������ɵ�ģ��Bean
	 */
	ModuleBean create(ModuleBean module);
	/**
	 * ɾ��ָ����ģ�顣
	 * 
	 * @param module Ҫɾ����ģ���ģ��Bean
	 */
	void delete(ModuleBean module);

	/**
	 * ɾ��ָ�����Ƶ�ģ�顣
	 * @param name ģ������
	 */
	void delete(String name);

	/**
	 * ͨ��ģ�����Ʋ���ģ�顣
	 * @param s ģ������
	 * @return ģ��bean
	 */
	ModuleBean getByName(String s);

	/**
	 * ��ȡȫ����Чģ�顣
	 * 
	 * @return ģ��bean���ϡ�
	 */
	List<ModuleBean> getModuleBeans();

	/**
	 * Ϊָ����ģ������ģ�����������
	 * 
	 * @param module ģ��Bean
	 * @param contentLength ģ����������ֽڳ���
	 * @param inputstream ģ���������
	 */
	void setModuleData(ModuleBean module, int contentLength, InputStream inputstream);

	/**
	 * ��ȡָ��ģ���ģ�����������
	 * 
	 * @param module ģ��bean
	 * @return ģ���������
	 */
	InputStream getModuleData(ModuleBean module);

	/**
	 * ����ģ�顣
	 * 
	 * @param module Ҫ������ģ��Bean
	 * @param contentLength ģ������������ֽڳ���
	 * @param inputstream ģ���������
	 * @return �����Ѵ�����ģ��Bean
	 */
	ModuleBean create(ModuleBean module, int contentLength, InputStream inputstream);
}
