package org.opoo.apps.el;

import java.util.Properties;

import org.opoo.apps.util.FactoryFinder;
import org.opoo.cache.Cache;
import org.opoo.cache.TimedExpirationMap;


/**
 * 字符串表达式工厂。
 * 
 * <p>要应用自定义解析器工厂类有两种配置方式方式：
 * <ul>
 *  <li>1. 在类路径中定义文件“appsel.properties”，内容为
 *  		“org.opoo.apps.el.ExpressionFactory=工厂实现类类全名”。
 *  <li>2. 配置ServiceID：在META-INF/services目录下创建文件名为
 * 			“org.opoo.apps.el.ExpressionFactory”的文本文件，
 * 			文件内容为“工厂实现类类全名”。
 * </ul>
 * 
 * <p>第二种优先级高于第一种。推荐使用第二种配置实现。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class ExpressionFactory {
	
	/**
	 * 创建工程实例。
	 * 
	 * @return
	 */
    public static ExpressionFactory newInstance() {
        return ExpressionFactory.newInstance(null);
    }

    public static ExpressionFactory newInstance(Properties properties) {
        return (ExpressionFactory) FactoryFinder.find(
            "org.opoo.apps.el.ExpressionFactory",
            "org.opoo.apps.el.freemarker.ExpressionFactoryImpl",
            "appsel.properties", properties);
    }

    
    private Cache<String, Expression> cache 
    	= new TimedExpirationMap<String, Expression>("stringelCache", 600000, 600000);
    

	/**
	 * 获取指定字符串的表达式对象，若无则创建。
	 * 
	 * 
	 * @param expr
	 * @return
	 */
	public Expression getExpression(String expr){
		Expression expression = cache.get(expr);
		if(expression == null){
			expression = createExpression(expr);
			cache.put(expr, expression);
		}
		return expression;
	}
	
	
	/**
	 * 根据表达式字符串创建表达式或者根据表达式名称加载表达式。
	 * 
	 * @param expr 表达式字符串或者表达式名称
	 * @return
	 */
	public abstract Expression createExpression(String expr);
}
