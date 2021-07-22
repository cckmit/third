package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.DatumAttachmentService;


/**
 * 查询业务对象指定类型的资料信息。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(ObjectDatumProcess.TYPE)
public class ObjectDatumProcess extends AbstractWorkProcess {
	public static final int TYPE = 7013;
	private DatumAttachmentService datumAttachmentService;
	private Long objectId;
	private Integer type;//type=1;
	
	public Long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	public void setDatumAttachmentService(DatumAttachmentService datumAttachmentService) {
		this.datumAttachmentService = datumAttachmentService;
	}

	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		return datumAttachmentService.findDatumAttachmentByType(objectId, type);
	}
}
