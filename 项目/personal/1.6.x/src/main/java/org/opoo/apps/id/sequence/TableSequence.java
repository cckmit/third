package org.opoo.apps.id.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.util.LockUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




/**
 * 这是最基本的从数据库生成ID的操作，效率比较低。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TableSequence implements Sequence, Blockable {
	private static final Log log = LogFactory.getLog(TableSequence.class);
	
	public static final String INSERT = "insert into T_SYS_ID(ID, CURR) values(?,?)";
	public static final String LOAD = "select CURR from T_SYS_ID where ID=?";
	public static final String UPDATE = "update T_SYS_ID set CURR=? where ID=?";
	
	private final DataSource dataSource;
	private final String name;
	protected long current = -1;
	
	//private final TransactionTemplate transactionTemplate;
	
	
	TableSequence(DataSource dataSource, String name){
		this.dataSource = dataSource;
		this.name = name;
		//this.transactionTemplate = createTransactionTemplate(dataSource);
	}
	
//	protected TransactionTemplate createTransactionTemplate(DataSource ds){
//		PlatformTransactionManager tm = new DataSourceTransactionManager(dataSource);
//		TransactionTemplate tt = new TransactionTemplate(tm);
//		tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//		return tt;
//	}
//	
//	protected Object doInTransaction(TransactionCallback action){
//		return transactionTemplate.execute(action);
//	}
	
	
	public synchronized Long getCurrent() {
		if(current == -1){
			throw new IllegalStateException("必须先调用 getNext()");
		}
		return current;
	}
	public synchronized Long getNext() {
		return getNext(1);
	}
	
	private void createNewSequence() {
		log.debug(INSERT);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = con.prepareStatement(INSERT);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			stmt.setString(1, name);
			stmt.setLong(2, 1L);
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
	}
	
	/**
	 * 每次返回的对象应该是不变的。
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	protected long getFromDb(){
		log.debug(LOAD);
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = con.prepareStatement(LOAD);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("Can not get sequence '" + name + "', creating sequence", e);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
		
		createNewSequence();
		return 0L;
		
//		try {
//			return dataSource.queryForLong(LOAD, new Object[]{name}, new int[]{Types.VARCHAR});
//		} catch (EmptyResultDataAccessException e) {
//			createNewSequence();
//			return 1L;
//		}
		
//		
//		long idInTable = (Long) jdbcTemplate.execute(LOAD, new PreparedStatementCallback(){
//			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//				ps.setString(1, name);
//				ResultSet rs = ps.executeQuery();
//				long id = -1L;
//				if(rs.next()){
//					id = rs.getLong(1);
//				}
//				JdbcUtils.closeResultSet(rs);
//				return id;
//			}
//		});
//		
//		if(idInTable == -1L){
//			createNewSequence();
//			return 1L;
//		}
//		return idInTable;
	}
	
	protected void updateSequence(){
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DataSourceUtils.getConnection(getDataSource());
			stmt = con.prepareStatement(UPDATE);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			stmt.setLong(1, current);
			stmt.setString(2, name);
			int row = stmt.executeUpdate();
			if(row != 1){
				//throw new IllegalStateException("更新id出错，变动记录不唯一：" + name);
				log.error("Can not update sequence '" + name + "'");
			}
		} catch (SQLException e) {
			log.error("Can not update sequence '" + name + "'", e);
		}finally{
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
	}
	
//	public final long getNext(final int blockSize){
//		return ((Number)doInTransaction(new TransactionCallback(){
//			public Object doInTransaction(TransactionStatus status) {
//				long id = getNextInTransaction(blockSize);
//				return new Long(id);
//			}})).longValue();
//	}
	

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public long getNext(int blockSize) {
		String key = LockUtils.intern("Sequence-table-" + name);
		try{
			CacheFactory.lockKey(key, -1);
		
			long idInDb = getFromDb();
			current = idInDb + blockSize;
			
			log.debug(UPDATE);
			
			updateSequence();
		
		
		
//		int row =dataSource.update(UPDATE, new PreparedStatementSetter(){
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setLong(1, current);
//				ps.setString(2, name);		
//			}
//		});
//		
//		if(row != 1){
//			throw new IllegalStateException("更新id出错，变动记录不唯一：" + name);
//		}
		
			return current;
		}finally{
			CacheFactory.unlockKey(key);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
