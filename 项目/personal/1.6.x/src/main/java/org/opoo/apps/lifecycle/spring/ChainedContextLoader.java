
package org.opoo.apps.lifecycle.spring;

import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * This is an interface for adding context files programmatically into the AppsContextLoader.  Any instance of this
 * class should be specified in web.xml under the 'chainedContextLoaders' context parameter for it to be loaded by the
 * AppsContextLoader.
 * @since 1.6.0
 */
public interface ChainedContextLoader {

    /**
     * Get a list of Spring context file names to load in line with the SBS context files.
     *
     * @param servletContext The active ServletContext.
     * @return A List<String> of file paths.
     */
    List<String> getContextFiles(ServletContext servletContext);

    /**
     * Since Spring has only protected access to 'customizeContext' we'll need to extend it to use it for our
     * purposes.
     */
    interface EmbeddedLoader {

        void applyContextCustomizations(ServletContext sc, ConfigurableWebApplicationContext ac);

    }

}
