package org.opoo.ndao;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NonUniqueResultException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5663175820756966313L;

	public NonUniqueResultException(int resultCount) {
		super( "查询结果不唯一: " + resultCount );
	}

}
