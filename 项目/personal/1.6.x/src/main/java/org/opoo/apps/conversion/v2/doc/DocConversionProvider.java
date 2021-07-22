package org.opoo.apps.conversion.v2.doc;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.ConversionStorageService;
import org.opoo.apps.conversion.ConvertableObject;
import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.util.FileFormats;
import org.opoo.apps.conversion.v2.BaseConversionArtifactType;
import org.opoo.apps.conversion.v2.ConversionEvent;
import org.opoo.apps.conversion.v2.ConversionEvent.Context;
import org.opoo.apps.conversion.v2.ConversionManagerImplV2.ProcessingInfo;
import org.opoo.apps.conversion.v2.ConversionProvider;
import org.opoo.apps.conversion.v2.doc.event.PdfRequestEvent;
import org.opoo.apps.conversion.v2.doc.event.PreviewRequestEvent;
import org.opoo.apps.conversion.v2.doc.event.ThumbnailRequestEvent;
import org.opoo.apps.event.v2.EventDispatcher;
import org.springframework.beans.factory.annotation.Required;

public class DocConversionProvider implements ConversionProvider {
	private ConversionStorageService conversionStorageService;
	private EventDispatcher dispatcher;
	
	@Required
	public void setConversionStorageService(
			ConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}

	@Required
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getName() {
		return "doc";
	}

	public boolean isConvertable(Object object) {
		if (!isConversionEnabled()) {
            return false;
        }
        if (object instanceof ConvertableObject) {
            ConvertableObject co = (ConvertableObject) object;
            String fileName = co.getFilename();
            String contentType = co.getContentType();
            if(StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(contentType)){
            	return isConvertableContentType(contentType);
            }
        }
		return false;
	}
	
	public boolean isConvertableContentType(String contentType){
		return FileFormats.DocumentType.isOffice2003MimeType(contentType)
                || FileFormats.DocumentType.isOffice2007MimeType(contentType) 
                || FileFormats.DocumentType.isPDFMimeType(contentType);
	}
	
	public boolean isConversionEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public ProcessingInfo createProcessingInfo() {
		return new ProcessingInfo(this);
	}




	public void handleFileGenerated(ProcessingInfo info, ConversionRevision rev, ConversionArtifactType type, int page,
			File file) throws Exception {
		File temp = ConversionUtils.createTempFile(rev);
		FileUtils.copyFile(file, temp);
		
		System.out.println("调用handleFileGenerated： " + file + ", " + page + ", " + type);
		if(type != null){
			System.out.println(type.getClass());
		}
		
		if(BaseConversionArtifactType.File == type){
			info.addScheduledArtifact(rev.getRevisionId(), DocConversionArtifactType.Pdf, 0);
			//call pdf executor
			System.out.println(this + "." + Thread.currentThread() + " 调用PDF生成器");
			dispatcher.dispatchEvent(new PdfRequestEvent(rev, 0, temp));
		}else if(DocConversionArtifactType.Pdf == type){
			System.out.println(this + "." + Thread.currentThread() + " 生成的文件" + file);
			if(rev.getNumberOfPages() <= 0){
				rev.setNumberOfPages(FileFormats.getNumberOfPages(file));
				conversionStorageService.updateRevision(rev);
			}
			
			for(int i = 1 ; i <= rev.getNumberOfPages() ;i++){
				info.addScheduledArtifact(rev.getRevisionId(), DocConversionArtifactType.Preview, i);
				info.addScheduledArtifact(rev.getRevisionId(), DocConversionArtifactType.Thumbnail, i);
				//call executor to generate files
				System.out.println(this + "." + Thread.currentThread() + " 调用Preview生成器: " + i);
				dispatcher.dispatchEvent(new PreviewRequestEvent(rev, i, temp));
				
				System.out.println(this + "." + Thread.currentThread() + " 调用Thumbnail生成器: " + i);
				dispatcher.dispatchEvent(new ThumbnailRequestEvent(rev, i, temp));
			}
		}else if(type == DocConversionArtifactType.Preview 
				|| type == DocConversionArtifactType.Thumbnail){
			checkProcessingCompletion(info, rev, type, page);
		}
	}
	
	public void checkProcessingCompletion(ProcessingInfo info, ConversionRevision rev, ConversionArtifactType type, int page) {
		System.out.println(this + "." + Thread.currentThread() + " 检查结束");
		if(type == DocConversionArtifactType.Preview || type == DocConversionArtifactType.Thumbnail){
			if(info.getScheduledArtifacts().size() == 0){
				Context context = new ConversionEvent.Context(rev, type, page);
				ConversionEvent event = new ConversionEvent(ConversionEvent.Type.Completion, context);
				
				System.out.println("转换完成了。");
				dispatcher.dispatchEvent(event);
			}
		}
	}
}
