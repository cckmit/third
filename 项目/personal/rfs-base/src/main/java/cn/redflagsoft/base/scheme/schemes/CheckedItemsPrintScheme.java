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
 * ѡ���Դ�ӡ/������ͨ�ô���������ڵ������� Scheme��WorkProcess ���� ͨ�ò�ѯ����ѯ
 * ��ѡ���б��е�ÿһ������ɱ���ӡ�ļ���
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
	 * Ҫ���õĲ�ѯ��������� Scheme �����ƣ��� IOC ���������õ����ơ�
	 */
	private String targetScheme;
	/**
	 * Ҫ���õĲ�ѯ��������� Scheme �ķ�������
	 */
	private String targetMethod;

	/**
	 * Ҫ���õĲ�ѯ��������� WorkProcess �� type���� targetScheme ��
	 * targetMethod ������ѡһ�֡�
	 */
	private Integer targetProcessType;
	
	/**
	 * Ҫ���õĲ�ѯ��������� scheme ���� WorkProcess ���յ� id �����Ĳ�������
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
			throw new IllegalArgumentException("����ָ��Ҫ��ӡ���ߵ������");
		}

		try {
			List<Object> rows = new ArrayList<Object>();
			for(String id: ids){
				Object o = get(id);
				if(o != null){
					rows.add(o);
				}else{
					log.warn("�Ҳ�������" + id);
				}
				//rows.add(get(id));
			}
			return rows;
		} catch (Exception e) {
			log.error("ѡ���Դ�ӡ����", e);
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
			throw new IllegalArgumentException("����ָ�� targetScheme+targetMethod��targetProcessType �� target ������");
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
			throw new IllegalArgumentException("Ҫ���õ�Ŀ�� Scheme ����ʵ�� ParametersAware �ӿڡ�");
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
