package org.opoo.apps.id.sequence.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.id.sequence.SequenceManager;


/**
 * ϵͳ�����¼�����������Ҫ��������<code>SequenceProvider.className</code>���޸�
 * �������������Է����˱仯����Ҫ���³�ʼ�� SequenceManager �Ա�����Ӧ����Щ���ġ�
 *
 */
public class SequenceProviderChangeEventListener implements EventListener<PropertyEvent> {
	static final Log log = LogFactory.getLog(SequenceProviderChangeEventListener.class);
	
	/**
	 * �����¼���
	 */
	public void handle(PropertyEvent event) {
		if(SequenceManager.SEQUENCE_PROVIDER_CLASSNAME.equalsIgnoreCase(event.getName())
				&& SequenceManager.isInitialized()){
			try {
				Thread.sleep(200L);
			} catch (InterruptedException e) {
				//ignore exception
			}
			log.info("���³�ʼ�� SequenceManager��" + event.getValue());
			SequenceManager.initialize();
		}
	}
}
