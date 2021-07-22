package org.opoo.apps.database.impl;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DatabaseType;
import org.opoo.apps.database.MetaData;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MetaDataImpl implements MetaData {
	private static final Log log = LogFactory.getLog(MetaDataImpl.class);
	private DatabaseType databaseType;
	private boolean transactionsSupported;
	private boolean subqueriesSupported;
	private boolean deleteSubqueriesSupported;
	private boolean scrollResultsSupported;
	private boolean batchUpdatesSupported;
	private boolean streamTextRequired;
	private boolean maxRowsSupported;
	private boolean fetchSizeSupported;
	private boolean streamBlobRequired;
	private boolean profilingEnabled;
	private int dateType;
	
	private boolean requiresOracleLobSupport;
	
	//private final DatabaseMetaData metaData;
	
	public MetaDataImpl(DatabaseMetaData metaData) throws SQLException{
		databaseType = DatabaseType.UNKNOWN;

		//this.metaData = meta;
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
		
		
		//
		requiresOracleLobSupport = requiresOracleLobSupport(metaData);
	}

	/**
	 * @return the databaseType
	 */
	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	/**
	 * @return the transactionsSupported
	 */
	public boolean isTransactionsSupported() {
		return transactionsSupported;
	}

	/**
	 * @return the subqueriesSupported
	 */
	public boolean isSubqueriesSupported() {
		return subqueriesSupported;
	}

	/**
	 * @return the deleteSubqueriesSupported
	 */
	public boolean isDeleteSubqueriesSupported() {
		return deleteSubqueriesSupported;
	}

	/**
	 * @return the scrollResultsSupported
	 */
	public boolean isScrollResultsSupported() {
		return scrollResultsSupported;
	}

	/**
	 * @return the batchUpdatesSupported
	 */
	public boolean isBatchUpdatesSupported() {
		return batchUpdatesSupported;
	}

	/**
	 * @return the streamTextRequired
	 */
	public boolean isStreamTextRequired() {
		return streamTextRequired;
	}

	/**
	 * @return the maxRowsSupported
	 */
	public boolean isMaxRowsSupported() {
		return maxRowsSupported;
	}

	/**
	 * @return the fetchSizeSupported
	 */
	public boolean isFetchSizeSupported() {
		return fetchSizeSupported;
	}

	/**
	 * @return the streamBlobRequired
	 */
	public boolean isStreamBlobRequired() {
		return streamBlobRequired;
	}

	/**
	 * @return the profilingEnabled
	 */
	public boolean isProfilingEnabled() {
		return profilingEnabled;
	}

	/**
	 * @return the dateType
	 */
	public int getDateType() {
		return dateType;
	}

	public LobHandler getLobHandler() {
		return requiresOracleLobSupport() ? new OracleLobHandler() : new DefaultLobHandler();
	}

	public boolean requiresOracleLobSupport(DatabaseMetaData metaData) {
		try {
			String dbName = metaData.getDatabaseProductName().toLowerCase();
			String driverName = metaData.getDriverName().toLowerCase();
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
	
	public boolean requiresOracleLobSupport() {
		return requiresOracleLobSupport;
	}
/*
	public boolean allProceduresAreCallable() throws SQLException {
		return metaData.allProceduresAreCallable();
	}

	public boolean allTablesAreSelectable() throws SQLException {
		return metaData.allTablesAreSelectable();
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return metaData.autoCommitFailureClosesAllResultSets();
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return metaData.dataDefinitionCausesTransactionCommit();
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return metaData.dataDefinitionIgnoredInTransactions();
	}

	public boolean deletesAreDetected(int type) throws SQLException {
		return metaData.deletesAreDetected(type);
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		return metaData.doesMaxRowSizeIncludeBlobs();
	}

	public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern,
			String attributeNamePattern) throws SQLException {
		return metaData.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern);
	}

	public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
			throws SQLException {
		return metaData.getBestRowIdentifier(catalog, schema, table, scope, nullable);
	}

	public String getCatalogSeparator() throws SQLException {
		return metaData.getCatalogSeparator();
	}

	public String getCatalogTerm() throws SQLException {
		return metaData.getCatalogTerm();
	}

	public ResultSet getCatalogs() throws SQLException {
		return metaData.getCatalogs();
	}

	public ResultSet getClientInfoProperties() throws SQLException {
		return null;
	}

	public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable,
			String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDatabaseMajorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDatabaseMinorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDatabaseProductName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDatabaseProductVersion() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDriverMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDriverMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDriverName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDriverVersion() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getExtraNameCharacters() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern,
			String columnNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdentifierQuoteString() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getJDBCMajorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getJDBCMinorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxBinaryLiteralLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCatalogNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCharLiteralLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInIndex() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInSelect() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInTable() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxConnections() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCursorNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxIndexLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxProcedureNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxRowSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxSchemaNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxStatementLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxStatements() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxTableNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxTablesInSelect() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxUserNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNumericFunctions() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern,
			String columnNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProcedureTerm() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public RowIdLifetime getRowIdLifetime() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSQLKeywords() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSQLStateType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSchemaTerm() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSchemas() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSearchStringEscape() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStringFunctions() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSystemFunctions() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTableTypes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTimeDateFunctions() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTypeInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getURL() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insertsAreDetected(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCatalogAtStart() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nullsAreSortedLow() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersDeletesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersInsertsAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownDeletesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownInsertsAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsANSI92FullSQL() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsBatchUpdates() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsConvert() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsConvert(int fromType, int toType) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCoreSQLGrammar() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCorrelatedSubqueries() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsExpressionsInOrderBy() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsExtendedSQLGrammar() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsFullOuterJoins() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsGetGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsGroupBy() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsLimitedOuterJoins() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMinimumSQLGrammar() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMultipleOpenResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsNamedParameters() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOuterJoins() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsPositionedDelete() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsResultSetHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsResultSetType(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSavepoints() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSelectForUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsStatementPooling() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsStoredProcedures() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInComparisons() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInExists() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInIns() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsTableCorrelationNames() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsTransactions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsUnion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsUnionAll() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updatesAreDetected(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean usesLocalFilePerTable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean usesLocalFiles() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
