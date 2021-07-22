package org.opoo.apps.dvi.office.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.dv.connector.EasySSLProtocolSocketFactory;
import org.opoo.apps.dv.office.OfficeConversionConfig;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class OfficeConversionConfigImpl implements OfficeConversionConfig {
	private static final Logger log = Logger.getLogger(OfficeConversionConfigImpl.class);
	 
    private static final boolean DEFAULT_CONVERSION_ENABLED = false;

    private static final long DEFAULT_CONVERSION_PROGRESS_TIMEOUT = 3000;
    private static final int DEFAULT_CONVERSION_VIEWER_PAGE_CUTOFF = 5;
    private static final int DEFAULT_CONVERSION_MAX_SCHEDULED_ARTIFACTS = 50;
    private static final boolean DEFAULT_ALWAYS_PASSINPUT_FILES = false;

    // docconverter default values
    private static final String DEFAULT_CONVERSION_SERVER_SCHEME = "http";
    private static final int DEFAULT_CONVERSION_SERVER_PORT = 19003;
    private static final String DEFAULT_CONVERSION_SERVER_CONTEXT = "/conversion/v1";
    private static final String DEFAULT_SERVICE_HOSTS = "localhost";
    private static final Map<String, Long> DEFAULT_CONVERSION_STEP_TIMEOUTS = new HashMap<String, Long>();
    private static final long DEFAULT_CONVERSION_STEP_TIMEOUT = 120000;
    private static final boolean DEFAULT_DOWNLOAD_MODIFICATION_ENABLED = true;
    private static final long DEFAULT_DOWNLOAD_MODIFICATION_CORRUPTION_THRESHHOLD_BYTES = 10240; // 10K

    // docconverter properties
    // can be sueful if HTTP balancer is used for docconverters farm to pass the pdf for each preview generation as
    // payload, false otherwise since it shoule be cached after the first page
    private static final String PROP_CONVERSION_ENABLED = "officeintegration.enabled";
    private static final String PROP_ALLWAYS_PASS_INPUT_FILES = "officeintegration.conversion.always.pass.input.files";
    private static final String PROP_CONVERSION_SERVER_SCHEME = "officeintegration.conversion.server.scheme";
    private static final String PROP_CONVERSION_SERVER_PORT = "officeintegration.conversion.server.port";
    private static final String PROP_CONVERSION_SERVER_CONTEXT = "officeintegration.conversion.server.context";
    private static final String PROP_CONVERSION_STEP_TIMEOUT_PREXIF = "officeintegration.conversion.step.timeout.";
    private static final String PROP_CONVERSION_STEP_TIMEOUT_PDF2SWF = PROP_CONVERSION_STEP_TIMEOUT_PREXIF + "pdf2swf";
    private static final String PROP_CONVERSION_STEP_TIMEOUT_TEXTEXTRACT = PROP_CONVERSION_STEP_TIMEOUT_PREXIF
            + "textextract";
    private static final String PROP_CONVERSION_STEP_TIMEOUT_OFFICE2PDF = PROP_CONVERSION_STEP_TIMEOUT_PREXIF
            + "office2pdf";
    private static final String PROP_CONVERSION_STEP_TIMEOUT_IMAGER = PROP_CONVERSION_STEP_TIMEOUT_PREXIF + "imager";
    private static final String PROP_DISABLED_EXTENSIONS = "apps.dv.disabled.extensions";

    // keeping dv for now for backward compatibility maybe will do a db upgrade later
    private static final String PROP_HOSTS_SVC = "apps.dv.service.hosts";

    // manager controlling proeprties
    private static final String PROP_CONVERSION_PROGRESS_TIMEOUT = "officeintegration.conversion.processtimeout";
    private static final String PROP_CONVERSION_VIEWER_PAGE_CUTOFF = "officeintegration.conversion.viewerpagecutoff";
    private static final String PROP_OFFICE_PLUGIN_DOWNLOAD_ENABLED = "officeintegration.plugin.download.enabled";
    private static final String PROP_OUTLOOK_PLUGIN_DOWNLOAD_ENABLED = "outlook.display.links";
    private static final String PROP_CONVERSION_MAX_SCHEDULED_ARTIFACTS
            = "officeintegration.conversion.maxscheduledartifacts";

    private static final String PROP_DOWNLOAD_MODIFICATION_ENABLED
            = "officeintegration.download.modficationEnabled";

    private static final String PROP_DOWNLOAD_MODIFICATION_CORRUPTION_THRESHHOLD_BYTES
            = "officeintegration.download.corruptionThreshhold";

    private static final String SSL_SCHEME_LENIENT = "httpsss";

    static {
        // setup default timeouts for all conversion steps
        DEFAULT_CONVERSION_STEP_TIMEOUTS.put(PROP_CONVERSION_STEP_TIMEOUT_PDF2SWF, new Long(120000));
        DEFAULT_CONVERSION_STEP_TIMEOUTS.put(PROP_CONVERSION_STEP_TIMEOUT_OFFICE2PDF, new Long(1200000));
        DEFAULT_CONVERSION_STEP_TIMEOUTS.put(PROP_CONVERSION_STEP_TIMEOUT_TEXTEXTRACT, new Long(120000));
        DEFAULT_CONVERSION_STEP_TIMEOUTS.put(PROP_CONVERSION_STEP_TIMEOUT_IMAGER, new Long(120000));
    }


    public void init() {
        registerLinientHttpsProtocol();
    }

    /**
     *  The method registers a lenient ssl protocol for testing with
     *  self-signed certs.
     *
     */
    public void registerLinientHttpsProtocol()  {

        // allow for linient HTTPS access for testing HTTPS
        // probably does not belong here
        if (getConversionServerScheme().equalsIgnoreCase(SSL_SCHEME_LENIENT)) {

            log.info(String.format("Register the self-sined protocol %s on port %d", SSL_SCHEME_LENIENT, getConversionServerPort()));
            SecureProtocolSocketFactory httpSocketFactoryLenient = new EasySSLProtocolSocketFactory();
            Protocol httpProtocolLenient =
                new Protocol(SSL_SCHEME_LENIENT, (ProtocolSocketFactory) httpSocketFactoryLenient, getConversionServerPort());
            Protocol.registerProtocol(SSL_SCHEME_LENIENT, httpProtocolLenient);

        }
    }

    public long getDownloadFileSizeModificationCorruptionThreshhold() {
        return AppsGlobals.getProperty(PROP_DOWNLOAD_MODIFICATION_CORRUPTION_THRESHHOLD_BYTES,
                DEFAULT_DOWNLOAD_MODIFICATION_CORRUPTION_THRESHHOLD_BYTES);
    }

    public boolean isDownloadFileModificationEnabled() {
        return AppsGlobals.getProperty(PROP_DOWNLOAD_MODIFICATION_ENABLED,
                DEFAULT_DOWNLOAD_MODIFICATION_ENABLED);
    }

   
    public boolean isConversionEnabled() {
        return isConversionEnabled(DEFAULT_CONVERSION_ENABLED);
    }

   
    public boolean isConversionEnabled(boolean defaultValue) {
        return AppsGlobals.getProperty(PROP_CONVERSION_ENABLED, defaultValue);
    }

   
    public void setConversionEnabled(boolean conversionEnabled) {
        if (conversionEnabled != isConversionEnabled()) {
            AppsGlobals.setProperty(PROP_CONVERSION_ENABLED, String.valueOf(conversionEnabled));
        }
    }

   
    public List<String> getServiceHosts() {
        return getServiceHosts(DEFAULT_SERVICE_HOSTS);
    }

   
    public List<String> getServiceHosts(String defaultValue) {
        String hosts = AppsGlobals.getProperty(PROP_HOSTS_SVC, defaultValue);

        return Arrays.asList(hosts.split(","));
    }

   
    public void setServiceHosts(List<String> serviceHosts) {
        String joined = StringUtils.join(Lists.transform(serviceHosts, new TrimWhiteSpace()), ",");
        if (!StringUtils.equals(AppsGlobals.getProperty(PROP_HOSTS_SVC, DEFAULT_SERVICE_HOSTS), joined)) {
            AppsGlobals.setProperty(PROP_HOSTS_SVC, joined);
        }
    }

   
    public List<String> getDisabledExtensions() {
        return getDisabledExtensions(Collections.<String>emptyList());
    }

   
    public List<String> getDisabledExtensions(List<String> defaultValue) {
        String extensions = AppsGlobals.getProperty(PROP_DISABLED_EXTENSIONS);

        return extensions != null ? Arrays.asList(extensions.split(",")) : defaultValue;
    }

   
    public void setDisabledExtensions(List<String> disabledExtensions) {
        if (!getDisabledExtensions().equals(disabledExtensions)) {
            AppsGlobals.setProperty(PROP_DISABLED_EXTENSIONS,
                    StringUtils.join(Lists.transform(disabledExtensions, new TrimWhiteSpace()), ","));
        }
    }

   
    public void setConversionServerPort(int port) {
        if (getConversionServerPort() != port) {
            AppsGlobals.setProperty(PROP_CONVERSION_SERVER_PORT, String.valueOf(port));
        }
    }

   
    public void setConversionServerScheme(String scheme) {
        if (!getConversionServerScheme().equals(scheme)) {
            AppsGlobals.setProperty(PROP_CONVERSION_SERVER_SCHEME, scheme);
        }
    }

   
    public void setConversionServerContext(String context) {
        if (!getConversionServerContext().equals(context)) {
            AppsGlobals.setProperty(PROP_CONVERSION_SERVER_CONTEXT, context);
        }
    }

    /**
     * Returns a service URI for the remote services to use when calling back to this application server.
     *
     * @return
     */
    /*
   
    public URI getResourceCallbackURI() {
        try {
            String callbackHost = getCallbackHost();
            if(callbackHost == null || callbackHost.equals("")) {
                return new URI(String.format("%s://%s", getCallbackScheme(), getCallbackContext()));
            }
            else if (callbackHost.equalsIgnoreCase("localhost")) {
                return new URI(String.format("%s://%s:%s%s", getCallbackScheme(), InetAddress.getLocalHost().getHostName(), getCallbackPort(),
                    this.getCallbackContext()));
            }
            else {
                return new URI(String.format("%s://%s:%s%s", getCallbackScheme(), getCallbackHost(), getCallbackPort(),
                    this.getCallbackContext()));
            }
        } catch(Exception uis) {
            //checked exceptions are dum
            throw new RuntimeException(uis);
        }
    } */

    // manager controlling proeprties
   
    public long getConversionProgressTimeout() {
        return AppsGlobals.getProperty(PROP_CONVERSION_PROGRESS_TIMEOUT, DEFAULT_CONVERSION_PROGRESS_TIMEOUT);
    }

   
    public int getConversionViewerPageCutoff() {
        return AppsGlobals
                .getProperty(PROP_CONVERSION_VIEWER_PAGE_CUTOFF, DEFAULT_CONVERSION_VIEWER_PAGE_CUTOFF);
    }

   
    public int getMaxScheduledConversionArtifacts() {
        return AppsGlobals.getProperty(PROP_CONVERSION_MAX_SCHEDULED_ARTIFACTS,
                DEFAULT_CONVERSION_MAX_SCHEDULED_ARTIFACTS);
    }

   
    public boolean isOfficePluginDownloadEnabled() {
        return AppsGlobals.getProperty(PROP_OFFICE_PLUGIN_DOWNLOAD_ENABLED, true);
    }

   
    public boolean isOutlookPluginDownloadEnabled() {
        return AppsGlobals.getProperty(PROP_OUTLOOK_PLUGIN_DOWNLOAD_ENABLED, true);
    }

   
    public boolean getAlwaysPassInputFile() {
        return AppsGlobals.getProperty(PROP_ALLWAYS_PASS_INPUT_FILES, DEFAULT_ALWAYS_PASSINPUT_FILES);
    }

   
    public String getConversionServerScheme() {
        return AppsGlobals.getProperty(PROP_CONVERSION_SERVER_SCHEME, DEFAULT_CONVERSION_SERVER_SCHEME);
    }

   
    public String getConversionServerContext() {
        return AppsGlobals.getProperty(PROP_CONVERSION_SERVER_CONTEXT, DEFAULT_CONVERSION_SERVER_CONTEXT);
    }

   
    public int getConversionServerPort() {
        return AppsGlobals.getProperty(PROP_CONVERSION_SERVER_PORT, DEFAULT_CONVERSION_SERVER_PORT);
    }

   
    public long getConversionStepTimeout(String stepName) {

        String stepNameProperty = PROP_CONVERSION_STEP_TIMEOUT_PREXIF + stepName.toLowerCase();
        return AppsGlobals.getProperty(stepNameProperty,
                DEFAULT_CONVERSION_STEP_TIMEOUTS.containsKey(stepNameProperty) ? DEFAULT_CONVERSION_STEP_TIMEOUTS
                        .get(stepNameProperty) : DEFAULT_CONVERSION_STEP_TIMEOUT);
    }

    public long getDefaultStepTimeout() {
        return DEFAULT_CONVERSION_STEP_TIMEOUT;
    }

    private static class TrimWhiteSpace implements Function<String, String> {
        public String apply(String s) {
            return StringUtils.trim(s);
        }
    }
}
