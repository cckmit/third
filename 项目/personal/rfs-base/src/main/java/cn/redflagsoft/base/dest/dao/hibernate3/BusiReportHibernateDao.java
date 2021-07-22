package cn.redflagsoft.base.dest.dao.hibernate3;


import org.opoo.apps.id.IdGeneratorProvider;

import cn.redflagsoft.base.dao.hibernate3.AbstractBaseHibernateDao;
import cn.redflagsoft.base.dest.bean.BusiReport;
import cn.redflagsoft.base.dest.dao.BusiReportDao;


@Deprecated
public class BusiReportHibernateDao extends AbstractBaseHibernateDao<BusiReport,Long> implements BusiReportDao {
	@Override
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
		//super.setIdGeneratorProvider(idGeneratorProvider);
		setIdGenerator(idGeneratorProvider.getIdGenerator("busiReport"));
	}
}
