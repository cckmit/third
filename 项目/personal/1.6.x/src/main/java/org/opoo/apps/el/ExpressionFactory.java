package org.opoo.apps.el;

import java.util.Properties;

import org.opoo.apps.util.FactoryFinder;
import org.opoo.cache.Cache;
import org.opoo.cache.TimedExpirationMap;


/**
 * �ַ������ʽ������
 * 
 * <p>ҪӦ���Զ�����������������������÷�ʽ��ʽ��
 * <ul>
 *  <li>1. ����·���ж����ļ���appsel.properties��������Ϊ
 *  		��org.opoo.apps.el.ExpressionFactory=����ʵ������ȫ������
 *  <li>2. ����ServiceID����META-INF/servicesĿ¼�´����ļ���Ϊ
 * 			��org.opoo.apps.el.ExpressionFactory�����ı��ļ���
 * 			�ļ�����Ϊ������ʵ������ȫ������
 * </ul>
 * 
 * <p>�ڶ������ȼ����ڵ�һ�֡��Ƽ�ʹ�õڶ�������ʵ�֡�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class ExpressionFactory {
	
	/**
	 * ��������ʵ����
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
	 * ��ȡָ���ַ����ı��ʽ���������򴴽���
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
	 * ���ݱ��ʽ�ַ����������ʽ���߸��ݱ��ʽ���Ƽ��ر��ʽ��
	 * 
	 * @param expr ���ʽ�ַ������߱��ʽ����
	 * @return
	 */
	public abstract Expression createExpression(String expr);
}
