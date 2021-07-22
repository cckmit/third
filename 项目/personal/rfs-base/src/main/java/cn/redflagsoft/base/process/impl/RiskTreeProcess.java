package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.RiskService;

@ProcessType(RiskTreeProcess.TYPE)
public class RiskTreeProcess extends AbstractWorkProcess  {
	public static final int TYPE = 6010;
	private RiskService riskService;
	private Long dutyerId;
	
	public Long getDutyerId() {
		return dutyerId;
	}

	public void setDutyerId(Long dutyerId) {
		this.dutyerId = dutyerId;
	}

	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	/**
	 * 
	 * @see cn.redflagsoft.base.bean.RiskTree
	 */
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		if(dutyerId == null){
			dutyerId = UserClerkHolder.getClerk().getEntityID();
		}
		//System.out.println(this + ".. dutyerId=" + dutyerId);
		return riskService.findRiskTree(dutyerId);
	}
}
