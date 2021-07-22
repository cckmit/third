package cn.redflagsoft.base.scheme.schemes.caution;


import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionNotify;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.CautionNotifyService;
import cn.redflagsoft.base.service.CautionService;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.service.RiskService;


public class CautionScheme extends AbstractScheme{
	private CautionService cautionService;
	private RiskService riskService;
	private ClerkService clerkService;
	private CautionNotifyService cautionNotifyService;
	
	private OrgService orgService;
	  
	private Caution caution;
	private Long riskId;
	private Long id;
	
	
	public CautionNotifyService getCautionNotifyService() {
		return cautionNotifyService;
	}

	public void setCautionNotifyService(CautionNotifyService cautionNotifyService) {
		this.cautionNotifyService = cautionNotifyService;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public CautionService getCautionService() {
		return cautionService;
	}

	public void setCautionService(CautionService cautionService) {
		this.cautionService = cautionService;
	}

	public Caution getCaution() {
		return caution;
	}

	public void setCaution(Caution caution) {
		this.caution = caution;    
	}
	
	public Long getRiskId() {
		return riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}
	
	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object doScheme(){
		Assert.notNull(caution,"指定业务对象不存在！");

		Assert.notNull(riskId,"RiskID不能为空！");
		Risk risk = riskService.getRiskById(riskId);
		cautionService.createCaution(risk, UserClerkHolder.getClerk(), caution.getName(), caution.getCode(), caution.getGrade(), caution.getObjAttrValue(), caution.getConclusion(), caution.getHappenTime(),caution.getCautionSummary(), caution.getRemark());
   
		return "业务办理成功！";
	}
	
	public Object viewCaution(){
		Caution c = cautionService.getObject(id);
		if(c != null){
			String dutyerTelNo = null,dutyerManagerTelNo = null,dutyerLeaderTelNo = null;
			
			String dutyerOrgFullName = c.getDutyerOrgName();
			//责任人办公电话
			if(c.getDutyerId() != null){
				Clerk clerk = clerkService.getClerk(c.getDutyerId());
				dutyerTelNo = clerk.getTelNo();
			}
			//主管办公电话
			if(c.getDutyerManagerId() != null){
				Clerk clerk = clerkService.getClerk(c.getDutyerManagerId());
				dutyerManagerTelNo = clerk.getTelNo();
			}
			//领导办公电话
			if(c.getDutyerLeaderId() != null){
				Clerk clerk = clerkService.getClerk(c.getDutyerLeaderId());
				dutyerLeaderTelNo = clerk.getTelNo();
			}
			if(c.getDutyerOrgId() != null){
				Org org = orgService.getOrg(c.getDutyerOrgId());
				if(org != null){
					dutyerOrgFullName = org.getName();
				}
			}
			
			CautionNotify cautionNotify = cautionNotifyService.getCautionNotifyByCautionId(c.getId(), CautionNotify.TYPE_SMS_NOTIFY);
			if(cautionNotify == null){
				cautionNotify = cautionNotifyService.getCautionNotifyByCautionId(c.getId(), CautionNotify.TYPE_BOOK_NOYIFY);
			}
			Date notifyTime = null;
			if(cautionNotify != null){
				notifyTime = cautionNotify.getNotifyTime();
			}
			
			CautionExt ext = new CautionExt(c, dutyerTelNo, dutyerManagerTelNo, dutyerLeaderTelNo, dutyerOrgFullName);
			ext.setNotifyTime(notifyTime);
			
			return ext;
		}
		
		return null;
	}
	
	
	public static class CautionExt extends Caution{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String dutyerTelNo;
		private String dutyerManagerTelNo;
		private String dutyerLeaderTelNo;
		private String dutyerOrgFullName;
		private Date notifyTime;

		public CautionExt(Caution c, String dutyerTelNo,String dutyerManagerTelNo,String dutyerLeaderTelNo, String dutyerOrgFullName) {
			super();
			try {
				PropertyUtils.copyProperties(this, c);
			} catch (Exception e) {
				throw new SchemeException(e);
			}
			this.dutyerTelNo = dutyerTelNo;
			this.dutyerManagerTelNo = dutyerManagerTelNo;
			this.dutyerLeaderTelNo = dutyerLeaderTelNo;
			this.dutyerOrgFullName = dutyerOrgFullName;
		}


		public Date getNotifyTime() {
			return notifyTime;
		}

		public void setNotifyTime(Date notifyTime) {
			this.notifyTime = notifyTime;
		}



		public String getDutyerOrgFullName() {
			return dutyerOrgFullName;
		}

		public void setDutyerOrgFullName(String dutyerOrgFullName) {
			this.dutyerOrgFullName = dutyerOrgFullName;
		}





		public String getDutyerTelNo() {
			return dutyerTelNo;
		}

		public void setDutyerTelNo(String dutyerTelNo) {
			this.dutyerTelNo = dutyerTelNo;
		}

		public String getDutyerManagerTelNo() {
			return dutyerManagerTelNo;
		}

		public void setDutyerManagerTelNo(String dutyerManagerTelNo) {
			this.dutyerManagerTelNo = dutyerManagerTelNo;
		}

		public String getDutyerLeaderTelNo() {
			return dutyerLeaderTelNo;
		}

		public void setDutyerLeaderTelNo(String dutyerLeaderTelNo) {
			this.dutyerLeaderTelNo = dutyerLeaderTelNo;
		}

		
	}
}
