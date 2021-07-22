package org.opoo.apps.id.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;


/**
 * 主要用于支持 Sequence 的数据库，使用数据库的原始 Sequence。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class DatabaseSequence implements Sequence {
	private final Log log = LogFactory.getLog(getClass());
	private final DataSource dataSource; 
	private final String name;
	public DatabaseSequence(DataSource dataSource, String name){
		this.dataSource = dataSource;
		this.name = name;
		log.debug("create '" + this.getClass().getName() + "' for sequence '" + name + "'.");
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}
	
	public Long getCurrent() {
		return getFromDb(name, false, true);
	}

	public Long getNext() {
		return getFromDb(name, true, true);
	}
	
	private long getFromDb(String name, boolean next, boolean retry) {
		String sql = next ? getNextSql(name) : getCurrentSql(name);
		log.debug(sql);
		

		
//		long result = (Long) jdbcTemplate.execute(sql, new PreparedStatementCallback(){
//			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//				ResultSet rs = ps.executeQuery();
//				if(rs.next()){
//					return rs.getLong(1);
//				}
//				return -1L;
//			}
//		});
//		//
//		if(result != -1){
//			return result;
//		}
//		
//		if (!retry) {
//            log.error("Could not get sequence");
//            throw new RuntimeException("Could not get sequence");
//        }
		
		
		Connection con = null;//DataSourceUtils.getConnection(getDataSource());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = con.prepareStatement(sql);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			rs = stmt.executeQuery();
			rs.next();
			return rs.getLong(1);
			
//			if(rs.next()){
//				return rs.getLong(1);
//			}else{
//				if (!retry) {
//		            log.error("Could not get sequence");
//		            throw new RuntimeException("Could not get sequence");
//		        }
//			}
		}catch (SQLException e) {
            if (!retry) {
            	log.error("Could not get sequence", e);
                throw new RuntimeException(e);
            }
            
            log.warn("Could not get sequence, creating sequence and retrying", e);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
		
//		try {
//			return jdbcTemplate.queryForLong(sql);
//		} catch (EmptyResultDataAccessException e) {//org.springframework.dao.EmptyResultDataAccessException
//			if (!retry) {
//	            log.error("Could not get sequence");
//	            throw new RuntimeException("Could not get sequence", e);
//	        }
//		}
		
		
		createSequence(name);
		
		return getFromDb(sql, next, false);
	}
	
    protected void createSequence(String name) {
    	String sql = getCreateSql(name);
    	log.debug(sql);
    	
    	Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = con.prepareStatement(sql);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			int row = stmt.executeUpdate();
			if(row != 1){
				log.error("Could not create sequence '" + name + "'");
			}
		} catch (SQLException e) {
			log.error("Can not create sequence '" + name + "'", e);
		}finally{
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
    	
    	
//    	int i = jdbcTemplate.update(sql);
//    	if(i != 1){
//    		log.error("Could not create sequence '" + name + "'");
//    	}
    }
	
	protected abstract String getNextSql(String name);
	protected abstract String getCurrentSql(String name);
	protected abstract String getCreateSql(String name);
	protected abstract String getDropSql(String name);
}
