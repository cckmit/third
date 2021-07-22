package org.opoo.apps.id.sequence;

import javax.sql.DataSource;

import org.opoo.apps.database.DataSourceManager;


/**
 * Oracle 数据库 Sequence 提供者。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OracleSequenceProvider extends AbstractSequenceProvider {
	private DataSource dataSource;
	public OracleSequenceProvider(){
		dataSource = DataSourceManager.getRuntimeDataSource();
	}
	public OracleSequenceProvider(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public Sequence getSequence(String key) {
		return new OracleSequence(dataSource, key);
	}
}
