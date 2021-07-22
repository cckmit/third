package cn.redflagsoft.base.scheme.schemes;

import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.service.IssueService;


/**
 * 问题上报的WorkScheme。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class IssueWorkScheme extends AbstractWorkScheme {

	private Issue issue;
	private RFSObject rfsObject;
		
	/**
	 * @return the rfsObject
	 */
	public RFSObject getRfsObject() {
		return rfsObject;
	}

	/**
	 * @param rfsObject the rfsObject to set
	 */
	public void setRfsObject(RFSObject rfsObject) {
		this.rfsObject = rfsObject;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
		
	public IssueService getIssueService(){
		return (IssueService)getObjectService();
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#createObject()
	 */
	@Override
	protected RFSObject createObject() {
		RFSObject object = getRfsObject();
		Assert.notNull(object, "问题关联的业务对象不能为空。");
		Assert.notNull(issue, "问题对象不能为空。");
		return getIssueService().createIssue(object, issue);
	}
	
	/**
	 * 问题上报。
	 * 
	 * @return
	 */
	public Object doCreate() {
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "业务处理成功！";
	}

}
