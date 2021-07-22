package org.opoo.apps.service.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;

public class MimeTypeManagerImplTest {

	@Test
	public void test() throws IOException {
		MagicMimeTypeIdentifier mmt = new MagicMimeTypeIdentifier("/mimetypes.xml");
		MimeTypeManagerImpl managerImpl = new MimeTypeManagerImpl();
		managerImpl.setMagicMimeTypeIdentifier(mmt);
		
		String type = managerImpl.getExtensionMimeType("assd.zip");
		System.out.println(type);
		assertEquals("application/zip", type);
		
		InputStream stream = MimeTypeManagerImpl.class.getClassLoader().getResourceAsStream("conversion/pdf.doc");
		System.out.println(stream);
		String type2 = managerImpl.getFileMimeType("pdf.doc", "application/x", stream);
		System.out.println(type2);
		assertEquals("application/pdf", type2);
	}

}
