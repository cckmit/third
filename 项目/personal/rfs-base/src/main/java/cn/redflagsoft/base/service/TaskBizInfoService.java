/*
 * $Id: TaskBizInfoService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;


import cn.redflagsoft.base.NonUniqueResultException;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskBizInfo;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskBizInfoService {

	/**
	 * 
	 * @param task
	 * @param taskBizInfo
	 * @return
	 */
	TaskBizInfo createTaskBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * ��ѯָ�� id �� TaskBizInfo ��Ϣ
	 * @param id
	 * @return
	 */
	TaskBizInfo getTaskBizInfo(Long id);
	
	
	/**
	 * ���� TaskBizInfo ����
	 * @param taskBizInfo
	 * @return
	 */
	TaskBizInfo update(TaskBizInfo taskBizInfo);

	
	/**
	 * ���� TaskSN ����ָ���� TaskBizInfo ����
	 * 
	 * ͨ��һ�� task ���������Ӧһ�� TaskBizInfo ����
	 * @param taskSN
	 * @return ���� ָ���� TaskBizInfo ������� null��
	 * @throws ͬһ��TaskSN�ж��TaskBizInfo�������׳��쳣��
	 */
	TaskBizInfo getTaskBizInfoByTaskSN(Long taskSN) throws NonUniqueResultException;
	
	
	
	/**
	 * ������������Ǽ���Ϣ��
	 * 
	 * <p>���ȸ���task��Ϣ��ѯ TaskBizInfo��������������򴴽���Ȼ����� TaskBizInfo ����
	 * �����������Ե����ݡ�
	 * @param task Task ������Ϊ�գ��� sn �� name ����Ҳ����Ϊ�ա�
	 * @param taskBizInfo ���ڴ���������Ϣ�Ĳ������󣬲���ͬ�ڳ־ö��� TaskBizInfo��ֻ�ǽ������������͡�
	 * @return �����־ö��� TaskBizInfo��
	 */
	TaskBizInfo saveAcptBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	
	/**
	 * �������������Ǽ���Ϣ��
	 * 
	 * <p>���ȸ���task��Ϣ��ѯ TaskBizInfo��������������򴴽���Ȼ����� TaskBizInfo ����
	 * �����������Ǽǲ������Ե����ݡ�
	 * 
	 * 
	 * @param task ������Ϊ�գ��� sn �� name ����Ҳ����Ϊ�ա�
	 * @param taskBizInfo ���ڴ������������Ǽ���Ϣ�Ĳ������󣬲���ͬ�ڳ־ö��� TaskBizInfo��ֻ�ǽ������������͡�
	 * @return �����־ö��� TaskBizInfo��
	 */
	TaskBizInfo saveDaBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * ������������������Ϣ��
	 * 
	 * <p>���ȸ���task��Ϣ��ѯ TaskBizInfo��������������򴴽���Ȼ����� TaskBizInfo ����
	 * �������������Ĳ������Ե����ݡ�
	 * 
	 * 
	 * @param task ������Ϊ�գ��� sn �� name ����Ҳ����Ϊ�ա�
	 * @param taskBizInfo ���ڴ�����������������Ϣ�Ĳ������󣬲���ͬ�ڳ־ö��� TaskBizInfo��ֻ�ǽ������������͡�
	 * @return �����־ö��� TaskBizInfo��
	 */
	TaskBizInfo saveDrBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * ���汨����������Ϣ��
	 * 
	 * <p>���ȸ���task��Ϣ��ѯ TaskBizInfo��������������򴴽���Ȼ����� TaskBizInfo ����
	 * �ı����������������Ե����ݡ�
	 * 
	 * 
	 * @param task ������Ϊ�գ��� sn �� name ����Ҳ����Ϊ�ա�
	 * @param taskBizInfo ���ڴ��ݱ�����������Ϣ�Ĳ������󣬲���ͬ�ڳ־ö��� TaskBizInfo��ֻ�ǽ������������͡�
	 * @return �����־ö��� TaskBizInfo��
	 */
	TaskBizInfo saveRcdBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * ���湤�����ϴ浵��Ϣ��
	 * 
	 * <p>���ȸ���task��Ϣ��ѯ TaskBizInfo��������������򴴽���Ȼ����� TaskBizInfo ����
	 * �Ĺ������ϴ浵�������Ե����ݡ�
	 * 
	 * 
	 * @param task ������Ϊ�գ��� sn �� name ����Ҳ����Ϊ�ա�
	 * @param taskBizInfo ���ڴ��ݹ������ϴ浵��Ϣ�Ĳ������󣬲���ͬ�ڳ־ö��� TaskBizInfo��ֻ�ǽ������������͡�
	 * @return �����־ö��� TaskBizInfo��
	 */
	TaskBizInfo saveAcvBizInfo(Task task, TaskBizInfo taskBizInfo);
}
