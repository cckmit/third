package org.opoo.apps.module.dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 集群中模块的Dao访问类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ClusterModuleDaoImpl extends SimpleJdbcDaoSupport implements ModuleDao {
	private static final String SELECT = "SELECT name, creationTime, modificationTime FROM SYS_MODULES ";
	private static final Log log = LogFactory.getLog(ClusterModuleDaoImpl.class);
	private static final ParameterizedRowMapper<ModuleBean> MAPPER = new ParameterizedRowMapper<ModuleBean>() {
		public ModuleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			ModuleBean bean = new ModuleBean();
			bean.setCreationTime(new Date(rs.getLong("creationTime")));
			bean.setModificationTime(new Date(rs.getLong("modificationTime")));
			bean.setName(rs.getString("name"));
			return bean;
		}
	};
	
	/**
	 * Oracle 10g之后都可以使用DefaultLobHandler
	 */
	private LobHandler lobHandler = new DefaultLobHandler();
	

	@Transactional(propagation=Propagation.REQUIRED)
	public ModuleBean create(ModuleBean module) {
		String sql = "INSERT INTO SYS_MODULES (name, creationTime, modificationTime) VALUES (?, ?, ?)";
		Date now = new Date();
		module.setCreationTime(now);
		module.setModificationTime(now);
        getSimpleJdbcTemplate().update(sql, module.getName(), Long.valueOf(now.getTime()), Long.valueOf(now.getTime()));
		return module;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public ModuleBean create(final ModuleBean module, final int contentLength, final InputStream inputstream) {
		final String sql = "INSERT INTO SYS_MODULES (name, creationTime, modificationTime, data) VALUES (?, ?, ?, ?)";
		final Date now = new Date();
		module.setCreationTime(now);
		module.setModificationTime(now);
		AbstractLobCreatingPreparedStatementCallback action = new AbstractLobCreatingPreparedStatementCallback(lobHandler){
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
					DataAccessException {
				ps.setString(1, module.getName());
				ps.setLong(2, now.getTime());
				ps.setLong(3, now.getTime());
				lobCreator.setBlobAsBinaryStream(ps, 4, inputstream, contentLength);
			}
		};
		getSimpleJdbcTemplate().getJdbcOperations().execute(sql, action);
	    return module;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(ModuleBean module) {
		try {
			String sql = "DELETE FROM SYS_MODULES WHERE name = ?";
			getSimpleJdbcTemplate().update(sql, module.getName());
		} catch (DataAccessException e) {
			log.error(e);
			throw e;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(String name) {
		try {
			String sql = "DELETE FROM SYS_MODULES WHERE name = ?";
			getSimpleJdbcTemplate().update(sql, name);
		} catch (DataAccessException e) {
			log.error(e);
			throw e;
		}
	}
	
	
	@Transactional(readOnly=true)
	public ModuleBean getByName(String name) {
		String sql = "SELECT name, creationTime, modificationTime FROM SYS_MODULES WHERE name = ?";
		List<ModuleBean> list = getSimpleJdbcTemplate().query(sql, MAPPER, name);
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new IncorrectResultSizeDataAccessException(1, list.size());
		}
	}
	
	
	@Transactional(readOnly=true)
	public List<ModuleBean> getModuleBeans() {
		try {
			return getSimpleJdbcTemplate().query(SELECT, MAPPER);
		} catch (DataAccessException e) {
			log.error(e);
			throw e;
		}
	}

	@Transactional(readOnly=true)
	public InputStream getModuleData(ModuleBean module) {
		String sql = "SELECT data FROM SYS_MODULES WHERE name = ?";
		ParameterizedRowMapper<InputStream> rowMapper = new ParameterizedRowMapper<InputStream>(){
			public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
				return lobHandler.getBlobAsBinaryStream(rs, 1);
			}
		};
		InputStream stream = getSimpleJdbcTemplate().queryForObject(sql, rowMapper, module.getName());
		return stream;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void setModuleData(final ModuleBean module, final int contentLength, final InputStream inputstream) {
		try {
			module.setModificationTime(new Date());
			String sql = "UPDATE SYS_MODULES SET data = ?, modificationTime = ? WHERE name = ?";
			AbstractLobCreatingPreparedStatementCallback callback = new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				@Override
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
						DataAccessException {
					lobCreator.setBlobAsBinaryStream(ps, 1, inputstream, contentLength);
					ps.setLong(2, module.getModificationTime().getTime());
					ps.setString(3, module.getName());
				}
			};
			getSimpleJdbcTemplate().getJdbcOperations().execute(sql, callback);
		} catch (DataAccessException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * @return the lobHandler
	 */
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	/**
	 * @param lobHandler the lobHandler to set
	 */
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}
}
