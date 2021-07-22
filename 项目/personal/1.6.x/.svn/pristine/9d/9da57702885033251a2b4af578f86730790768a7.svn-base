package org.opoo.apps.dv.office.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Test;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.impl.OfficeConversionStorageServiceImpl.ArtifactTypeInfo;

public class ArtifactTypeInfoTest {
	static final FastDateFormat format = FastDateFormat.getInstance("yyyy/MM/dd");
	@Test
	public void test() {
		long start = System.currentTimeMillis();
		System.out.println(format.format(new Date()));
		System.out.println(System.currentTimeMillis() - start);
		ArtifactTypeInfo info = ArtifactTypeInfo.getTypeInfo(OfficeConversionArtifactType.Pdf);
		System.out.println(info.getExt());
	}
}
