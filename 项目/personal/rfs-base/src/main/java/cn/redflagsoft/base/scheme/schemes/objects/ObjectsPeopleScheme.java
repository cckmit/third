package cn.redflagsoft.base.scheme.schemes.objects;

import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.bean.LongKeyBean;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.ObjectPeopleService;
import cn.redflagsoft.base.vo.BatchUpdateResult;

public class ObjectsPeopleScheme extends AbstractScheme {
	
	private ObjectPeopleService objectPeopleService;
	
	private List<LongKeyBean> list;
	private Integer type;
	private int objectType;
	private Clerk people;

	public List<LongKeyBean> getList() {
		return list;
	}
	public void setList(List<LongKeyBean> list) {
		this.list = list;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	
	
	public ObjectPeopleService getObjectPeopleService() {
		return objectPeopleService;
	}
	public void setObjectPeopleService(ObjectPeopleService objectPeopleService) {
		this.objectPeopleService = objectPeopleService;
	}
	public Clerk getPeople() {
		return people;
	}
	public void setPeople(Clerk people) {
		this.people = people;
	}
	@Override
	public Object doScheme() throws SchemeException {
		Assert.notNull(people,"对象不能为空!!!");
		Assert.notNull(objectType,"对象类型不能为空!!!");
		Assert.notNull(type,"关联关系类型不能为空!!!");
		Assert.notNull(list,"请提供正确的数据!!!!");
		Assert.notNull(people.getId(),"用户的ID属性不能为空!!!");
		
		
		List<Long> ids = new ArrayList<Long>();
		
		BatchUpdateResult result = objectPeopleService.setObjectsToPeople(people.getId(), ids, type, objectType);
		return "新增了 " + result.getCreatedRows() + " 项， 删除了 " + result.getDeletedRows() + " 项。"; 
		
	}
}
