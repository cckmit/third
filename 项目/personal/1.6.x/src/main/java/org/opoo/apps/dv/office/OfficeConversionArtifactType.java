package org.opoo.apps.dv.office;

import org.opoo.apps.dv.ConversionArtifactType;

public enum OfficeConversionArtifactType implements ConversionArtifactType {
	File, Pdf, Preview, Thumbnail, Text;

	public String getName() {
		return name();
	}
	
	/**
	 * 不同于 valueOf,该方法大小写不敏感。
	 * 
	 * @param type
	 * @return
	 */
	public static OfficeConversionArtifactType getType(String type) {
		for (OfficeConversionArtifactType storageObjectType : OfficeConversionArtifactType.values()) {
			if (storageObjectType.name().equalsIgnoreCase(type)) {
				return storageObjectType;
			}
		}
		return null;
	}
}
