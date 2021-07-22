package org.opoo.apps.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 模块配置上下文。
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
     * 模块头信息
     * @return 模块头
     */
    public ModuleMetaData getModuleMetaData()
    {
        return moduleMetaData;
    }

    /**
     * 配置结果 
     * @return
     */
    public List<ConfiguratorResult> getResults()
    {
        return results;
    }

    /**
     * 添加一个配置结果。
     * 
     * @param result
     */
    public void addResult(ConfiguratorResult result)
    {
        results.add(result);
    }
    /**
     * 返回配置属性。
     * 
     * @return
     */
    public Map<String,String> getAttributes()
    {
        return attributes;
    }


}
