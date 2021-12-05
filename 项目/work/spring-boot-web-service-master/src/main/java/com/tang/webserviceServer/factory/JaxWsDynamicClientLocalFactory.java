package com.tang.webserviceServer.factory;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.io.File;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/12/1
 */
public class JaxWsDynamicClientLocalFactory extends JaxWsDynamicClientFactory {
    protected JaxWsDynamicClientLocalFactory(Bus bus) {
        super(bus);
    }
    public static JaxWsDynamicClientLocalFactory newInstance(Bus b) {
        return new JaxWsDynamicClientLocalFactory(b);
    }

    public static JaxWsDynamicClientLocalFactory newInstance() {
        Bus bus = BusFactory.getThreadDefaultBus();
        return new JaxWsDynamicClientLocalFactory(bus);
    }
    /**
     * 覆写父类的该方法<br/>
     * 注：解决此（错误：编码GBK的不可映射字符）问题
     *
     * @return
     */
    protected boolean compileJavaSrc(String classPath, List<File> srcList, String dest) {
        org.apache.cxf.common.util.Compiler javaCompiler
                = new org.apache.cxf.common.util.Compiler();

        // 设置编译编码格式（此处为新增代码）
        javaCompiler.setEncoding("UTF-8");

        javaCompiler.setClassPath(classPath);
        javaCompiler.setOutputDir(dest);
        javaCompiler.setTarget("1.6");

        return javaCompiler.compileFiles(srcList);
    }
}
