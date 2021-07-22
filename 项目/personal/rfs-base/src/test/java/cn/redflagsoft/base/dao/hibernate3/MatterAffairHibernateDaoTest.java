/**
 * 
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author lcj
 *
 */
public class MatterAffairHibernateDaoTest extends  BaseTests  {

	protected MatterAffairHibernateDao matterAffairDao;
	

	public void testFindAffairs(){
		List<MatterAffair> list = matterAffairDao.findAffairs((byte)0, 1001L, 0, (byte)0, (short)0);
		System.out.println(list);
	}
}
