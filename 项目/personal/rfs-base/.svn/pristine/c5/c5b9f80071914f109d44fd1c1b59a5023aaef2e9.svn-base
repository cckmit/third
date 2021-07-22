package cn.redflagsoft.base;

public abstract class ObjectTypes {

	//业务异常
	public static final int BUSINESS_EXCEPTION = 500;
	
	public static final int JOB = 101;
	public static final int TASK = 102;
	public static final int WORK = 103;
	public static final int PROCESS = 104;
	/**
	 * 问题。
	 */
	public static final int ISSUE = 201;
	public static final int PROGRESS = 202;
	
	public static final int SIGNCARD = 211;
	public static final int SMSG = 212;
	
	public static final int DATUM = 213;

	
	public static final int RISK = 121;
	public static final int CAUTION = 122;
	
	/**
	 * 301
	 */
	public static final int OBJECT_ARCHIVE = 301;
	/**
	 * 302
	 */
	public static final int OBJECT_UNARCHIVE = 302;
	/**
	 * 303
	 */
	public static final int OBJECT_CANCEL = 303;
	/**
	 * 304
	 */
	public static final int OBJECT_INVALID = 304;
	
	/**
	 * 305
	 */
	public static final int OBJECT_PUBLISH = 305;
	
	/**
	 * 306
	 */
	public static final int OBJECT_PAUSE = 306;
	
	/**
	 * 307
	 */
	public static final int OBJECT_WAKE = 307;
	
	/**
	 * 308
	 */
	public static final int OBJECT_TRANS = 308;
	
	/**
	 * 309
	 */
	public static final int OBJECT_SHELVE = 309;
	
	/**
	 * 310
	 */
	public static final int OBJECT_FINISH = 310;
	
	/**
	 * 警示事实复核
	 */
	public static final int OBJECT_CAUTIONCHECK = 311;
	
	/**
	 * 警示告知
	 */
	public static final int OBJECT_CAUTIONNOTIFY = 312;
	
	/**
	 * 警示调查约谈
	 */
	public static final int OBJECT_CAUTIONSURVEY = 313;
	
	/**
	 * 警示处理决定
	 */
	public static final int OBJECT_CAUTIONDECIDE = 314;
	
	
	public static boolean isRFSObject(int objectType){
		switch (objectType) {
			case ISSUE:
			case SIGNCARD:
			case SMSG:
			case CAUTION:
				return true;
			default:
				return false;
		}
	}
}
