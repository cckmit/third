/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license.validator;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.validator.Validator;

public class NameValidator implements Validator {
	//private static final Logger log = Logger.getLogger(NameValidator.class.getName());
    private final String name;

    /**
     * Creates a NameValidate with the name to validate the license's name against.
     *
     * @param name The name to validate the license's name against.
     */
    public NameValidator(String name) {
        this.name = name;
    }

    public void validate(License license) throws LicenseException {
        if (!name.equals(license.getName()) && !license.getName().startsWith(name + "(")) {
            throw new LicenseException("License must be for " + name + " not " + license.getName());
        }
    }

}
