package org.opoo.apps.id.sequence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.OraclePreparedStatement;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.AppsHome;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.database.spring.SpringJdbcSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;

public class OracleTableBlockableTest {
	
	private static int oracleNumberType;
	private static OracleMethods oracleMethods;
	
	@BeforeClass
	public static void init() throws Exception{
		AppsHome.setEffectiveAppsHomePath("E:\\work.home\\applications\\milestone\\pms-szft");
		AppsHome.setEffectiveAppsRootPath("E:\\work.home\\applications");
//		System.setProperty("apps.home", "E:\\work.home\\applications\\milestone\\pms-szft");
		
        Class<?> oracleType = ClassUtils.forName("oracle.jdbc.OracleTypes");
     // Call Oracle specific JDBC methods by reflection:
        Field typeField = oracleType.getDeclaredField("NUMBER");
        oracleNumberType = (Integer) typeField.get(oracleType);
        
        
        oracleMethods = new OracleMethods();
	}
	
	

	public void test1() throws Exception{
		System.out.println(oracleMethods);
		System.out.println(oracleNumberType);
		
		DataSource dataSource = DataSourceManager.getRuntimeDataSource();
		System.out.println(DataSourceManager.getRuntimeDataSource());
		
		Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	connection = DataSourceUtils.getConnection(dataSource);
        	//System.out.println(SpringJdbcSupport.getNativeJdbcExtractor());
        	System.out.println(connection.getClass());
        	pstmt = connection.prepareStatement(OracleTableBlockable.UPDATE);
        	System.out.println(pstmt.getClass() + " : " + pstmt);
        	pstmt.close();
            
        	connection = SpringJdbcSupport.getNativeJdbcExtractor().getNativeConnection(connection);
        	System.out.println(connection.getClass());
        	
            pstmt = connection.prepareStatement(OracleTableBlockable.UPDATE);
        	System.out.println(pstmt.getClass() + " : " + pstmt);
        	
        	oracle.jdbc.OraclePreparedStatement ps = (OraclePreparedStatement) pstmt;
//        	ps.registerReturnParameter(3, oracle.jdbc.OracleTypes.NUMBER);
        	oracleMethods.registerReturnParameter(ps, 3, 2);
        	ps.setLong(1, 5);
        	ps.setString(2, "test_seq");
        	
        	int row = ps.executeUpdate();
        	if(row == 1){
        		//rs = ps.getReturnResultSet();
        		rs = oracleMethods.getReturnResultSet(ps);
        		if(rs.next()){
        			long long1 = rs.getLong(1);
        			System.out.println("--------------------" + long1);
        		}
        	}else{
        		System.out.println("MMMMMMMMMM");
        	}
        	pstmt.close();
            
            
        }finally{
        	JdbcUtils.closeResultSet(rs);
        	DataSourceUtils.releaseConnection(connection, dataSource);
        	((BasicDataSource) dataSource).close();
        }
	}
	

	
	private static class OracleMethods{
		private Method registerReturnParameterMethod;
		private Method getReturnResultSetMethod;
		
		public OracleMethods() throws Exception{
			Class<?> oracleStmt = ClassUtils.forName("oracle.jdbc.OraclePreparedStatement");
	        registerReturnParameterMethod = oracleStmt.getMethod("registerReturnParameter", int.class, int.class);
	        getReturnResultSetMethod = oracleStmt.getMethod("getReturnResultSet");
		}
		
		public void registerReturnParameter(PreparedStatement stmt, int index, int type) throws SQLException{
			try {
				registerReturnParameterMethod.invoke(stmt, index, type);
			} catch (Exception e) {
				throw new SQLException(e);
			}
		}
		
		public ResultSet getReturnResultSet(PreparedStatement stmt) throws SQLException{
			try {
				return (ResultSet) getReturnResultSetMethod.invoke(stmt);
			} catch (Exception e) {
				throw new SQLException(e);
			}
		}
	}
	
	
	
	@Test
	public void test2(){
		DataSource dataSource = DataSourceManager.getRuntimeDataSource();
		System.out.println(dataSource);
		
		OracleTableBlockable seq = new OracleTableBlockable(dataSource, "test2");
		long next = seq.getNext(2);
		System.out.println(next);
	}
}
