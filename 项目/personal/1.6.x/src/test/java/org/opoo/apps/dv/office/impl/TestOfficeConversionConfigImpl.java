package org.opoo.apps.dv.office.impl;

import java.util.List;

import org.opoo.apps.dv.office.OfficeConversionConfig;

import com.google.common.collect.Lists;

public class TestOfficeConversionConfigImpl extends OfficeConversionConfigImpl implements OfficeConversionConfig {

	@Override
	public List<String> getDisabledExtensions() {
		return Lists.newArrayList();
	}
	@Override
	public long getConversionProgressTimeout() {
		return 3000L;
	}
	@Override
	public long getDownloadFileSizeModificationCorruptionThreshhold() {
		return 10240;
	}
	@Override
	public boolean isDownloadFileModificationEnabled() {
		return true;
	}
	@Override
	public boolean isConversionEnabled() {
		return true;
	}
	@Override
	public boolean isConversionEnabled(boolean defaultValue) {
		return true;
	}
	@Override
	public List<String> getServiceHosts() {
		return getServiceHosts("localhost");
	}
	@Override
	public List<String> getServiceHosts(String defaultValue) {
		return Lists.newArrayList(defaultValue);
	}
	@Override
	public List<String> getDisabledExtensions(List<String> defaultValue) {
		return defaultValue;
	}
	@Override
	public int getConversionViewerPageCutoff() {
		return 5;
	}
	@Override
	public int getMaxScheduledConversionArtifacts() {
		return 50;
	}
	@Override
	public boolean isOfficePluginDownloadEnabled() {
		return true;
	}
	@Override
	public boolean isOutlookPluginDownloadEnabled() {
		return true;
	}
	@Override
	public boolean getAlwaysPassInputFile() {
		return false;
	}
	@Override
	public String getConversionServerScheme() {
		return "http";
	}
	@Override
	public String getConversionServerContext() {
		return "/conversion/v1";
	}
	@Override
	public int getConversionServerPort() {
		return 60018;
	}
	@Override
	public long getConversionStepTimeout(String stepName) {
		return 120000 ;
	}
	@Override
	public long getDefaultStepTimeout() {
		return 120000 ;
	}
}
