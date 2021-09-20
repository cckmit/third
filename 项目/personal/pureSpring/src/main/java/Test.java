import com.beitie.test.ConfigParse;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class Test {
    public static void main(String[] args) {
        System.out.println(ConfigParse.getString("name"));
    }
}
