package org.opoo.apps.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ģ�����������ġ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ConfigurationContext
{
    private final List<ConfiguratorResult> results = new ArrayList<ConfiguratorResult>();
    private final ModuleMetaData moduleMetaData;
    private final Map<String,String> attributes = new HashMap<String,String>();
    
    public ConfigurationContext(ModuleMetaData moduleMetaData)
    {
        this.moduleMetaData = moduleMetaData;
    }

    /**
     * ģ��ͷ��Ϣ
     * @return ģ��ͷ
     */
    public ModuleMetaData getModuleMetaData()
    {
        return moduleMetaData;
    }

    /**
     * ���ý�� 
     * @return
     */
    public List<ConfiguratorResult> getResults()
    {
        return results;
    }

    /**
     * ���һ�����ý����
     * 
     * @param result
     */
    public void addResult(ConfiguratorResult result)
    {
        results.add(result);
    }
    /**
     * �����������ԡ�
     * 
     * @return
     */
    public Map<String,String> getAttributes()
    {
        return attributes;
    }


}
