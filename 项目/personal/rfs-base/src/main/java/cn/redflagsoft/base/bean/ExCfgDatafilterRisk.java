package cn.redflagsoft.base.bean;

public class ExCfgDatafilterRisk extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1279500288929940385L;
	private Long id;               //编号                                                                        
	private String name;           //本配置的名称                                                                
	private byte cataLog;          //分类：1 政府投资建设项目全过程跟踪监管系统的监察信息上传。缺省为1           
	private short conn;            //数据交换的连接。缺省为0，即此项未作限制。                                   
	private Long riskRuleID;       //当risk_rule的status为有效时，本记录才有效。                                 
	private String event;          //操作类别：1-开始 5-暂停 6-重启 9-结束 10-时限变更  11-责任人变更 19-其他变更
	private byte flag;             //交换标志：0 不可交换；1 可交换。默认不可交换                                                                         
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getCataLog() {
		return cataLog;
	}
	public void setCataLog(byte cataLog) {
		this.cataLog = cataLog;
	}
	public short getConn() {
		return conn;
	}
	public void setConn(short conn) {
		this.conn = conn;
	}
	public Long getRiskRuleID() {
		return riskRuleID;
	}
	public void setRiskRuleID(Long riskRuleID) {
		this.riskRuleID = riskRuleID;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public byte getFlag() {
		return flag;
	}
	public void setFlag(byte flag) {
		this.flag = flag;
	}
	
	
}
