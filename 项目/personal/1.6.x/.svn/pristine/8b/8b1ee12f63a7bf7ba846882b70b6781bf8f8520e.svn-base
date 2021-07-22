package org.opoo.apps.id.sequence;

import org.opoo.apps.database.DataSourceManager;


public class IdTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("apps.home", "c:\\apps");
		
//		String sql = "select curr from t_sys_id where id=?";
//		long id = SpringJdbcSupport.getSimpleJdbcTemplate().queryForLong(sql, "test2");
//		System.out.println(id);
		
		SequenceProvider provider = SequenceManager.getSequenceProvider();
		System.out.println(provider);
		
		Sequence sequence = provider.getSequence("test");
		sequence = new TableSequence(DataSourceManager.getRuntimeDataSource(), "test2");
		sequence = new HiLoSequence(sequence);
		System.out.println(sequence);
		for(int i = 0 ; i < 100 ; i++){
			System.out.println(sequence.getNext());
		}
		
	}

}
