package cn.redflagsoft.base.schemes;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.DistrictBean;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.DistrictScheme;
import cn.redflagsoft.base.test.BaseTests;

public class DistrictSchemeTest extends BaseTests {

	protected SchemeManager schemeManager;
	
	public void testUpdateTwo() throws Exception{
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		Scheme scheme = schemeManager.getScheme("districtScheme");
		
		DistrictScheme ds = (DistrictScheme)scheme;
		ds.setCode("710300");  //���������޸ġ�
		ds.setName("������");
		ds.setParentCode("510000");
		
		SchemeInvoker.invoke(ds, "updateTwo");
		setComplete();
	}
	
	public void otestSaveOne() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		Scheme scheme = schemeManager.getScheme("districtScheme");
		
		assertNotNull(scheme);
		assertTrue(scheme instanceof DistrictScheme);
		
		DistrictBean d = new DistrictBean();
		d.setCode("710000");
		d.setName("����ʡ");
		
		DistrictScheme ds = (DistrictScheme)scheme;
		ds.setDistrictBean(d);
		ds.doSaveOne();
		setComplete();
	}
	
	
	public void otestSaveTwo() throws Exception {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		Scheme scheme = schemeManager.getScheme("districtScheme");
		
		DistrictScheme ds = (DistrictScheme)scheme;
		ds.setCode("710300");
		ds.setName("����");
		ds.setParentCode("710000");
		
		SchemeInvoker.invoke(ds, "saveTwo");
		setComplete();
	}
}
