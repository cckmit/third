package org.opoo.apps.conversion.dao;

import java.util.List;

import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.model.ConversionArtifactImpl;
import org.opoo.apps.conversion.model.ConversionErrorStepImpl;
import org.opoo.apps.conversion.model.ConversionRevisionImpl;
import org.opoo.ndao.support.ResultFilter;

public interface ConversionDao {

	ConversionRevisionImpl getRevision(long revisionId);

	ConversionRevisionImpl saveRevision(ConversionRevisionImpl rev);

//	ConversionRevisionImpl getRevision(long objectId, int objectType);

	ConversionRevisionImpl updateRevision(ConversionRevisionImpl revImpl);

	ConversionArtifactImpl getArtifact(long revisionId, ConversionArtifactType type, int page);

	ConversionArtifactImpl saveArtifact(ConversionArtifactImpl a);

	int getArtifactCount(long revisionId, ConversionArtifactType type);

	int removeErrorStepsByRevisionId(long revisionId);

	List<ConversionErrorStepImpl> findErrorStepsByRevisionId(long revisionId);

	ConversionErrorStepImpl saveErrorStep(ConversionErrorStepImpl step);

	//int removeConversionErrorStepsByRevisionId(long revisionId);

	int removeArtifactsByRevisionId(long revisionId);

	int removeRevision(long revisionId);

	List<ConversionArtifactImpl> findArtifactsByRevisionId(long revisionId);

	int getRevisionCount();

	int getErrorRevisionCount();

	List<Long> findErrorRevisionIds(ResultFilter filter);

	ConversionRevision getRevision(int objectType, long objectId, int revisionNumber);

}
