package org.opoo.apps.security.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * 命名空间配置处理器。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SecurityNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler{

	public void init() {
		//registerBeanDefinitionParser(Elements.ENTRY_POINTS, new EntryPointsBeanDefinitionParser());
		registerBeanDefinitionParser(Elements.FORM_LOGIN_ENTRY_POINT, new FormLoginEntryPointBeanDefinitionParser());
	}
}
