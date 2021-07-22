/*
 * $Id: GlossaryHibernateDao.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.dao.GlossaryDao;


/**
 * @author mwx
 *
 */
public class GlossaryHibernateDao extends AbstractBaseHibernateDao<Glossary,Long> implements GlossaryDao {

	@SuppressWarnings("unchecked")
	public Map<Integer, String> findByCategory(short category) {
		String qs = "from Glossary where category=? order by displayOrder asc";
		List<Glossary> list = getHibernateTemplate().find(qs, category);
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for(Glossary g:list){
			map.put(g.getCode(), g.getTerm());
		}
		return Collections.unmodifiableMap(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Glossary> findByCategory2(short category){
		String qs = "select a from Glossary a where category=? order by displayOrder";
		return getHibernateTemplate().find(qs, category);
	}

	public String getByCategoryAndCode(short category, int code) {
		Criterion c = Restrictions.logic(Restrictions.eq("category",category )).and(Restrictions.eq("code", code));
		Glossary g = get(c);
		if(g != null)
			return g.getTerm();
		return null;
	}

	@Override
	protected String getDataProperty() {
		return "code";
	}

	@Override
	protected String getLabelProperty() {
		return "term";
	}
}
