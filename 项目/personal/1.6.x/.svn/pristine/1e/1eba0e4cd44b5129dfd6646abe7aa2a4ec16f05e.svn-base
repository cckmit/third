package org.opoo.apps.module.configurators;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.opoo.apps.module.ConfigurationContext;
import org.opoo.apps.module.ModuleConfigurator;
import org.opoo.apps.module.ModuleMetaData;
import org.opoo.apps.web.sitemesh.DynamicDecoratorMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class SitemeshConfigurator implements ModuleConfigurator {
	private static final Log log = LogFactory.getLog(SitemeshConfigurator.class);
	
	@SuppressWarnings("unchecked")
	public void configure(ConfigurationContext context) {
		
		
		ModuleMetaData meta = context.getModuleMetaData();
		Document config = meta.getConfig();
		
		log.debug("Config module sitemesh for " + meta.getName());
		
		List<?> decorators = config.selectNodes("/module/sitemesh/decorator");
		//DynamicDecoratorMapper mapper = DynamicDecoratorMapper.getInstance();
		for (Object o : decorators) {
			Element e = (Element) o;
			String name = e.attributeValue("name");
			assert name != null;
			String page = e.attributeValue("page");
			assert page != null;
			String pattern = e.elementText("pattern");
			assert pattern != null;
			DynamicDecoratorMapper.addDecoratorBean(new DynamicDecoratorMapper.DecoratorBean(name, page, pattern));
		}
        List<Element> excludes = config.selectNodes("/module/sitemesh/excludes/pattern");
        for (Object o : excludes) {
            Element e = (Element) o;
            DynamicDecoratorMapper.addExcludedPath(e.getText());
        }
	}

	public void destroy(ConfigurationContext context) {
	}
}
