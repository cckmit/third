package org.opoo.apps.dvi.office.converter;

import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.converter.Converter;
import org.opoo.apps.dvi.converter.ConverterFactory;
import org.opoo.apps.dvi.office.OfficeConversionArtifactType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * 需要将 GenericOfficeConverter 的 scope 在 spring 中配置成  prototype 的。
 * @author lcj
 *
 */
public class SpringGenericOfficeConverterFactory implements
		ConverterFactory, ApplicationContextAware, InitializingBean {
	private ApplicationContext applicationContext;
	private String genericOfficeConverterBeanName;
	
	public Converter getConverter(ConversionArtifactType type) {
		GenericOfficeConverter c = (GenericOfficeConverter) applicationContext.getBean(genericOfficeConverterBeanName);
		c.setConversionArtifactType((OfficeConversionArtifactType) type);
		return c;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setGenericOfficeConverterBeanName(
			String genericOfficeConverterBeanName) {
		this.genericOfficeConverterBeanName = genericOfficeConverterBeanName;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(genericOfficeConverterBeanName);
	}
}
