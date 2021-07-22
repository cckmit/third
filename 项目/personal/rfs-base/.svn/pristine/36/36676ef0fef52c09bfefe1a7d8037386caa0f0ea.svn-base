package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.RiskService;

@ProcessType(ObjectTaskRiskSummaryProcess.TYPE)
public class ObjectTaskRiskSummaryProcess extends AbstractWorkProcess {
	public static final int TYPE = 7014;
	private RiskService riskService;
	private Long objectId;
	private int objectType;

	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public int getObjectType() {
		return objectType;
	}
	
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	
	public RiskService getRiskService() {
		return riskService;
	}
	
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		return riskService.findRiskByObjectId(objectId, objectType);
	}
}
