package org.opoo.apps.dv.converter;

import org.opoo.apps.dv.ConversionArtifactType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;


/**
 * 使用Spring实现的工厂类，
 * 指定的converter在配置时必须执行属性scope=“prototype”。
 * @author lcj
 *
 */
public class SpringConverterFactory extends AbstractConverterFactory implements ConverterFactory, ApplicationContextAware, InitializingBean {
	private ApplicationContext applicationContext;
	private String converterBean;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setConverterBean(String converterBean) {
		this.converterBean = converterBean;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(converterBean, "必须指定converter的beanName。");
	}
	
	@Override
	protected Converter createConverter(ConversionArtifactType type) {
		return (Converter) applicationContext.getBean(converterBean);
	}
}
