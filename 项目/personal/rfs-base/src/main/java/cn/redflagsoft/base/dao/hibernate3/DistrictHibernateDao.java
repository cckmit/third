/*
 * $Id: DistrictHibernateDao.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.dao.hibernate3;

import cn.redflagsoft.base.bean.DistrictBean;
import cn.redflagsoft.base.dao.DistrictDao;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictHibernateDao extends AbstractBaseHibernateDao<DistrictBean, String> implements DistrictDao {

	@Override
	public String getIdPropertyName() {
		return "code";
	}
}
