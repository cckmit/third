import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import pojo.FetchBizInfoRequest;
import utils.CommonUtils;
import utils.EncryptTools;
import utils.RSAUtil;
import utils.XmlUtil;


public class InvokeService {
    public static void main(String[] args) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.1.115:8080/spi/fetchListedBizEndInfo?wsdl");
        CommonUtils.buildClient(client, RSAUtil.getSign(packageObject()),new SignInterceptor());
        Object[] data = client.invoke("fetchListedBizEndInfo", new Object[]{EncryptTools.SM4Encode(XmlUtil.convertToXml(packageObject()))});
        System.out.println("返回数据为:\n"+EncryptTools.SM4Decode((String)data[0]));
    }

    private static Object packageObject(){
        FetchBizInfoRequest request = new FetchBizInfoRequest();
        request.setSxlx("1001");
        request.setSjcs("徐定成");
        return request;
    }

}
