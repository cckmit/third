package org.opoo.apps.dv.office.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionErrorStep;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStep;
import org.opoo.apps.dv.office.dao.OfficeConversionDao;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.apps.dv.office.model.OfficeErrorStep;
import org.opoo.apps.dv.office.model.OfficeMetaData;
import org.opoo.apps.id.IdGenerator;
import org.opoo.ndao.NonUniqueResultException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class OfficeConversionJdbcDao extends SimpleJdbcDaoSupport implements OfficeConversionDao {
	private HibernateTemplate tempate;
	/*
	 private long id;
	private long covnertableObjectId;
	private int convertableObjectType;
    private String filename;
    private long length;
    private int numberOfPages;
    private int revisionNumber;
    private String metadata = null;
    private Date creationDate;
    private Date modificationDate;
    
  long conversionMetaDataID, Date date, String message, String step
	 */
	private static final String BASE_SELECT_META_SQL 
		= "select ID, OBJTYPE, OBJID, FILENAME, FILESIZE, PAGES, RV, METADATA, CTIME, MTIME from DV_META_DATA m";
	private static final String INSERT_META_SQL
		= "insert into DV_META_DATA(ID, OBJTYPE, OBJID, FILENAME, FILESIZE, PAGES, RV, METADATA, CTIME, MTIME) values (?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_META_SQL 
		= "update DV_META_DATA set OBJTYPE=?, OBJID=?, FILENAME=?, FILESIZE=?, PAGES=?, RV=?, METADATA=?, CTIME=?, MTIME=? where ID=?";
	
	private static final String BASE_SELECT_ARTIFACT_SQL
		= "select INSTANCEID, CMID, CATYPE, FILESIZE, FILENAME, CONTENTTYPE, PAGE from DV_ARTIFACT a";
	private static final String INSERT_ARTIFACT_SQL
		= "insert into DV_ARTIFACT(INSTANCEID, CMID, CATYPE, FILESIZE, FILENAME, CONTENTTYPE, PAGE) values(?,?,?,?,?,?,?)";
	
	private static final String BASE_SELECT_STEP_SQL
		= "select CMID, DATE, MSG, STEP from DV_ERROR_STEP s";
	private static final String INSERT_STEP_SQL
		= "insert into DV_ERROR_STEP(CMID, DATE, MSG, STEP) values(?,?,?,?)";
	
	private IdGenerator<Long> idGenerator;
	
	public OfficeConversionMetaData getMetaData(long id) {
		String sql = BASE_SELECT_META_SQL + " where m.ID=?";
		return getSimpleJdbcTemplate().queryForObject(sql, new MetaDataRowMapper(), id);
	}

	public Long getMetaDataID(int objectType, long objectId, int version) {
		String sql = "select ID from DV_META_DATA m where m.OBJTYPE=? and m.OBJID=? and m.RV=?";
		List<Long> list = getSimpleJdbcTemplate().query(sql, new LongRowMapper(), objectType, objectId, version);
		if(list.size() == 1){
			return list.iterator().next().longValue();
		}else if(list.isEmpty()){
			return null;
		}
		throw new NonUniqueResultException(list.size());
	}

	public int getMetaDataCount() {
		String sql = "select count(*) from DV_META_DATA";
		return getSimpleJdbcTemplate().queryForInt(sql);
	}

	

	public OfficeConversionArtifact getArtifact(long cmID, OfficeConversionArtifactType type, int partNumber) {
		String sql = BASE_SELECT_ARTIFACT_SQL + " where a.CMID=? and a.CATYPE=? and a.PAGE=?";
		List<OfficeConversionArtifact> list = getSimpleJdbcTemplate().query(sql, new ArtifactRowMapper(), cmID, type.getName(), partNumber);
		if(list.size() == 1){
			return list.iterator().next();
		}else if(list.isEmpty()){
			return null;
		}
		throw new NonUniqueResultException(list.size());
	}

	public int getArtifactCount(long cmID, OfficeConversionArtifactType type) {
		String sql = "select count(*) from DV_ARTIFACT a where a.CMID=? and a.CATYPE=?";
		return getSimpleJdbcTemplate().queryForInt(sql, cmID, type.getName());
	}

	public List<OfficeConversionArtifact> findArtifacts(long cmID) {
		String sql = BASE_SELECT_ARTIFACT_SQL + " where a.CMID=?";
		return getSimpleJdbcTemplate().query(sql, new ArtifactRowMapper(), cmID);
	}

	public void removeAllByMetaDataID(long cmID) {
		String sql1 = "delete from DV_ARTIFACT where CMID=?";
		String sql2 = "delete from DV_ERROR_STEP where CMID=?";
		String sql3 = "delete from DV_META_DATA where ID=?";
		getSimpleJdbcTemplate().update(sql1, cmID);
		getSimpleJdbcTemplate().update(sql2, cmID);
		getSimpleJdbcTemplate().update(sql3, cmID);
	}

	public OfficeConversionMetaData saveMetaData(OfficeConversionMetaData meta) {
		//ID, OBJTYPE, OBJID, FILENAME, FILESIZE, PAGES, RV, METADATA, CTIME, MTIME
		Long id = idGenerator.getNext();
		int update = getSimpleJdbcTemplate().update(
				INSERT_META_SQL,
				id,
				meta.getConvertableObjectType(),
				meta.getConvertableObjectId(),
				meta.getFilename(),
				meta.getLength(),
				meta.getNumberOfPages(),
				meta.getRevisionNumber(),
				meta.getMetadata(),
				meta.getCreationDate() != null ? meta.getCreationDate().getTime() : 0L,
				meta.getModificationDate() != null ? meta.getModificationDate().getTime() : 0L);
		
		return update == 0 ? null : 
			new OfficeMetaData(id, meta.getConvertableObjectType(), meta.getConvertableObjectId(),
				meta.getFilename(), meta.getLength(),
				meta.getNumberOfPages(), meta.getRevisionNumber(), meta.getMetadata(),
				meta.getCreationDate(), meta.getModificationDate());
	}

	public OfficeConversionMetaData updateMetaData(OfficeConversionMetaData meta) {
		int update = getSimpleJdbcTemplate().update(
				UPDATE_META_SQL,
				meta.getConvertableObjectType(),
				meta.getConvertableObjectId(),
				meta.getFilename(),
				meta.getLength(),
				meta.getNumberOfPages(),
				meta.getRevisionNumber(),
				meta.getMetadata(),
				meta.getCreationDate() != null ? meta.getCreationDate().getTime() : 0L,
				meta.getModificationDate() != null ? meta.getModificationDate().getTime() : 0L,
				meta.getId());
		return update > 0 ? meta : null;
	}

	public OfficeConversionArtifact saveArtifact(OfficeConversionArtifact ca) {
		//INSTANCEID, CMID, CATYPE, FILESIZE, FILENAME, CONTENTTYPE, PAGE
		int update = getSimpleJdbcTemplate().update(INSERT_ARTIFACT_SQL, 
				ca.getInstanceID(),
				ca.getConversionMetadataID(),
				ca.getType().getName(),
				ca.getLength(),
				ca.getFilename(),
				ca.getContentType(),
				ca.getPage());
		return update > 0 ? ca : null;
	}

	public OfficeConversionErrorStep saveErrorStep(OfficeConversionErrorStep s) {
		//CMID, DATE, MSG, STEP
		int update = getSimpleJdbcTemplate().update(
				INSERT_STEP_SQL,
				s.getConversionMetaDataID(),
				s.getModificationDate() != null ? s.getModificationDate().getTime() : 0L, 
				s.getMessage(), s.getStep());
						
		return update > 0 ? s : null;
	}

	public List<OfficeConversionErrorStep> findErrorStepsByMetaDataID(long cmID) {
		String sql = BASE_SELECT_STEP_SQL + " where s.CMID=?";
		return getSimpleJdbcTemplate().query(sql, new StepRowMapper(), cmID);
	}

	public void removeErrorStepsByMetaDataID(long cmID) {
		String sql = "delete from DV_ERROR_STEP where CMID=?";
		getSimpleJdbcTemplate().update(sql, cmID);
	}

	public int getMetaDataErrorCount(String filter) {
		String sql = "select count(distinct m.ID) from DV_META_DATA m, DV_ERROR_STEP s where r.ID=s.CMID and s.MSG is not null";
		if(StringUtils.isNotBlank(filter)){
			sql += " and m.filename like ?";
			return getSimpleJdbcTemplate().queryForInt(sql, "%" + filter + "%");
		}else{
			return getSimpleJdbcTemplate().queryForInt(sql);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findErrorMetadataIDs(final ResultFilter filter) {
		String sql = "select distinct m.ID from DV_META_DATA m, DV_ERROR_STEP s where r.ID=s.CMID and s.MSG is not null";
		Object[] args = new Object[0];
		if(filter != null){
			Criterion c = filter.getCriterion();
			if(c != null){
				sql += " and " + c.toString();
				args = c.getValues();
			}
			Order order = filter.getOrder();
			if(order != null){
				sql += " order by " + order.toOrderString();
			}else{
				sql += " order by m.CTIME DESC";
			}
		}
		
		if(!filter.isPageable()){
			return getSimpleJdbcTemplate().query(sql, new LongRowMapper(), args);
		}else{
			final String qs = sql;
			final Object[] vals = args;
			return tempate.executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException,	SQLException {
					SQLQuery q = session.createSQLQuery(qs);
					int index = 0;
					for(Object val: vals){
						q.setParameter(index++, val);
					}
					q.addScalar("ID", Hibernate.LONG);
					q.setFirstResult(filter.getFirstResult());
					q.setMaxResults(filter.getMaxResults());
					return q.list();
				}
			});
		}
	}

	
	public static class MetaDataRowMapper implements ParameterizedRowMapper<OfficeConversionMetaData> {
		public OfficeConversionMetaData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			/*
			 * private long id; private long covnertableObjectId; private int
			 * convertableObjectType; private String filename; private long
			 * length; private int numberOfPages; private int revisionNumber;
			 * private String metadata = null; private Date creationDate;
			 * private Date modificationDate;
			 */
			long id = rs.getLong(1);
			int objectType = rs.getInt(2);
			long objectId = rs.getLong(3);
			String filename = rs.getString(4);
			long size = rs.getLong(5);
			int numberOfPages = rs.getInt(6);
			int version = rs.getInt(7);
			String metadata = rs.getString(8);
			long creationDate = rs.getLong(9);
			long modificationDate = rs.getLong(10);
			Date dc = creationDate > 0 ? new Date(creationDate) : null;
			Date dm = modificationDate > 0 ? new Date(modificationDate) : null;
			return new OfficeMetaData(id, objectType, objectId,
					filename, size, numberOfPages, version, metadata, dc, dm);
		}
	}
	
	public static class LongRowMapper implements ParameterizedRowMapper<Long>{
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getLong(1);
		}
	}
	
	public static class ArtifactRowMapper implements
			ParameterizedRowMapper<OfficeConversionArtifact> {
		public OfficeConversionArtifact mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			/*
			 * String instanceID, long conversionMetadataID,
			 * OfficeConversionArtifactType type, long length, String filename,
			 * String contentType, int page
			 */

			String instanceID = rs.getString(1);
			long cmid = rs.getLong(2);
			String catype = rs.getString(3);
			long fileSize = rs.getLong(4);
			String filename = rs.getString(5);
			String contentType = rs.getString(6);
			int page = rs.getInt(7);

			OfficeConversionArtifactType artifactType = OfficeConversionArtifactType.valueOf(catype);
			return new OfficeArtifact(instanceID, cmid,
					artifactType, fileSize, filename, contentType, page);
		}
	}
	
	public static class StepRowMapper implements ParameterizedRowMapper<OfficeConversionErrorStep>{
		public OfficeConversionErrorStep mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			long conversionMetaDataID = rs.getLong(1);
			long ld = rs.getLong(2);
			Date date = ld > 0 ? new Date(ld) : null;
			String message = rs.getString(3);
			String step = rs.getString(4);
			OfficeConversionStep valueOf = OfficeConversionStep.valueOf(step);
			return new OfficeErrorStep(conversionMetaDataID, date, message, valueOf);
		}
	}
}
