package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.ObjectData;
import cn.redflagsoft.base.dao.ObjectDataDao;
import cn.redflagsoft.base.service.ObjectDataService;

public class ObjectDataServiceImpl implements ObjectDataService{
	private ObjectDataDao objectDataDao;

	public void setObjectDataDao(ObjectDataDao objectDataDao) {
		this.objectDataDao = objectDataDao;
	}

	public int deleteObjectData(ObjectData objectData) {
		return objectDataDao.delete(objectData);
	}

	public ObjectData getObjectData(Long sn) {
		return objectDataDao.get(sn);
	}

	public ObjectData updateObjectData(ObjectData objectData) {
		return objectDataDao.update(objectData);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectDataService#saveObjectData(cn.redflagsoft.base.bean.ObjectData)
	 */
	public ObjectData saveObjectData(ObjectData objectData) {
		return objectDataDao.save(objectData);
	}
}
