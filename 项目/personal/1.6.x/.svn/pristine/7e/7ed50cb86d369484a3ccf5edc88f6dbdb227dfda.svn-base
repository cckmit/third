package org.opoo.apps.id.sequence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.database.spring.SpringJdbcSupport;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;




/**
 * 
 * Oracle数据库表存储的id。
 * 
 * <p>为了减少对Oracle驱动程序的依赖，使用反射机制调用了其中2个方法。
 * 原方法见注释。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OracleTableSequence2 extends TableSequence2 implements Sequence {
    private static final Log log = LogFactory.getLog(OracleTableSequence2.class);
	public static final String UPDATE = "update T_SYS_ID set CURR = CURR + ? where ID=? RETURNING CURR INTO ?";
	public static final boolean IS_PRESENT = isPresent();
	private long current = -1;
	
	OracleTableSequence2(DataSource ds, String name){
		super(ds, name);
		if(!IS_PRESENT){
			throw new IllegalStateException("不能使用 Oracle 序列产生器");
		}
	}
	
	/**
	 * 判断是否可以使用这个序列产生器。
	 * 
	 * @return
	 */
	public static boolean isPresent(){
		try {
			ClassUtils.forName("oracle.jdbc.OraclePreparedStatement");
			ClassUtils.forName("oracle.jdbc.OracleTypes");
		} catch (ClassNotFoundException e) {
			log.error(e);
			return false;
		} catch (LinkageError e) {
			log.error(e);
			return false;
		}
		return true;
	}
	
	
	protected void registerReturnParameter(PreparedStatement stmt, int index, int type)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, 
			ClassNotFoundException,	SecurityException, NoSuchMethodException {
		Class<?> oracleStmt = Class.forName("oracle.jdbc.OraclePreparedStatement");
		Method method = oracleStmt.getMethod("registerReturnParameter", int.class, int.class);
		method.invoke(stmt, index, type);
	}
	
	protected ResultSet getReturnResultSet(PreparedStatement stmt) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> oracleStmt = Class.forName("oracle.jdbc.OraclePreparedStatement");
		Method method = oracleStmt.getMethod("getReturnResultSet");
		return (ResultSet) method.invoke(stmt);
	}
	
	protected long getNextInternal(final Connection conn, final int blockSize) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{
			log.debug(UPDATE);
			stmt = conn.prepareStatement(UPDATE);
//			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			stmt.setInt(1, blockSize);
			stmt.setString(2, getName());
//			ostmt.registerReturnParameter(3, OracleTypes.NUMBER);
			registerReturnParameter(stmt, 3, 2);
			
			int row = stmt.executeUpdate();
			if (row == 1) {
				rs = getReturnResultSet(stmt);//ostmt.getReturnResultSet();
				if (rs.next()) {
					return rs.getLong(1);
				}else{
					log.error("找不到 Oracle update return 的返回值");
				}
			}else if(row == 0){
				return createNewSequence(conn, NEW_ID_START + blockSize);
			}else{
				log.error("更新的记录数不唯一：" + row);
			}
//		}catch(SQLException e){
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (IllegalArgumentException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (SecurityException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (IllegalAccessException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (InvocationTargetException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (ClassNotFoundException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
//		} catch (NoSuchMethodException e) {
//			log.fatal(e.getMessage(), e);
//			throw e;
		}catch(Exception e){
			log.fatal(e.getMessage(), e);
			throw e;	
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
		
		
		throw new IllegalStateException("failed to obtain next id for oracle table sequence '" + getName() + "'");
	}
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.id.sequence.TableSequence#getNext(int)
	 */
	@Override
	public long getNext(int blockSize) {
			
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			conn = SpringJdbcSupport.getNativeJdbcExtractor().getNativeConnection(conn);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot get JDBC connection.", e);
		}
		
		
		try{
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			current = getNextInternal(conn, blockSize);
		
			conn.commit();
			conn.setAutoCommit(autoCommit);
		
			if(log.isDebugEnabled()){
				String s = String.format("OracleTableSequence '%s' 跳到 %s 。", getName(), current);
				log.debug(s);
			}
			
			return current;
		} catch (Exception e) {
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
}
