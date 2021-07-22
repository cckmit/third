package org.opoo.apps.conversion.v2.doc.listener;

import java.io.File;
import java.io.IOException;

import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.v2.ConversionEvent;
import org.opoo.apps.conversion.v2.ConversionEvent.Context;
import org.opoo.apps.conversion.v2.doc.DocRequestEvent.Request;
import org.opoo.apps.conversion.v2.doc.event.ThumbnailRequestEvent;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventListener;
import org.springframework.beans.factory.annotation.Required;

public class TestThumbnailRequestEventListener implements EventListener<ThumbnailRequestEvent> {

	private EventDispatcher dispatcher;
	
	@Required
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void handle(ThumbnailRequestEvent event) {
		File file = event.getPdfFile();
		System.out.println(this + " ԭʼ�ļ��ǣ�" + file);
		Request request = event.getSource();
		try {
			File png = ConversionUtils.getTestFile("test.png");
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), png);
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		} catch (IOException e) {
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), e.getMessage());
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		}
	}
}
