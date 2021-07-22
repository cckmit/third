/*
 * $Id: PictureHibernateDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Picture;
import cn.redflagsoft.base.dao.PictureDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PictureHibernateDao extends AbstractBaseHibernateDao<Picture, Long> implements PictureDao, ClerkNameChangeListener  {
	private static final Log log = LogFactory.getLog(PictureHibernateDao.class);
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.milestone.dao.SceneLogDao#queryByObjectId(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Picture> queryByObjectId(long objectId, String query) {
		String qs = "from Picture where objectId=?";
		String orderBy = " order by publishTime desc";
		if(StringUtils.isBlank(query)){
			return getHibernateTemplate().find(qs + orderBy, objectId);
		}else{
			qs += " and (title like ? or categoryName like ? or scene like ? " +
					"or keywords like ? or publisherName like ? or description like ?)";
			Object[] values = new Object[7];
			values[0] = objectId;
			for(int i = 1 ; i < values.length ; i++){
				values[i] = "%" + query + "%";
			}
			return getHibernateTemplate().find(qs + orderBy, values);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.milestone.dao.SceneLogDao#queryByObjectsFstObjectId(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Picture> queryByObjectsFstObjectId(long fstObjectId, int type, String query) {
		String qs = "select a from Picture a, Objects b where a.id=b.sndObject and b.fstObject=? and b.type=?";
		String orderBy = " order by a.publishTime desc";
		if(StringUtils.isBlank(query)){
			return getHibernateTemplate().find(qs + orderBy, new Object[]{fstObjectId, type});
		}else{
			qs += " and (a.title like ? or a.categoryName like ? or a.scene like ? " +
					"or a.keywords like ? or a.publisherName like ? or a.description like ?)";
			Object[] values = new Object[8];
			values[0] = fstObjectId;
			values[1] = type;
			for(int i = 2 ; i < values.length ; i++){
				values[i] = "%" + query + "%";
			}
			return getHibernateTemplate().find(qs + orderBy, values);
		}
	}
	
	
//	@SuppressWarnings("unchecked")
//	public List<Picture> findByObjects(ResultFilter filter){
//		String qs = "select a from Picture a, Objects b where a.id=b.sndObject";
//		return getQuerySupport().find(qs, filter);
//	}
	
	
	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("人员信息发生了变化，同步更新Picture的相关人员信息：" + oldName + " --> " + newName);
		}
		String qs1 = "update Picture set publisherName=? where publisherId=?";
		
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//清理缓存
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了Picture缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新Picture的相关人员信息 ‘" + rows + "’个"); 
		}
	}
}
