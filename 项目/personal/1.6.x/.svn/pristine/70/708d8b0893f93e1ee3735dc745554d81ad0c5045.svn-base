package org.opoo.apps.database.impl;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.database.DatabaseType;
import org.opoo.apps.database.MetaData;
import org.opoo.apps.database.DatabaseHelper;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class DatabaseHelperImpl implements DatabaseHelper {
	private static final Log log = LogFactory.getLog(DatabaseHelperImpl.class);
	private MetaData metaData;

	public DatabaseHelperImpl(MetaData metaData) {
		this.metaData = metaData;
	}

	public PreparedStatement createScrollablePreparedStatement(Connection con, String sql) throws SQLException {
		if (metaData.isScrollResultsSupported())
			return con.prepareStatement(sql, 1004, 1007);
		else
			return con.prepareStatement(sql);
	}

	public Statement createScrollableStatement(Connection con) throws SQLException {
		if (metaData.isScrollResultsSupported())
			return con.createStatement(1004, 1007);
		else
			return con.createStatement();
	}

	public void disablePostgresTablescan(Connection con) throws SQLException {
		if (metaData.getDatabaseType() == DatabaseType.POSTGRES) {
			PreparedStatement pstmt = con.prepareStatement("SET enable_seqscan TO 'off'");
			pstmt.execute();
			pstmt.close();
		}
	}

	public void enablePostgresTablescan(Connection con) {
		if (metaData.getDatabaseType() == DatabaseType.POSTGRES)
			try {
				PreparedStatement pstmt = con.prepareStatement("SET enable_seqscan TO 'on'");
				pstmt.execute();
				pstmt.close();
			} catch (SQLException sqle) {
				log.error(sqle);
			}
	}

	public InputStream getBlob(ResultSet rs, int columnIndex) throws SQLException {
		InputStream in;
		if (metaData.isStreamBlobRequired())
			in = rs.getBinaryStream(columnIndex);
		else
			in = rs.getBlob(columnIndex).getBinaryStream();
		return in;
	}

	public String getLargeTextField(ResultSet rs, int columnIndex) throws SQLException {
		if (!metaData.isStreamTextRequired()) {
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

	public void scrollResultSet(ResultSet rs, int rowNumber) throws SQLException {
		if (metaData.isScrollResultsSupported()) {
			if (rowNumber > 0) {
				rs.setFetchDirection(1000);
				rs.absolute(rowNumber);
			}
		} else {
			for (int i = 0; i < rowNumber; i++)
				rs.next();

		}
	}

	public void setFetchSize(ResultSet rs, int fetchSize) {
		if (metaData.isFetchSizeSupported())
			try {
				rs.setFetchSize(fetchSize);
			} catch (Throwable t) {
				// fetchSizeSupported = false;
			}
	}

	public void setMaxRows(Statement stmt, int maxRows) {
		if (metaData.isMaxRowsSupported())
			try {
				stmt.setMaxRows(maxRows);
			} catch (Throwable t) {
				// maxRowsSupported = false;
			}
	}

}
