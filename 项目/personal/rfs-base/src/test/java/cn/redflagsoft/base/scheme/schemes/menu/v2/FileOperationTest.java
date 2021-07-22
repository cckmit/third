package cn.redflagsoft.base.scheme.schemes.menu.v2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.datum.FileOperationScheme;
import cn.redflagsoft.base.test.BaseTests;

public class FileOperationTest extends BaseTests{
	protected SchemeManager schemeManager;

	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}
	
	
	public void testFileOperation(){
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("wuj", "123"));
		FileOperationScheme scheme = (FileOperationScheme)schemeManager.getScheme("fileOperationScheme");
		
		Map m = new HashMap();
		
		m.put("fileId","2020");
		m.put("type","1");
		
		scheme.setParameters(m);
		//scheme.setFileId(2007L);
		//scheme.setType(1);
		
		String doFileOperationLog = scheme.doFileOperationLog();
		
		System.out.println(doFileOperationLog);
		super.setComplete();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
