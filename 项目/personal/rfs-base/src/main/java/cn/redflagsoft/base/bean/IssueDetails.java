package cn.redflagsoft.base.bean;

import java.util.Map;


/****
 * 
 * Œ Ã‚œÍ«È
 * 
 * @author
 * 
 */
public class IssueDetails extends ObjectDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Issue issue;
	
	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	
	public IssueDetails(){
		super();
	}
	
	public IssueDetails(RFSObject object, Task task, Progress progress,Issue issue){
		super(object,task,progress,issue);
	}

	@SuppressWarnings("unchecked")
	public Map toMap() {
		return super.toMap();
	}

}
