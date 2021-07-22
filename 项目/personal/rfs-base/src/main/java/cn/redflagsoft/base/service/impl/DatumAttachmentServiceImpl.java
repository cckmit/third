package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.dao.DatumDao;
import cn.redflagsoft.base.service.DatumAttachmentService;

public class DatumAttachmentServiceImpl implements DatumAttachmentService{
	private static final Log log = LogFactory.getLog(DatumAttachmentServiceImpl.class);
	private DatumDao datumDao;

	public void setDatumDao(DatumDao datumDao) {
		this.datumDao = datumDao;
	}

	public List<DatumAttachment> findDatumAttachment(Long objectID) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		resultFilter.setCriterion(Restrictions.eq("a.objectID", objectID));
		List<DatumAttachment> list = datumDao.findDatumAttachments(resultFilter);
		if(list == null || list.isEmpty()) {
			log.warn("û�в�ѯ������,���ؿռ���!����û���ҵ�project����superobject");
		}
		return list;		
	}

	public List<DatumAttachment> findDatumAttachmentByType(Long objectId, Integer type) {
		if (objectId != null && type != null) {
			return datumDao.findDatumAttachmentByType(objectId, type);
		} else {
			log.warn("findDatumAttachmentByType ʱ����Ϊ��,���Բ��� ... ");
			return null;
		}
	}
}
