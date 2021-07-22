package cn.redflagsoft.base.dao;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.test.BaseTests;

public class IssueDaoTest extends BaseTests {
	
	protected IssueDao issueDao;
	
	
	public void testSave(){
		
		super.setComplete();
		Issue temp =new Issue();
		temp.setGrade((short)1);
		temp.setName("≤‚ ‘ ˝æ›1");
		temp.setRefObjectId(new Long(7));
		temp = issueDao.save(temp);
		Issue result =issueDao.get(temp.getId());
		assertEquals(temp, result);
	}
	
	public void testUpdate() throws Exception{	
		Issue  issue  =getOneIssue();	
		Issue clone =new Issue();		
		PropertyUtils.copyProperties(clone, issue);	
		issue.setName(issue.getName()+"update");	
		assertNotSame(issue.getName(),clone.getName());	
		super.setComplete();
	}
	public void testDelete(){
		Issue issue =getOneIssue();
		int result = issueDao.delete(issue);
		System.out.println(result);
		assertSame(result, 1);
	}
	
	public void testfind(){
		List<Issue> issuelist = issueDao.find();
		assertNotNull(issuelist);
	}
	
	
	public Issue getOneIssue()
	{
		List<Issue> issuelist = issueDao.find();
		Issue   result = null;
		if(issuelist.size()!= 0 ||issuelist!= null){
			result =issuelist.get(0);
		}
		return result;
	}

}
