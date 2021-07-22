package org.opoo.apps.dvi.office.converter;

public enum GenericConversionError {
    GeneralError(1000, "Generic Conversion Error");


    private int errorCode;
    private String description;

    private GenericConversionError(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public static GenericConversionError getConversionError(int errorCode) {

        for (GenericConversionError code:values()) {
            if (code.getErrorCode() == errorCode) {
                return code;
            }
        }

        return null;

    }

    @Override
    public String toString() {
        return "ConversionError{" + "errorCode=" + errorCode + ", description='" + description + '\'' + '}';
    }
}

