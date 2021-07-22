package org.opoo.apps.web.struts2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//import org.opoo.ndao.support.Pageable;

/**
 * @deprecated
 */
public interface ActionResult extends Serializable{//, Pageable {
	/**
	 * 结果集合。
	 * 根据实际情况，如果仅仅是表单提交，应该没有集合。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List getRows();
	/**
	 * 消息。
	 * @return
	 */
	String getMessage();
	/**
	 * 操作是否成功。
	 * @return
	 */
	boolean isSuccess();
	/**
	 * 错误集合。
	 * 键对应字段名称，值对应错误消息。
	 * @return
	 */
	Map<String, String> getErrors();
	
	/**
	 * 单个对象。
	 * @return
	 */
	Serializable getData();
	
	
	int getLimit();
	int getTotal();
	int getStart();
	String getDir();
	String getSort();
}
