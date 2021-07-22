import com.beitie.service.GlossaryService;
import com.beitie.util.CodeMapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/springApplicationContext-*.xml")
public class GlossaryTest {
    @Autowired
    private GlossaryService glossaryService;
    @Test
    public void testGetNameByCategoryAndCode(){
        String name=glossaryService.getNameByCodeAndCategory(101,(short)1);
        System.out.println(name);
    }
    @Test
    public void testDirectInvokeCodeMapUtils(){
        String name=CodeMapUtils.getNameByCategoryAndCode(101,(short)1);
//        String name=glossaryService.getNameByCodeAndCategory(101,(short)1);
        System.out.println(name);
    }
}
