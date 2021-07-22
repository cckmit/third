package org.opoo.apps.el;


/**
 * 字符串表达式。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Expression {
	
	
	/**
	 * 根据上下文解析表达式，并返回字符串形式的值。
	 * 
	 * @param context
	 * @return
	 * @throws RuntimeException
	 */
	String getValue(Object context) throws RuntimeException;
	
	
	/**
	 * 
	 * @return
	 */
	String getExpressionString();
}
