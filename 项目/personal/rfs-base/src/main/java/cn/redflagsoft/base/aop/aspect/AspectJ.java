package cn.redflagsoft.base.aop.aspect;

import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.aop.annotation.Audit;
import cn.redflagsoft.base.aop.annotation.BizLog;
import cn.redflagsoft.base.aop.annotation.Operation;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Smsg;


/**
 * AspectJ 相关类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AspectJ {

	/**
	 * 表示代码是否在编译期间被织入。
	 * 
	 * 在编译期，返回值被改变。
	 * 如果CTW, 返回值为true.
	 * @return
	 */
	public static boolean isCompileTimeWoven(){
		return false;
	}
	
	@Audit
	public static void testMethodAudit() {
		System.out.println("AspectJ.testMethodAudit is running.");
	}

	@Operation(targetName = "aspectj", operation = "测试")
	public static void testMethodOperation() {
		System.out.println("AspectJ.testMethodOperation is running.");
	}

	@Operation(targetName = "RFSObject", operation = "测试", domainArgIndex = 0)
	public static void testMethodOperstion(RFSObject o) {
		System.out.println("AspectJ.testMethodOperation is running, RFSObject="
				+ o);
	}

	@Operation(targetName = "RFSObject", operation = "测试", domainIdArgIndex = 0, domainType = "cn.redflagsoft.base.bean.RFSObject")
	public static void testMethodOperstion(long id) {
		System.out
				.println("AspectJ.testMethodOperation is running. RFSObject.id="
						+ id);
	}

	@Operation(targetName = "RFSObject", operation = "测试", domainIdArgIndex = 0, domainClass = RFSObject.class)
	public static void testMethodOperstion2(long id) {
		System.out
				.println("AspectJ.testMethodOperation is running. RFSObject.id="
						+ id);
	}
	
	@BizLog(taskType=1999, workType=199999, objectIndex=-1)
	public static Smsg testBizLog(){
		Smsg smsg = new Smsg();
		smsg.setId(1000L);
		smsg.setName("Test BizLog");
		return smsg;
	}
	
	@Transactional(readOnly=true)
	public static void doNothing(){
		
	}
}
