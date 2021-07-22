package org.opoo.apps.query;

import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;

public interface Query<T> {

	T execute(QueryParameters parameters) throws QueryException;
}
