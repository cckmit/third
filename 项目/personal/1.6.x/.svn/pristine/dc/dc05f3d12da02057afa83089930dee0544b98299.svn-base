package org.opoo.apps;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.util.OrderUtils;
import org.opoo.apps.util.StringUtils;
import org.opoo.ndao.support.Pageable;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.Paginator;
import org.opoo.util.ClassUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 操作结果对象模型。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Model implements Result {
	private static final long serialVersionUID = -1777616074617386167L;
	
//	public static enum SortDir{
//		ASC, DESC;
//	}
	
	public final static String ASC = "ASC";
	public final static String DESC = "DESC";
	
	private static final Log log = LogFactory.getLog(Model.class);
	private String code = "";
    private String message = ""; 
    private boolean success = true;
	private List<?> rows;
    private Serializable data;
    private Map<String, String> errors;
    private Map<String, ?> parameters;
        
        
    private int start = 0;
    private int limit = 0;
    private int total = 0;
    private String sort;
    private String dir = ASC;//SortDir.ASC.name();
    
    private boolean mappable = false;
    //private boolean mapped = false;
    private String proxyClassName;
    private Constructor<?> proxyClassConstructor;
    //private boolean listProxied = false;
    //private AtomicBoolean listProxied = new AtomicBoolean(false);
    //private AtomicBoolean dataProxied = new AtomicBoolean(false);
    private boolean listProxied = false;
    private boolean dataProxied = false;
    
	public Model(){
    }
	
	public Model(PageableList<?> list){
    	setPageableList(list);
    }
    
    public Model(boolean success, String message, String code){
    	setMessage(success, message, code);
    }
 
    /**
     * 
     */
    public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		if(sort != null){
//			StringUtils.validatePropertyName(sort);
			OrderUtils.validateSortName(sort);
		}
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		if(dir != null){
			OrderUtils.validateSortDir(dir);
		}
		this.dir = dir;
	}
    
    
    public void setMessage(boolean success, String message, String code){
    	this.success = success;
    	this.message = message;
    	this.code = code;
    }

    
    public void setException(Exception ex){
    	this.setMessage(false, ex.getMessage(), null);
    	//log.error(getClass().getName() + " 发生异常", ex);
    	log.error(ex);
    }
    
    
	public void setPageableList(PageableList<?> list){
    	this.total = list.getItemCount();
    	this.rows = list;
    	this.limit = list.getPageSize();
    	this.start = list.getStartIndex();
    	//setRows(list);
    }
    
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}	
	
	@JSON(serialize = false)
	public Paginator getPaginator() {
		if (rows instanceof Pageable) {
			return new Paginator((Pageable) rows);
		}
		return null;
	}
	
	/**
	 * 
	 * @param field
	 * @param message
	 */
	public void addError(String fieldName, String errorMessage){
		if(errors == null){
			errors = new HashMap<String, String>();
		}
		errors.put(fieldName, errorMessage);
		this.success = false;
	}
	
	public void setErrors(Map<String, String> errors){
		this.errors = errors;
		this.success = false;
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the rows
	 */
	public List<?> getRows() {
		if(rows != null && !listProxied){
			listProxied = true;
			List<Object> newList = new ArrayList<Object>(rows);
			for(int i = 0 , n = newList.size() ; i < n ; i++){
				Object o = newList.get(i);
				o = proxyObject(o);
				if(o != null){
					newList.set(i, o);
				}
			}
			rows = newList;
		}
		return rows;
		
		
//		/**
//		 * 如果结果需要映射而且还没有映射成map，则执行
//		 */
//		if(rows != null && mappable && !mapped){
//			mapped = true;
//			
//				//System.out.println(this + " mappable...");
//				if(log.isDebugEnabled()){
//					log.debug("mappable rows ");
//				}
//				//final List list = rows;
//				final int size = rows.size();
//				for(int i = 0 ; i < size; i++){
//					Object o = rows.get(i);
//					if(o != null && o instanceof Mappable){
//						rows.set(i, ((Mappable) o).toMap());
//					}
//				}
//			
//			
//			//动态
//			/*
//			rows = new AbstractList(){
//				@Override
//				public Object get(int index) {
//					Object o = list.get(index);
//					if(o != null && o instanceof Mappable){
//						return ((Mappable) o).toMap();
//					}
//					return o;
//				}
//
//				@Override
//				public int size() {
//					return size;
//				}
//			};*/
//		}
		
//		return rows;
	}


	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<?> rows) {
		if (rows != null) {
			if (rows instanceof PageableList) {
				setPageableList((PageableList<?>) rows);
			} else {
				this.rows = rows;
				total = rows.size();
			}
		}
	}
	/**
	 * @return the data
	 */
	public Serializable getData() {
//		if(mappable && data != null){
//			if(data instanceof Mappable){
//				return (Serializable) ((Mappable)data).toMap();
//			}
//		}
		
		if(data != null && !dataProxied){
			dataProxied = true;
			Serializable o = (Serializable) proxyObject(data);
			if(o != null){
				data = o;
			}
		}
		
		return data;
	}
	
	
	private Object proxyObject(Object o) {
		if(mappable){
			if(o instanceof Mappable){
				//log.debug("满足 mappable 设置");
				return ((Mappable)o).toMap();
			}
		}
		
		if(proxyClassConstructor != null){
			try {
				//log.debug("通过构造函数创建代理对象");
				return proxyClassConstructor.newInstance(o);
			} catch (Exception e) {
				throw new IllegalArgumentException("对象代理出错", e);
			} 
		}
		
		return null;
	}
	
	
	
	/**
	 * @param data the data to set
	 */
	public void setData(Serializable data) {
		this.data = data;
	}
	
	public void setData(Serializable data, String message){
		setData(data);
		setMessage(message);
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the mappable
	 */
	public boolean isMappable() {
		return mappable;
	}

	/**
	 * @param mappable the mappable to set
	 */
	public void setMappable(boolean mappable) {
		this.mappable = mappable;
	}

	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return getTotal();
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		setTotal(itemCount);
	}

//	/**
//	 * @return the items
//	 */
//	public List getItems() {
//		return getRows();
//	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<?> items) {
		setRows(items);
	}
	
	public Map<String, ?> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, ?> parameters) {
		this.parameters = parameters;
	}

	public String getProxyClassName() {
		return proxyClassName;// != null ? proxyClass.getName() : null;
	}

	/**
	 * 设置返回结果中对象的代理类类名。
	 * 
	 * <p>当代理类类名不为空时，model 中的数据对象（data或者rows中的每个对象）都
	 * 将试图使用代理类类名指定的类去代理原数据对象。
	 * 
	 * <p>代理类必须定义一个参数为被代理对象的构造函数，且参数数量为 1 的构造函数
	 * 只能有 1 个。
	 * 
	 * @param className
	 */
	public void setProxyClassName(String className) {
		log.debug("setProxyClassName: " + className);
		if(StringUtils.isNotBlank(className)){
			try {
				Class<?> proxyClass = ClassUtils.forName(className);
				Constructor<?>[] constructors = proxyClass.getConstructors();
				for (Constructor<?> constructor : constructors) {
					if(constructor.getParameterTypes().length == 1){
						proxyClassConstructor = constructor;
						break;
					}
				}
			} catch (Exception e) {
				log.error("代理类设置错误", e);
				throw new IllegalArgumentException("代理类设置错误", e);
			}
			
			
			if(proxyClassConstructor == null){
				log.error("找不到类或者类没有适当的构造函数");
				throw new IllegalArgumentException("找不到类或者类没有适当的构造函数：" + className);
			}
			//System.out.println("构造函数：" + proxyClassConstructor);
			log.debug("构造函数为：" + proxyClassConstructor);
			this.proxyClassName = className;
		}
	}
}
