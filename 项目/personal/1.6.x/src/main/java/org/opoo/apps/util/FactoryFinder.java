package org.opoo.apps.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Properties;


/**
 * 工厂查找器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FactoryFinder {
	
	
	/**
	 * 工厂类处理异常。
	 *
	 */
	static class FactoryException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3930283698437563786L;

		public FactoryException() {
			super();
		}

		public FactoryException(String message, Throwable cause) {
			super(message, cause);
		}

		public FactoryException(String message) {
			super(message);
		}

		public FactoryException(Throwable cause) {
			super(cause);
		}
	}
	

    /**
     * Creates an instance of the specified class using the specified 
     * <code>ClassLoader</code> object.
     *
     * @exception FactoryException if the given class could not be found
     *            or could not be instantiated
     */
    private static Object newInstance(String className,
                                      ClassLoader classLoader,
                                      Properties properties)
    {
        try {
            Class<?> spiClass;
            if (classLoader == null) {
                spiClass = Class.forName(className);
            } else {
                spiClass = classLoader.loadClass(className);
            }
            if (properties != null) {
                Constructor<?> constr = null;
                try {
                    constr = spiClass.getConstructor(Properties.class);
                } catch (Exception ex) {
                }
                if (constr != null) {
                    return constr.newInstance(properties);
                }
            }
            return spiClass.newInstance();
        } catch (ClassNotFoundException x) {
            throw new FactoryException("Provider " + className + " not found", x);
        } catch (Exception x) {
            throw new FactoryException("Provider " + className + " could not be instantiated: " + x,
                x);
        }
    }

    /**
     * Finds the implementation <code>Class</code> object for the given
     * factory name, or if that fails, finds the <code>Class</code> object
     * for the given fallback class name. The arguments supplied must be
     * used in order. If using the first argument is successful, the second
     * one will not be used.
     * <P>
     * This method is package private so that this code can be shared.
     *
     * @return the <code>Class</code> object of the specified message factory;
     *         may not be <code>null</code>
     *
     * @param factoryId             the name of the factory to find, which is
     *                              a system property
     * @param fallbackClassName     the implementation class name, which is
     *                              to be used only if nothing else
     *                              is found; <code>null</code> to indicate that
     *                              there is no fallback class name
     * @param propertiesFilename 	工程配置文件名称
     * @param properties			创建工程实例时的参数
     * @exception FactoryException if there is an error
     */
    public static Object find(String factoryId, String fallbackClassName,
    					String propertiesFilename, Properties properties)
    {
        ClassLoader classLoader;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception x) {
            throw new RuntimeException(x.toString(), x);
        }

        String serviceId = "META-INF/services/" + factoryId;
        // try to find services in CLASSPATH
        try {
            InputStream is=null;
            if (classLoader == null) {
                is=ClassLoader.getSystemResourceAsStream(serviceId);
            } else {
                is=classLoader.getResourceAsStream(serviceId);
            }
        
            if( is!=null ) {
                BufferedReader rd =
                    new BufferedReader(new InputStreamReader(is, "UTF-8"));
        
                String factoryClassName = rd.readLine();
                rd.close();

				if (factoryClassName != null && !"".equals(factoryClassName)) {
					return newInstance(factoryClassName, classLoader, properties);
				}
            }
        } catch( Exception ex ) {
        }
        

        serviceId = propertiesFilename;
        // try to find stringel.properties in CLASSPATH
        try {
        	InputStream is=null;
            if (classLoader == null) {
                is=ClassLoader.getSystemResourceAsStream(serviceId);
            } else {
                is=classLoader.getResourceAsStream(serviceId);
            }
        
            if( is!=null ) {
            	Properties props=new Properties();
            	props.load(is);
            	String factoryClassName = props.getProperty(factoryId);
				if (factoryClassName != null && !"".equals(factoryClassName)) {
					return newInstance(factoryClassName, classLoader, properties);
				}
            	return newInstance(factoryClassName, classLoader, properties);
            }
        	
            // try to read from $java.home/lib/el.properties
//            String javah=System.getProperty( "java.home" );
//            String configFile = javah + File.separator +
//                "lib" + File.separator + "el.properties";
//            File f=new File( configFile );
//            if( f.exists()) {
//                Properties props=new Properties();
//                props.load( new FileInputStream(f));
//                String factoryClassName = props.getProperty(factoryId);
//                return newInstance(factoryClassName, classLoader, properties);
//            }
        } catch(Exception ex ) {
        }


        // Use the system property
        try {
            String systemProp =
                System.getProperty( factoryId );
            if( systemProp!=null) {
                return newInstance(systemProp, classLoader, properties);
            }
        } catch (SecurityException se) {
        }

        if (fallbackClassName == null) {
            throw new FactoryException(
                "Provider for " + factoryId + " cannot be found", null);
        }

        return newInstance(fallbackClassName, classLoader, properties);
    }
    
    /**
     * 
     * @param factoryId
     * @param fallbackClassName
     * @param propertiesFilename
     * @return
     */
    public static Object find(String factoryId, String fallbackClassName,
			String propertiesFilename){
    	return FactoryFinder.find(factoryId, fallbackClassName, propertiesFilename, null);
    }
}

