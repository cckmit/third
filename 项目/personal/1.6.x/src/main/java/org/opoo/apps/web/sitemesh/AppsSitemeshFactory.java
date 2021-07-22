package org.opoo.apps.web.sitemesh;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.factory.DefaultFactory;
import com.opensymphony.module.sitemesh.mapper.PathMapper;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsSitemeshFactory extends DefaultFactory {

	public AppsSitemeshFactory(Config config) {
		super(config);
	}
	
    public void addExcludeUrl(String path) {
        super.addExcludeUrl(path);
    }

    public PathMapper getExcludeUrls(){
        return this.excludeUrls;
    }
}
