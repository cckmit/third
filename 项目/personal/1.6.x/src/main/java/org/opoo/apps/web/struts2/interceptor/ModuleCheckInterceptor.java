package org.opoo.apps.web.struts2.interceptor;


import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.license.annotation.ProductModule;
import org.opoo.apps.lifecycle.Application;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ModuleCheckInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -3172809440522601095L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        if(action.getClass().isAnnotationPresent(ProductModule.class)){
            ProductModule rp = (ProductModule)action.getClass().getAnnotation(ProductModule.class);
            AppsLicenseManager licenseManager = Application.getContext().getLicenseManager();
            boolean isAvailable = licenseManager.isModuleAvailable(rp.module());
            if(!isAvailable){
            	System.err.println("Module '" + rp.module() + "' is not licensed.");
                return "none";
            }
        }
        return invocation.invoke();
	}

}
