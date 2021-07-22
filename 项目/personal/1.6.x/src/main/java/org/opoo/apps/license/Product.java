/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license;

import java.io.Serializable;
import java.util.Collection;

import com.jivesoftware.license.License.Module;
import com.jivesoftware.license.License.Version;

@Deprecated
public interface Product extends Serializable {
	String getName();
	Version getVersion();
	Collection<Module> getModules();
}
