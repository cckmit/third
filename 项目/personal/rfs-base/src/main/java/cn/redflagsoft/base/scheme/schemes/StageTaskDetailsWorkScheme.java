/*
 * $Id: StageTaskDetailsWorkScheme.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.bean.StageTaskDetails;
import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.dao.LifeStageDao;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.service.StageTaskDetailsService;
import cn.redflagsoft.base.service.TaskDefinitionService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class StageTaskDetailsWorkScheme extends AbstractWorkScheme {
	private static final Log log = LogFactory.getLog(StageTaskDetailsWorkScheme.class);
	
	private StageTaskDetailsService stageTaskDetailsService;
	private LifeStageDao lifeStageDao;
	private TaskDefinitionService taskDefinitionService;
	
	private StageTaskDetails stageTaskDetails;
	private Integer objectType;
	private Long id;
	
//	/private Map<Short,String> fields;
//	private String fieldsDescription;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public StageTaskDetailsService getStageTaskDetailsService() {
		return stageTaskDetailsService;
	}
	
	public void setStageTaskDetailsService(StageTaskDetailsService stageTaskDetailsService) {
		this.stageTaskDetailsService = stageTaskDetailsService;
	}
	

	public TaskDefinitionService getTaskDefinitionService() {
		return taskDefinitionService;
	}

	public void setTaskDefinitionService(TaskDefinitionService taskDefinitionService) {
		this.taskDefinitionService = taskDefinitionService;
	}

	public StageTaskDetails getStageTaskDetails() {
		return stageTaskDetails;
	}

	public LifeStageDao getLifeStageDao() {
		return lifeStageDao;
	}

	public void setLifeStageDao(LifeStageDao lifeStageDao) {
		this.lifeStageDao = lifeStageDao;
	}

	public void setStageTaskDetails(StageTaskDetails stageTaskDetails) {
		this.stageTaskDetails = stageTaskDetails;
	}
	
	
	public Object doSubmit(){
		Assert.notNull(getObjectId(), "objectId����Ϊ��");
		Assert.notNull(getObjectType(),"objectType����Ϊ��");
		//Assert.notNull(getTaskType(),"taskType����Ϊ��");
		Assert.notNull(stageTaskDetails, "������������Ϊ��");
		
		int stageTaskType = stageTaskDetails.getTaskType();
		Assert.isTrue(stageTaskType > 0, "stageTaskType��Ч");
		
		boolean isUpdate = true;
		StageTaskDetails details = stageTaskDetailsService.getStageTaskDetail(getObjectId(), getObjectType(), stageTaskType);
		if(details == null){
			details = new StageTaskDetails();
//			details.setObjectId(getObjectId());
//			details.setObjectType(getObjectType());
			isUpdate = false;
		}
		details.setBzdwName(stageTaskDetails.getBzdwName());
		details.setBzdwId(stageTaskDetails.getBzdwId());
		details.setSbTime(stageTaskDetails.getSbTime());
		details.setSbFileId(stageTaskDetails.getSbFileId());
		details.setPfTime(stageTaskDetails.getPfTime());
		details.setPfFileNo(stageTaskDetails.getPfFileNo());
		details.setPfFileId(stageTaskDetails.getPfFileId());
		details.setWorkItemName(stageTaskDetails.getWorkItemName());
		details.setActualFinishTime(stageTaskDetails.getPfTime());
		
		//System.out.println("�Ƿ�����������----------------1����죬0������-------"+stageTaskDetails.getType());
		details.setType(stageTaskDetails.getType());
		
		String tmp = details.getTaskSNs();
		String s = StringUtils.isNotBlank(tmp) ?  tmp + ":" : "";
		s += getTask().getSn();
		details.setTaskSNs(s);
		
		tmp = details.getWorkSNs();
		s = StringUtils.isNotBlank(tmp) ? tmp + ":" : "";
		s += getWork().getSn();
		details.setWorkSNs(s);
		
		details.setTaskType(stageTaskType);
		details.setObjectType(getObject().getObjectType());
		details.setObjectId(getObject().getId());
		details.setObjectName(getObject().getName());
		//ע��: stageTaskDetails.getTaskType()��getTaskType()��ͬ
		//��view�͸÷����е�getTaskType()��ͬ��
		//details.setTaskType(getTaskType());

		//type������ʾ�������Ƿ�������
		if(details.getType() == 1){
			if(details.getPfTime() != null){
				details.setStatus(StageTaskDetails.STATUS_���);
			}else{
				details.setStatus(StageTaskDetails.STATUS_δ��);
			}
		}else{
			details.setStatus(StageTaskDetails.STATUS_���);
		}
		
		
		if(isUpdate) {
			details = stageTaskDetailsService.updateStageTaskDetails(details);
		}else{
			details = stageTaskDetailsService.saveStageTaskDetails(details);
		}
		

		postSubmit(details);
		
		
		if(details.getType() == 1){
			getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
			//�������ʱ���Ѿ�ȷ����������׶ε�task�����matteraffair����
			if(details.getPfTime() != null){
				getMattersHandler().finishMatter(getTask(), getWork(), getObject(), getMatterVO().getMatterIds()[0], (short)1);
			}
		}else{//���
			log.debug("����������...");
			//getTaskService().avoidTask(getTask());
			getMattersHandler().avoid(getWork(), getObject(), getMatterIds());
		}
		return "�����ɹ�";
	}
	
	/**
	 * �ڲ������ǰִ�еĴ���һ����������Ըù��ܵ���չ��
	 * @param details
	 */
	protected void postSubmit(StageTaskDetails details) {
		
	}
	

	public StageTaskDetails viewDetails(){
		Assert.notNull(getId(), "id����Ϊ��");
		Assert.notNull(getObjectType(), "objectType����Ϊ��");
		Assert.notNull(getTaskType(),"taskType����Ϊ��");
		//StageTaskDetails stageTaskDetails = stageTaskDetailsService.getStageTaskDetail(objectId, objectType, getTaskType()); 
		StageTaskDetails detail = stageTaskDetailsService.getStageTaskDetail(getId(), getObjectType(), getTaskType());
		if(detail == null){
			return new StageTaskDetails();
		}else{
			return detail;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public LifeStage viewGetStageTaskStatus() throws IllegalAccessException, InvocationTargetException, Exception{
		Assert.notNull(getId(), "id����Ϊ��");
		Assert.notNull(getObjectType(), "objectType����Ϊ��");
		LifeStage ls = lifeStageDao.get(getId());
		if(ls != null){
			List<StageTaskDetails> list = stageTaskDetailsService.findStageTaskDetails(getId(), getObjectType());
			for(int i = 0 ; i <= 99 ; i++){
				PropertyUtils.setProperty(ls, "status" + i, StageTaskDetails.STATUS_δ��);
			}
			for (StageTaskDetails details : list) {
				TaskDefinition taskDef = taskDefinitionService.getTaskDefinition(details.getTaskType());
				if(taskDef == null || StringUtils.isBlank(taskDef.getLifeStageFieldName())){
//					log.error("TaskDefû�ж����û�ж���LifeStageFieldName");
					log.error("TaskDefû�ж����û�ж���LifeStageFieldName��" + details.getTaskType());
					//throw new SchemeException("TaskDefû�ж����û�ж���LifeStageFieldName��" + details.getTaskType());
					//�˴�����������ʷ���ݣ�������ƣ�task_type190,lifeStageFieldName status8��
					continue;
				}
				PropertyUtils.setProperty(ls, taskDef.getLifeStageFieldName(), details.getStatus());
			}
		}
		return ls;
	}

//	/**
//	 * @param desc 'status1:104,status2:105'
//	 * @return
//	 */
//	private Map<Short, String> parseFieldsDescription(String desc) throws IllegalArgumentException{
//		StringTokenizer st = new StringTokenizer(desc, ",; ");
//		Map<Short, String> map = new HashMap<Short, String>();
//		while(st.hasMoreTokens()){
//			String f = st.nextToken();
//			String[] aa = f.split(":");
//			if(aa.length == 2){
//				try {
//					int type = Short.parseShort(aa[1]);
//					map.put(type, aa[0]);
//				} catch (NumberFormatException e) {
//					//log.debug(e.getMessage(), e);
//					throw new IllegalArgumentException(e);
//				}
//			}
//		}
//		return map;
//	}

//	/**
//	 * @param fields 
//	 * @param taskType
//	 * @return
//	 */
//	private String getStatusPropertyNameByTaskType(Map<Short, String> fields, int taskType) {
//		return fields.get(taskType);
//	}
//	
}
