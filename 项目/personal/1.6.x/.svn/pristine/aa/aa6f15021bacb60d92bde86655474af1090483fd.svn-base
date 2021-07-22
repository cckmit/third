package org.opoo.apps.conversion.v2.doc.listener;

import java.io.File;
import java.io.IOException;

import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.v2.ConversionEvent;
import org.opoo.apps.conversion.v2.ConversionEvent.Context;
import org.opoo.apps.conversion.v2.doc.DocRequestEvent.Request;
import org.opoo.apps.conversion.v2.doc.event.PdfRequestEvent;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventListener;
import org.springframework.beans.factory.annotation.Required;

public class TestPdfRequestEventListener implements EventListener<PdfRequestEvent> {

	private EventDispatcher dispatcher;
	
	@Required
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void handle(PdfRequestEvent event) {
		File file = event.getOriginalFile();
		System.out.println(this + " 原始文件是：" + file);
		Request request = event.getSource();
		try {
			File pdf = ConversionUtils.getTestFile("test.pdf");
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), pdf);
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		} catch (IOException e) {
			Context context = new ConversionEvent.Context(request.getRevision(), request.getType(), request.getPage(), e.getMessage());
			ConversionEvent event2 = new ConversionEvent(ConversionEvent.Type.FileGenerated, context);
			dispatcher.dispatchEvent(event2);
		}
	}
}
