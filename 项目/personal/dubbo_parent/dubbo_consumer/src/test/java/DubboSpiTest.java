import com.beitie.service.OrderService;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/5/24
 */
public class DubboSpiTest {
    @Test
    public  void spiTest(){
        ExtensionLoader<OrderService> loader =ExtensionLoader.getExtensionLoader(OrderService.class);
        OrderService orderService2=loader.getExtension("orderService2");
        OrderService orderService3=loader.getExtension("orderService3");
        orderService2.initData();
        orderService3.initData();
    }
}
