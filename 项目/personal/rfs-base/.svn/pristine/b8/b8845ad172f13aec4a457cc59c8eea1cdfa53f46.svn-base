package cn.redflagsoft.base.dao.hibernate3;


import java.util.List;

import org.opoo.apps.id.IdGeneratorProvider;

import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.dao.ExDataRiskDao;

public class ExDataRiskHibernateDao extends AbstractBaseHibernateDao<ExDataRisk,Long> implements ExDataRiskDao{
	@Override
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
		//super.setIdGeneratorProvider(idGeneratorProvider);
		setIdGenerator(idGeneratorProvider.getIdGenerator("exDataRisk"));
	}
	
	@SuppressWarnings("unchecked")
	public List<ExDataRisk> findWaitExRisk() {
		String qs = "select a from ExDataRisk a where a.status=1 ";
		return getHibernateTemplate().find(qs);
	}


}
