package org.opoo.apps.conversion;

public class ConversionStatus {
	private long conversionRevisionId;
    private int generated;
    private boolean converting;
    private String error;
    private long conversionStartedTime;
    private long lastConvertionProgressTime;
    
	public ConversionStatus(long conversionRevisionId, int generated,
			boolean converting, String error, long conversionStartedTime,
			long lastConvertionProgressTime) {
		super();
		this.conversionRevisionId = conversionRevisionId;
		this.generated = generated;
		this.converting = converting;
		this.error = error;
		this.conversionStartedTime = conversionStartedTime;
		this.lastConvertionProgressTime = lastConvertionProgressTime;
	}
	
	public ConversionStatus() {
		super();
	}
	public long getConversionRevisionId() {
		return conversionRevisionId;
	}
	public void setConversionRevisionId(long conversionRevisionId) {
		this.conversionRevisionId = conversionRevisionId;
	}
	public int getGenerated() {
		return generated;
	}
	public void setGenerated(int generated) {
		this.generated = generated;
	}
	public boolean isConverting() {
		return converting;
	}
	public void setConverting(boolean converting) {
		this.converting = converting;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public long getConversionStartedTime() {
		return conversionStartedTime;
	}
	public void setConversionStartedTime(long conversionStartedTime) {
		this.conversionStartedTime = conversionStartedTime;
	}
	public long getLastConvertionProgressTime() {
		return lastConvertionProgressTime;
	}
	public void setLastConvertionProgressTime(long lastConvertionProgressTime) {
		this.lastConvertionProgressTime = lastConvertionProgressTime;
	}
}
