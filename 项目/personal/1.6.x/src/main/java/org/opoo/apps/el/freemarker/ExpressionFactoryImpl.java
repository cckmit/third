package org.opoo.apps.el.freemarker;

import org.opoo.apps.el.Expression;
import org.opoo.apps.el.ExpressionFactory;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ExpressionFactoryImpl extends ExpressionFactory {

	/**
	 * 
	 */
	public Expression createExpression(String expr) {
		return new ExpressionImpl(expr);
	}
}
