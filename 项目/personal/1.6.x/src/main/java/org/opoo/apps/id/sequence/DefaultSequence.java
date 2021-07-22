package org.opoo.apps.id.sequence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.opoo.apps.bean.core.SysId;
import org.opoo.apps.id.AbstractBlockIdGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.util.Assert;


/**
 * 读取数据表生成 id。
 * @author Alex Lin(alex@opoo.org)
 * @deprecated 不支持集群，不再使用。
 */
public class DefaultSequence extends AbstractBlockIdGenerator implements Sequence {
	public static final String INSERT = "insert into T_SYS_ID(ID, CURRENT, STEP) values(?,?,?)";
	public static final String LOAD = "select ID, CURRENT, STEP, DESCRIPTION from T_SYS_ID where ID=?";
	public static final String UPDATE = "update T_SYS_ID set CURRENT=? where ID=?";
	private JdbcTemplate jdbcTemplate;
	public DefaultSequence(DataSource dataSource, String name){
		super(name);
		Assert.notNull(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	protected void createSysId(final SysId sysId) {
		jdbcTemplate.execute(INSERT, new PreparedStatementCallback(){
			public Object doInPreparedStatement(PreparedStatement st)
					throws SQLException, DataAccessException {
				st.setString(1, sysId.getId());
				st.setLong(2, sysId.getCurrent());
				st.setInt(3, sysId.getStep());
				st.execute();
				return null;
			}});
	}

	@Override
	protected SysId loadSysId(String name) {/*
		return (SysId) factory.createQuery(LOAD)
		.setString(0, name)
		.setMapper(new MapperAdaptor(){
			public Object map(ResultSet rs, int arg1) throws SQLException {
				SysId id = new SysId();
				id.setId(rs.getString(1));
				id.setCurrent(rs.getLong(2));
				id.setStep(rs.getInt(3));
				id.setDescription(rs.getString(4));
				return id;
			}
		}).uniqueResult();*/
		return null;
	}

	@Override
	protected void updateSysId(String name, long current) {
		//factory.createQuery(UPDATE).setLong(0, current).setString(1, name).executeUpdate();
	}
}
