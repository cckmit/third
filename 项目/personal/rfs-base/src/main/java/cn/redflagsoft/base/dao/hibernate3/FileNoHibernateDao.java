package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.NonUniqueResultException;
import cn.redflagsoft.base.bean.FileNo;
import cn.redflagsoft.base.dao.FileNoDao;


public class FileNoHibernateDao extends AbstractBaseHibernateDao<FileNo, Long> implements FileNoDao {
	@SuppressWarnings("unchecked")
	public FileNo getByUid(String uid) {
		String qs = "from FileNo where uid=?";
		List<FileNo> list = getHibernateTemplate().find(qs, uid);
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new NonUniqueResultException(list.size());//IllegalStateException("返回结果不唯一: " + list.size());
		}
	}

}
