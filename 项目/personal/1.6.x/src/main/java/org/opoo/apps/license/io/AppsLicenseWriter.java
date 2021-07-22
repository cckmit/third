package org.opoo.apps.license.io;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseSigner;

public final class AppsLicenseWriter {

	private static final Logger log = Logger.getLogger(AppsLicenseWriter.class.getName());
	private final License license;
	private Document document;

	public AppsLicenseWriter(License license, LicenseSigner signer)	throws LicenseException {
		this.license = license;
		try {
			document = DocumentHelper.parseText(license.toXML());
			Element root = document.getRootElement();
			signer.sign(license);
			Element sig = root.addElement("signature");
			sig.setText(license.getSignature());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new LicenseException((new StringBuilder()).append("Unable to sign license ").append(e.getMessage()).toString(), e);
		}
	}

	public License getLicense() {
		return license;
	}

	public Document getDocument() {
		return document;
	}

	public static String encode(License license, int columns, LicenseSigner signer) throws IOException {
		AppsLicenseWriter lw = new AppsLicenseWriter(license, signer);
		StringWriter writer = new StringWriter();
		lw.write(writer, columns);
		return writer.toString();
	}
	
	public static String encodeSignedLicense(License license, int columns) throws IOException{
		if(StringUtils.isBlank(license.getSignature())){
			throw new IllegalArgumentException("License is not signed.");
		}
		
//		StringWriter writer = new StringWriter();
		Document document = null;
		try {
			document = DocumentHelper.parseText(license.toXML());
		} catch (DocumentException e) {
			throw new LicenseException("Unable to parse license " + e.getMessage(), e);
		}
		Element root = document.getRootElement();
		Element sig = root.addElement("signature");
		sig.setText(license.getSignature());
		String xml = document.asXML();
		String base64 = new String(Base64.encodeBase64(xml.getBytes()));
		
		if(columns > 0){
			base64 = addLineBreaks(base64, columns);
		}
		
		return base64;
		
//		StringReader reader = new StringReader(base64);
//		char buffer[] = new char[32768];
//		int len;
//		while ((len = reader.read(buffer)) != -1){
//			writer.write(buffer, 0, len);
//		}
//		
//		return writer.toString();
	}

	public void write(Writer writer) throws IOException {
		write(writer, 80);
	}

	public void write(Writer writer, int columns) throws IOException {
		String xml = document.asXML();
		String base64 = new String(Base64.encodeBase64(xml.getBytes()));
		if (columns > 0){
			base64 = addLineBreaks(base64, columns);
		}
		StringReader reader = new StringReader(base64);
		char buffer[] = new char[32768];
		int len;
		while ((len = reader.read(buffer)) != -1){
			writer.write(buffer, 0, len);
		}
	}
	

	private static String addLineBreaks(String s, int cols) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < s.length(); i += cols) {
			b.append(s.substring(i, i + cols < s.length() ? i + cols : s.length()));
			b.append("\n");
		}

		return b.toString();
	}

}
