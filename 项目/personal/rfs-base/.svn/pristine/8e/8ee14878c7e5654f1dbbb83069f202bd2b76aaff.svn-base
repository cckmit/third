package cn.redflagsoft.base.scheme.schemes.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;

public class ObjectBatchAdminScheme extends AbstractScheme {
	private static final Log log = LogFactory.getLog(ObjectBatchAdminScheme.class);
	
	private List<Long> ids;
	private SchemeManager schemeManager;
	private String adminWorkSchemeName;
	private String schemeDesc;
	
	public String getSchemeDesc() {
		return schemeDesc;
	}

	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}

	
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	public String getAdminWorkSchemeName() {
		return adminWorkSchemeName;
	}

	public void setAdminWorkSchemeName(String adminWorkSchemeName) {
		this.adminWorkSchemeName = adminWorkSchemeName;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	/**
	 * 批量处理
	 * @return
	 */
	public Object doBatchHandle(){
		Assert.notNull(ids, "需要" + schemeDesc + "的集合不能为空！");
		for (Long id : ids) {
			Scheme scheme = schemeManager.getScheme(adminWorkSchemeName);	
			Map<String,String> params = new HashMap<String,String>();
			params.put("objectId",id.toString());
			((AbstractWorkScheme)scheme).setParameters(params);
		    try {
				SchemeInvoker.invoke(scheme,null);
			} catch (Exception e) {
				//log.info("对象作废处理失败...");
				log.error("对象" + schemeDesc + "失败：" + id, e);
			}
		}
		String msg = String.format("批量处理对象cnt=%s个", ids.size());
		log.info(msg);
		return "业务处理成功!";
	}
}
