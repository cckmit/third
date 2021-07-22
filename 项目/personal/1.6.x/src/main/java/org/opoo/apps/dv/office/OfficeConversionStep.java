package org.opoo.apps.dv.office;

import java.util.HashMap;
import java.util.Map;

import org.opoo.apps.dv.ConversionStep;

public enum OfficeConversionStep implements ConversionStep{

    Uploaded (0x01),
    PdfGeneration (0x02),
    ThumbnailGeneration (0x04),
    PreviewGeneration (0x08),
    TextIndexing (0x10),
    Unsupported(9998);

    private static final Map<Integer, OfficeConversionStep> STEPS;

    static {
        STEPS = new HashMap<Integer, OfficeConversionStep>();
        for (OfficeConversionStep step : OfficeConversionStep.values()) {
            STEPS.put(step.getValue(), step);
        }
    }

    private int bitMask;

    OfficeConversionStep(int mask) {
        this.bitMask = mask;
    }

    public int getValue() {
        return this.bitMask;
    }
  
    /**
     * Returns the correct step for the mask or null if the step for
     * the mask cant' be found
     * @param mask
     * @return
     */
    public static OfficeConversionStep getByMask(int mask) {
        return STEPS.get(mask);
    }

	public String getName() {
		return name();
	}
}