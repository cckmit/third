package org.opoo.apps.dv.office;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.dv.ConversionStatus;

public class OfficeConversionStatus implements ConversionStatus {
	
	private boolean pdfGenerated;
    private int previewsGenerated;
    private int previewsTotal;
    private int thumbnailsGenerated;
    private int thumbnailsTotal;
    private boolean converting;
    private String error;
    private Date conversionStartedTime;
    private Date lastConvertionProgressTime;
    private long conversionMetaDataID;

    public OfficeConversionStatus(boolean pdfGenerated, int previewsGenerated, int totalPreviews, 
    		int thumbnailsGenerated, int totalThumbnails,
    		boolean converting, String error, 
    		Date conversionStartedTime, Date lastConvertionProgressTime, 
    		long conversionMetaDataID) {
        this.pdfGenerated = pdfGenerated;
        this.previewsGenerated = previewsGenerated;
        this.previewsTotal = totalPreviews;
        this.thumbnailsGenerated = thumbnailsGenerated;
        this.thumbnailsTotal = totalThumbnails;
        this.converting = converting;
        this.error = error;
        this.conversionStartedTime = conversionStartedTime;
        this.lastConvertionProgressTime = lastConvertionProgressTime;
        this.conversionMetaDataID = conversionMetaDataID;
    }

    public boolean isPdfGenerated() {
        return pdfGenerated;
    }

    public int getPreviewsGenerated() {
        return previewsGenerated;
    }

    public int getPreviewsTotal() {
        return previewsTotal;
    }

    public boolean isConverting() {
        return converting;
    }

    public boolean isError() {
        return !StringUtils.isEmpty(error);
    }

    public String getError() {
        return error;
    }


    public int getThumbnailsTotal() {
		return thumbnailsTotal;
	}

	public int getThumbnailsGenerated() {
        return thumbnailsGenerated;
    }

    public Date getConversionStartedTime() {
        return conversionStartedTime;
    }

    public Date getLastConvertionProgressTime() {
        return lastConvertionProgressTime;
    }

    public boolean isDone() {
        return previewsTotal == previewsGenerated && previewsGenerated > 0
        		&& thumbnailsGenerated == thumbnailsTotal && thumbnailsGenerated > 0;
    }

    public long getConversionMetaDataID() {
        return conversionMetaDataID;
    }
}
