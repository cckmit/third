import com.beitie.pojo.UserInfo;
import com.beitie.service.UserInfoService;
import com.beitie.service.impl.UseInfoServiceImpl;
import org.junit.Test;

public class UserInfoServiceTest {
    @Test
    public void testFindUserInfoByid(){
        UserInfoService userInfoService=new UseInfoServiceImpl();
        UserInfo userInfo=userInfoService.findUserInfoById(1);
        System.out.println(userInfo);
    }
}
