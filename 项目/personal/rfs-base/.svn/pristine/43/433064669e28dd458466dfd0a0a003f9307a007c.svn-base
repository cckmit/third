package cn.redflagsoft.base.scheme.schemes.caution;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionCheck;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.CautionCheckService;
import cn.redflagsoft.base.service.CautionService;

public class CautionCheckScheme extends AbstractScheme {
	private static final Log log = LogFactory.getLog(CautionCheckScheme.class);
			
	private SchemeManager schemeManager;
	private CautionService cautionService;
	private CautionCheckService cautionCheckService;
	
	private List<Long> ids;
	private final String cautionCheck = "cautionCheck";
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}
	
	public CautionService getCautionService() {
		return cautionService;
	}

	public void setCautionService(CautionService cautionService) {
		this.cautionService = cautionService;
	}

	public CautionCheckService getCautionCheckService() {
		return cautionCheckService;
	}

	public void setCautionCheckService(CautionCheckService cautionCheckService) {
		this.cautionCheckService = cautionCheckService;
	}

	public Object doCheck(){
		Assert.notNull(ids, "要事实复核的集合不能为空！");
		// 获得当前登录系统用户信息
		Clerk clerk = UserClerkHolder.getClerk();
		
		for(Long id:ids){
			Caution caution = cautionService.getObject(id);
			
			if(caution.getGrade() > Risk.GRADE_WHITE){
				CautionCheck cautionC = cautionCheckService.getCautionCheckByCautionId(id);
				if(cautionC== null){
					Scheme scheme = schemeManager.getScheme(cautionCheck);
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("objectId",id.toString());
					params.put("cautionCheck.checkResult", CautionCheck.CHECK_RESULT_TRUE);
					params.put("cautionCheck.checkerId", clerk.getId()+"");
					params.put("cautionCheck.checkerName", clerk.getName());
					params.put("cautionCheck.checkTime", AppsGlobals.formatDate(new Date()));
					
					((AbstractWorkScheme)scheme).setParameters(params);

				    try {
						SchemeInvoker.invoke(scheme,null);
					} catch (Exception e) {
						log.info("事实复核办理失败...");
					}
				}
			}
			
		}
		return "业务处理成功！";
	}
}
