package cn.redflagsoft.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectDataDao;
import cn.redflagsoft.base.service.InfoStatHandler;

public class DatumInfoStatHandler implements InfoStatHandler {
	private static final Log log = LogFactory.getLog(DatumInfoStatHandler.class);
	
	private ObjectDataDao objectDataDao;
	
	/**
	 * @return the objectDataDao
	 */
	public ObjectDataDao getObjectDataDao() {
		return objectDataDao;
	}

	/**
	 * @param objectDataDao the objectDataDao to set
	 */
	public void setObjectDataDao(ObjectDataDao objectDataDao) {
		this.objectDataDao = objectDataDao;
	}

	public boolean supports(RFSObject o, InfoConfig config) {
		return (InfoConfig.TYPE_DATUM == config.getType());
	}

	public byte getStatStatus(RFSObject o, InfoConfig config) {
		long datumCategoryId = Long.parseLong(config.getValue());
		if(log.isDebugEnabled()){
			log.debug("���Ҹö���" + o + "������: " + datumCategoryId);
		}
//		ObjectData a;
//		a.getObjectID();
//		a.getObjectType();
//		a.getDatumCategoryID();
		
		int objectType = 0;//RFSObject ��objectData���е�objectTypeΪ0����������ȡ  o.getObjectType();
		Logic logic = Restrictions.logic(Restrictions.eq("objectType", objectType))
			.and(Restrictions.eq("objectID", o.getId()))
			.and(Restrictions.eq("datumCategoryID", datumCategoryId));
		
		int count = objectDataDao.getCount(new ResultFilter(logic, null));
		return count > 0 ? InfoDetail.STATUS_COMPLETE : InfoDetail.STATUS_INCOMPLETE;
	}
}
