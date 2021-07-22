package cn.redflagsoft.base.lifecycle.spring;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;

import cn.redflagsoft.base.aop.aspect.AspectJ;
import cn.redflagsoft.base.audit.proxy.AuditManagerProxy;

public class BaseContextLoader extends AppsContextLoader {
	private static final Log log = LogFactory.getLog(BaseContextLoader.class);
	/* (non-Javadoc)
	 * @see org.opoo.apps.lifecycle.spring.AppsContextLoader#getRuntimeContextFiles()
	 */
	@Override
	public List<String> getRuntimeContextFiles() {
		List<String> list = super.getRuntimeContextFiles();
		if(!isInSetup()){
			//�滻
			if(list.contains(RUNTIME_CONTEXT)){
				list.remove(RUNTIME_CONTEXT);
			}
			list.add(0, "classpath:spring-application-override.xml");
			
			list.add("classpath:spring-base-application.xml");
			if(AspectJ.isCompileTimeWoven()){
				log.debug("Դ�����ڱ������Ѿ�֯��������");
				list.add("classpath:spring-base-aop-ctw.xml");
			}else{
				log.info("���� Aop ��̬����");
				list.add("classpath:spring-base-aop.xml");
			}
			if(AuditManagerProxy.isAuditEnabledInProperties()){
				log.info("���� �û��������");
			}else{
				log.info("�����û��������");
			}
			//�����Ƿ����ö���������
			list.add("classpath:spring-base-audit.xml");
		}
		return list;
	}
	
	@Override
	public List<String> getSetupContextFiles() {
		List<String> list = super.getSetupContextFiles();
		if(isInSetup()){
			list.add("classpath:spring-base-setup.xml");
		}
		return list;
	}
}
