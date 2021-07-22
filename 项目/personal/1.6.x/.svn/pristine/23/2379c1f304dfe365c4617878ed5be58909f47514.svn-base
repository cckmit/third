package org.opoo.apps.web.struts2;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;
import org.opoo.apps.security.User.OnlineStatus;

public class UserOnlineStatusConverter extends StrutsTypeConverter {
	private static final Log log = LogFactory.getLog(UserOnlineStatusConverter.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values != null && values.length > 0 && StringUtils.isNotBlank(values[0])){
			if(log.isDebugEnabled()){
				log.debug("转化 User.OnlineStatus 参数：" + values[0]);
			}
			return OnlineStatus.valueOf(values[0]);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		return ((OnlineStatus)o).name();
	}
	
	
	public static void main(String[] args){
		OnlineStatus os = OnlineStatus.valueOf("OFFLINE");
		System.out.println(os);
		System.out.println(os.name());
	}

}
