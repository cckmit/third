package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opoo.apps.Labelable;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.service.QueryService;
import org.opoo.ndao.support.PageableList;

import com.opensymphony.xwork2.ActionContext;

public class QueryAction2 extends AbstractAppsAction implements QueryParameters {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4777161785386844451L;

	//private static final Log log = LogFactory.getLog(QueryAction2.class);
	
	//action请求参数
	private String id;
	private String target;
	private String methodName;
	private List<QueryParameter> parameters;
	private List<QueryParameter> staticParameters;
	private QueryService queryService;
	
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
	public void setM(String methodName){
		this.methodName = methodName;
	}
	public String getM(){
		return this.methodName;
	}
	
	/**
	 * @return the parameters
	 */
	public List<QueryParameter> getParameters() {
		List<QueryParameter> list = new ArrayList<QueryParameter>();
		if(parameters != null){
			list.addAll(parameters);
		}
		if(staticParameters != null){
			list.addAll(staticParameters);
		}
		return list;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<QueryParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void setQ(List<QueryParameter> parameters) {
		this.parameters = parameters;
	}
	
	public List<QueryParameter> getQ(){
		return this.parameters;
	}
	
	//?s[0]
	public List<QueryParameter> getS(){
		return this.staticParameters;
	}
	
	public void setS(List<QueryParameter> s){
		this.staticParameters = s;
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
	
	
	
	//////////////////////////////////////////
	// 通用查询方法
	///////////////////////////////////

	
	public String list() throws Exception{
		Object result = getQueryService().query(target, "find", this, List.class);
		model.setRows((List<?>) result);
		return SUCCESS;
	}
	
	public String page() throws Exception{
		Object result = getQueryService().query(target, "findPageableList", this, PageableList.class);
		model.setRows((PageableList<?>) result);
		return SUCCESS;
	}
	
	public String dynlist() throws Exception{
		Object result = getQueryService().query(target, methodName, this, List.class);
		model.setRows((List<?>) result);
		return SUCCESS;
	}
	
	public String dynpage() throws Exception{
		Object result = getQueryService().query(target, methodName, this, PageableList.class);
		model.setRows((PageableList<?>) result);
		return SUCCESS;
	}
	
	public String get() throws Exception{
		Serializable result = getQueryService().get(target, id);
		model.setData(result);
		return SUCCESS;
	}
	
	public String namedQueryList() throws Exception{
		List<?> list = getQueryService().namedQueryList(target, this);
		model.setRows(list);
		return SUCCESS;
	}
	
	public String list2() throws Exception{
		return namedQueryList();
	}
	
	
	public String namedQueryPageableList() throws Exception{
		PageableList<?> list = getQueryService().namedQueryPageableList(target, this);
		model.setRows(list);
		return SUCCESS;
	}
	
	public String page2() throws Exception{
		return namedQueryPageableList();
	}
	
	
	public String labeldata() throws Exception{
		List<Labelable> list = getQueryService().queryLabelables(target, this);
		model.setRows(list);
		return SUCCESS;
	}
	
	/**
	 * 指定指定的类的指定方法，根据返回值的类型确定是放在rows中还是data中。
	 * @return
	 * @throws Exception
	 */
	public String invoke()throws Exception{
		Object object = getQueryService().invoke(target, methodName, this);
		if(object instanceof List){
			model.setRows((List<?>) object);
		}else{
			model.setData((Serializable) object);
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, ?> getRawParameters() {
		return ActionContext.getContext().getParameters();
	}
}
