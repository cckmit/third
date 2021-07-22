package org.opoo.apps.id.sequence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.database.spring.SpringJdbcSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
public class OracleTableSequence extends TableSequence implements Sequence {
    private static final Log log = LogFactory.getLog(OracleTableSequence.class);
	public static final String UPDATE = "update T_SYS_ID set CURR = CURR + ? where ID=? RETURNING CURR INTO ?";
	public static final boolean IS_PRESENT = isPresent();
	private AtomicBoolean initialized = new AtomicBoolean(false); 
	
	OracleTableSequence(DataSource ds, String name){
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
	
	protected long getNextInternal(final int blockSize){
		Connection con = null;//DataSourceUtils.getConnection(getDataSource());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			con = getDataSource().getConnection();//DataSourceUtils.getConnection(getDataSource());
			Connection con2 = SpringJdbcSupport.getNativeJdbcExtractor().getNativeConnection(con);
			stmt = con2.prepareStatement(UPDATE);
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
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
			}else{
				log.error("更新的记录数不唯一：" + row);
			}
		}catch(SQLException e){
			log.fatal(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.fatal(e.getMessage(), e);
		} catch (SecurityException e) {
			log.fatal(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.fatal(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.fatal(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.fatal(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.fatal(e.getMessage(), e);
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			//DataSourceUtils.releaseConnection(con, getDataSource());
			JdbcUtils.closeConnection(con);
		}
		
		
		throw new IllegalStateException("failed to obtain next id for oracle table sequence '" + getName() + "'");
	}
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.id.sequence.TableSequence#getNext(int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public long getNext(int blockSize) {
			
		//确认表式存在的
		if(!initialized.get()){
			getFromDb();
			initialized.set(true);
		}
		
		current = getNextInternal(blockSize);
		
		return current;
		
		/*
		final JdbcTemplate template = getJdbcTemplate();
		log.debug(UPDATE);
		current = ((Long) template.execute(UPDATE, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				PreparedStatement stmt = template.getNativeJdbcExtractor().getNativePreparedStatement(ps);
//				if (stmt instanceof OraclePreparedStatement) {
//					OraclePreparedStatement ostmt = (OraclePreparedStatement) stmt;
					stmt.setInt(1, blockSize);
					stmt.setString(2, getName());
//					ostmt.registerReturnParameter(3, OracleTypes.NUMBER);
					registerReturnParameter(stmt, 3, 2);
					
					int row = stmt.executeUpdate();
					if (row == 1) {
						long result = -1;
						ResultSet set = getReturnResultSet(stmt);//ostmt.getReturnResultSet();
						if (set.next()) {
							result = set.getLong(1);
						}
						JdbcUtils.closeResultSet(set);
						if (result != -1) {
							return new Long(result);
						} else {
							throw new IllegalStateException("返回的 Id 不正确：" + getName());
						}
					} else {
						throw new IllegalStateException("更新 Id 数据出错：" + getName());
					}
//				} else {
//					throw new IllegalStateException("Failed to obtain next ID for name " + getName()
//							+ ": It's not a oracle connection");
//				}
			}
		})).longValue();

		return current;
		
		*/
	}

}
