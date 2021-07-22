package cn.redflagsoft.base.scheme.schemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.service.QueryService;

import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.process.WorkProcess;
import cn.redflagsoft.base.process.WorkProcessManager;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;


/**
 * 选择性打印/导出的通用处理程序，用于调用其他 Scheme、WorkProcess 或者 通用查询来查询
 * 被选择列表中的每一项，并生成被打印文件。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CheckedItemsPrintScheme extends AbstractScheme {
	private static final Log log = LogFactory.getLog(CheckedItemsPrintScheme.class);
	
	private SchemeManager schemeManager;
	private WorkProcessManager workProcessManager;
	private QueryService queryService;
	
	private List<String> ids = new ArrayList<String>();

	/**
	 * 要调用的查询对象详情的 Scheme 的名称，在 IOC 容器中配置的名称。
	 */
	private String targetScheme;
	/**
	 * 要调用的查询对象详情的 Scheme 的方法名。
	 */
	private String targetMethod;

	/**
	 * 要调用的查询对象详情的 WorkProcess 的 type，与 targetScheme 和
	 * targetMethod 参数任选一种。
	 */
	private Integer targetProcessType;
	
	/**
	 * 要调用的查询对象详情的 scheme 或者 WorkProcess 接收的 id 参数的参数名。
	 */
	private String targetObjectIdProperty = "id";

	/**
	 * 
	 */
	private String target;

	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractScheme#doScheme()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object doScheme() throws SchemeException {
		if(ids.isEmpty()){
			throw new IllegalArgumentException("必须指定要打印或者导出的项。");
		}

		try {
			List<Object> rows = new ArrayList<Object>();
			for(String id: ids){
				Object o = get(id);
				if(o != null){
					rows.add(o);
				}else{
					log.warn("找不到对象：" + id);
				}
				//rows.add(get(id));
			}
			return rows;
		} catch (Exception e) {
			log.error("选择性打印出错", e);
			throw new SchemeException(e);
		}
	}


	protected Object get(String id) throws Exception{
		Map<String,String> params = new HashMap<String,String>();
		params.put(targetObjectIdProperty, id);
		if(targetScheme != null){
			return getByScheme(targetScheme, targetMethod, params);
		}else if(targetProcessType != null){
			return getByProcess(targetProcessType, params);
		}else if(target != null){
			return queryService.get(target, id);
		}else{
			throw new IllegalArgumentException("必须指定 targetScheme+targetMethod，targetProcessType 或 target 参数。");
		}
	}
	
	
	private Object getByProcess(Integer processType, Map<?, ?> params) {
		WorkProcess process = workProcessManager.getProcess(processType);
		return process.execute(params);
	}


	private Object getByScheme(String name, String method, Map<?, ?> params) throws Exception{
		Scheme scheme = schemeManager.getScheme(name);
		if(scheme instanceof ParametersAware){
			((ParametersAware) scheme).setParameters(params);
		}else{
			throw new IllegalArgumentException("要调用的目标 Scheme 必须实现 ParametersAware 接口。");
		}
		return SchemeInvoker.invoke(scheme, method);
	}
	
	
	/**
	 * @return the schemeManager
	 */
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}


	/**
	 * @param schemeManager the schemeManager to set
	 */
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}


	/**
	 * @return the workProcessManager
	 */
	public WorkProcessManager getWorkProcessManager() {
		return workProcessManager;
	}


	/**
	 * @param workProcessManager the workProcessManager to set
	 */
	public void setWorkProcessManager(WorkProcessManager workProcessManager) {
		this.workProcessManager = workProcessManager;
	}


	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}


	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}


	/**
	 * @return the targetScheme
	 */
	public String getTargetScheme() {
		return targetScheme;
	}


	/**
	 * @param targetScheme the targetScheme to set
	 */
	public void setTargetScheme(String targetScheme) {
		this.targetScheme = targetScheme;
	}


	/**
	 * @return the targetMethod
	 */
	public String getTargetMethod() {
		return targetMethod;
	}


	/**
	 * @param targetMethod the targetMethod to set
	 */
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}


	/**
	 * @return the targetProcessType
	 */
	public Integer getTargetProcessType() {
		return targetProcessType;
	}


	/**
	 * @param targetProcessType the targetProcessType to set
	 */
	public void setTargetProcessType(Integer targetProcessType) {
		this.targetProcessType = targetProcessType;
	}


	/**
	 * @return the targetObjectIdProperty
	 */
	public String getTargetObjectIdProperty() {
		return targetObjectIdProperty;
	}


	/**
	 * @param targetObjectIdProperty the targetObjectIdProperty to set
	 */
	public void setTargetObjectIdProperty(String targetObjectIdProperty) {
		this.targetObjectIdProperty = targetObjectIdProperty;
	}


	/**
	 * @return the queryService
	 */
	public QueryService getQueryService() {
		return queryService;
	}


	/**
	 * @param queryService the queryService to set
	 */
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
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
		this.target = target;
	}

}
