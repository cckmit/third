package org.opoo.apps.database;

import org.springframework.jdbc.support.lob.LobHandler;


/**
 * 数据源的头信息。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface MetaData{// extends DatabaseMetaData {
	
	/**
	 * 数据库类型。
	 */
	DatabaseType getDatabaseType();

	/**
	 * 日期类型。
	 * @return
	 */
	int getDateType();

	/**
	 * 是否可性能调试
	 * @return
	 */
	boolean isProfilingEnabled();

	/**
	 * 是否支持事务
	 * @return
	 */
	boolean isTransactionsSupported();

	/**
	 * 是否需要以流的方式访问 Text 类型数据
	 * @return
	 */
	boolean isStreamTextRequired();

	boolean isStreamBlobRequired();

	boolean isMaxRowsSupported();

	boolean isFetchSizeSupported() ;

	boolean isSubqueriesSupported() ;

	boolean isDeleteSubqueriesSupported();

	boolean isScrollResultsSupported() ;

	boolean isBatchUpdatesSupported();

	LobHandler getLobHandler();

	boolean requiresOracleLobSupport();
}
