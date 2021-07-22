package org.opoo.apps.database;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;


/**
 * 数据库连接管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 * @since 1.5
 */
public class StaticConnectionManager {
	public static enum DatabaseType {
		ORACLE, POSTGRES, MYSQL, MYSQL3, SQL_SERVER, DERBY, INFORMIX, HSQL, MCKOI, DB2, HSQLDB, OTHER, UNKNOWN;

		public boolean isDb2() {
			return equals(DB2);
		}

		public boolean isOracle() {
			return equals(ORACLE);
		}

		public boolean isPostgres() {
			return equals(POSTGRES);
		}
	}

	private static final Log log = LogFactory.getLog(StaticConnectionManager.class);
	private static final Object providerLock = new Object();
	private static DatabaseType databaseType;
	private static DataSourceProvider dataSourceProvider;
	private static boolean transactionsSupported;
	private static boolean subqueriesSupported;
	private static boolean deleteSubqueriesSupported;
	private static boolean scrollResultsSupported;
	private static boolean batchUpdatesSupported;
	private static boolean streamTextRequired;
	private static boolean maxRowsSupported;
	private static boolean fetchSizeSupported;
	private static boolean streamBlobRequired;
	private static boolean profilingEnabled;
	private static int dateType;

	public static Connection getConnection() throws SQLException {
		int retryCnt = 0;
		int retryMax = AppsGlobals.getSetupProperty("database.retry.max", 10);
		int retryWait = AppsGlobals.getSetupProperty("database.retry.delay", 250);
		Connection con = null;
		for (retryCnt = 1; retryCnt <= retryMax; retryCnt++) {
			try {
				con = DataSourceUtils.getConnection(getDataSource());
			} catch (CannotGetJdbcConnectionException e) {
				if (AppsGlobals.isSetup()) {
					log.info((new StringBuilder())
							.append("Unable to get a connection from the database pool (attempt ").append(retryCnt)
							.append(" out of ").append(retryMax).append(").").toString(), e);
				}
			}
			if (con != null || !AppsGlobals.isSetup())
				break;
			try {
				Thread.sleep(retryWait);
				continue;
			} catch (InterruptedException e) {
				break;
			} catch (Exception e) {
			}
		}

		if (con == null) {
			if (AppsGlobals.isSetup()) {
				log.error("WARNING: ConnectionManager.getConnection() failed to obtain a connection.");
			}
			return null;
		} else {
			return con;
		}
	}

	public static DataSource getDataSource() {
		String className = AppsGlobals.getSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS);
		String newProviderClassName = null;
		DataSource dataSource = null;
		synchronized (providerLock) {
			if (dataSourceProvider == null) {
				if (className != null) {
					try {
						Class<?> conClass = Class.forName(className);
						setDataSourceProvider((DataSourceProvider) conClass.newInstance());
						newProviderClassName = className;
					} catch (Exception e) {
						log
								.error(
										"Warning: failed to create the connection provider specified by connectionProvider.className. Using the default pool.",
										e);
						setDataSourceProvider(new DefaultDataSourceProvider());
						newProviderClassName = DefaultDataSourceProvider.class.getName();
					}
				} else {
					setDataSourceProvider(new DefaultDataSourceProvider());
					newProviderClassName = DefaultDataSourceProvider.class.getName();
				}
			}

			try {
				dataSource = dataSourceProvider.getDataSource();
			} catch (Exception e) {
				log.error("无法获取数据源", e);
				throw new IllegalStateException("无法获取数据源", e);
			}
		}
		if (newProviderClassName != null) {
			AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, newProviderClassName);
		}
		// if(tokenReplaceDatabaseNames)
		// dataSource = new TokenReplaceDataSourceWrapper(dataSource,
		// tokenReplaceMappings);
		// if(profilingEnabled)
		// dataSource = new ProfiledDataSourceWrapper(dataSource);
		// if(transactionTrackingEnabled)
		// dataSource = new TransactionalTrackingDataSourceWrapper(dataSource);
		return dataSource;
	}

	public static void close(Connection con) {
		DataSourceUtils.releaseConnection(con, getDataSource());
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void close(ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
	}

	public static void close(ResultSet rs, Statement stmt, Connection con) {
		close(rs);
		close(stmt);
		close(con);
	}

	public static void close(Statement stmt, Connection con) {
		close(stmt);
		close(con);
	}
	public static Statement createScrollableStatement(Connection con) throws SQLException {
		if (isScrollResultsSupported())
			return con.createStatement(1004, 1007);
		else
			return con.createStatement();
	}

	public static PreparedStatement createScrollablePreparedStatement(Connection con, String sql) throws SQLException {
		if (isScrollResultsSupported())
			return con.prepareStatement(sql, 1004, 1007);
		else
			return con.prepareStatement(sql);
	}

	public static void disablePostgresTablescan(Connection con) throws SQLException {
		if (getDatabaseType() == DatabaseType.POSTGRES) {
			PreparedStatement pstmt = con.prepareStatement("SET enable_seqscan TO 'off'");
			pstmt.execute();
			pstmt.close();
		}
	}

	public static void enablePostgresTablescan(Connection con) {
		if (getDatabaseType() == DatabaseType.POSTGRES)
			try {
				PreparedStatement pstmt = con.prepareStatement("SET enable_seqscan TO 'on'");
				pstmt.execute();
				pstmt.close();
			} catch (SQLException sqle) {
				log.error(sqle);
			}
	}

	public static void scrollResultSet(ResultSet rs, int rowNumber) throws SQLException {
		if (isScrollResultsSupported()) {
			if (rowNumber > 0) {
				rs.setFetchDirection(1000);
				rs.absolute(rowNumber);
			}
		} else {
			for (int i = 0; i < rowNumber; i++)
				rs.next();

		}
	}

	public static String getLargeTextField(ResultSet rs, int columnIndex) throws SQLException {
		if (!isStreamTextRequired()) {
			return rs.getString(columnIndex);
		}
		Reader bodyReader = rs.getCharacterStream(columnIndex);
		if (bodyReader == null) {
			return null;
		}

		String value = null;
		try {
			char buf[] = new char[256];
			StringWriter out = new StringWriter(256);
			int len;
			while ((len = bodyReader.read(buf)) >= 0)
				out.write(buf, 0, len);
			value = out.toString();
			out.close();
		} catch (Exception e) {
			log.error(e);
			throw new SQLException("Failed to load text field");
		} finally {
			try {
				if (bodyReader != null)
					bodyReader.close();
			} catch (Exception e) {
			}
		}

		return value;
	}

	public static InputStream getBlob(ResultSet rs, int columnIndex) throws SQLException {
		InputStream in;
		if (isStreamBlobRequired())
			in = rs.getBinaryStream(columnIndex);
		else
			in = rs.getBlob(columnIndex).getBinaryStream();
		return in;
	}

	public static int getDateType() {
		return dateType;
	}

	public static void setMaxRows(Statement stmt, int maxRows) {
		if (isMaxRowsSupported())
			try {
				stmt.setMaxRows(maxRows);
			} catch (Throwable t) {
				maxRowsSupported = false;
			}
	}

	public static void setFetchSize(ResultSet rs, int fetchSize) {
		if (isFetchSizeSupported())
			try {
				rs.setFetchSize(fetchSize);
			} catch (Throwable t) {
				fetchSizeSupported = false;
			}
	}

	public static void batchDeleteWithoutSubqueries(Connection con, String sql, long objectIDs[]) throws SQLException {
		if (objectIDs.length == 0)
			return;
		int DELETE_SIZE = 250;
		int n = (objectIDs.length - 1) / DELETE_SIZE;
		if (n == 0)
			n = 1;
		for (int i = 0; i < n; i++) {
			Statement stmt = con.createStatement();
			StringBuffer deleteSQL = new StringBuffer(sql.trim());
			deleteSQL.append(" (");
			int start = i * DELETE_SIZE;
			int end = start + DELETE_SIZE <= objectIDs.length ? start + DELETE_SIZE : objectIDs.length;
			if (end > objectIDs.length)
				end = objectIDs.length;
			deleteSQL.append(objectIDs[start]);
			for (int j = start + 1; j < end; j++)
				deleteSQL.append(", ").append(objectIDs[j]);

			deleteSQL.append(")");
			stmt.execute(deleteSQL.toString());
			stmt.close();
		}
	}

	private static void setMetaData(Connection con) throws SQLException {
		DatabaseMetaData metaData = con.getMetaData();
		transactionsSupported = metaData.supportsTransactions();
		subqueriesSupported = metaData.supportsCorrelatedSubqueries();
		String val;
		if (subqueriesSupported) {
			val = AppsGlobals.getSetupProperty("database.subqueriesSupported");
			if (val != null)
				try {
					subqueriesSupported = Boolean.valueOf(val).booleanValue();
				} catch (Exception e) {
					log.debug(e);
				}
		}
		deleteSubqueriesSupported = false;
		val = AppsGlobals.getSetupProperty("database.deleteSubqueriesSupported");
		if (val != null)
			try {
				deleteSubqueriesSupported = Boolean.valueOf(val).booleanValue();
			} catch (Exception e) {
				log.debug(e);
			}
		try {
			scrollResultsSupported = metaData.supportsResultSetType(1004);
		} catch (Exception e) {
			scrollResultsSupported = false;
		}
		batchUpdatesSupported = metaData.supportsBatchUpdates();
		streamTextRequired = false;
		maxRowsSupported = true;
		fetchSizeSupported = true;
		String dbName = metaData.getDatabaseProductName().toLowerCase();
		String driverName = metaData.getDriverName().toLowerCase();
		String dbVersion = metaData.getDatabaseProductVersion();
		if (dbName.indexOf("oracle") != -1) {
			databaseType = DatabaseType.ORACLE;
			streamTextRequired = true;
			scrollResultsSupported = false;
			if (driverName.indexOf("auguro") != -1) {
				streamTextRequired = false;
				fetchSizeSupported = true;
				maxRowsSupported = false;
			} else if (driverName.indexOf("Weblogic, Inc. Java-OCI JDBC Driver") != -1)
				streamTextRequired = false;
		} else if (dbName.indexOf("postgres") != -1) {
			databaseType = DatabaseType.POSTGRES;
			scrollResultsSupported = false;
			streamBlobRequired = true;
			fetchSizeSupported = false;
		} else if (dbName.indexOf("interbase") != -1) {
			fetchSizeSupported = false;
			maxRowsSupported = false;
		} else if (dbName.indexOf("sql server") != -1) {
			databaseType = DatabaseType.SQL_SERVER;
			if (driverName.indexOf("una") != -1) {
				fetchSizeSupported = true;
				maxRowsSupported = false;
			}
			if (driverName.indexOf("jtds") != -1) {
				fetchSizeSupported = true;
				maxRowsSupported = true;
			} else {
				streamBlobRequired = true;
				fetchSizeSupported = false;
				maxRowsSupported = false;
				scrollResultsSupported = false;
			}
		} else if (dbName.indexOf("mysql") != -1) {
			if (dbVersion != null && dbVersion.startsWith("3."))
				databaseType = DatabaseType.MYSQL3;
			else
				databaseType = DatabaseType.MYSQL;
			transactionsSupported = false;
		} else if (dbName.indexOf("derby") != -1)
			databaseType = DatabaseType.DERBY;
		else if (dbName.indexOf("db2") != -1)
			databaseType = DatabaseType.DB2;
		else if (dbName.indexOf("hsql") != -1)
			databaseType = DatabaseType.HSQL;
		String propValue = AppsGlobals.getSetupProperty("database.streamTextRequired");
		if (propValue != null)
			streamTextRequired = Boolean.valueOf(propValue).booleanValue();
		propValue = AppsGlobals.getSetupProperty("database.streamBlobRequired");
		if (propValue != null)
			streamBlobRequired = Boolean.valueOf(propValue).booleanValue();
		propValue = AppsGlobals.getSetupProperty("database.maxRowsSupported");
		if (propValue != null)
			maxRowsSupported = Boolean.valueOf(propValue).booleanValue();
		propValue = AppsGlobals.getSetupProperty("database.fetchSizeSupported");
		if (propValue != null)
			fetchSizeSupported = Boolean.valueOf(propValue).booleanValue();
		propValue = AppsGlobals.getSetupProperty("database.batchUpdatesSupported");
		if (propValue != null)
			batchUpdatesSupported = Boolean.valueOf(propValue).booleanValue();
		propValue = AppsGlobals.getSetupProperty("database.transactionsSupported");
		if (propValue != null)
			transactionsSupported = Boolean.valueOf(propValue).booleanValue();
	}

	public static DatabaseType getDatabaseType() {
		return databaseType;
	}

	public static boolean isProfilingEnabled() {
		return profilingEnabled;
	}

	public static void setProfilingEnabled(boolean enable) {
		// if(!profilingEnabled && enable)
		// ProfiledConnection.start();
		// else
		// if(profilingEnabled && !enable)
		// ProfiledConnection.stop();
		profilingEnabled = enable;
	}

	public static boolean isTransactionsSupported() {
		return transactionsSupported;
	}

	public static boolean isStreamTextRequired() {
		return streamTextRequired;
	}

	public static boolean isStreamBlobRequired() {
		return streamBlobRequired;
	}

	public static boolean isMaxRowsSupported() {
		return maxRowsSupported;
	}

	public static boolean isFetchSizeSupported() {
		return fetchSizeSupported;
	}

	public static boolean isSubqueriesSupported() {
		return subqueriesSupported;
	}

	public static boolean isDeleteSubqueriesSupported() {
		return deleteSubqueriesSupported;
	}

	public static boolean isScrollResultsSupported() {
		return scrollResultsSupported;
	}

	public static boolean isBatchUpdatesSupported() {
		return batchUpdatesSupported;
	}

	public static boolean schemaExists() {
		boolean exists;
		Connection con;
		Statement stmt;
		exists = true;
		con = null;
		stmt = null;
		try {
			con = getConnection();
			if (null == con) {
				exists = false;
				if (log.isDebugEnabled())
					log.debug("Could not obtain database connection.");
			} else {
				stmt = con.createStatement();
				stmt.executeQuery("SELECT * FROM sec_users");
				stmt.close();
			}
			close(stmt, con);
			if (log.isDebugEnabled())
				log.debug("Database schema does not appear to be installed.");
			exists = false;
		} catch (Exception e) {
			exists = false;
		} finally {
			close(stmt, con);
		}
		return exists;
	}

	public static LobHandler getLobHandler(DatabaseMetaData dbmd) {
		return ((LobHandler) (requiresOracleLobSupport(dbmd) ? new OracleLobHandler() : new DefaultLobHandler()));
	}

	public static boolean requiresOracleLobSupport(DatabaseMetaData dbmd) {
		try {
			String dbName = dbmd.getDatabaseProductName().toLowerCase();
			String driverName = dbmd.getDriverName().toLowerCase();
			if (dbName.indexOf("oracle") == -1)
				return false;
			if (driverName.indexOf("auguro") != -1)
				return false;
			if (driverName.indexOf("Weblogic, Inc. Java-OCI JDBC Driver") != -1)
				return false;
			return true;
		} catch (Exception ex) {
			log.error(ex);
		}
		return false;
	}
	

	public static boolean testConnection() {
		Connection con = null;
		Statement statement = null;
		try {
			con = getConnection();
			statement = con.createStatement();
			statement.execute("select count(*) from sec_users");

		} catch (Exception e) {
			return false;
		} finally {
			if (con != null)
				close(statement, con);
		}
		return true;
	}

	/**
	 * @return the dataSourceProvider
	 */
	public static DataSourceProvider getDataSourceProvider() {
		return dataSourceProvider;
	}

	/**
	 * @param dataSourceProvider
	 *            the dataSourceProvider to set
	 */
	public static void setDataSourceProvider(DataSourceProvider dataSourceProvider) {
		setDataSourceProvider(dataSourceProvider, false);
	}

	public static void setDataSourceProvider(DataSourceProvider provider, boolean updateSequenceProvider) {
		if (provider != null && provider != dataSourceProvider) {
			synchronized (providerLock) {
				if (dataSourceProvider != null) {
					dataSourceProvider.destroy();
					dataSourceProvider = null;
				}
				dataSourceProvider = provider;
			}
	
			Connection con = null;
			try {
				DataSource ds = dataSourceProvider.getDataSource();
				con = ds.getConnection();
				if (con != null)
					setMetaData(con);
			} catch (Exception e) {
				log.error(e);
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
			
	
			String className = provider.getClass().getName();
			AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, className);
		}
	}
}
