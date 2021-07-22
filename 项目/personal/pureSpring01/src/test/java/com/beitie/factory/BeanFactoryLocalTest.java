package com.beitie.factory;

import com.beitie.bean.Music;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-simpleConfig.xml")
public class BeanFactoryLocalTest {
    @Test
    public void getApplicationContextTest(){
        ApplicationContext applicationContextLocal = BeanFactoryLocal.applicationContextLocal;
        Music music1=(Music)applicationContextLocal.getBean("music1");
        System.out.println("music1--歌曲名："+music1.getName()+"--作者是"+music1.getAuthor());
        Music music2=(Music)applicationContextLocal.getBean("music2");
        System.out.println("music2--歌曲名："+music2.getName()+"--作者是"+music2.getAuthor());
        Music music4=(Music)applicationContextLocal.getBean("music4");
        System.out.println("music4--歌曲名："+music4.getName()+"--作者是"+music4.getAuthor());
        Music music5=(Music)applicationContextLocal.getBean("music5");
        System.out.println("music5--歌曲名："+music5.getName()+"--作者是"+music5.getAuthor());
    }
}
