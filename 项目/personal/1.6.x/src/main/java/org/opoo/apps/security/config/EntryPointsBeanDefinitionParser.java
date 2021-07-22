package org.opoo.apps.security.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;


/**
 * «–»Îµ„≈‰÷√Ω‚Œˆ∆˜°£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EntryPointsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser implements BeanDefinitionParser {
	public static final String ENTRY_POINT_PATTERN = "pattern";
	public static final String ENTRY_POINT_LOGIN_PAGE = "login-page";
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClassName(org.w3c.dom.Element)
	 */
	@Override
    protected String getBeanClassName(Element element) {
        return "java.util.LinkedHashMap";
    }

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		List list = DomUtils.getChildElementsByTagName(element, Elements.ENTRY_POINT);
	     
		// Check for attributes that aren't allowed in this context
        Iterator urlElts = list.iterator();
        Map<String,String> entryPoints = new LinkedHashMap<String,String>();
        while(urlElts.hasNext()) {
            Element elt = (Element) urlElts.next();
            String pattern = elt.getAttribute(ENTRY_POINT_PATTERN);
            String loginPage = elt.getAttribute(ENTRY_POINT_LOGIN_PAGE);
            
            if (!StringUtils.hasLength(pattern)) {
                parserContext.getReaderContext().error("The attribute '" + ENTRY_POINT_PATTERN + "' is required here.", elt);
            }

            if (!StringUtils.hasLength(loginPage)) {
                parserContext.getReaderContext().error("The attribute '" + ENTRY_POINT_LOGIN_PAGE + "' is required here.", elt);
            }
            
            entryPoints.put(pattern.trim(), loginPage.trim());
        }
        
		builder.addConstructorArgValue(entryPoints);
	}

//	public BeanDefinition parse(Element element, ParserContext parserContext) {
//		DomUtils.getChildElementsByTagName(element, Elements.ENTRY_POINT);
//		return null;
//	}

}
