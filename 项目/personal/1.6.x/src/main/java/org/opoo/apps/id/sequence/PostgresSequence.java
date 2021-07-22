package org.opoo.apps.id.sequence;

import javax.sql.DataSource;

/**
 * Postgres Êý¾Ý¿â Sequence¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PostgresSequence extends DatabaseSequence {
	private final String nextSql;
	private final String currentSql;
	public PostgresSequence(DataSource ds, String name) {
		super(ds, name);
		this.nextSql = "SELECT nextval('" + name + "')";
		this.currentSql = "SELECT currval('" + name + "')";
	}
	@Override
	protected String getCreateSql(String name) {
		return "CREATE SEQUENCE " + name;
	}
	@Override
	protected String getCurrentSql(String name) {
		return currentSql;
	}
	@Override
	protected String getDropSql(String name) {
		return "DROP SEQUENCE " + name;
	}
	@Override
	protected String getNextSql(String name) {
		return nextSql;
	}

}
