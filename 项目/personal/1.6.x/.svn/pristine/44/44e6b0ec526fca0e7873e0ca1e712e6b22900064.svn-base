package org.opoo.apps.dvi.office;

import java.util.List;

public interface OfficeConversionConfig {

	List<String> getDisabledExtensions();

	boolean isConversionEnabled();

	boolean isConversionEnabled(boolean defaultValue);

	void setConversionEnabled(boolean conversionEnabled);

	List<String> getServiceHosts();

	List<String> getServiceHosts(String defaultValue);

	void setServiceHosts(List<String> serviceHosts);

	List<String> getDisabledExtensions(List<String> defaultValue);

	void setDisabledExtensions(List<String> disabledExtensions);

	void setConversionServerPort(int port);

	void setConversionServerScheme(String scheme);

	void setConversionServerContext(String context);

	// manager controlling proeprties
	long getConversionProgressTimeout();

	int getConversionViewerPageCutoff();

	// can't shedule for mroe than this numer of artifacts at once
	// for load limit
	int getMaxScheduledConversionArtifacts();

	boolean isOfficePluginDownloadEnabled();

	boolean isOutlookPluginDownloadEnabled();

	// conversion server

	boolean getAlwaysPassInputFile();

	String getConversionServerScheme();

	String getConversionServerContext();

	int getConversionServerPort();

	long getConversionStepTimeout(String stepName);

	long getDefaultStepTimeout();

	/**
	 * The method registers a lenient ssl protocol for testing with self-signed
	 * certs.
	 * 
	 */
	public void registerLinientHttpsProtocol();

	/**
	 * openxml4j corrupts documents without throwing exceptions so if the new
	 * document is smaller or large by the give threshhold - return the original
	 * 
	 * @return
	 */
	long getDownloadFileSizeModificationCorruptionThreshhold();

	/**
	 * disable the download document modification all together
	 * 
	 * @return
	 */
	boolean isDownloadFileModificationEnabled();
}
