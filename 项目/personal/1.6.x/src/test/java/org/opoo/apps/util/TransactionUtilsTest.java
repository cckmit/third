package org.opoo.apps.util;

import org.junit.Test;
import org.opoo.apps.transaction.TransactionUtils;


public class TransactionUtilsTest {
	@Test
	public void testOnTransactionCommit(){
		TransactionUtils.onTransactionCommit(new Runnable() {
			public void run() {
				System.out.println("running...");
				//Assert.fail("");
			}
		});
	}
}
