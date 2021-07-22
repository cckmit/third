package com.beitie;

import com.beitie.bean.User;
import com.beitie.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserTest {
    SqlSessionFactory sqlMapper;
    {
        //定义读取文件名
        String resources = "mybatis/mybatis-config.xml";
        //创建流
        Reader reader=null;
        try {
            //读取mybatis-config.xml文件到reader对象中
            reader= Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化mybatis,创建SqlSessionFactory类的实例
        sqlMapper=new SqlSessionFactoryBuilder().build(reader);
    }
    @Test
    public void userFindByIdTest(){
        //创建session实例
        SqlSession session=sqlMapper.openSession();
        //传入参数查询，返回结果
        User user=session.selectOne("selectUserById",1);
        //输出结果
        System.out.println(user.getUname());
        //关闭session
        session.close();

    }
    @Test
    public void selectUserByIdTest(){
        SqlSession session=sqlMapper.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user =new User();
        user=mapper.selectUserById(1);
    }

    @Test
    public void selectAllUserTest(){ 
        SqlSession session=sqlMapper.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User>  list=mapper.selectAllUser();
        for (User user :list){
            System.out.println(user);
        }
        session.close();
    }

    @Test
    public void addUserTest(){
        SqlSession session=sqlMapper.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user=new User();
        user.setUname("李嘉诚sb");
        user.setUage(19);
        mapper.addUser(user);
        System.out.println(user.getUid());
        session.commit();
        session.close();
    }
    @Test
    public void updateUserTest(){
        SqlSession session=sqlMapper.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user=new User();
        user.setUname("李四2");
        user.setUage(28);
        user.setUid(5);
        mapper.updateUser(user);
        session.commit();
        session.close();
    }
    @Test
    public void deleteUserTest(){
        SqlSession session=sqlMapper.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user=new User();
        user.setUname("王五");
        user.setUage(19);
        user.setUid(6);
        mapper.deleteUser(6);
        session.commit();
        session.close();
    }
}