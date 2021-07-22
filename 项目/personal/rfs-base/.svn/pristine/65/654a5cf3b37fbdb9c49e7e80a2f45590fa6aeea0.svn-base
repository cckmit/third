package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.ndao.Domain;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;

/**
 * 事务日志。
 * 
 * @author Alex Lin
 *
 */
@ObjectType(ObjectTypes.PROCESS)
public class Process extends BaseBean implements Domain<Long>, RFSEntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1123478021775101164L;
	public static final int OBJECT_TYPE = ObjectTypes.PROCESS;
	private Long sn;
	private String code;
	private Long taskSN;
	private Long workSN;
	private Date processTime;
	private byte result;
	private byte rollBack;
	private Long rollClerkID;
	private Date rollTime;
	private String note;
	
	public static final int PROCESS_TRANSACTION = 3022;
	public static final int PROCESS_WAKE = 3003;
	public static final int PROCESS_HANG = 3002;
	
	public Process() {
	}

	/**
	 * 序号
	 * 
	 * 事务序号,系统唯一
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * 序号
	 * 
	 * 事务序号,系统唯一
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * 代码
	 * 
	 * 编号.
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 任务
	 * 
	 * 任务序号.
	 * @return Long
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * @param taskSN
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}

	/**
	 * 工作
	 * 
	 * 工作序号.
	 * @return Long
	 */
	public Long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN
	 */
	public void setWorkSN(Long workSN) {
		this.workSN = workSN;
	}

	/**
	 * 时间
	 * 
	 * 发生时间
	 * @return Date
	 */
	public Date getProcessTime() {
		return processTime;
	}

	/**
	 * @param processTime
	 */
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	/**
	 * 结果
	 * 
	 * 事务操作的结果,包括成功,失败,空.
	 * @return byte
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	/**
	 * 滚回
	 * 
	 * 0 正常, 1 滚回.默认滚回为0.
	 * @return byte
	 */
	public byte getRollBack() {
		return rollBack;
	}

	/**
	 * @param rollBack
	 */
	public void setRollBack(byte rollBack) {
		this.rollBack = rollBack;
	}

	/**
	 * 滚回人
	 * 
	 * 发出滚回指令的人.默认为0,表示无.
	 * @return Long
	 */
	public Long getRollClerkID() {
		return rollClerkID;
	}

	/**
	 * @param rollClerkId
	 */
	public void setRollClerkID(Long rollClerkID) {
		this.rollClerkID = rollClerkID;
	}

	/**
	 * 滚回时间
	 * 
	 * 滚回指令的时间.早于开始时间为空.
	 * @return Date
	 */
	public Date getRollTime() {
		return rollTime;
	}

	/**
	 * @param rollTime
	 */
	public void setRollTime(Date rollTime) {
		this.rollTime = rollTime;
	}

	/**
	 * 备注
	 * 
	 * xml形式的内容,根据具体类别解释.默认为空.
	 * @return String
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSEntityObject#getObjectType()
	 */
	public int getObjectType() {
		return OBJECT_TYPE;
	}
}
