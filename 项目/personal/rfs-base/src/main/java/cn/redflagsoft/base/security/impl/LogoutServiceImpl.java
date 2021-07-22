package cn.redflagsoft.base.security.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.security.LogoutService;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;

public class LogoutServiceImpl implements LogoutService {
	private static final Log log = LogFactory.getLog(LogoutServiceImpl.class);
	private AuditManager auditManager;
	
	public AuditManager getAuditManager() {
		return auditManager;
	}

	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}

	public void logout() {
//		UserClerk uc = UserClerkHolder.getUserClerk();
//		String detail = String.format("用户‘%s(%s)’退出系统。", uc.getClerk().getName(), uc.getUser().getUsername());
//		try {
//			InetAddress localHost = InetAddress.getLocalHost();
//			String desc = String.format("用户‘%s(%s)’退出了系统，登录 IP为‘%s’，退出时间为:‘%s’。",uc.getClerk().getName(), uc.getUser().getUsername(), localHost,new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//			auditManager.audit(uc,localHost, detail,desc,new DomainIdentifier(), "logout");
//		} catch (UnknownHostException e) {
//			log.error(e.getMessage());
//		}
		
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context != null ? context.getAuthentication() : null;
		logout(null, null, auth);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.ui.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.Authentication)
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		UserClerk uc = UserClerkHolder.getNullableUserClerk();
		if(uc == null){
			log.debug("没有找到当前登录用户，无法记录注销记录");
			return;
		}
		
		String addr = (request != null) ? request.getRemoteAddr() : null;
		InetAddress host = null;
		try {
			if(addr != null){
				host = InetAddress.getByName(addr);
			}else{
				host = InetAddress.getLocalHost();
			}
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		}
		
		String detail = String.format("%s(%s)退出系统。", uc.getClerk().getName(), uc.getUser().getUsername());
		String desc = String.format("IP:%s。", host != null ? host.getHostAddress() : "undefined");
		auditManager.audit(uc, host, detail, desc, new DomainIdentifier(uc.getClerk().getId() + "", "0"), "logout");
	}
}
