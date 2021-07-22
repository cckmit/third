/*
 * $Id: PictureDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Picture;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface PictureDao extends Dao<Picture, Long> {
	/**
	 * 通过相关对象ID和查询条件查询。
	 * 
	 * @param objectId
	 * @param query 对众多字段的模糊查询条件
	 * @return
	 */
	List<Picture> queryByObjectId(long objectId, String query);
	
	/**
	 * 通过关联对象（Objects）中的fstObject和查询条件查询。
	 * 
	 * @param fstObjectId
	 * @param type 指定类型的关联关系
	 * @param query 对众多字段的模糊查询条件
	 * @return
	 */
	List<Picture> queryByObjectsFstObjectId(long fstObjectId, int type, String query);
}
