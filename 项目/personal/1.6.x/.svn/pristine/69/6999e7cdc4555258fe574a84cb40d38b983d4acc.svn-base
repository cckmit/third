package org.opoo.apps.dv.connector;

import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * Manages a pool of service host configurations, and can test service node before handing out an address.
 *
 * @since Jive SBS Double Mountain
 */
public class ServiceHostObjectFactory extends BasePoolableObjectFactory {
    private List<String> params;

    public void setConnectionParams(List<String> params){
        this.params = params;
    }

    @Override
    public Object makeObject() throws Exception {

        ServiceHost host = new ServiceHost(randomItem(params));
        return host;
    }

     private String randomItem(List<String> items) {
        int idx = (int) ((Math.random() * 100 * items.size())/100);
        if(idx >= items.size()){
            idx = items.size() -1;
        }
        return items.get(idx);
     }

}
