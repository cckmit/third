package cn.redflagsoft.base.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.service.InfoStatHandler;

public class ObjectPropertyInfoStatHandler implements InfoStatHandler {
	private static final Log log = LogFactory.getLog(ObjectPropertyInfoStatHandler.class);
	
	public boolean supports(RFSObject o, InfoConfig config) {
		return (InfoConfig.TYPE_OBJECT_PROPERTY == config.getType());
	}
	public byte getStatStatus(RFSObject o, InfoConfig config) {
		String property = config.getValue();
		if(log.isDebugEnabled()){
			log.debug(String.format("查询对象（%s）的属性 %s", o, property));
		}
		try {
			Object object = PropertyUtils.getProperty(o, property);
			return object != null ? InfoDetail.STATUS_COMPLETE : InfoDetail.STATUS_INCOMPLETE;
		} catch (RuntimeException e) {
			log.error(String.format("获取对象(%s)的属性(%s)错误：", o, property), e);
			throw e;
		} catch(Exception e){
			log.error(String.format("获取对象(%s)的属性(%s)错误：", o, property), e);
			throw new RuntimeException(e);
		}
	}
}
