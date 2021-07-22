package org.opoo.apps.dv.office.converter;

import java.io.File;
import java.util.List;

import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.TestUtils;
import org.opoo.apps.dv.office.converter.OfficeConverter;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.apps.event.v2.EventDispatcher;


public class MockOfficeConverter extends OfficeConverter {
	private EventDispatcher dispatcher;
	private String file;
	private String contentType;
	private String artifactType;
	private String eventType;
	
	public void setFile(String file) {
		this.file = file;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	@Override
	protected void convert(OfficeConversionMetaData cm,
			InputArtifact inputFile, List<Integer> partNumbers) {
		OfficeConversionMetaData m  = (OfficeConversionMetaData) cm;
		for(Integer page: partNumbers){
			File f = TestUtils.getTestFile(file);
			OfficeArtifact ca = new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.valueOf(artifactType), page);
			ca.setContentType(contentType);
			ca.setFilename(f.getName());
			ca.setLength(f.length());
			ca.setPage(page);
			OfficeConversionEvent.ConversionEventPayload p = new OfficeConversionEvent.ConversionEventPayload(m, ca, f);
			OfficeConversionEvent event = new OfficeConversionEvent(OfficeConversionEvent.Type.valueOf(eventType), p);
			
			System.out.println("转换文件：" + m.getFilename() + " " + contentType + " 第 " + page + "页");
			dispatcher.dispatchEvent(event);
		}
		
	}
}
