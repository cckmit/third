/*
 * $Id: ObjectPeopleScheme.java 4656 2011-09-05 01:23:26Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.objects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Checkable;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.CheckableService;
import cn.redflagsoft.base.service.ObjectPeopleService;
import cn.redflagsoft.base.vo.BatchUpdateResult;

/**
 * 对象人员之间关系管理的Scheme。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ObjectPeopleScheme extends AbstractScheme {
	private CheckableService<Long> checkableService;
	private ObjectPeopleService objectPeopleService;
	private int objectType;
	
	private List<Long> ids;
	private Integer type;
	private Long peopleId;
	
	public CheckableService<Long> getCheckableService() {
		return checkableService;
	}
	@Required
	public void setCheckableService(CheckableService<Long> checkableService) {
		this.checkableService = checkableService;
	}
	public ObjectPeopleService getObjectPeopleService() {
		return objectPeopleService;
	}
	@Required
	public void setObjectPeopleService(ObjectPeopleService objectPeopleService) {
		this.objectPeopleService = objectPeopleService;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getPeopleId() {
		return peopleId;
	}
	public void setPeopleId(Long peopleId) {
		this.peopleId = peopleId;
	}
	/**
	 * 设置批量对象到人的关联。
	 * @return
	 */
	public Object doSetObjectsToPeople(){
		//Assert.notNull(ids, "被设置的对象 id 不能为 null");
		Assert.notNull(type, "关系类型不能为空");
//		Assert.notNull(ids);
		
		if(peopleId == null){
			//当前人员
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		return setObjectsToPeople(peopleId, ids, type, objectType);
	}
	
	/**
	 * 设置批量对象到人的关联。
	 * @return
	 */
	public Object doAddObjectsToPeople(){
		//Assert.notNull(ids, "被设置的对象 id 不能为 null");
		Assert.notNull(type, "关系类型不能为空");
		Assert.notNull(ids, "新增的集合不能为空");
		if(ids.isEmpty()){
			throw new IllegalArgumentException("新增的集合不能为空");
		}
		
		if(peopleId == null){
			//当前人员
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		return addObjectsToPeople(peopleId, ids, type, objectType);
	}
	
	/**
	 * 
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	protected Object setObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType){
		BatchUpdateResult result = objectPeopleService.setObjectsToPeople(peopleId, objectIds, type, objectType);
		return "新增了 " + result.getCreatedRows() + " 项， 删除了 " + result.getDeletedRows() + " 项。"; 
	}
	
	/**
	 * 
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	protected Object addObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType){
		BatchUpdateResult result = objectPeopleService.addObjectsToPeople(peopleId, objectIds, type, objectType);
		String msg = "新增了 " + result.getCreatedRows() + " 项";
		if(result.getDeletedRows() > 0){
			msg += "，" + result.getDeletedRows() + " 项已经存在";
		}
		msg += "。";
		return msg;
	}
	
	/**
	 * 查询对象列表，将已选中的对象标记为选中。
	 * @return
	 */
	public List<Checkable<Long>> viewList(){
		Assert.notNull(type, "关系类型不能为空");
		if(peopleId == null){
			//当前人员
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		
		return findCheckableList(peopleId, type, objectType);
	}
	protected List<Checkable<Long>> findCheckableList(Long peopleId, int type, int objectType){
		List<Long> checkedObjectIds = objectPeopleService.findObjectIdsByPeopleId(peopleId, type, objectType);
		return checkableService.findCheckableList(getParameters(), checkedObjectIds);
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Object doRemoveObjects(){
		Assert.notNull(type, "关系类型不能为空");
		if(ids == null || ids.isEmpty()){
			throw new IllegalArgumentException("被移除对象的ID集合不能为空。");
		}
		if(peopleId == null){
			//当前人员
			Clerk uc = UserClerkHolder.getClerk();
			peopleId = uc.getId();
		}
		
		return removeObjectsOfPeople(peopleId, ids, type, objectType);
	}
	protected Object removeObjectsOfPeople(Long peopleId, List<Long> objectIds, int type, int objectType){
		int rows = objectPeopleService.removeObjectsOfPeople(peopleId, objectIds, type, objectType);
		return "删除了 " + rows + " 项。";
	}
	
	
	/**
	 * 查询对象列表，将已存在关联的对象排除。
	 * @return
	 */
	public List<Checkable<Long>> viewFindObjectsNotIn(){
		Assert.notNull(type, "关系类型不能为空");
		if(peopleId == null){
			//当前人员
			Clerk uc = UserClerkHolder.getClerk();    
			peopleId = uc.getId();
		}
		return findCheckableListNotIn(peopleId, type, objectType);
	}
	protected List<Checkable<Long>> findCheckableListNotIn(Long peopleId, int type, int objectType){
		@SuppressWarnings("unchecked")
		Map<Object,Object> map = (Map<Object, Object>) getParameters();
		
		List<Long> checkedObjectIds = objectPeopleService.findObjectIdsByPeopleId(peopleId, type, objectType);
		
		if(checkedObjectIds != null && !checkedObjectIds.isEmpty()){
			int index = 0;
			if(map == null){
				map = new LinkedHashMap<Object,Object>();
			}else{
				while(true){
					if(map.containsKey("s[" + index + "].n")){
						index++;
					}else{
						break;
					}
				}
			}
			StringBuffer sb = new StringBuffer();
			for(Long checkedObjectId: checkedObjectIds){
				if(sb.length() > 1){
					sb.append(",");
				}
				sb.append(checkedObjectId);
			}
			map.put("s[" + index + "].n", "id");
			map.put("s[" + index + "].o", "notin");
			map.put("s[" + index + "].t", "long");
			map.put("s[" + index + "].v", sb.toString());
		}
		
		return checkableService.findCheckableList(map, checkedObjectIds);
	}
}
