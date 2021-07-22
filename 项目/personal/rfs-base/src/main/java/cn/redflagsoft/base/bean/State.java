package cn.redflagsoft.base.bean;



/**
 * ��״̬
 * 
 * 
 * <pre>
����
ͼʾ
�����˵��
����
10


��ͣ
20

��ָǰ�ںͽ���׶ε���ͣ�С�

��


ȡ��
100

ԭ��ֹ��ȡ���ϲ�Ϊȡ����
ת��
110

��ԭ��ת�����о���ת��������һЩ��

��


����
126


����
127
</pre>
 * 
 * @author lcj
 *
 */
public enum State {
	NORMAL((byte)10, "����"),
	PAUSE((byte)20, "��ͣ", "��ָǰ�ںͽ���׶ε���ͣ�С�"),
	CANCEL((byte)100,"ȡ��","ԭ��ֹ��ȡ���ϲ�Ϊȡ����"),
	TRANS((byte)110,"ת��","��ԭ��ת�����о���ת��������һЩ��"),
	SHELVE((byte)126,"�ݻ�")//,
	//DELETED((byte)127,"����"),
	;
	
	/**
	 * 10
	 */
	public static final byte STATE_����  = State.NORMAL.getByteStatus();
	/**
	 * 20
	 */
	public static final byte STATE_��ͣ  = State.PAUSE.getByteStatus();
	/**
	 * 100
	 */
	public static final byte STATE_ȡ��  = State.CANCEL.getByteStatus();
	/**
	 * 110
	 */
	public static final byte STATE_ת��  = State.TRANS.getByteStatus();
	/**
	 * 126
	 */
	public static final byte STATE_�ݻ�  = State.SHELVE.getByteStatus();
	//public static final byte STATE_����  = State.DELETED.getByteStatus();
	
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
