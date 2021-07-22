/*
 * $Id: OrgInOrgGroupSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.dao.OrgDao;


/**
 * ��ָ�������в�ѯ��λ�б�
 * 
 * <p>SelectDataSource��source��ʾ��λ�����ID��
 * 
 * <p>���û����ȷָ������˳�����Ե�λ�����е�˳��Ϊ׼��
 * <p>��ѯ��������ȫ���Org����ġ�
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgInOrgGroupSelectDataHandler extends AbstractOrgSelectDataHandler implements SelectDataHandler<Org> {

	private OrgDao orgDao;
	
	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}


	public boolean supports(SelectDataSource dataSource) {
		return dataSource.getCat() == SelectDataSource.CAT_��λ����;
	}

	protected List<Org> findOrgs(SelectDataSource dataSource, ResultFilter filter) {
		//String hql="select org from Org org, EntityGroup grp where org.id=grp.entityID and grp.groupID="+dataSource.getSource();
		String source = dataSource.getSource();
		long orgGroupId = Long.parseLong(source);
		//���������ѯ����
		if(filter == null || (filter.getCriterion() == null && filter.getOrder() == null)){
			return getOrgGroupService().findOrgsInGroup(orgGroupId);
		}
		//����ֻ��ѯID
		List<Long> orgIds = getOrgGroupService().findOrgIdsInGroup(orgGroupId);
		if(orgIds.isEmpty()){
			return Collections.emptyList();
		}
		
		ResultFilter filter2 = ResultFilterUtils.append(filter, Restrictions.in("id", orgIds))
			.append(Restrictions.gt("id", Org.MAX_SYS_ID));
		boolean isOrder = filter.getOrder() != null;
		List<Org> orgs = orgDao.find(filter2);
		//�����ѯ�����д����������������Ϊ׼
		if(isOrder){
			return orgs;
		}
		
		//���������е�˳��Ϊ׼
		List<Org> result = new ArrayList<Org>();
		for(Long id: orgIds){
			Org org = lookupOrg(orgs, id);
			if(org != null){
				result.add(org);
			}
		}
		return result;
	}
	
	private Org lookupOrg(List<Org> orgs, Long id){
		for(Org o: orgs){
			if(o.getId().longValue() == id.longValue()){
				return o;
			}
		}
		return null;
	}
}
