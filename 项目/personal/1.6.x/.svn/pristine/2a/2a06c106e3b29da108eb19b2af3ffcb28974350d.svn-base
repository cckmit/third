package org.opoo.apps.file;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

public class DatabaseFileStorageFactory implements FileStorageFactory, InitializingBean, ApplicationContextAware {
	public static final String SUPPORTED_SCHEME = "db";
	private DataSource dataSource;
	private ApplicationContext context;
	
	public FileStorage createFileStorage(String storeName, URI uri) {
		String dataSourceName = uri.getHost();
		if(dataSource == null){
			if(StringUtils.isBlank(dataSourceName)){
				throw new IllegalArgumentException("必须指定dataSource或者在URI中指定dataSource的名称。");
			}
			if(context != null){
				dataSource = (DataSource) context.getBean(dataSourceName);
			}
		}
		Assert.notNull(dataSource, "无法找到适当的dataSourc：" + dataSourceName);
		String[] path = uri.getPath().split("/");
		String tableName = null;
		String columnName = null;
		if(path.length >= 2){
			tableName = path[1];
		}
		if(path.length >= 3){
			columnName = path[2];
		}
		return new DatabaseFileStorage(storeName, dataSource, tableName, columnName);
	}

	public boolean supports(URI uri) {
		String scheme = uri.getScheme();
		return SUPPORTED_SCHEME.equalsIgnoreCase(scheme);
	}
	


	public void afterPropertiesSet() throws Exception {
		//Assert.notNull(context, "必须实现");
	}
	




	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;	
	}	
	
	
	public static void main(String[] args) throws URISyntaxException{
		String uriString = "db://dataSources/table_name/column_name";
		URI uri = new URI(uriString);
		String scheme = uri.getScheme();
		System.out.println(scheme);
		System.out.println(uri.getHost());
		System.out.println(uri.getPath());
		System.out.println(uri.getPath().split("/")[1]);
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
