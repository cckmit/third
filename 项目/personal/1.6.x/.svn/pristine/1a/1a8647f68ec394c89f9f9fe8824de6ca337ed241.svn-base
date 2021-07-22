package org.opoo.apps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 结果对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Result extends Message {
	/**
	 * 结果集合。 根据实际情况，如果仅仅是表单提交，应该没有集合。
	 * 
	 * @return
	 */
	List<?> getRows();

	/**
	 * 消息。
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * 操作是否成功。
	 * 
	 * @return
	 */
	boolean isSuccess();

	/**
	 * 错误集合。 键对应字段名称，值对应错误消息。
	 * 
	 * @return
	 */
	Map<String, String> getErrors();

	/**
	 * 单个对象。
	 * 
	 * @return
	 */
	Serializable getData();

	/**
	 * 页大小。结果集限制。
	 * @return
	 */
	int getLimit();

	/**
	 * 结果总数。
	 * @return
	 */
	int getTotal();

	/**
	 * 结果集开始序号。
	 * 
	 * @return
	 */
	int getStart();

	/**
	 * 排序方向。
	 * @return
	 */
	String getDir();

	/**
	 * 排序字段、属性。
	 * @return
	 */
	String getSort();

	/**
	 * 单个操作结果对象。
	 * @param data
	 */
	void setData(Serializable data);

	/**
	 * 结果集。
	 * @param rows
	 */
	void setRows(List<?> rows);
}
