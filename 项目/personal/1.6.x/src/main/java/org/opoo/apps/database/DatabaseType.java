package org.opoo.apps.database;


/**
 * 数据库类型枚举。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public enum DatabaseType {
	
	ORACLE, POSTGRES, MYSQL, MYSQL3, SQL_SERVER, DERBY, INFORMIX, HSQL, MCKOI, DB2, HSQLDB, OTHER, UNKNOWN;

	/**
	 * 是否是db2数据库
	 * @return
	 */
	public boolean isDb2() {
		return equals(DB2);
	}

	/**
	 * 是否是 oracle 数据库
	 * @return
	 */
	public boolean isOracle() {
		return equals(ORACLE);
	}

	/**
	 * 是否是 postgres 数据库
	 * @return
	 */
	public boolean isPostgres() {
		return equals(POSTGRES);
	}
}
