package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.DatumAttachment;

public interface DatumAttachmentService {
	List<DatumAttachment> findDatumAttachment(Long objectId);
	
	List<DatumAttachment> findDatumAttachmentByType(Long objectId, Integer type);
}
