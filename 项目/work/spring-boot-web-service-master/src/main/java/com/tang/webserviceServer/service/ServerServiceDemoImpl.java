package com.tang.webserviceServer.service;

import com.tang.webserviceServer.pojo.User;
import com.tang.webserviceServer.pojo.masterAccountStatus.Request;
import com.tang.webserviceServer.pojo.masterAccountStatus.Response;
import com.tang.webserviceServer.utils.EncryptTools;
import org.apache.cxf.interceptor.Fault;
import org.springframework.stereotype.Component;

import javax.jws.WebParam;
import javax.jws.WebService;

import java.security.GeneralSecurityException;

import static com.tang.webserviceServer.utils.RSAUtil.getSign;
import static com.tang.webserviceServer.utils.RSAUtil.validateSign;
import static com.tang.webserviceServer.utils.SignHolder.setSign;
import static com.tang.webserviceServer.utils.SignHolder.takeSign;
import static com.tang.webserviceServer.utils.XmlUtil.convertXmlStrToObject;


/**
 * WebService涉及到的有这些 "四解三类 ", 即四个注解，三个类
 * @WebMethod
 * @WebService
 * @WebResult
 * @WebParam
 * SpringBus
 * Endpoint
 * EndpointImpl
 *
 * 一般我们都会写一个接口，然后再写一个实现接口的实现类，但是这不是强制性的
 * @WebService 注解表明是一个webservice服务。
 *      name：对外发布的服务名, 对应于<wsdl:portType name="ServerServiceDemo"></wsdl:portType>
 *      targetNamespace：命名空间,一般是接口的包名倒序, 实现类与接口类的这个配置一定要一致这种错误
 *              Exception in thread "main" org.apache.cxf.common.i18n.UncheckedException: No operation was found with the name xxxx
 *              对应于targetNamespace="http://server.webservice.example.com"
 *      endpointInterface：服务接口全路径（如果是没有接口，直接写实现类的，该属性不用配置）, 指定做SEI（Service EndPoint Interface）服务端点接口
 *      serviceName：对应于<wsdl:service name="ServerServiceDemoImplService"></wsdl:service>
 *      portName：对应于<wsdl:port binding="tns:ServerServiceDemoImplServiceSoapBinding" name="ServerServiceDemoPort"></wsdl:port>
 *
 * @WebMethod 表示暴露的服务方法, 这里有接口ServerServiceDemo存在，在接口方法已加上@WebMethod, 所以在实现类中不用再加上，否则就要加上
 *      operationName: 接口的方法名
 *      action: 没发现又什么用处
 *      exclude: 默认是false, 用于阻止将某一继承方法公开为web服务
 *
 * @WebResult 表示方法的返回值
 *      name：返回值的名称
 *      partName：
 *      targetNamespace:
 *      header: 默认是false, 是否将参数放到头信息中，用于保护参数，默认在body中
 *
 * @WebParam
 *       name：接口的参数
 *       partName：
 *       targetNamespace:
 *       header: 默认是false, 是否将参数放到头信息中，用于保护参数，默认在body中
 *       model：WebParam.Mode.IN/OUT/INOUT
 */
@Component
@WebService(name = "ServerServiceDemo", targetNamespace = "http://service.webserviceServer.tang.com",
        endpointInterface = "com.tang.webserviceServer.service.ServerServiceDemo")
public class ServerServiceDemoImpl implements ServerServiceDemo {


    public ServerServiceDemoImpl() {
    }


    public String queryJgzh(@WebParam String data) throws GeneralSecurityException {
        Request object = (Request)convertXmlStrToObject(Request.class, EncryptTools.SM4Decode(data));
        if(validateSign(object,takeSign())){
            String res = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<Response success=\"*\" msg=\"*\">\n<jgzh>监管账户</jgzh>\n<jgzhmc>监管账户名称</jgzhmc>\n<dqye>当前余额</dqye>\n<zhzt>账户状态</zhzt>\n</Response>" ;
            Response response = (Response) convertXmlStrToObject(Response.class,res);
            setSign(getSign(response));
            return EncryptTools.SM4Encode(res);
        }else{
            throw new Fault(new Exception("签名校验失败！"));
        }

    }

    public String queryJgzzh(@WebParam String data) {
        return null != data && !"".equals(data.trim()) ? "<Response success=\"*\" msg=\"*\">\n<jgzzh>监管子账户</jgzzh>\n<htcode>ht111</htcode>\n<dqye>2200.00</dqye>\n<zhzt>正常</zhzt>\n</Response>" : "传入的参数为空";
    }

    public String payJgzzh(@WebParam String data) {
        return null != data && !"".equals(data.trim()) ? "<Response success=\"true\" msg=\"msg\">\n</Response>" : "传入的参数为空";
    }

    public String detailJgzh(@WebParam String data) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<Response success=\"*\" msg=\"*\">\n<lsxx>\n<jylsh>交易流水号</jylsh>\n<jgzh>监管账户</jgzh>\n<jyrq>交易日期</jyrq>\n<jysj>交易时间</jysj>\n<dfzh>对方账户</dfzh>\n<dfhm>对方户名</dfhm>\n<jyqd>交易渠道</jyqd>\n<szbz>收支标志</szbz>\n<jyqje>2200.00</jyqje>\n<jyje>500.00</jyje>\n<jyhje>1700.00</jyhje>\n<zy>摘要</zy>\n</lsxx>\n<lsxx>\n<jylsh>交易流水号</jylsh>\n<jgzh>监管账户</jgzh>\n<jyrq>交易日期</jyrq>\n<jysj>交易时间</jysj>\n<dfzh>对方账户</dfzh>\n<dfhm>对方户名</dfhm>\n<jyqd>交易渠道</jyqd>\n<szbz>收支标志</szbz>\n<jyqje>2200.00</jyqje>\n<jyje>500.00</jyje>\n<jyhje>1700.00</jyhje>\n<zy>摘要</zy>\n</lsxx>\n</Response>";
    }

    public String detailJgzzh(@WebParam String data) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<Response success=\"true\" msg=\"*\">\n<lsxx>\n<jylsh>交易流水号</jylsh>\n<jyrq>交易日期</jyrq>\n<jysj>交易时间</jysj>\n<dfzh>对方账户</dfzh>\n<dfhm>对方户名</dfhm>\n<jyqd>交易渠道</jyqd>\n<szbz>收支标志</szbz>\n<jyqje>2200.00</jyqje>\n<jyje>500.00</jyje>\n<jyhje>1700.00</jyhje>\n<zy>摘要</zy>\n</lsxx><lsxx>\n<jylsh>交易流水号</jylsh>\n<jyrq>交易日期</jyrq>\n<jysj>交易时间</jysj>\n<dfzh>对方账户</dfzh>\n<dfhm>对方户名</dfhm>\n<jyqd>交易渠道</jyqd>\n<szbz>收支标志</szbz>\n<jyqje>2200.00</jyqje>\n<jyje>500.00</jyje>\n<jyhje>1700.00</jyhje>\n<zy>摘要</zy>\n</lsxx></Response>";
    }

    public String matchJgzzh(@WebParam String data) {
        if (null != data && !"".equals(data.trim())) {
            long id = System.currentTimeMillis();
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<Response success=\"true\" msg=\"匹配成功\">\n<xzqh>行政区划</xzqh>\n<jgzh>监管子账户</jgzh>\n<jgzzh>" + id + "</jgzzh>\n</Response>";
        } else {
            return "传入的参数为空";
        }
    }

    public User getUserById(@WebParam Integer id) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        return user;
    }

    public Integer insertUser(@WebParam String user) {
        System.out.println("user信息:" + user);
        return 1;
    }
}
