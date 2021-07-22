package org.opoo.apps.id.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.support.JdbcUtils;




/**
 * 这是最基本的从数据库生成ID的操作，效率比较低。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TableSequence2 implements Sequence, Blockable {
	private static final Log log = LogFactory.getLog(TableSequence2.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	public static final String INSERT = "insert into T_SYS_ID(ID, CURR) values(?,?)";
	public static final String LOAD = "select CURR from T_SYS_ID where ID=?";
	public static final String UPDATE = "update T_SYS_ID set CURR=? where ID=?";
	public static final long NEW_ID_START = 100L;
	
	private final DataSource dataSource;
	private final String name;
	private long current = -1;
	
	//private final TransactionTemplate transactionTemplate;
	
	
	TableSequence2(DataSource dataSource, String name){
		this.dataSource = dataSource;
		this.name = name;
		//this.transactionTemplate = createTransactionTemplate(dataSource);
		if(IS_DEBUG_ENABLED){
			log.debug("Building TableSequence '" + name + "'");
		}
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
	
	/**
	 * 每次返回的对象应该是不变的。
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public synchronized Long getCurrent() {
		if(current == -1){
			throw new IllegalStateException("必须先调用 getNext()");
		}
		return current;
	}
	public synchronized Long getNext() {
		return getNext(1);
	}
	
	protected long createNewSequence(Connection conn, long newId) throws SQLException {
		if(IS_DEBUG_ENABLED){
			log.debug(INSERT);
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT);
			stmt.setString(1, name);
			stmt.setLong(2, newId);
			int row = stmt.executeUpdate();
			if(row != 1){
				log.error("Could not create sequence '" + name + "'");
				throw new IllegalStateException("Could not create sequence '" + name + "'");
			}
			return newId;
		} catch (SQLException e) {
			log.error("Can not create sequence '" + name + "'", e);
			throw e;
		}finally{
			JdbcUtils.closeStatement(stmt);
		}
	}


	protected long getFromDb(Connection conn) throws SQLException{
		if(IS_DEBUG_ENABLED){
			log.debug(LOAD);
		}
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(LOAD);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("Can not get sequence '" + name + "', creating sequence", e);
			throw e;
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
		
		return createNewSequence(conn, NEW_ID_START);
//		return 0L;
		
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
	
	protected void updateSequence(Connection conn) throws SQLException{
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setLong(1, current);
			stmt.setString(2, name);
			int row = stmt.executeUpdate();
			if(row != 1){
				log.error("Can not update sequence '" + name + "'");
				throw new IllegalStateException("更新id出错，变动记录不唯一：" + name);
			}
		} catch (SQLException e) {
			log.error("Can not update sequence '" + name + "'", e);
			throw e;
		}finally{
			JdbcUtils.closeStatement(stmt);
		}
	}
	
//	public final long getNext(final int blockSize){
//		return ((Number)doInTransaction(new TransactionCallback(){
//			public Object doInTransaction(TransactionStatus status) {
//				long id = getNextInTransaction(blockSize);
//				return new Long(id);
//			}})).longValue();
//	}
	

	public synchronized long getNext(int blockSize) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e1) {
			throw new IllegalStateException("Cannot get JDBC connection.");
		}
		
		try{
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
		
			long idInDb = getFromDb(conn);
			current = idInDb + blockSize;
			
			if(IS_DEBUG_ENABLED){
				log.debug(UPDATE);
			}
			
			updateSequence(conn);
		
			conn.commit();
			conn.setAutoCommit(autoCommit);
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
		
			if(IS_DEBUG_ENABLED){
				String s = String.format("Sequence '%s' 从 %s 跳到 %s 。", name, idInDb, current);
				log.debug(s);
			}
			
			return current;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			throw new IllegalStateException(e);
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					//ignore
				}
			}
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
