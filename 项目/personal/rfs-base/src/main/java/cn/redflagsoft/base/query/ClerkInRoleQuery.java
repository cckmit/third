package cn.redflagsoft.base.query;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;

import com.google.common.collect.Lists;

import cn.redflagsoft.base.bean.Clerk;

public class ClerkInRoleQuery extends HibernateDaoSupport{
	@Queryable(name="findClerksInRoles",argNames="roleIds")
	public List<Clerk> findClerksInRoles(String roleIds){
		if(StringUtils.isBlank(roleIds)){
			return Collections.emptyList();
		}
		String[] split = roleIds.split(",");
		List<Long> ids = Lists.newArrayList();
		for (String s : split) {
			if(StringUtils.isNotBlank(s)){
				long parseLong = Long.parseLong(s.trim());
				ids.add(parseLong);
			}
		}
		
		if(ids.isEmpty()){
			return Collections.emptyList();
		}
		
		String hql = "select c from Clerk c, LiveUser u, GroupMember m where c.id=u.userId and u.username=m.username"+ 
					 " and m.groupId>100 and c.id>=100 "; //and m.groupId in (?)
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.in("m.groupId",ids));
		return getQuerySupport().find(hql,filter);
	}
}
