package org.opoo.apps.id.sequence.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.id.sequence.SequenceManager;


/**
 * 系统属性事件监听器，主要监听属性<code>SequenceProvider.className</code>的修改
 * 情况，如果该属性发生了变化，需要重新初始化 SequenceManager 以便立刻应用这些更改。
 *
 */
public class SequenceProviderChangeEventListener implements EventListener<PropertyEvent> {
	static final Log log = LogFactory.getLog(SequenceProviderChangeEventListener.class);
	
	/**
	 * 处理事件。
	 */
	public void handle(PropertyEvent event) {
		if(SequenceManager.SEQUENCE_PROVIDER_CLASSNAME.equalsIgnoreCase(event.getName())
				&& SequenceManager.isInitialized()){
			try {
				Thread.sleep(200L);
			} catch (InterruptedException e) {
				//ignore exception
			}
			log.info("重新初始化 SequenceManager：" + event.getValue());
			SequenceManager.initialize();
		}
	}
}
