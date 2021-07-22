package cn.redflagsoft.base.schemes;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.ObjectIssueReportWorkScheme;
import cn.redflagsoft.base.test.BaseTests;

public class ObjectIssueReportWorkSchemeTest extends BaseTests {

	protected SchemeManager schemeManager;
	
	public void testObjectIssueReportWorkScheme() throws Exception{
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "123"));
				
		Scheme scheme = schemeManager.getScheme("projectIssueWorkScheme");
		
		ObjectIssueReportWorkScheme iws = (ObjectIssueReportWorkScheme)scheme;
		
		Issue  issue = new Issue();
		issue.setType((short)0);
		issue.setDescription("lgx");
		issue.setGrade((short)1);
		iws.setObjectId(157L);
		iws.setIssue(issue);
		iws.doObjectIssue();
		setComplete();
	}
}
