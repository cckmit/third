package org.opoo.apps.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Êý¾Ý¿â¸¨ÖúÀà¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface DatabaseHelper {
	
	Statement createScrollableStatement(Connection con) throws SQLException ;

	PreparedStatement createScrollablePreparedStatement(Connection con, String sql) throws SQLException;

	void disablePostgresTablescan(Connection con) throws SQLException;

	void enablePostgresTablescan(Connection con);

	void scrollResultSet(ResultSet rs, int rowNumber) throws SQLException;

	String getLargeTextField(ResultSet rs, int columnIndex) throws SQLException;

	InputStream getBlob(ResultSet rs, int columnIndex) throws SQLException;

	void setMaxRows(Statement stmt, int maxRows);

	void setFetchSize(ResultSet rs, int fetchSize);
}
