package cn.redflagsoft.base.test;



import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.spring.StrutsSpringObjectFactory;

import com.opensymphony.xwork2.inject.Inject;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
@Deprecated
public class TestSpringObjectFactory extends StrutsSpringObjectFactory {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5657333583166408257L;

	public TestSpringObjectFactory(
			@Inject(value=StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE,required=false) String autoWire,
            @Inject(value=StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE,required=false) String useClassCacheStr,
            @Inject ServletContext servletContext) {
		super(autoWire, useClassCacheStr, servletContext);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.spring.SpringObjectFactory#buildBean(java.lang.Class, java.util.Map)
	 */
	@Override
	public Object buildBean(Class clazz, Map extraContext) throws Exception {
		Object bean = super.buildBean(clazz, extraContext);
		System.out.println(this + " 创建了bean：" + bean);
		return bean;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.spring.SpringObjectFactory#buildBean(java.lang.String, java.util.Map, boolean)
	 */
	@Override
	public Object buildBean(String beanName, Map extraContext, boolean injectInternal) throws Exception {
		Object bean = super.buildBean(beanName, extraContext, injectInternal);
		System.out.println(this + " 创建了bean：" + bean);
		return bean;
	}

}
