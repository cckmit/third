package org.opoo.apps.conversion.v2.doc.listener;

import java.io.File;
import java.io.IOException;

import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.v2.ConversionEvent;
import org.opoo.apps.conversion.v2.ConversionEvent.Context;
import org.opoo.apps.conversion.v2.doc.DocRequestEvent.Request;
import org.opoo.apps.conversion.v2.doc.event.PreviewRequestEvent;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventListener;
import org.springframework.beans.factory.annotation.Required;

public class TestPreviewRequestEventListener implements
		EventListener<PreviewRequestEvent> {
	private EventDispatcher dispatcher;
	
	@Required
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public void handle(PreviewRequestEvent event) {
		File file = event.getPdfFile();
		System.out.println(this + " 原始文件是：" + file);
		Request request = event.getSource();
		try {
			File swf = ConversionUtils.getTestFile("test.swf");
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), swf);
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		} catch (IOException e) {
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), e.getMessage());
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		}
	}

}
