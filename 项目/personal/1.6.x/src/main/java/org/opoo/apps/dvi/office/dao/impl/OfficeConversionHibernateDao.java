package org.opoo.apps.dvi.office.dao.impl;

import java.util.List;

import org.opoo.apps.dvi.ConversionArtifact;
import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.ConversionMetaData;
import org.opoo.apps.dvi.office.OfficeConversionErrorStep;
import org.opoo.apps.dvi.office.OfficeConversionMetaData;
import org.opoo.apps.dvi.office.dao.OfficeConversionDao;
import org.opoo.apps.dvi.office.model.OfficeArtifact;
import org.opoo.apps.dvi.office.model.OfficeErrorStep;
import org.opoo.apps.dvi.office.model.OfficeMetaData;
import org.opoo.apps.id.IdGenerator;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.ndao.NonUniqueResultException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.beans.factory.annotation.Required;

public class OfficeConversionHibernateDao extends HibernateDaoSupport implements OfficeConversionDao {

	private IdGeneratorProvider<Long> idGeneratorProvider;
	private IdGenerator<Long> cmIdGenerator;
	private IdGenerator<Long> caIdGenerator;
	private IdGenerator<Long> stepIdGenerator;
	
	
	@Required
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider){
		this.idGeneratorProvider = idGeneratorProvider;
	}
	
	@Override
	protected void initDao() throws Exception {
		super.initDao();
		Assert.notNull(idGeneratorProvider, "IdGeneratorProvider is required.");
		cmIdGenerator = idGeneratorProvider.getIdGenerator(OfficeMetaData.class.getSimpleName());
		caIdGenerator = idGeneratorProvider.getIdGenerator(OfficeArtifact.class.getSimpleName());
		stepIdGenerator = idGeneratorProvider.getIdGenerator(OfficeErrorStep.class.getSimpleName());
	}

	public OfficeConversionMetaData getMetaData(long id) {
		return (OfficeConversionMetaData) getHibernateTemplate().get(OfficeMetaData.class, id);
	}

	public Long getMetaDataID(int objectType, long objectId, int version) {
		String qs = "select id from OfficeMetaData m where m.convertableObjectType=?" +
				" and m.convertableObjectId=? and m.revisionNumber=?";
		@SuppressWarnings("unchecked")
		List<Number> list = getHibernateTemplate().find(qs, new Object[]{objectType, objectId, version});
		if(list.size() == 1){
			return list.iterator().next().longValue();
		}else if(list.isEmpty()){
			return null;
		}
		throw new NonUniqueResultException(list.size());
	}

	public int getMetaDataCount() {
		String qs = "select count(*) from OfficeMetaData";
		return getQuerySupport().getInt(qs, null);
	}

	public int getMetaDataErrorCount(String filter) {
		String qs = "select count(distinct m.id) from OfficeMetaData m, OfficeErrorStep s " +
				"where n.is=s.converionMetaDataID and s.message is not null";
		Criterion c = null;
		if(filter != null){
			c = Restrictions.like("m.filename", "%" + filter + "%");
		}
		return getQuerySupport().getInt(qs, c);
	}

	public ConversionArtifact getArtifact(long cmID, ConversionArtifactType type, int partNumber) {
		String qs = "select a from OfficeArtifact a where a.conversionMetadataID=? and a.type=? and a.page=?";
		@SuppressWarnings("unchecked")
		List<OfficeArtifact> list = getHibernateTemplate().find(qs, new Object[]{cmID, type, partNumber});
		if(list.size() == 1){
			return list.iterator().next();
		}else if(list.isEmpty()){
			return null;
		}
		throw new NonUniqueResultException(list.size());
	}

	public int getArtifactCount(long cmID, ConversionArtifactType type) {
		String qs = "select count(*) from OfficeArtifact a where a.conversionMetadataID=? and a.type=?";
		return ((Number) getHibernateTemplate().iterate(qs, new Object[]{cmID, type}).next()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<ConversionArtifact> findArtifacts(long cmID) {
		String qs = "from OfficeArtifact a where a.conversionMetadataID=?";
		return getHibernateTemplate().find(qs, cmID);
	}

	public void removeAllByMetaDataID(long cmID) {
		removeErrorStepsByMetaDataID(cmID);
		String qs2 = "delete from OfficeArtifact where conversionMetadataID=?";
		String qs3 = "delete from OfficeMetaData where id=?";
		getQuerySupport().executeUpdate(qs2, cmID);
		getQuerySupport().executeUpdate(qs3, cmID);
	}

	public ConversionMetaData saveMetaData(ConversionMetaData meta) {
		Long id = cmIdGenerator.getNext();
//		if(meta instanceof OfficeMetaData){
		//must be OfficeMetaData
		((OfficeMetaData) meta).setId(id);
//		}else{
//			meta = new OfficeMetaData(id,
//					meta.getConvertableObjectType(), meta.getConvertableObjectId(),
//					meta.getFilename(), meta.getLength(), meta.getNumberOfPages(),
//					meta.getRevisionNumber(), meta.getMetadata(),
//					meta.getCreationDate(), meta.getModificationDate());
//		}
		getHibernateTemplate().save(meta);
		return meta;
	}

	public ConversionMetaData updateMetaData(ConversionMetaData meta) {
		//must be OfficeMetaData
		getHibernateTemplate().update((OfficeMetaData)meta);
		return meta;
	}

	public ConversionArtifact saveArtifact(ConversionArtifact ca) {
		Long id = caIdGenerator.getNext();
		//must be OfficeArtifact
		((OfficeArtifact) ca).setId(id);
		getHibernateTemplate().save(ca);
		return ca;
	}

	public OfficeConversionErrorStep saveErrorStep(OfficeConversionErrorStep conversionErrorStep) {
		Long id = stepIdGenerator.getNext();
		OfficeErrorStep step = (OfficeErrorStep) conversionErrorStep;
		step.setId(id);
		if(step.getMessage() != null && step.getMessage().length() > 2000){
			step.setMessage(step.getMessage().substring(0, 2000));
		}
		getHibernateTemplate().save(conversionErrorStep);
		return conversionErrorStep;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeConversionErrorStep> findErrorStepsByMetaDataID(long cmID) {
		String qs = "from OfficeErrorStep where conversionMetaDataID=?";
		return getHibernateTemplate().find(qs, cmID);
	}

	public void removeErrorStepsByMetaDataID(long cmID) {
		String qs = "delete from OfficeErrorStep where conversionMetaDataID=?";
		getQuerySupport().executeUpdate(qs, cmID);
	}

	@SuppressWarnings("unchecked")
	public List<Long> findErrorMetadataIDs(ResultFilter filter) {
		String qs = "select distinct m.id from OfficeMetaData m, OfficeErrorStep s where s.conversionMetaDataID=m.id"
				+ " and s.message is not null";
		if(filter == null){
			qs += " order by m.creationDate desc";
			return getHibernateTemplate().find(qs);
		}else{
			if(filter.getOrder() == null){
				filter.setOrder(Order.desc("m.creationDate"));
			}
			return getQuerySupport().find(qs, filter);
		}
	}
}
