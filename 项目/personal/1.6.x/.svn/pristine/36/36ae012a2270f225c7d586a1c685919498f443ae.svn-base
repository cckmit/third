package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.Message;
import org.opoo.apps.Model;
import org.opoo.apps.ModelAware;
import org.opoo.apps.license.annotation.ProductModule;
import org.opoo.apps.util.OrderUtils;
import org.opoo.apps.util.StringUtils;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;

import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 所有应用的 struts2 action的超类。
 * 
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
@ProductModule(edition = "common", module = "core")
public abstract class AbstractAppsAction extends ActionSupport implements Serializable, ModelAware {
	private static final long serialVersionUID = 3568338637946950765L;
	/**
	 * 分页查询时默认的页大小，可通过系统属性“defaultPageSize”设置修改。
	 */
	public static final int DEFAULT_PAGE_SIZE = 15;
	/**
	 * 分页查询时最大的页大小，可通过系统属性“maxPageSize”设置修改。
	 */
	public static final int PAGE_SIZE_MAX = 1000;
	
	private final Log log = LogFactory.getLog(getClass());
	
	private AppsActionExceptionHandler appsActionExceptionHandler = new AppsActionExceptionHandlerImpl();
	
	// 以下页面时接受请求用的
	private int start = -1;
	private int limit;
	private String sort;
	private String dir;
	private String ids;
	private Order order2;
	
	protected Model model = new Model();
	
	private int maxPageSize = PAGE_SIZE_MAX;
	private int defaultPageSize = DEFAULT_PAGE_SIZE;
	
	public AbstractAppsAction(){
		super();
		maxPageSize = AppsGlobals.getProperty("maxPageSize", PAGE_SIZE_MAX);
		defaultPageSize = AppsGlobals.getProperty("defaultPageSize", DEFAULT_PAGE_SIZE);
		
		if(log.isDebugEnabled()){
			log.debug(String.format("默认页面大小 %s，最大页面大小 %s。", defaultPageSize, maxPageSize));
		}
	}

	public Model getModel(){
		return model;
	}
	
	public void setModel(Model model){
		this.model = model;
	}
	
	public void setStart(int start) {
		this.start = start;
		model.setStart(start);
	}

	public void setLimit(int limit) {
		this.limit = limit;
		model.setLimit(limit);
	}

	public void setSort(String sort) {
		//StringUtils.validatePropertyName(sort);
		if(sort != null && !OrderUtils.isValidSortName(sort)/*.isValidPropertyName(sort)*/){
			addFieldError("sort", "invalid sort value.");
			return;
		}
		
		this.sort = sort;
		model.setSort(sort);
	}

	public void setDir(String dir) {
//		StringUtils.validateSortDir(dir);
		if(dir != null && !OrderUtils.isValidSortDir(dir)){
			addFieldError("dir", "invalid dir value.");
			return;
		}
		this.dir = dir;
		model.setDir(dir);
	}
	
	public void setSortString(String sortString){
		if(StringUtils.isNotBlank(sortString)){
			try{
				order2 = OrderUtils.buildOrder(sortString);
			}catch(IllegalArgumentException e){
				log.error("解析排序字段时出错，忽略排序条件: " + sortString, e);
			}
		}
	}
	
	public String getSortString(){
		return null;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public int getMaxResults() {
		if (limit <= 0){
			return defaultPageSize;
		}
//		if(limit > PAGE_SIZE_MAX) {
//			return PAGE_SIZE_MAX;
//		}
		if(limit > maxPageSize){
			return maxPageSize;
		}
		
		model.setLimit(limit);
		return limit;
	}

	public String getSort() {
		return sort;
	}

	public String getDir() {
		return dir;
	}

	public String getIds() {
		return ids;
	}

	public String[] getStringIds() {
		return ids.split(",");
	}
	

	
	public Long[] getLongIds() {
		String[] sids = getStringIds();
		Long[] lids = new Long[sids.length];
		for (int i = 0; i < lids.length; i++) {
			lids[i] = Long.parseLong(sids[i]);
		}
		return lids;
	}

	interface Converter<T> {
		T convert(String s);
	}
	@SuppressWarnings("unchecked")
	public <T> T[] getIds(Class<T> clazz){
		log.debug("getIds: " + clazz.getName());
		Converter<?> c = null;
		String[] stringIds = getStringIds();
		
		if(clazz.isAssignableFrom(String.class)){
			return (T[]) stringIds;
		}else if(clazz.isAssignableFrom(Long.class)){
			c = new Converter<Long>(){
				public Long convert(String s) {
					return Long.parseLong(s);
				}
			};
		} else if (clazz.isAssignableFrom(Integer.class)) {
			c = new Converter<Integer>() {
				public Integer convert(String s) {
					return Integer.parseInt(s);
				}
			};
		} else {
			throw new IllegalArgumentException("不支持的主键类型：" + clazz.getName());
		}

		//log.info(c);
		T[] t = (T[]) Array.newInstance(clazz, stringIds.length);
		for (int i = 0; i < stringIds.length; i++) {
			t[i] = (T) c.convert(stringIds[i]);
		}
		return t;
	}	
	
	public Order getOrder() {
		Order result = null;//order2;
		if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(dir)) {
			result = "ASC".equalsIgnoreCase(dir) ? Order.asc(sort) : Order.desc(sort);
		}
		
		if(order2 != null){
			if(result != null){
				result.add(order2);
			}else{
				result = order2;
			}
		}
		//ActionContext.getContext().getParameters();
		return result;
	}

	protected ResultFilter buildResultFilter(Criterion c) {
		return new ResultFilter(c, getOrder(), getStart(), getMaxResults());
	}


	@JSON(serialize = false)
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	@JSON(serialize = false)
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}	
	
	
	@SuppressWarnings("unchecked")
	public void printHeaders(HttpServletRequest request){
		Enumeration<String> en = request.getHeaderNames();
		while(en.hasMoreElements()){
			String name = en.nextElement();
			System.out.println(name + " : " + request.getHeader(name));
		}
	}
	
	/**
	 * 
	 */
	public void addFieldError(String fieldName, String errorMessage){
		super.addFieldError(fieldName, errorMessage);
		model.addError(fieldName, errorMessage);
		model.setSuccess(false);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#addActionError(java.lang.String)
	 */
	@Override
	public void addActionError(String anErrorMessage) {
		super.addActionError(anErrorMessage);
		model.setMessage(false, anErrorMessage, null);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#addActionMessage(java.lang.String)
	 */
	@Override
	public void addActionMessage(String message) {
		super.addActionMessage(message);
//		model.setMessage(false, message, null);
		model.setMessage(message);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#setActionErrors(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setActionErrors(Collection errorMessages) {
		super.setActionErrors(errorMessages);
		model.setSuccess(false);
		if(errorMessages != null && !errorMessages.isEmpty()){
			model.setMessage(errorMessages.iterator().next().toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#setActionMessages(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setActionMessages(Collection messages) {
		super.setActionMessages(messages);
		//model.setSuccess(false);
		//不一定是错误，普通消息
		if(messages != null && !messages.isEmpty()){
			model.setMessage(messages.iterator().next().toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#setFieldErrors(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setFieldErrors(Map errorMap) {
		super.setFieldErrors(errorMap);
		model.setErrors(errorMap);
		model.setSuccess(false);
	}

	/**
	 * @return the appsActionExceptionHandler
	 */
	public AppsActionExceptionHandler getAppsActionExceptionHandler() {
		return appsActionExceptionHandler;
	}

	/**
	 * @param appsActionExceptionHandler the appsActionExceptionHandler to set
	 */
	public void setAppsActionExceptionHandler(
			AppsActionExceptionHandler appsActionExceptionHandler) {
		this.appsActionExceptionHandler = appsActionExceptionHandler;
	}
	
	
	protected void handleActionException(Exception e) throws Exception{
		appsActionExceptionHandler.handle(this, e);
	}

	/**
	 * 返回的数据模型是不是可以映射的。
	 * @return the mappable
	 */
	public boolean isMappable() {
		return model.isMappable();
	}

	/**
	 * @param mappable the mappable to set
	 */
	public void setMappable(boolean mappable) {
		model.setMappable(mappable);
	}
	
	/**
	 * 获取返回结果中对象的代理类类名。
	 * 
	 * @return
	 */
	public String getProxyClassName(){
		return model.getProxyClassName();
	}
	
	/**
	 * 设置返回结果中对象的代理类类名。
	 * 
	 * 
	 * @see org.opoo.apps.Model#setProxyClassName(String)
	 * @param proxyClassName
	 */
	public void setProxyClassName(String proxyClassName){
		model.setProxyClassName(proxyClassName);
	}
	
	/**
	 * 设置返回结果中对象的代理类类名。
	 * 
	 * @see AbstractAppsAction#setProxyClassName(String)
	 * @param proxyClassName
	 */
	public void setPc(String proxyClassName){
		setProxyClassName(proxyClassName);
	}
	
	public String getPc(){
		return getProxyClassName();
	}
	
	public void setModelResult(Object result){
		if(result != null){
			//if(result instanceof com.opensymphony.xwork2.Result){
				//return ((com.opensymphony.xwork2.Result) result).execute(invocation);
			//}
			if(result instanceof Model){
				model = (Model) result;
				addModelToValidation(model);
			}else if (result instanceof List){
				model.setRows((List<?>) result);
			}else if(result instanceof Message){
				Message msg = (Message) result;
				model.setErrors(msg.getErrors());
				model.setMessage(msg.getMessage());
				model.setSuccess(msg.isSuccess());
				addModelToValidation(model);
			}else if(result instanceof Exception){
				model.setException((Exception) result);
				addModelToValidation(model);
			}else if(result instanceof String){
				model.setMessage((String) result);
				addModelToValidation(model);
			}else if(result instanceof Serializable){
				model.setData((Serializable)result);
			}
		}
	}

	private void addModelToValidation(Model model){
		if(model.getMessage() != null){
			String msg = model.getMessage();
			if(model.isSuccess()){
				super.addActionMessage(msg);
			}else{
				super.addActionError(msg);
			}
		}
		if(model.getErrors() != null && !model.getErrors().isEmpty()){
			addFieldErrors(model.getErrors());
		}
	}
	private void addFieldErrors(Map<String, String> errors) {
		for(Map.Entry<String, String> en: errors.entrySet()){
			super.addFieldError(en.getKey(), en.getValue());
		}
	}
}
