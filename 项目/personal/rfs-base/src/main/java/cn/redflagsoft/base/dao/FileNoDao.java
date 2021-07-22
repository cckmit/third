package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.FileNo;

public interface FileNoDao extends Dao<FileNo, Long> {
	
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	FileNo getByUid(String uid);
}
