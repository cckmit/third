package org.opoo.apps.database.spring;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.database.DataSourceProvider;
import org.opoo.apps.database.DefaultDataSourceProvider;
import org.opoo.apps.database.EmbeddedDataSourceProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.util.ClassUtils;


/**
 * Spring JDBC ֧���ࡣ
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class SpringJdbcSupport {
	private static final Log log = LogFactory.getLog(SpringJdbcSupport.class);
	
	private static NativeJdbcExtractor nativeJdbcExtractor;
	private static boolean nativeJdbcExtractorDetermined = false;
	
	private static JdbcTemplate jdbcTemplate;
	private static SimpleJdbcTemplate simpleJdbcTemplate;
	
	/**
	 * ��ȡϵͳ NativeJdbcExtractor
	 * @return
	 */
	public static NativeJdbcExtractor getNativeJdbcExtractor(){
		if(!nativeJdbcExtractorDetermined){
			nativeJdbcExtractor = determineNativeJdbcExtractor();
		}
		return nativeJdbcExtractor;
	}
	
	/**
	 * ̽��ϵͳ NativeJdbcExtractor
	 * @return
	 */
	public static NativeJdbcExtractor determineNativeJdbcExtractor(){
		DataSourceProvider provider = DataSourceManager.getDataSourceProvider();
		if(provider instanceof DefaultDataSourceProvider){
			return new CommonsDbcpNativeJdbcExtractor();
		}else if(provider instanceof EmbeddedDataSourceProvider){
			return new SimpleNativeJdbcExtractor();
		}else{
			String className = AppsGlobals.getSetupProperty("database.nativeJdbcExtractor.className");
			try {
				Class<?> clazz = ClassUtils.forName(className, SpringJdbcSupport.class.getClassLoader());
				return (NativeJdbcExtractor) clazz.newInstance();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException("�޷����� NativeJdbcExtractor", e);
			}
		}
		//return null;
	}
	
	/**
	 * ��ȡȫ�ֵ� JDBC ����ģ�塣
	 * @return
	 */
	public static JdbcTemplate getJdbcTemplate(){
		if(jdbcTemplate == null){
			jdbcTemplate = createJdbcTemplate();
		}
		return jdbcTemplate;
	}
	
	/**
	 * ����ȫ�ֵ� JDBC ����ģ�塣
	 * @return
	 */
	public static JdbcTemplate createJdbcTemplate(){
		DataSource source = DataSourceManager.getRuntimeDataSource();
		JdbcTemplate template = new JdbcTemplate(source);
		NativeJdbcExtractor extractor = SpringJdbcSupport.getNativeJdbcExtractor();
		if(extractor != null){
			template.setNativeJdbcExtractor(extractor);
		}
		return template;
	}
	
	/**
	 * ��ȡ�򵥵� JDBC ����ģ�塣
	 * @return
	 */
	public static SimpleJdbcTemplate getSimpleJdbcTemplate(){
		if(simpleJdbcTemplate == null){
			simpleJdbcTemplate = createSimpleJdbcTemplate();
		}
		return simpleJdbcTemplate;
	}
	
	/**
	 * �����򵥵� JDBC ����ģ�塣
	 * @return
	 */
	public static SimpleJdbcTemplate createSimpleJdbcTemplate(){
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		return new SimpleJdbcTemplate(jdbcTemplate);
	}
}
