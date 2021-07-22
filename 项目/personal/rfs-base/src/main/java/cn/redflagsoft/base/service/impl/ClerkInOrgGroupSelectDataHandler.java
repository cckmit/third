/*
 * $Id: ClerkInOrgGroupSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.Collections;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.OrgGroupService;

/**
 * ��ָ���ĵ�λ�����е����е�λ��ѯ��Ա�б�
 * 
 * <p>SelectDataSource�� source �д洢���ǵ�λ�����ID��
 * ���ȸ��ݵ�λ����ID��ѯ
 * �������е����е�λ���ٲ�ѯ����Щ��λ�е�������Ա��
 * ���û����ȷָ������������Ա������Ϊ׼��
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkInOrgGroupSelectDataHandler extends AbstractClerkSelectDataHandler implements SelectDataHandler<Clerk> {
	private OrgGroupService orgGroupService;
	private ClerkService clerkService;
	
	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}

	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#supports(cn.redflagsoft.base.bean.SelectDataSource)
	 */
	public boolean supports(SelectDataSource dataSource) {
		return SelectDataSource.CAT_��λ�����е���Ա == dataSource.getCat();
	}

	protected List<Clerk> findClerks(SelectDataSource dataSource, ResultFilter filter) {
		List<Long> orgIds = orgGroupService.findOrgIdsInGroup(Long.parseLong(dataSource.getSource()));
		if(orgIds == null){
			return null;
		}
		
		if(orgIds.isEmpty()){
			return Collections.emptyList();
		}
		
		ResultFilter filter2 = ResultFilterUtils.append(filter, Restrictions.in("entityID", orgIds));
		return clerkService.findClerks(filter2);
	}

}
