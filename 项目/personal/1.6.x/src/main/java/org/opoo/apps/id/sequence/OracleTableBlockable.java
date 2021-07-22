package org.opoo.apps.id.sequence;

import java.lang.reflect.Field;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ClassUtils;

public class OracleTableBlockable implements Blockable {
	private static final Log log = LogFactory.getLog(OracleTableBlockable.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	public static final String INSERT = "insert into T_SYS_ID(ID, CURR) values(?,?)";
	public static final String LOAD = "select CURR from T_SYS_ID where ID=?";
//	public static final String UPDATE = "update T_SYS_ID set CURR=? where ID=?";
	public static final String UPDATE = "update T_SYS_ID set CURR = CURR + ? where ID=? RETURNING CURR INTO ?";
	public static final long NEW_ID_START = 100L;
	
//	public static final boolean IS_PRESENT = isPresent();
	
	private final String name;
	private final DataSource dataSource;
	private final TransactionTemplate transactionTemplate;
	private final OracleMethods oracleMethods;
	
	OracleTableBlockable(DataSource dataSource, String name){
		try {
			oracleMethods = new OracleMethods();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException("不能使用 Oracle 序列产生器");
		}
		
		this.dataSource = dataSource;
		this.name = name;
		//this.transactionTemplate = createTransactionTemplate(dataSource);
		if(IS_DEBUG_ENABLED){
			log.debug("Building OracleTableBlockable sequence '" + name + "'");
		}
		//if(!IS_PRESENT){
//			throw new IllegalStateException("不能使用 Oracle 序列产生器");
		//}
		
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSource);
		transactionTemplate = new TransactionTemplate(tx);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}
	
	
//	/**
//	 * 判断是否可以使用这个序列产生器。
//	 * 
//	 * @return
//	 */
//	public static boolean isPresent(){
//		try {
//			ClassUtils.forName("oracle.jdbc.OraclePreparedStatement");
//			ClassUtils.forName("oracle.jdbc.OracleTypes");
//		} catch (ClassNotFoundException e) {
//			log.error(e);
//			return false;
//		} catch (LinkageError e) {
//			log.error(e);
//			return false;
//		}
//		return true;
//	}
	
//	@SuppressWarnings("null")
//	public Long getCurrent() {
//		
//		TransactionTemplate transactionTemplate = null;
//		transactionTemplate.execute(new TransactionCallback() {
//			public Object doInTransaction(TransactionStatus status) {
//				return null;
//			}
//		});
//		
//		transactionTemplate.getPropagationBehavior();
//		
//		PlatformTransactionManager tx = null;
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//		TransactionStatus status = tx.getTransaction(def);
//		tx.commit(status);
//		tx.rollback(status);
//		
//		return null;
//	}

	public String getName() {
		return name;
	}

	public long getNext(final int blockSize) {
		return ((Number)transactionTemplate.execute(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				return getNextBlock(blockSize);
			}
		})).longValue();
	}
	
	private long getNextBlock(int blockSize) {
		Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try {
        	//是否可以优化？不必每次都调用verify呢？
            if (verifyRow()) {
            	//
            }
            //
            log.debug(UPDATE);
            
            connection = DataSourceUtils.getConnection(dataSource);
            connection = SpringJdbcSupport.getNativeJdbcExtractor().getNativeConnection(connection);
            
            pstmt = connection.prepareStatement(UPDATE);
            oracleMethods.registerReturnParameter(pstmt, 3, oracleMethods.getOracleNumberType());
            pstmt.setLong(1, blockSize);
            pstmt.setString(2, name);

            // process the DML returning statement
            success = pstmt.executeUpdate() == 1;
            if (success) {
                //m = oracleStmt.getMethod("getReturnResultSet");
                //rs = (ResultSet) m.invoke(pstmt);
                rs = oracleMethods.getReturnResultSet(pstmt);
            	if (rs.next()) {
                	return rs.getLong(1);
                }
                else {
                    log.error("WARNING: failed to obtain next ID block for name " + name);
                    success = false;
                }
            }else{
            	log.error("oracle table sequence not exists: " + name); 
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e) {
            log.debug(e.getMessage(), e);
            log.warn("OracleSequenceProvider: Oracle JDBC driver is less than 10.2g, failing back to DefaultSequenceProvider.");
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Throwable e) {
        	 log.error(e.getMessage(), e);
             throw new RuntimeException(e);
		}
        finally {
        	JdbcUtils.closeResultSet(rs);
        	JdbcUtils.closeStatement(pstmt);
        	DataSourceUtils.releaseConnection(connection, dataSource);
        }
        
        throw new IllegalStateException("failed to obtain next id for oracle table sequence '" + name + "'");
	}

	private boolean verifyRow() throws SQLException {
		log.debug("verifyRow for '" + name + "': " + LOAD);
		
        Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean found = true;
        
        try {
            // Get the current ID from the database.
            pstmt = con.prepareStatement(LOAD);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                found = false;
            }
        }
        finally {
        	JdbcUtils.closeResultSet(rs);
        	JdbcUtils.closeStatement(pstmt);
        	DataSourceUtils.releaseConnection(con, dataSource);
        }

        if (!found) {
            createNewID(name);
            return true;
        }
        return false;
    }

    private void createNewID(String name) throws SQLException {
        log.warn("Autocreating ID row for name '" + name + "': " + INSERT);

        // create new ID row
        Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(INSERT);
            pstmt.setString(1, name);
            pstmt.setLong(2, NEW_ID_START);
            pstmt.execute();
        }
        finally {
        	JdbcUtils.closeStatement(pstmt);
        	DataSourceUtils.releaseConnection(con, dataSource);
        }
    }
    
    /**
     * 定义当前类所需的各种Oracle方法和参数。
     */
	private static class OracleMethods{
		private Method registerReturnParameterMethod;
		private Method getReturnResultSetMethod;
		private int oracleNumberType;
		
		public OracleMethods() throws Exception{
			Class<?> oracleStmt = ClassUtils.forName("oracle.jdbc.OraclePreparedStatement");
	        registerReturnParameterMethod = oracleStmt.getMethod("registerReturnParameter", int.class, int.class);
	        getReturnResultSetMethod = oracleStmt.getMethod("getReturnResultSet");
	        
	        
	        Class<?> oracleType = ClassUtils.forName("oracle.jdbc.OracleTypes");
	        // Call Oracle specific JDBC methods by reflection:
	        Field typeField = oracleType.getDeclaredField("NUMBER");
	        oracleNumberType = (Integer) typeField.get(oracleType);
		}
		
		public void registerReturnParameter(PreparedStatement stmt, int index, int type) throws Throwable{
			try {
				registerReturnParameterMethod.invoke(stmt, index, type);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
		
		public ResultSet getReturnResultSet(PreparedStatement stmt) throws Throwable{
			try {
				return (ResultSet) getReturnResultSetMethod.invoke(stmt);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}

		public int getOracleNumberType() {
			return oracleNumberType;
		}
	}
}
