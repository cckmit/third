package cn.redflagsoft.base.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.security.UserClerk;




/**
 * 定时检查在线用启（心跳机制）
 * @deprecated 使用presencefilter
 */
public class CheckOnlineFilter implements Filter, InitializingBean{
    private  UserManager userManager;
    private ClerkDao clerkDao;
    private static  Map onlineMap;
    private long timeOutMax=180;
    private boolean isRuning=true;
    private long period=60;
  
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void destroy() {
		isRuning=false;
		
	}


	public ClerkDao getClerkDao() {
		return clerkDao;
	}

	public void setClerkDao(ClerkDao clerkDao) {
		this.clerkDao = clerkDao;
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		User u=UserHolder.getNullableUser();
		if(u!=null){
			//System.out.println("==================u:"+u.getUsername()+" time:"+new Date());
			if(onlineMap.get(u.getUsername())==null){
				LiveUser lu=userManager.loadLiveUserByUsername(u.getUsername());
				lu.setOnlineStatus(OnlineStatus.ONLINE);
				userManager.updateLiveUser(lu);	
			}
			onlineMap.put(u.getUsername(), new Date().getTime());
		}
		chain.doFilter(request, response);    	
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	
	}


	public void checkOnline() {
		while(isRuning){
			Date now=new Date();
			if(onlineMap!=null){
			Map checkMap=new HashMap(onlineMap);
			Object[] key=(Object[]) checkMap.keySet().toArray();
			for(int i=0;i<key.length;i++){
				Long lastActiveTime=(Long) checkMap.get((String)key[i]);
				if(now.getTime()-lastActiveTime>timeOutMax*1000){
					removeUser((String)key[i]);
				}
			}
			}
			try {
				Thread.sleep(period*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeUser(String username){
		//System.out.println("大于"+timeOutMax+"秒！");
		//System.out.println("username:"+username);
		LiveUser lu=userManager.loadLiveUserByUsername(username);
		lu.setOnlineStatus(OnlineStatus.IDLE);
		userManager.updateLiveUser(lu);
		//System.out.println("remove:"+username);
		onlineMap.remove(username);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(userManager);
		init();
	}

	/**
	 * 
	 */
	private void init() {
		onlineMap=new ConcurrentHashMap();
		new Thread(){
				public void run(){
					//OffLineAllUsers();
					checkOnline();
				}
			}.start();
	}
   private void OffLineAllUsers(){
	   List<UserClerk> lc=clerkDao.findUserClerks(ResultFilter.createEmptyResultFilter());
	   for(UserClerk c:lc){
		   if(c.getUser()!=null){
			   if(c.getUser().getOnlineStatus()!=OnlineStatus.OFFLINE){//非在线用户设成非在线。
				   String username=c.getUser().getUsername();
				   LiveUser lu=userManager.loadLiveUserByUsername(username);
				   lu.setOnlineStatus(OnlineStatus.OFFLINE);
				   userManager.updateLiveUser(lu);
			   }
		   }
		   
	   }
   }

}
