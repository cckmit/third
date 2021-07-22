package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.ObjectData;

public interface ObjectDataService {
	ObjectData getObjectData(Long sn);
	
	int deleteObjectData(ObjectData objectData);
	
	ObjectData updateObjectData(ObjectData objectData);
	
	ObjectData saveObjectData(ObjectData objectData);
}
