
package cn.redflagsoft.base.bean;


import java.util.Date;

/**
 * @author 
 *
 */
public class ExDataRisk extends VersionableBean  {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7948758977218542475L;
	private Long recID;		 //编号
    private Long dataID; 	 //待交换数据的主键		
    private Date exTime;  	 //交换时间    
    
	public static byte STATUS_INEFFICACY = 0;
	public static byte STATUS_WAIT = 1;
	public static byte STATUS_CHANGEING = 2;
	public static byte STATUS_LOSE = 5;
	public static byte STATUS_TERMINATE = 6;
	public static byte STATUS_SUCCESS = 9;
	
	public Long getRecID() {
		return recID;
	}


	public void setRecID(Long recID) {
		this.recID = recID;
	}


	public Long getDataID() {
		return dataID;
	}


	public void setDataID(Long dataID) {
		this.dataID = dataID;
	}


	public Date getExTime() {
		return exTime;
	}


	public void setExTime(Date exTime) {
		this.exTime = exTime;
	}

	public Long getId(){
		return getRecID();
	}
	public void setId(Long id){
	     setRecID(id);
	}
}