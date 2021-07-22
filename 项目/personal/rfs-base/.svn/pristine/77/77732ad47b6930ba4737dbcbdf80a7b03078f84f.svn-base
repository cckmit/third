package cn.redflagsoft.base.audit;

import java.lang.reflect.Method;

/**
 * An Auditor contains logic specific to a given class which can capture the
 * relevant details of method calls on instances of that class.
 *
 * Not all methods of a class are necessarily audited, but for those that are,
 * this object will convert runtime data in the supplied {@link MethodCall}
 * to an {@link AuditMessage} which will be persisted an (presumable) viewed later.
 */
public interface Auditor {

    /**
     * Handles the details of capturing and formatting relevant data
     * from a given method call.
     *
     * @param call the runtime method call data.
     * @return an audit message to be persisted or null if the auditor can determine the
     *      called method will no op or fail due to invalid parameter values.
     * @throws NotAuditableException if the supplied method call
     * is unknown to this auditor instance.
     */
    public MutableAuditMessage auditMethodCall(MethodCall call)
            throws NotAuditableException;

    /**
     * Returns true if this auditor instance knows how to handle the supplied method.
     *
     * @param method the method to be audited.
     * @return true if this auditor instance knows how to handle the supplied method.
     */
    public boolean methodIsAuditable(Method method);


    /**
     * Sets the class which this auditor instance covers. A given auditor will expect a class instance of a certain type
     * and will throw NotAuditableException if it is passed an unexpected type to audit. The purpose of this setter
     * is to pass the auditor the exact class instance to be audited, so that tests of method object equality, etc. will
     * work correctly. This method must be called before any others in this interface.
     *
     * @param audited the class which this auditor instance will operate on.
     * @throws NotAuditableException if the class is of an unexpected type.
     */
    public void setAuditedClass(Class<?> audited) throws NotAuditableException;


}
