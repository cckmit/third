package org.opoo.apps.file.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SimpleFileHandlerChainManager implements FileHandlerChainManager, ApplicationContextAware, InitializingBean {
	private static final Log log = LogFactory.getLog(SimpleFileHandlerChainManager.class);
	public static final String HANDLER_SEPARATOR = ",";
	//public static final String CONFIG_SEPARATOR = "|";
	private ApplicationContext applicationContext;
	
	
	private boolean includeDefaultFileHandler = true;
	private FileHandler defaultFileHandler;
	
	
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public FileHandlerChain getFileHandlerChain(String config) {	
		List<FileHandler> handlers = findFileHandlers(config);
		SimpleFileHandlerChain chain = new SimpleFileHandlerChain();
		chain.setHandlers(handlers);
		
		if(log.isDebugEnabled()){
			StringBuffer sb = new StringBuffer();
			for(FileHandler fh:handlers){
				sb.append("\n").append(fh);
			}
			log.debug("构建FileHandlerChain: "  + chain + "\n包含FileHandler：" + sb.toString());
		}
		
		return chain;
	}
	private List<FileHandler> findFileHandlers(String config) throws BeansException{
		boolean include = includeDefaultFileHandler;

		List<FileHandler> list = new ArrayList<FileHandler>(1);
		if(StringUtils.isNotBlank(config)){
			String[] names = config.split(HANDLER_SEPARATOR);
			for(int i = 0 ; i < names.length ; i++){
				String name = names[i];
				if(StringUtils.isNotBlank(name)){
					if(i == 0 && name.length() == 1){
						if("0".equals(name)){
//							includeDefaultFileHandler = false;
                            include = false;
						}
						//只要是一位长度
						continue;
					}
					
					FileHandler fh = findFileHandler(name);
					if(log.isDebugEnabled()){
						log.debug("发现FileHandler: " + fh);
					}
					
					list.add(fh);
				}
			}
		}
		
		if(include/*includeDefaultFileHandler*/){
			list.add(0, defaultFileHandler);
		}
		
		return list;
	}
	
	private FileHandler findFileHandler(String name) throws BeansException{
		return (FileHandler) applicationContext.getBean(name);
	}

	/**
	 * @return the includeDefaultFileHandler
	 */
	public boolean isIncludeDefaultFileHandler() {
		return includeDefaultFileHandler;
	}

	/**
	 * @param includeDefaultFileHandler the includeDefaultFileHandler to set
	 */
	public void setIncludeDefaultFileHandler(boolean includeDefaultFileHandler) {
		this.includeDefaultFileHandler = includeDefaultFileHandler;
	}

	/**
	 * @return the defaultFileHandler
	 */
	public FileHandler getDefaultFileHandler() {
		return defaultFileHandler;
	}

	/**
	 * @param defaultFileHandler the defaultFileHandler to set
	 */
	public void setDefaultFileHandler(FileHandler defaultFileHandler) {
		this.defaultFileHandler = defaultFileHandler;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(applicationContext);
		Assert.notNull(defaultFileHandler);
	}





	public Model performChain(FileHandlerChain chain, FileInfo fileInfo) {
		if(chain instanceof SimpleFileHandlerChain){
			return ((SimpleFileHandlerChain) chain).perform(fileInfo);
		}
		throw new IllegalArgumentException("chain不是SimpleFileHandlerChain的类型。");
	}


	public Model performChain(String chainConfig, FileInfo fileInfo) {
		SimpleFileHandlerChain chain = (SimpleFileHandlerChain)getFileHandlerChain(chainConfig);
		return chain.perform(fileInfo);
	}
}
