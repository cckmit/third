package cn.redflagsoft.base.listener;

import org.opoo.apps.event.v2.EventListener;

import cn.redflagsoft.base.event2.AttachmentEvent;
import cn.redflagsoft.base.service.SmsgService;

public class AttachmentEventListener implements EventListener<AttachmentEvent>{
	private SmsgService smsgService;
	
	/**
	 * @param smsgService the smsgService to set
	 */
	public void setSmsgService(SmsgService smsgService) {
		this.smsgService = smsgService;
	}

	public void handle(AttachmentEvent event) {
		if(event.getType() == AttachmentEvent.Type.DELETED){
			handleSmsgAttachmentDeleted(event);
		}
	}
	
	public void handleSmsgAttachmentDeleted(AttachmentEvent event){
		if(event.getSource() != null){
			smsgService.removeSmsgAttachmentsByFileId(event.getSource().getId());
		}
	}
}
