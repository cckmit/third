package org.opoo.apps.id.sequence;

import javax.sql.DataSource;

import org.opoo.apps.database.DataSourceManager;


/**
 * Postgres 数据库 Sequence 提供者。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PostgresSequenceProvider extends AbstractSequenceProvider {
	private DataSource dataSource;
	public PostgresSequenceProvider(){
		dataSource = DataSourceManager.getRuntimeDataSource();
	}
	public PostgresSequenceProvider(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public Sequence getSequence(String key) {
		return new PostgresSequence(dataSource, key);
	}
}
