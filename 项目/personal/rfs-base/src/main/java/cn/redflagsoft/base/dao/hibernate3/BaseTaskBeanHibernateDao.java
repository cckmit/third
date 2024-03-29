/*
 * $Id: BaseTaskBeanHibernateDao.java 6411 2014-05-30 00:52:31Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.database.spring.SpringJdbcSupport;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;

import cn.redflagsoft.base.bean.BaseTaskBean;
import cn.redflagsoft.base.dao.BaseTaskBeanDao;

import com.google.common.collect.Lists;
/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BaseTaskBeanHibernateDao extends AbstractBaseHibernateDao<BaseTaskBean, Long> implements BaseTaskBeanDao{
	private static final Log log = LogFactory.getLog(BaseTaskBeanHibernateDao.class);
	private DataSource dataSource;
	
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

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.BaseTaskBeanDao#updateStatus(java.lang.Long, byte)
	 */
	public void updateStatus(Long id, byte status) {
		String hql = "update BaseTaskBean set status=? where id=?";
		getQuerySupport().executeUpdate(hql, new Object[]{status, id});
	}
	
	
	public List<Long> updateStatusAndReturnIds(byte selectStatus, byte updateStatus){
		if(isOraclePresent()){
			return updateStatusAndReturnIdsOracle(selectStatus, updateStatus);
		}else{
			Criterion c = Restrictions.eq("status", selectStatus);
			List<Long> ids = findIds(new ResultFilter(c, Order.asc("creationTime")));
			getQuerySupport().executeUpdate("update BaseTaskBean set status=? where status=?", new Object[]{updateStatus, selectStatus});
			
			return ids;
		}
	}
	
	
	/**
	 * @param c
	 * @param status
	 * @return
	 */
	private List<Long> updateStatusAndReturnIdsOracle(byte selectStatus, byte updateStatus) {
		Connection c;
		Connection conn = null;
		try {
			c = dataSource.getConnection();
			conn = SpringJdbcSupport.getNativeJdbcExtractor().getNativeConnection(c);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot get JDBC connection.", e);
		}
		
		try{
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			List<Long> list = updateStatusAndReturnIdsOracleInternal(conn, selectStatus, updateStatus);
			
			conn.commit();
			conn.setAutoCommit(autoCommit);
			
			return list;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			throw new IllegalStateException(e);
		}finally{
//			if(conn != null){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					//ignore
//				}
//			}
			JdbcUtils.closeConnection(c);
		}
	}
	
	private List<Long> updateStatusAndReturnIdsOracleInternal(Connection conn, byte selectStatus, byte updateStatus) throws Exception {
		final String sql = "update BASE_TASK_BEAN set STATUS=? where STATUS=? RETURNING ID into ?";
		List<Long> result = Lists.newArrayList();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			log.debug(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setByte(1, updateStatus);
			stmt.setByte(2, selectStatus);
			//ostmt.registerReturnParameter(3, OracleTypes.NUMBER);
			registerReturnParameter(stmt, 3, 2);
			int row = stmt.executeUpdate();
			
			if(row > 0){
				rs = getReturnResultSet(stmt);
				while(rs.next()){
					result.add(rs.getLong(1));
				}
			}
		}catch(Exception e){
			log.fatal(e.getMessage(), e);
			throw e;	
		}finally{
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
		}
		return result;
	}
	


	/**
	 * 判断是否可以使用这个序列产生器。
	 * 
	 * @return
	 */
	public static boolean isOraclePresent(){
		try {
			ClassUtils.forName("oracle.jdbc.OraclePreparedStatement");
			ClassUtils.forName("oracle.jdbc.OracleTypes");
		} catch (ClassNotFoundException e) {
			log.debug(e);
			return false;
		} catch (LinkageError e) {
			log.debug(e);
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
	
	public static void main(String[] args){
		System.out.println(isOraclePresent());
		SimpleExpression c = Restrictions.eq("status", BaseTaskBean.STATUS_READY);
		System.out.println(c.toString());
	}
}
