/**
 * 
 */
package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Dao;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.AccessDeniedException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * @author Alex Lin
 * @deprecated using QueryAction2
 */
public class QueryAction extends AbstractAppsAction implements ApplicationContextAware {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(QueryAction.class);
	
	//内部变量
	private ApplicationContext applicationContext;
	private Dao<?,?> dao;
	
	//配置变量
	private List<String> excludeTargets;
	
	
	//action请求参数
	private String id;
	private String target;
	private String methodName;
	
	
	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Dao getDao(){
		if(dao != null){
			return dao;
		}
		
		String daoName = target + "Dao";
		Object bean = null;
		try {
			bean = applicationContext.getBean(daoName);
		} catch (Exception e) {
			log.error(e);
			model.setException(e);
			//e.printStackTrace();
			return null;
		}
		
		if(bean != null && bean instanceof Dao){
			dao = (Dao) bean;
			return dao;
		}
		log.error("Find dao for " + target + " : " + bean);
		model.setMessage(false, "找不到处理数据的DAO: " + target, null);
		return null;
	}
	
	protected boolean isExcudeTarget(String target){
		return excludeTargets != null && excludeTargets.contains(target);
	}
	
	/**
	 * 子类覆盖这个方法来进行权限检查等。
	 * @return
	 */
	protected String check(){
		if(isExcudeTarget(target)){
			throw new AccessDeniedException("不允许使用通用查询访问此类对象：" + target);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String get() throws Exception{
		String ck = check();
		if(ck != null) return ck;

		Dao dao = getDao();
		if(dao == null) return SUCCESS;
		
		if(dao instanceof HibernateDao){
			Class idClass = ((HibernateDao)dao).getIdClass();
			Serializable value = (Serializable) XWorkConverter.getInstance().convertValue(ActionContext.getContext().getContextMap(), id, idClass);
			Serializable o = dao.get(value);
			if(o == null){
				throw new ObjectNotFoundException("找不到对象：id=" + id);
			}
			model.setData(o);
		
		}else{
			throw new ObjectNotFoundException("不支持查询此类对象：target=" + target + ", id=" + id);
		}
		
		
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String list() throws Exception{
		String ck = check();
		if(ck != null) return ck;

		Dao dao = getDao();
		if(dao == null) return SUCCESS;

		
		model.setRows(dao.find(buildResultFilter()));
		
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String page() throws Exception{
		String ck = check();
		if(ck != null) return ck;

		Dao dao = getDao();
		if(dao == null) return SUCCESS;
		
		model.setPageableList(dao.findPageableList(buildResultFilter()));
		
		return SUCCESS;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		if(!target.equals(this.target)){
			this.target = target;
			this.dao = null;
		}
	}
	
	
	
	
	
	/**
	 *
	 * @return
	 */
	protected ResultFilter buildResultFilter(){
		return super.buildResultFilter(null);
	}


	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the excludeTargets
	 */
	public List<String> getExcludeTargets() {
		return excludeTargets;
	}

	/**
	 * @param excludeTargets the excludeTargets to set
	 */
	public void setExcludeTargets(List<String> excludeTargets) {
		this.excludeTargets = excludeTargets;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * methodName的别名
	 * @param methodName
	 */
	public void setM(String methodName){
		this.methodName = methodName;
	}

	
}
