package org.opoo.apps.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * ָ��ĳ���Ƿ񲻿��Ա�ͨ�ò�ѯ���á�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface NotQueryable {
}
