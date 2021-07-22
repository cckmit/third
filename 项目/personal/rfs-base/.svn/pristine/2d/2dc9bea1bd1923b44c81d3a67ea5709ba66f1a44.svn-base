package cn.redflagsoft.base.query;

import java.util.List;

import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.OrgService;

import com.google.common.collect.Lists;

public class OrgQuery extends HibernateDaoSupport{
	
	private OrgService orgService;

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	@Queryable
	public List<Org> findAllOrg(ResultFilter filter){
		
		if(filter == null){
			filter = new ResultFilter();
		}
		filter.setOrder(Order.asc("displayOrder"));
		
		List<Org> orgs = orgService.findOrgs(filter);
		if(AuthUtils.isAdministrator()){
			List<Org> list = Lists.newArrayList();
			for(Org org: orgs){
				if(org.getId().longValue() > Org.MAX_SYS_ID){
					list.add(org);
				}else{
					//去掉系统部分
				}
			}
			orgs = list;
		}
		return orgs;
	}
}
