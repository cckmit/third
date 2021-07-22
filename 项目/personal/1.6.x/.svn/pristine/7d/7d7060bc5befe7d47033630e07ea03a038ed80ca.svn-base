package org.opoo.apps.license.loader;

import java.util.concurrent.atomic.AtomicLong;

import org.opoo.apps.license.AppsLicenseLog;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseProvider;

public abstract class AbstractLicenseLoader implements LicenseLoader {
	private AtomicLong lastCheck = new AtomicLong(-1L);
	private static final int MAX_INTERVAL = 10 * 60 * 1000;
	
	public final void check(LicenseProvider provider, License license, CheckCallback callback) {
		int interval = getCheckInterval();
		if(interval > MAX_INTERVAL){
			interval = MAX_INTERVAL;
		}
		//AppsLicenseLog.LOG.debug("checking now");
		long now = System.currentTimeMillis();
		if(lastCheck.get() <= 0){
			lastCheck.set(System.currentTimeMillis());
		}else if(now - lastCheck.get() > interval){
			lastCheck.set(System.currentTimeMillis());
			AppsLicenseLog.LOG.debug("Checking license");
			checkInternal(provider, license, callback);
		}else{
			AppsLicenseLog.LOG.debug("Skip checking.");
		}
	}
	protected abstract void checkInternal(LicenseProvider provider, License license, CheckCallback callback);
	
	protected abstract int getCheckInterval();
	
	public void stopListen(){}
	
	public void startListen(LicenseProvider provider){}
}
