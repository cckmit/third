package org.opoo.apps.id.sequence;

import org.junit.Test;
import org.opoo.apps.database.DataSourceManager;


public class OracleTableSequenceTest {
	static{
		System.setProperty("apps.home", "c:\\apps");
	}

	public void testOracleTableSequence(){
		if(DataSourceManager.getMetaData().getDatabaseType().isOracle()){
			OracleTableSequence ots = new OracleTableSequence(DataSourceManager.getRuntimeDataSource(), "req-test");
//			Long current = ots.getCurrent();
//			System.out.println(current);
			for(int i = 0 ; i < 100 ; i++){
				long next = ots.getNext();
				System.out.println(next);
			}
		}
		
		TableSequence ts = new TableSequence(DataSourceManager.getRuntimeDataSource(), "req-test2");
		for(int i = 0 ; i < 100 ; i++){
			long next = ts.getNext();
			System.out.println(next);
		}
	}
	
	
	public void testOracleSequence(){
		if(DataSourceManager.getMetaData().getDatabaseType().isOracle()){
			OracleSequence os = new OracleSequence(DataSourceManager.getRuntimeDataSource(), "reqtest1");
//			Long current = os.getCurrent();
//			System.out.println(current);
			for(int i = 0 ; i < 100 ; i++){
				long next = os.getNext();
				System.out.println(next);
			}
		}
	}
	
	//@Test
	public void testHiLoSequence(){
		if(DataSourceManager.getMetaData().getDatabaseType().isOracle()){
			OracleSequence os = new OracleSequence(DataSourceManager.getRuntimeDataSource(), "reqtest1");
			HiLoSequence hiloSeq = new HiLoSequence(os);
			for(int i = 0 ; i < 100 ; i++){
				long next = hiloSeq.getNext();
				System.out.println(next);
			}
		}
	}
	
	@Test
	public void testBlockableSequenceWrapper(){
		Blockable b = new OracleTableSequence(DataSourceManager.getRuntimeDataSource(), "req-test");
		BlockableSequenceWrapper bsw = new BlockableSequenceWrapper(b, "req-test", 5, true);
		for(int i = 0 ; i < 100 ; i++){
			long next = bsw.getNext();
			System.out.println(next);
		}
	}
	
	
}
