/*
 * $Id: TaskTypeCodeGeneratorTest.java 5241 2011-12-21 09:28:27Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.codegenerator;

import java.util.Date;

import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.service.CodeService;
import cn.redflagsoft.base.test.BaseTests;
import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskTypeCodeGeneratorTest extends BaseTests {

	protected CodeService codeService;
	
	public void testGen(){
		super.setComplete();
		final TaskTypeCodeGenerator generator = new TaskTypeCodeGenerator();
		generator.setCodeService(codeService);
		final Task task = new Task();
		task.setType(1000);
		task.setCreationTime(new Date());
		
		
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i = 0 ; i< 100 ; i++){
			Runnable runnable = new Runnable() {
				public void run() {
					String code = generator.generateCode(task);
					System.out.println(code);
				}
			};
			service.execute(runnable);
		}
		
		try {
			Thread.sleep(500000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
