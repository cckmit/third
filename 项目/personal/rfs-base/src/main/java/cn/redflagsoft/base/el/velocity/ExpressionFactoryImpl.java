/*
 * $Id: ExpressionFactoryImpl.java 5973 2012-08-03 08:14:22Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.el.velocity;

import org.apache.velocity.app.Velocity;
import org.opoo.apps.el.Expression;
import org.opoo.apps.el.ExpressionFactory;

/**
 * 使用Velocity实现的表达式解析类工厂。
 * 
 * <p>默认的表达式解析使用了Freemarker，在处理对象中的泛型属性（如
 * 域对象的id属性）时常报readMethod和writeMethod参数类型不匹配的错
 * 误，因此在此使用Velocity实现了新的解析器。
 * 
 * <p>要应用该解析器有3种方式：
 * <ul>
 *  <li>1. 在类路径中定义文件“appsel.properties”，内容为
 *  		“org.opoo.apps.el.ExpressionFactory=cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl”。
 *  <li>2. 配置ServiceID：在META-INF/services目录下创建文件名为
 * 			“org.opoo.apps.el.ExpressionFactory”的文本文件，
 * 			文件内容为“cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl”。
 *  <li>3. 可在应用中添加属性 apps.el.factoryClass=cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl。
 *  		属性一般添加在数据库中和配置文件中，在运行时，如果应用启动了，将调用
 *  		AppsGlobals.getProperty 从数据库中读取，如果应用没有启动，则调用
 *  		AppsGlobals.getSetupProperty 从主配置文件中读取。
 * </ul>
 * 
 * <p>以上三种配置的方式优先级依次提高，第三种配置方式优先级最高，但只在使用 StringUtils.processExpression()方法
 * 时才有效。第二种优先级高于第一种，是一种API级别的配置。推荐使用第二种配置实现。
 * 
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ExpressionFactoryImpl extends ExpressionFactory {
	static
	{
		try {
			Velocity.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		} catch (Exception e) {
		} 
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.el.ExpressionFactory#createExpression(java.lang.String)
	 */
	@Override
	public Expression createExpression(String arg0) {
		return new ExpressionImpl(arg0);
	}

}
