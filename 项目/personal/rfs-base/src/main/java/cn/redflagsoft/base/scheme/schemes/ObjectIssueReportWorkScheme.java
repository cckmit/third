package cn.redflagsoft.base.scheme.schemes;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.event2.AttachmentEvent;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.service.ObjectsService;
import cn.redflagsoft.base.vo.DatumVOList;

public class ObjectIssueReportWorkScheme extends AbstractWorkScheme {
	private static final Log log = LogFactory.getLog(ObjectIssueReportWorkScheme.class);
	public static final int OBJECTS_TYPE_���ⱨ���븽��֮���ϵ = 20002;
	
	private Issue issue;

	private SchemeManager schemeManager;

	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Object doObjectIssue() throws Exception {
		RFSObject object = getObject();
		Scheme scheme = schemeManager.getScheme("issueWorkScheme");
//		if (scheme instanceof ParametersAware) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("rfsObject.key", String.valueOf(object.getId()));
//			map.put("rfsObject.name", object.getName());
//			map.put("rfsObject.objectType", String.valueOf(object
//					.getObjectType()));
//
//			((ParametersAware) scheme).setParameters(map);
//		}
//		SchemeInvoker.invoke(scheme, "create");
		
		IssueWorkScheme iws = (IssueWorkScheme)scheme;
		iws.setIssue(issue);
		iws.setRfsObject(object);
		iws.doCreate();
		
		issue = (Issue) iws.getObject();
		//�������Ϻ͸���
		saveIssueAttachments(issue, getFileIds());
		//savei
		saveProgressDatum(issue);
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		
		return "ҵ����ɹ���";
	}
	
	/**
	 * ����
	 * @return
	 * @throws Exception
	 */
	public Object doReport() throws Exception{
		RFSObject object = getObject();
		Scheme scheme = schemeManager.getScheme("issueWorkScheme");
		
		IssueWorkScheme iws = (IssueWorkScheme)scheme;
		processIssueBeforeSave(issue);
		iws.setIssue(issue);
		iws.setRfsObject(object);
		iws.doCreate();
		
		issue = (Issue) iws.getObject();
		//�������Ϻ͸���
		saveIssueAttachments(issue, getFileIds());
		//savei
		saveProgressDatum(issue);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "ҵ����ɹ���";
	}

	/**
	 * �����������ʵ����չ��
	 * 
	 * @param issue2
	 */
	protected void processIssueBeforeSave(Issue issue2) {
		
	}
	
	/**
	 * @param issue
	 */
	private void saveProgressDatum(Issue issue) {
		//��������
		DatumVOList datumList = getDatumVOList();
		if(datumList != null && datumList.size() > 0){
			if(log.isDebugEnabled()){
				log.debug("������ȱ�������ϣ�" + datumList);
			}
			/*List<Datum> ld =*/ 
			//datumService.processDatum((byte)1, task, work, DATUM_UPLOAD_PROCESS_TYPE, rfsobj, ws.getMatterVO(), datumList, clerk);
			log.warn("���ȱ��������δ����");
		}
	}
	
	/**
	 * 
	 * @param prss
	 * @param attachmentIds
	 */
	public static void saveIssueAttachments(Issue issue, List<Long> attachmentIds) {
		if(attachmentIds != null){
			ObjectsService objectsService = Application.getContext().get("objectsService", ObjectsService.class);
			Assert.notNull(objectsService, "objectsService is required.");
			for(Long attachmentId: attachmentIds){
				//��ʱ���жϸ����Ƿ����
				/**
				Attachment attachment = attachmentManager.getAttachment(attachmentId);
				if(attachment == null){
					throw new IllegalArgumentException("�Ҳ����ϴ��ĸ�����" + attachmentId);
				}
				*/
				
				objectsService.saveOrUpdateObjects(issue.getId(), attachmentId, OBJECTS_TYPE_���ⱨ���븽��֮���ϵ, "���ⱨ���븽��֮���ϵ");
			}
		}
	}
	
	
	
	/**
	 * ����ɾ��ʱ��ɾ����ϵ���е���ؼ�¼��
	 *
	 */
	public static class IssueAttachmentEventListener implements EventListener<AttachmentEvent>{
		private ObjectsDao objectsDao;
		
		/**
		 * @param objectsDao the objectsDao to set
		 */
		public void setObjectsDao(ObjectsDao objectsDao) {
			this.objectsDao = objectsDao;
		}

		/* (non-Javadoc)
		 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
		 */
		public void handle(AttachmentEvent event) {
			if(event.getType() == AttachmentEvent.Type.DELETED){
				Long attachmentId = event.getSource().getId();
				Logic logic = Restrictions.logic(Restrictions.eq("type", OBJECTS_TYPE_���ⱨ���븽��֮���ϵ))
					.and(Restrictions.eq("sndObject", attachmentId));
				objectsDao.remove(logic);
			}
		}
	}
}
