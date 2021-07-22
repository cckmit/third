package org.opoo.apps.file;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;




public class DatabaseFileStorage extends AbstractFileStorage implements FileStorage{

	private String tableName;
	private String columnName;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	public DatabaseFileStorage(String storeName, DataSource dataSource, String tableName, String columnName) {
		super(storeName);
		this.dataSource = dataSource;
		this.tableName = tableName;
		this.columnName = columnName;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}




	public void delete(Details details) {
		String sql = "delete from " + tableName + " where id=?";
		final long id = Long.parseLong(details.getLocation());
		jdbcTemplate.execute(sql, new PreparedStatementCallback(){
			public Object doInPreparedStatement(PreparedStatement st)
					throws SQLException, DataAccessException {
				st.setLong(1, id);
				return st.executeUpdate();
			}});
	}

	public InputStream fetch(Details details, String format) throws FileStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FileDetails save(InputStream is, FileDetails details, Long id) {
		String sql = "insert into " + tableName + "(id, " + columnName + ") values (?,?)";
		
		//todo
		getJdbcTemplate().execute(sql);
		return null;
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




	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}




	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
