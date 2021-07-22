package org.opoo.apps;

import java.util.Collection;
import java.util.Map;

/**
 * An extension of the {@link Map} interface to add support for getting child property
 * names or a {@link Collection} of all property names.
 *
 * @javadoc api
 */
public interface AppsProperties<K,V> extends Map<K,V> {
	/**
     * Return all children property names of a parent property as a {@link Collection}
     * of String objects. For example, given the properties <tt>X.Y.A</tt>,
     * <tt>X.Y.B</tt>, and <tt>X.Y.C</tt>, then the child properties of
     * <tt>X.Y</tt> are <tt>X.Y.A</tt>, <tt>X.Y.B</tt>, and <tt>X.Y.C</tt>. The method
     * is not recursive; ie, it does not return children of children.
     *
     * @param parentKey the name of the parent property.
     * @return all child property names for the given parent.
     */
    Collection<V> getChildrenNames(K parentKey);

    /**
     * Returns all property names as a {@link Collection} of String values.
     * @return all property names.
     */
    Collection<V> getPropertyNames();

    boolean getBooleanProperty(K propertyKey);

    int getIntProperty(K propertyKey);
    
    String getProperty(K propertyKey);
   
    String getProperty(K propertyKey, String defaultValue);
    
    boolean getProperty(K propertyKey, boolean defaultValue);

    int getProperty(K property, int defaultValue);
}
