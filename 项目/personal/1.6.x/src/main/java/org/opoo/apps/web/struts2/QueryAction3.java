package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.query.Query;
import org.opoo.apps.query.QueryManager;
import org.opoo.apps.util.StringUtils;
import org.opoo.ndao.support.PageableList;
import org.opoo.util.Assert;

import com.opensymphony.xwork2.ActionContext;

public class QueryAction3 extends AbstractAppsAction implements QueryParameters {
	private static final long serialVersionUID = -4777161785386844451L;

	//private static final Log log = LogFactory.getLog(QueryAction2.class);
	
	//action请求参数
	private String id;
	private String target;
	private String methodName;
	private List<QueryParameter> parameters;
	private List<QueryParameter> staticParameters;
	//防止跟其它变量名冲突，尽量留给其它字段
	private String queryKey__;
	
	private QueryManager queryManager;
	
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
		setM(methodName);
	}
	public void setM(String methodName){
		if(methodName != null && !StringUtils.isValidMethodName(methodName)){
			//throw new IllegalArgumentException("methodName is invalid.");
			addFieldError("methodName", "Invalid methodName");
			return;
		}
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
	
	public QueryManager getQueryManager() {
		return queryManager;
	}
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	
	
	//////////////////////////////////////////
	// 通用查询方法
	///////////////////////////////////
	
	private String execute(String key, Class<?> expectedType){
		Query<?> query = queryManager.getQuery(key);
		Object result = query.execute(this);
		if(expectedType != null && result != null){
			if(!expectedType.isInstance(result)){
				throw new QueryException("返回值类型不正确，要求类型'" 
						+ expectedType.getName() + "'，实际类型'"
						+ result.getClass().getName() + "'");
			}
		}
		if(result instanceof List){
			model.setRows((List<?>) result);
		}else{
			model.setData((Serializable) result);
		}
		return SUCCESS;
	}

	private String execute(String key){
		return execute(key, null);
	}
	
	public String execute() throws Exception{
		Assert.notBlank(id, "查询ID不能为空");
		return execute(id);
	}

	public String list() throws Exception{
		return execute(target + ".list", List.class);
	}
	
	public String page() throws Exception{
		return execute(target + ".page", PageableList.class);
	}
	
	public String dynlist() throws Exception{
		return execute(target + "." + methodName, List.class);
	}
	
	public String dynpage() throws Exception{
		return execute(target + "." + methodName, PageableList.class);
	}
	
	public String get() throws Exception{
		return execute(target + ".get", Serializable.class);
	}
	
	public String namedQueryList() throws Exception{
		return execute(target + ".list2", List.class);
	}
	
	public String list2() throws Exception{
		return namedQueryList();
	}
	
	public String namedQueryPageableList() throws Exception{
		return execute(target + ".page2", PageableList.class);
	}
	
	public String page2() throws Exception{
		return namedQueryPageableList();
	}
	
	public String labeldata() throws Exception{
		return execute(target + ".label", List.class);
	}
	
	/**
	 * 指定指定的类的指定方法，根据返回值的类型确定是放在rows中还是data中。
	 * @return
	 * @throws Exception
	 */
	public String invoke()throws Exception{
		String key = target;
		if(StringUtils.isNotBlank(methodName)){
			key += "." + methodName;
		}
		return execute(key);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, ?> getRawParameters() {
		return ActionContext.getContext().getParameters();
	}
	
	public String getQueryKey__() {
		return queryKey__;
	}
	
	public void setQueryKey__(String queryKey__) {
		this.queryKey__ = queryKey__;
	}
}
