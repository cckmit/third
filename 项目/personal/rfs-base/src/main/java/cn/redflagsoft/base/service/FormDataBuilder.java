/*
 * $Id: FormDataBuilder.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.Map;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * �����ݹ�������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface FormDataBuilder {
	
	
	/**
	 * ���������ݡ�
	 * @return
	 */
	Map<String, Object> toMap();
	
	/**
	 * ���Ӷ�����������Ե������ݡ�
	 * @param object ����
	 * @return ������������
	 */
	FormDataBuilder append(Object object);
	
	/**
	 * ���Ӷ���Ĳ������Ե������ݣ�ָ���ų������ԡ�
	 * @param object ����
	 * @param excludeFields �ų��������б�
	 * @return ������������
	 */
	FormDataBuilder appendExcludes(Object object, String... excludeFields);
	
	/**
	 * ���Ӷ���Ĳ������Ե������ݣ�ָ�����������ԡ�
	 * @param object ����
	 * @param includeFields ����������
	 * @return ������������
	 */
	FormDataBuilder appendIncludes(Object object, String... includeFields);
	
	/**
	 * ����Task��Ϣ�������ݡ����Ը�ʽΪ task_&lt;XXX&gt;_&lt;taskType&gt;��
	 * @param task �������
	 * @return ������������
	 */
	FormDataBuilder appendTask(Task task);
	
//	/**
//	 * ����Task��sn��ѯ��������task��Ϣ�������ݡ�
//	 * @param taskSN �����SN
//	 * @return ������������
//	 * @see #appendTask(Task)
//	 */
//	FormDataBuilder appendTask(Long taskSN);
//	
//	/**
//	 * ���ݶ���ID�Ͷ����ϵ���ͣ���objects����task��sn���������ҵ���
//	 * task������Ϣ���ӵ��������С�
//	 * 
//	 * @param objectId ��objects�еĵ�һ�������ID
//	 * @param objectsTypeOfTask ��objects�еĹ�ϵ����
//	 * @return ������������
//	 * @see #appendTask(Long)
//	 * @see #appendTask(Task)
//	 */
//	FormDataBuilder appendTask(long objectId, short objectsTypeOfTask);
	
//	/**
//	 * ���Ҷ�����ص�ָ�����͵�task������һ��task�Լ�task��ص�����work��
//	 * ������Щtask��work�������ӵ��������С�
//	 * 
//	 * @param objectId
//	 * @param objectType
//	 * @param taskType
//	 * @return
//	 */

//	FormDataBuilder appendObjectLastTaskAndWorks(long objectId, int objectType, int taskType);

	
//	/**
//	 * ����task�������Ϣ�������ݣ�һ��������ֻ������һ��
//	 * task�������Ϣ�������õĻḲ��֮ǰ���õ����ݡ�
//	 * ���Ը�ʽΪ task_&lt;XXX&gt;��
//	 * @param task �������
//	 * @return ������������
//	 */
//	FormDataBuilder setTask(Task task);
//	
//	/**
//	 * ����task��SN����task���󣬲�����task�������Ϣ�������ݡ�
//	 * @param taskSN �����SN
//	 * @return ������������
//	 * @see #setTask(Task)
//	 */
//	FormDataBuilder setTask(Long taskSN);
//	
//	/**
//	 * ���ݶ���ID�Ͷ����ϵ���ͣ���objects����task��sn���������ҵ���
//	 * task������Ϣ���õ��������С�
//	 * @param objectId ��objects�еĵ�һ�������ID
//	 * @param objectsTypeOfTask ��objects�еĹ�ϵ����
//	 * @return ������������
//	 * @see #setTask(Long)
//	 * @see #setTask(Task)
//	 */
//	FormDataBuilder setTask(long objectId, short objectsTypeOfTask);
//	
//	
//	/**
//	 * ����ҵ������ID�������Լ������ҵ�����ͣ�taskType����ѯ��ҵ���Ӧ��
//	 * task��Ϣ����������work��Ϣ�������������װ���������С�
//	 * 
//	 * <p>ͨ��Ҫ��ѯָ��ҵ�����ָ�������Task��Work��Ϣʱ���������������
//	 * 
//	 * @param objectId ҵ�����ID
//	 * @param objectType ҵ���������
//	 * @param taskType task������
//	 * @return ������������
//	 */


//	FormDataBuilder setTaskAndWorks(long objectId, int objectType, int taskType);

	
	/**
	 * ����Work����Ϣ�������ݡ�
	 * ���ӵ�����Ϊ work_&lt;XXX&gt;_&lt;workType&gt;
	 * 
	 * @param work work����
	 * @return ������������
	 */
	FormDataBuilder appendWork(Work work);
//	/**
//	 * ����Work��SN��ѯWork���󣬲���������Ϣ���������С�
//	 * @param workSN Work��SN
//	 * @return ������������
//	 * @see #appendWork(Work)
//	 */
//	FormDataBuilder appendWork(Long workSN);
//	
//	/**
//	 * ���ݶ���ID�Ͷ����ϵ���ͣ���objects����work��sn���������ҵ���
//	 * work������Ϣ���õ��������С�
//	 * 
//	 * @param objectId ��objects�еĵ�һ�������ID
//	 * @param objectsTypeOfWork  ��objects�еĹ�ϵ����
//	 * @return ������������
//	 * @see #appendWork(Work)
//	 * @see #appendWork(Long)
//	 */
//	FormDataBuilder appendWork(long objectId, short objectsTypeOfWork);
	
	/**
	 * ��ѯָ������ָ�����͵�task��work�����Ϣ�������¼��ڵ㡣
	 * @param object
	 * @param taskType
	 */
	FormDataBuilder appendTask(RFSEntityObject object, int taskType);
}
