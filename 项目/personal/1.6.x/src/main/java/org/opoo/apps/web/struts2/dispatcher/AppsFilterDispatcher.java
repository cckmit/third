package org.opoo.apps.web.struts2.dispatcher;

import static org.opoo.apps.web.struts2.event.ConfigurationProviderEvent.Type.ADD;
import static org.opoo.apps.web.struts2.event.ConfigurationProviderEvent.Type.REMOVE;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.config.DefaultSettings;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.EventListenerManager;
import org.opoo.apps.event.v2.EventListenerManagerAware;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.web.struts2.event.ConfigurationProviderEvent;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;

/**
 * Struts2的Action派发过滤器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsFilterDispatcher extends FilterDispatcher implements
		EventListener<ConfigurationProviderEvent>, EventListenerManagerAware {
	private static final Log log = LogFactory.getLog(AppsFilterDispatcher.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();

	private AtomicBoolean initialized = new AtomicBoolean(false);
	private BlockingQueue<ConfigurationProviderEvent> providerEvents = new LinkedBlockingQueue<ConfigurationProviderEvent>();
	private static AppsFilterDispatcher instance;

	/*
	 * 在 super 调用之前执行，则在 super 调用时会重新加载 DefaultSettings，所以该初始化无效。
	 * 在 super 调用之后执行的  DefaultSettings 不会影响到已经初始化的容器属性，所以也无效。
	 * <p>
	 * 因此要采用一种能够在初始化中间执行的方法，应用这些配置：自定义 ConfigurationProvider
	 * 并在添加自定义ConfigurationProvider后调用 ConfigurationManager 的 reload 方法。
	 * 
	 * @seeorg.apache.struts2.dispatcher.FilterDispatcher#init(javax.servlet.
	 * FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		instance = this;

		// We should not fully initialized until we are out of setup
		if (AppsGlobals.isSetup()) {
			init();
		}
		if(IS_DEBUG_ENABLED){
			log.debug("call super.init(filterConfig)");
		}
		super.init(filterConfig);
//		System.out.println(">>>2: " + DefaultSettings.getInstance());
//		ConfigurationManager configurationManager = dispatcher.getConfigurationManager();
//		configurationManager.addConfigurationProvider(new AppsSettingsProvider());
//		configurationManager.reload();
	}
	
	
	private synchronized void init() {
		if (!initialized.get()) {
			/********************
			String encoding = AppsGlobals.getCharacterEncoding();
			if (IS_DEBUG_ENABLED) {
				log.debug("Setting struts character encoding [" + StrutsConstants.STRUTS_I18N_ENCODING + "] to "
						+ encoding);
			}
			System.out.println(">>>1: " + DefaultSettings.getInstance());
			DefaultSettings.set(StrutsConstants.STRUTS_I18N_ENCODING, encoding);
			Locale locale = AppsGlobals.getLocale();
			if (DefaultSettings.isSet(StrutsConstants.STRUTS_LOCALE)) {
				if (IS_DEBUG_ENABLED) {
					log.debug("Setting struts locale [" + StrutsConstants.STRUTS_LOCALE + "] to " + locale.toString());
				}
				DefaultSettings.set(StrutsConstants.STRUTS_LOCALE, locale.toString());
			}
			File tempdir = AppsHome.getTemp();
			if (!tempdir.exists())
				tempdir.mkdir();
			try {
				if (IS_DEBUG_ENABLED) {
					log.debug("Setting struts multipart save dir [" + StrutsConstants.STRUTS_MULTIPART_SAVEDIR + "] to "
							+ tempdir.getCanonicalPath());
				}
				DefaultSettings.set(StrutsConstants.STRUTS_MULTIPART_SAVEDIR, tempdir.getCanonicalPath());
//				Dispatcher.setMultipartSaveDir(tempdir.getCanonicalPath());
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			setMultiPartMaxSize();
			setDevMode();
			******************/
			
			//doInit(new StrutsSettings());
			initialized.set(true);
		}
	}
	


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		if (AppsGlobals.isSetup() && !initialized.get()) {
			init();
		}
		if (!providerEvents.isEmpty()) {
			try {
				reloadConfiguration();
			} catch (RuntimeException e) {
				throw new ServletException(e);
			}
		}
		
		/*
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request = prepareDispatcherAndWrapRequest(request, response);
        ServletContext servletContext = getServletContext();
        
        ActionMapping mapping = new ActionMapping();
        String uri = getUri(request);
        System.out.println("URI---> " + uri);
        uri = dropExtension(uri);
        if (uri == null) {
            throw new NullPointerException("url");
        }

        parseNameAndNamespace(uri, mapping, dispatcher.getConfigurationManager());
        
        
        System.out.println(mapping);
        */
        
        
		//res.getWriter().write("ok");
		super.doFilter(req, res, chain);
	}
	
	
	/*
    String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request
                .getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
        	System.out.println("javax.servlet.include.servlet_path = " + uri);
            return uri;
        }

        uri = RequestUtils.getServletPath(request);
        if (uri != null && !"".equals(uri)) {
        	System.out.println("RequestUtils.getServletPath(request) = " + uri);
            return uri;
        }

        uri = request.getRequestURI();
        System.out.println("request.getRequestURI() = " + uri);
        return uri.substring(request.getContextPath().length());
    }

    String dropExtension(String name) {
    	List<String> extensions = new ArrayList<String>(){{add("jspa");}};
        if (extensions == null) {
            return name;
        }
        Iterator<String> it = extensions.iterator();
        while (it.hasNext()) {
            String extension = "." + (String) it.next();
            if (name.endsWith(extension)) {
                name = name.substring(0, name.length() - extension.length());
                return name;
            }
        }
        return null;
    }
    
    
    void parseNameAndNamespace(String uri, ActionMapping mapping,
            ConfigurationManager configManager) {
    	boolean alwaysSelectFullNamespace = false;
    	boolean allowSlashesInActionNames = true;
    	
        String namespace, name;
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else if (lastSlash == 0) {
            // ww-1046, assume it is the root namespace, it will fallback to
            // default
            // namespace anyway if not found in root namespace.
            namespace = "/";
            name = uri.substring(lastSlash + 1);
        } else if (alwaysSelectFullNamespace) {
            // Simply select the namespace as everything before the last slash
            namespace = uri.substring(0, lastSlash);
            name = uri.substring(lastSlash + 1);
        } else {
            // Try to find the namespace in those defined, defaulting to ""
            Configuration config = configManager.getConfiguration();
            String prefix = uri.substring(0, lastSlash);
            namespace = "";
            // Find the longest matching namespace, defaulting to the default
            for (Iterator i = config.getPackageConfigs().values().iterator(); i
                    .hasNext();) {
                String ns = ((PackageConfig) i.next()).getNamespace();
                if (ns != null && prefix.startsWith(ns) && (prefix.length() == ns.length() || prefix.charAt(ns.length()) == '/')) {
                    if (ns.length() > namespace.length()) {
                        namespace = ns;
                    }
                }
            }

            name = uri.substring(namespace.length() + 1);
        }

        if (!allowSlashesInActionNames && name != null) {
            int pos = name.lastIndexOf('/');
            if (pos > -1 && pos < name.length() - 1) {
                name = name.substring(pos + 1);
            }
        }

        mapping.setNamespace(namespace);
        mapping.setName(name);
    }
    */
    
	// protected Dispatcher createDispatcher(FilterConfig filterConfig)
	// {
	// Map params = new HashMap();
	// String name;
	// String value;
	// for(Enumeration e = filterConfig.getInitParameterNames();
	// e.hasMoreElements(); params.put(name, value))
	// {
	// name = (String)e.nextElement();
	// value = filterConfig.getInitParameter(name);
	// }
	//
	// Dispatcher d = new Dispatcher(filterConfig.getServletContext(), params);
	// d.setConfigurationManager(new JiveConfigurationManager("struts"));
	// return d;
	// }

	protected void reloadConfiguration() throws ServletException {
		List<ConfigurationProviderEvent> events = new ArrayList<ConfigurationProviderEvent>();
		providerEvents.drainTo(events);
		if (!events.isEmpty()) {
			ConfigurationManager configurationManager = dispatcher.getConfigurationManager();
			List<ConfigurationProvider> providers = configurationManager.getConfigurationProviders();
			for (ConfigurationProviderEvent event : events) {
				if (event.getType() == ADD) {
					providers.add(event.getSource());
				} else if (event.getType() == REMOVE) {
					providers.remove(event.getSource());
				}
			}

			// Iterator<ModuleStruts2ConfigurationProviderEvent> i$ =
			// events.iterator();
			// do
			// {
			// if(!i$.hasNext())
			// break;
			// ApplicationEvent event = (ApplicationEvent)i$.next();
			// if(event instanceof AddConfigurationProviderEvent)
			// {
			// AddConfigurationProviderEvent addEvent =
			// (AddConfigurationProviderEvent)event;
			// providers.add(addEvent.getProvider());
			// } else
			// if(event instanceof RemoveConfigurationProviderEvent)
			// {
			// RemoveConfigurationProviderEvent removeEvent =
			// (RemoveConfigurationProviderEvent)event;
			// providers.remove(removeEvent.getProvider());
			// }
			// } while(true);
			
			log.debug("reload.......");
			configurationManager.reload();
		}
	}

	public static Dispatcher getDispatcher() {
		return instance == null || !instance.initialized.get() ? null : instance.dispatcher;
	}

	public void handle(ConfigurationProviderEvent event) {
		providerEvents.add(event);
	}
	
	public void setEventListenerManager(EventListenerManager manager) {
		manager.addEventListener(new StrutsSettingsEventListener());
	}
	
	@Override
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		Map<String,String> params = new HashMap<String,String>();
        for (Enumeration<?> e = filterConfig.getInitParameterNames(); e.hasMoreElements(); ) {
            String name = (String) e.nextElement();
            String value = filterConfig.getInitParameter(name);
            params.put(name, value);
        }
        String cp = params.get("configProviders");
        if(cp == null){
        	cp = "";
        }else{
        	cp += ",";
        }
        cp += //"org.opoo.apps.web.struts2.dispatcher.AppsFilterDispatcher$AppsPropertiesProvider";
        	AppsPropertiesProvider.class.getName();
        params.put("configProviders", cp);
        
        return new Dispatcher(filterConfig.getServletContext(), params);
		
//		Dispatcher d = super.createDispatcher(filterConfig);
//		log.debug("增加了属性设置");
//		//handle(new ConfigurationProviderEvent(ADD, new AppsSettingsProvider()));
//		
//		return d;
	}
	
	
	
	private static void setStrutsProperties(StrutsSettings settings){
		String encoding = AppsGlobals.getCharacterEncoding();
		if (IS_DEBUG_ENABLED) {
			log.debug("Setting struts character encoding [" + StrutsConstants.STRUTS_I18N_ENCODING + "] to "
					+ encoding);
		}
		System.out.println(">>>1: " + DefaultSettings.getInstance());
		//DefaultSettings.set(StrutsConstants.STRUTS_I18N_ENCODING, encoding);
		settings.set(StrutsConstants.STRUTS_I18N_ENCODING, encoding);
		
		Locale locale = AppsGlobals.getLocale();
		if (settings.isSet(StrutsConstants.STRUTS_LOCALE)) {
			if (IS_DEBUG_ENABLED) {
				log.debug("Setting struts locale [" + StrutsConstants.STRUTS_LOCALE + "] to " + locale.toString());
			}
//			DefaultSettings.set(StrutsConstants.STRUTS_LOCALE, locale.toString());
			settings.set(StrutsConstants.STRUTS_LOCALE, locale.toString());
		}
		File tempdir = AppsHome.getTemp();
		if (!tempdir.exists())
			tempdir.mkdir();
		try {
			if (IS_DEBUG_ENABLED) {
				log.debug("Setting struts multipart save dir [" + StrutsConstants.STRUTS_MULTIPART_SAVEDIR + "] to "
						+ tempdir.getCanonicalPath());
			}
//			DefaultSettings.set(StrutsConstants.STRUTS_MULTIPART_SAVEDIR, tempdir.getCanonicalPath());
			settings.set(StrutsConstants.STRUTS_MULTIPART_SAVEDIR, tempdir.getCanonicalPath());
//			Dispatcher.setMultipartSaveDir(tempdir.getCanonicalPath());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		setMultiPartMaxSize(settings);
		setDevMode(settings);
	}
	
	private static void setDevMode(StrutsSettings settings){
		boolean isDevMode = AppsGlobals.isDevMode();
//		DefaultSettings.set(StrutsConstants.STRUTS_DEVMODE, String.valueOf(isDevMode));
		settings.set(StrutsConstants.STRUTS_DEVMODE, String.valueOf(isDevMode));
		log.info("Setting [" + StrutsConstants.STRUTS_DEVMODE + "] to " + isDevMode);
	}

	private static void setMultiPartMaxSize(StrutsSettings settings) {
		/*
		int maxNumber = AppsGlobals.getProperty(AppsConstants.ATTACHMENTS_MAX_ATTACHMENTS_PER_MESSAGE, 5);
		int maxSize = AppsGlobals.getProperty(AppsConstants.ATTACHMENTS_MAX_ATTACHMENT_SIZE, 1024);
		long maxAllowedSize = maxNumber * maxSize * 1024L + 2096L * maxNumber;
		long limit1 = (long) ((double) maxAllowedSize * 1.5D);
		long limit2 = maxAllowedSize + 0xf00000L;
		maxAllowedSize = limit1 <= limit2 ? limit2 : limit1;
		*/
		
		long maxAllowedSize = AppsGlobals.getMultipartMaxSize();
		if (IS_DEBUG_ENABLED) {
			log.debug("Setting [" + StrutsConstants.STRUTS_MULTIPART_MAXSIZE + "] to " + maxAllowedSize);
		}
//		DefaultSettings.set(StrutsConstants.STRUTS_MULTIPART_MAXSIZE, String.valueOf(maxAllowedSize));
		settings.set(StrutsConstants.STRUTS_MULTIPART_MAXSIZE, String.valueOf(maxAllowedSize));
	}
	


	private static class StrutsSettingsEventListener implements EventListener<PropertyEvent> {
		public void handle(PropertyEvent event) {
			PropertyEvent.Type type = event.getType();
			if(type == PropertyEvent.Type.ADDED){
				propertyAdded(event);
			}else if(type == PropertyEvent.Type.MODIFIED){
				propertyModified(event);
			}else if(type == PropertyEvent.Type.REMOVED){
				propertyRemoved(event);
			}
		}
		
		private void appsPropertiesChangeHandle(PropertyEvent event){
			String propName = event.getName();
			if(AppsConstants.ATTACHMENTS_MAX_ATTACHMENTS_PER_MESSAGE.equals(propName)
					|| AppsConstants.ATTACHMENTS_MAX_ATTACHMENT_SIZE.equals(propName)
					|| AppsConstants.LOCALE_CHARACTER_ENCODING.equals(propName)
					|| AppsConstants.LOCALE_LANGUAGE.equals(propName) 
					|| AppsConstants.LOCALE_COUNTRY.equals(propName)){
				Dispatcher d = AppsFilterDispatcher.getDispatcher();
				if(d != null){
					d.getConfigurationManager().reload();
				}
			}
		}

		private void propertyRemoved(PropertyEvent event) {
			appsPropertiesChangeHandle(event);
		}

		private void propertyModified(PropertyEvent event) {
			appsPropertiesChangeHandle(event);
		}

		private void propertyAdded(PropertyEvent event) {
			appsPropertiesChangeHandle(event);
		}
	}
	
	
	public static class AppsPropertiesProvider implements ConfigurationProvider{
		public AppsPropertiesProvider() {
			super();
			log.debug(this + " 构造。。");
		}

		public void destroy() {
	    }

	    public void init(Configuration configuration) throws ConfigurationException {
	    }
	    
	    public void loadPackages() throws ConfigurationException {
	    }

	    public boolean needsReload() {
	        return false;
	    }

	    public void register(ContainerBuilder builder, LocatableProperties props) throws ConfigurationException {
			setStrutsProperties(new StrutsSettings(builder, props));
			//props.setProperty(StrutsConstants.STRUTS_LOCALE, value);
	    }
	}
	
	
	@SuppressWarnings("unused")
	private static class StrutsSettings{
		ContainerBuilder builder;
		LocatableProperties props;
		public StrutsSettings(ContainerBuilder builder, LocatableProperties props) {
			super();
			this.builder = builder;
			this.props = props;
		}

		public StrutsSettings() {
			super();
		}
		
		public void set(String name, String value){
			if(props != null){
				props.setProperty(name, value);
			}else{
				DefaultSettings.set(name, value);
			}
		}
		public boolean isSet(String name) {
			if(props != null){
				return true;
			}
			return DefaultSettings.isSet(name);
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException{
		//AppsHome.initialize();
//		System.out.println(AppsGlobals.getCharacterEncoding());
//		System.out.println(AppsGlobals.getLocale());
//		AppsGlobals.setCharacterEncoding("UTF-8");
//		AppsGlobals.setLocale(Locale.CHINA);
		
		Method[] methods = FilterDispatcher.class.getDeclaredMethods();
		for(Method m : methods){
			System.out.println(m);
		}

		
		Field[] fields = Dispatcher.class.getDeclaredFields();
		System.out.println(fields.length);
		for(Field f: fields){
			if(Modifier.isStatic(f.getModifiers())){
				f.setAccessible(true);
				System.out.print(f);
				System.out.println(" : " + f.get(null));
			}
		}
		
		System.out.println(AppsPropertiesProvider.class.getName());
		
		Dispatcher d = AppsFilterDispatcher.getDispatcher();
		if(d != null){
			MultiPartRequest multi = d.getContainer().getInstance(MultiPartRequest.class);
			if(multi instanceof JakartaMultiPartRequest){
				JakartaMultiPartRequest pr = (JakartaMultiPartRequest) multi;
				Field[] fs = JakartaMultiPartRequest.class.getDeclaredFields();
				for (Field f : fs) {
					System.out.println(f.get(pr));
				}
			}
		}
	}
}
