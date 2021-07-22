/*
 * $Id: ExpressionFactoryImpl.java 5973 2012-08-03 08:14:22Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.el.velocity;

import org.apache.velocity.app.Velocity;
import org.opoo.apps.el.Expression;
import org.opoo.apps.el.ExpressionFactory;

/**
 * ʹ��Velocityʵ�ֵı��ʽ�����๤����
 * 
 * <p>Ĭ�ϵı��ʽ����ʹ����Freemarker���ڴ�������еķ������ԣ���
 * ������id���ԣ�ʱ����readMethod��writeMethod�������Ͳ�ƥ��Ĵ�
 * ������ڴ�ʹ��Velocityʵ�����µĽ�������
 * 
 * <p>ҪӦ�øý�������3�ַ�ʽ��
 * <ul>
 *  <li>1. ����·���ж����ļ���appsel.properties��������Ϊ
 *  		��org.opoo.apps.el.ExpressionFactory=cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl����
 *  <li>2. ����ServiceID����META-INF/servicesĿ¼�´����ļ���Ϊ
 * 			��org.opoo.apps.el.ExpressionFactory�����ı��ļ���
 * 			�ļ�����Ϊ��cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl����
 *  <li>3. ����Ӧ����������� apps.el.factoryClass=cn.redflagsoft.base.el.velocity.ExpressionFactoryImpl��
 *  		����һ����������ݿ��к������ļ��У�������ʱ�����Ӧ�������ˣ�������
 *  		AppsGlobals.getProperty �����ݿ��ж�ȡ�����Ӧ��û�������������
 *  		AppsGlobals.getSetupProperty ���������ļ��ж�ȡ��
 * </ul>
 * 
 * <p>�����������õķ�ʽ���ȼ�������ߣ����������÷�ʽ���ȼ���ߣ���ֻ��ʹ�� StringUtils.processExpression()����
 * ʱ����Ч���ڶ������ȼ����ڵ�һ�֣���һ��API��������á��Ƽ�ʹ�õڶ�������ʵ�֡�
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
