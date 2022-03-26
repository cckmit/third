package jvm;

import com.beitie.bean.Student;
import org.junit.Test;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/31
 */
public class JvmClassLoader {
    @Test
    public void loaderTest(){
        try {
            //查看当前系统类路径中包含的路径条目
            System.out.println(System.getProperty("java.class.path"));
            //调用加载当前类的类加载器（这里即为系统类加载器）加载TestBean
            Class typeLoaded = Class.forName("com.beitie.bean.Student");
            Student student=(Student)typeLoaded.newInstance();
            student.outPrintln();
            //查看被加载的TestBean类型是被那个类加载器加载的
            System.out.println(typeLoaded.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
