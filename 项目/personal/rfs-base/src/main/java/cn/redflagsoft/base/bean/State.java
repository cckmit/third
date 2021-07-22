package cn.redflagsoft.base.bean;



/**
 * 辅状态
 * 
 * 
 * <pre>
代码
图示
含义和说明
正常
10


暂停
20

特指前期和建设阶段的暂停中。

…


取消
100

原中止和取消合并为取消。
转出
110

即原来转交，感觉用转出更贴切一些。

…


搁置
126


作废
127
</pre>
 * 
 * @author lcj
 *
 */
public enum State {
	NORMAL((byte)10, "正常"),
	PAUSE((byte)20, "暂停", "特指前期和建设阶段的暂停中。"),
	CANCEL((byte)100,"取消","原中止和取消合并为取消。"),
	TRANS((byte)110,"转出","即原来转交，感觉用转出更贴切一些。"),
	SHELVE((byte)126,"暂缓")//,
	//DELETED((byte)127,"作废"),
	;
	
	/**
	 * 10
	 */
	public static final byte STATE_正常  = State.NORMAL.getByteStatus();
	/**
	 * 20
	 */
	public static final byte STATE_暂停  = State.PAUSE.getByteStatus();
	/**
	 * 100
	 */
	public static final byte STATE_取消  = State.CANCEL.getByteStatus();
	/**
	 * 110
	 */
	public static final byte STATE_转出  = State.TRANS.getByteStatus();
	/**
	 * 126
	 */
	public static final byte STATE_暂缓  = State.SHELVE.getByteStatus();
	//public static final byte STATE_作废  = State.DELETED.getByteStatus();
	
	private final byte status;
	private final String description;
	private final String statusName;
	private State(byte status, String statusName, String description){
		this.status = status;
		this.description = description;
		this.statusName = statusName;
	}
	private State(byte status, String statusName){
		this(status, statusName, null);
	}
	/**
	 * @return the status
	 */
	public byte getByteStatus() {
		return status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}
}
