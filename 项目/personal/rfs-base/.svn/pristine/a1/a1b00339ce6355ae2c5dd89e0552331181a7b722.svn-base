package cn.redflagsoft.base.query;

import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.vo.UserClerkVO;

public class ManagerQuery extends HibernateDaoSupport{
	
	private ClerkService clerkService;
	private UserManager userManager;

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Queryable
	public List<Clerk> findAllAdminUser(ResultFilter filter){
		
		String hql="select c.id from LiveUser u,Clerk c, Org o where u.userId=c.id and c.entityID=o.id";
		if(filter == null){
			filter = ResultFilter.createEmptyResultFilter();
		}
		if(filter.getOrder() == null){
			filter.setOrder(Order.asc("o.displayOrder").add(Order.asc("c.displayOrder")));
		}
		filter = ResultFilterUtils.append(filter, Restrictions.eq("u.enabled", Boolean.TRUE));
		
		List<Long> ids = getQuerySupport().find(hql, filter);
		List<Clerk> resultList = new ArrayList<Clerk>();
		for(Long clerkId: ids){
			User user = (User) userManager.loadUserByUserId(clerkId);
			if(SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER) && user.isEnabled()){
				Clerk clerk = clerkService.getClerk(clerkId);
				resultList.add(new UserClerkVO(user, clerk));
			}
		}
		return resultList;
	}
}
