package org.opoo.apps.database;

import java.lang.reflect.Method;

import javax.sql.DataSource;


/**
 * 数据源提供者基类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class AbstractDataSourceProvider implements DataSourceProvider {

	private Object lock = new Object();
	private DataSource dataSource;
	
	public DataSource getDataSource() throws Exception {
		if(dataSource == null){
			synchronized(lock){
				if(dataSource == null){
					dataSource = buildDataSource();
				}
			}
		}
		return dataSource;
	}
	
	protected void clearInstance(){
		dataSource = null;
	}

	protected abstract DataSource buildDataSource() throws Exception;

	/**
	 * 
	 */
	public void destroy(){
		if(dataSource != null){
			try {
				Method m = dataSource.getClass().getMethod("close");
				if(m != null){
					m.invoke(dataSource);
				}
			} catch (SecurityException e) {
				//System.err.println(e);
			} catch (NoSuchMethodException e) {
				//System.err.println(e);
			}catch (Exception e) {
				//log.error("关闭数据源出错", e);
				System.err.println(e);
			} 
		}
	}
}
