package org.opoo.apps.web.sitemesh;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.DefaultDecorator;
import com.opensymphony.module.sitemesh.mapper.PathMapper;

public class DynamicDecoratorMapper extends ConfigDecoratorMapper {
	private static final Log log = LogFactory.getLog(DynamicDecoratorMapper.class);
	
    protected ConcurrentHashMap<String, Decorator> decorators = new ConcurrentHashMap<String, Decorator>();
	protected PathMapper pathMapper = new PathMapper();
    private static ConcurrentLinkedQueue<DecoratorBean> extraDecorators = new ConcurrentLinkedQueue<DecoratorBean>();
    private static ConcurrentLinkedQueue<String> extraExcludedPaths = new ConcurrentLinkedQueue<String>();
    
	@Override
	public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
		super.init(config, properties, parent);
		updateDecorators();
		updateExcludedPaths();
	}

	private void updateDecorators() {
		DecoratorBean bean;
		while ((bean =  extraDecorators.poll()) != null){
			addDecorator(bean.name, bean.page, bean.pattern);
		}
	}

	private void updateExcludedPaths() {
		String path;
		while ((path = (String) extraExcludedPaths.poll()) != null){
			addExclude(path);
		}
	}


	@Override
	public Decorator getDecorator(HttpServletRequest request, Page page) {
		//log.debug(request.getRequestURI() + " : " + page.getPage());

		
		Decorator decorator = super.getDecorator(request, page);
		if (decorator != null) {
			return decorator;
		}

		String thisPath = request.getServletPath();
		// getServletPath() returns null unless the mapping corresponds to a
		// servlet
		if (thisPath == null) {
			String requestURI = request.getRequestURI();
			if (request.getPathInfo() != null)
				// strip the pathInfo from the requestURI
				thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
			else
				thisPath = requestURI;
		} else if (thisPath.equals("") && request.getPathInfo() != null){
			thisPath = request.getPathInfo();
		}
		updateDecorators();
		String name = pathMapper.get(thisPath);
		return getNamedDecorator(request, name);
	}
	
	

	@Override
	public Decorator getNamedDecorator(HttpServletRequest request, String name) {
		if (StringUtils.isBlank(name)) {
			String thisPath = request.getServletPath();
			if (thisPath == null) {
				String requestURI = request.getRequestURI();
				if (request.getPathInfo() != null)
					thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
				else
					thisPath = requestURI;
			} else if (thisPath.equals("") && request.getPathInfo() != null)
				thisPath = request.getPathInfo();
			updateDecorators();
			//log.debug("thisPath: " + thisPath);
			name = pathMapper.get(thisPath);
		}
		log.debug("Get named decorator for " + name);
		Decorator result = super.getNamedDecorator(request, name);
		if (result != null) {
			log.debug("super.getNamedDecorator: " + result);
			return result;
		}
		if (StringUtils.isBlank(name)){
			return null;
		}else{
			//log.debug("decorators.get(name): " + decorators.get(name));
			return decorators.get(name);
		}
	}

    public void addDecorator(String name, String page, String pattern) {
        String uriPath = null;
        Map<?, ?> params = null;
        String role = null;

        Decorator d = new DefaultDecorator(name, page, uriPath, role, params);
        decorators.put(name, d);
        pathMapper.put(name, pattern);
        
        if(log.isDebugEnabled()){
        	log.debug(String.format("Add decorator %s -> %s : %s", name, page, pattern));
        }
    }
	
	
    public Collection<Decorator> getDecorators() {
    	updateDecorators();
        return decorators.values();
    }
    
    public void addExclude(String path) {
    	AppsSitemeshFactory factory = (AppsSitemeshFactory) Factory.getInstance(config);
        factory.addExcludeUrl(path);
    }

    public PathMapper getExcludes() {
    	updateExcludedPaths();
    	AppsSitemeshFactory factory = (AppsSitemeshFactory) Factory.getInstance(config);
        return factory.getExcludeUrls();
    }

    public boolean isPathExcluded(String path) {
    	updateExcludedPaths();
    	AppsSitemeshFactory factory = (AppsSitemeshFactory) Factory.getInstance(config);
        return factory.isPathExcluded(path);
    }
    
	public static void addDecoratorBean(DecoratorBean bean) {
		extraDecorators.add(bean);
	}

	public static void addExcludedPath(String path) {
		extraExcludedPaths.add(path);
	}
    
	public static class DecoratorBean {
		private String name;
		private String page;
		private String pattern;

		public DecoratorBean(String name, String page, String pattern) {
			this.name = name;
			this.page = page;
			this.pattern = pattern;
		}
	}
}
