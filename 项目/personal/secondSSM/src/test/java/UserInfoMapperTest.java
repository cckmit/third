import com.beitie.dao.UserInfoMapper;
import com.beitie.pojo.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class UserInfoMapperTest {
    SqlSessionFactory sqlMapper;
    @Before
    public void init(){
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
    public void testFindUserInfoById(){
        SqlSession sqlSession=sqlMapper.openSession();
        UserInfoMapper userInfoMapper=sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo=userInfoMapper.findUserInfoById(1);
        System.out.println(userInfo);
    }
}
