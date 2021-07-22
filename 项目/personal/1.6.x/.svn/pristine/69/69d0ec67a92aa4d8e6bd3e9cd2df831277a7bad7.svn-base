package org.opoo.apps.cache.coherence;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Hex;

import com.tangosol.coherence.component.application.console.Coherence;
import com.tangosol.run.xml.SimpleParser;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.util.Base;
import com.tangosol.util.Binary;
import com.tangosol.util.UID;
import com.tangosol.util.WrapperException;

public class CoherenceTest extends TestCase {

	public static final String TITLE =Coherence.TITLE;
	
	public void etestLicense() throws IOException{
		String sLicenseFile = "coherence-grid.xml";
		InputStream stream = null;
		ClassLoader loader = getClass().getClassLoader();
		URL url = loader.getResource(sLicenseFile);
        if(url != null){
            try
            {
                //stream = url.openStream();
                stream = new FileInputStream("D:\\m2.repo\\oracle-coherence\\coherence\\3.5-b459\\coherence-grid.xml");
            }
            catch(IOException ioexception) { }
        }
        
        
        if(stream == null)
        {
            String sErrorMsg = "Edition file (" + sLicenseFile + ") is missing from the OC libraries";
//            Component._trace(sErrorMsg, 1);
            throw new RuntimeException(sErrorMsg);
        }
       
        
        String sUrl = url.toString();
        String sXml;
        try
        {
            sXml = new String(Base.read(stream), "ISO-8859-1");
        }
        catch(IOException e)
        {
            throw new WrapperException(e, "An exception occurred while reading the license data");
        }
        
        
        if((sXml != null) ? sXml.length() == 0 : true)
        {
            String sErrorMsg = "Edition data (" + sUrl + ") is missing or empty.";
//            Component._trace(sErrorMsg, 1);
            throw new RuntimeException(sErrorMsg);
        }
        
        //Component._trace("Loaded edition data from \"" + sUrl + "\"", 6);
        XmlDocument xmlLicenses;
        try
        {
            xmlLicenses = (new SimpleParser()).parseXml(sXml);
        }
        catch(IOException e)
        {
            throw new WrapperException(e, "An exception occurred while parsing the license data");
        }
        
        
        
        Signature signature = null;
        try
        {
            //InputStream streamCert = loader.getResourceAsStream(Coherence.FILE_CFG_CERTIFICATE);
            InputStream streamCert = new FileInputStream("E:\\fx\\keystore\\rfs.cer");
        	CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate cert = factory.generateCertificate(streamCert);
            signature = Signature.getInstance("SHA1withDSA");
            signature.initVerify(cert.getPublicKey());
            System.out.println(signature);
        }
        catch(Exception e)
        {
            throw Base.ensureRuntimeException(e, "Error during license validation");
        }
        
        
        
        
        
        
        Iterator<?> it = xmlLicenses.getElements("license");
        System.out.println(it);
        while(it.hasNext()){
        	XmlElement el = (XmlElement) it.next();
        	//System.out.println(el);
        	process(el, signature);
        }
        //xml.ensureElement("license-list").getElementList().addAll(xmlLicenses.getElementList());
	}
    
	
	
	@SuppressWarnings("deprecation")
	public static long parseDate(String s)
    {
        if((s != null) ? s.length() == 0 : true)
        {
            return 0L;
        } else
        {
            String asParts[] = Base.parseDelimitedString(s, '-');
            return Date.UTC(Integer.parseInt(asParts[0]) - 1900, Integer.parseInt(asParts[1]) - 1, Integer.parseInt(asParts[2]), 0, 0, 0);
        }
    }
    
	private void process(XmlElement xmlLicense, Signature signature){
		String sLicensee = xmlLicense.getSafeElement("licensee").getString(null);
        String sAgreement = xmlLicense.getSafeElement("agreement").getString(null);
        String sMode = xmlLicense.getSafeElement("type").getString(null);
        String sFromDate = xmlLicense.getSafeElement("from-date").getString(null);
        String sToDate = xmlLicense.getSafeElement("to-date").getString(null);
        String sRenewDate = xmlLicense.getSafeElement("maintenance-renewal-date").getString(null);
        int cSeats = xmlLicense.getSafeElement("max-seats").getInt();
        int cUsers = xmlLicense.getSafeElement("max-users").getInt();
        String sSite = xmlLicense.getSafeElement("site").getString(null);
        int cServers = xmlLicense.getSafeElement("max-servers").getInt();
        int cSockets = xmlLicense.getSafeElement("max-sockets").getInt();
        int cCores = xmlLicense.getSafeElement("max-cpus").getInt();
        String sUid = xmlLicense.getSafeElement("id").getString(null);
        String sKey = xmlLicense.getSafeElement("key").getString(null);
        String sSig = xmlLicense.getSafeElement("signature").getString(null);
        String sClass = null;
        String sSoftware = null;
        String sEdition = null;
        if(sUid == null)
        {
            String sMsg = "You are using an out-of-date license format; " + "please contact Oracle to obtain a replacement license.";
//            Component._trace(sMsg, 1);
            throw new RuntimeException(sMsg);
        }
        UID uid = new UID(sUid);
        long lDateFrom = (sFromDate != null) ? parseDate(sFromDate) : 0L;
        long lDateTo = (sToDate != null) ? parseDate(sToDate) : 0L;
        long lDateRenew = (sRenewDate != null) ? parseDate(sRenewDate) : 0L;
        int nMode = -1;
        if((sMode != null) ? sMode.length() > 0 : false)
            switch(sMode.charAt(0))
            {
            case 101: // 'e'
                nMode = 0;
                break;

            case 100: // 'd'
                nMode = 1;
                break;

            case 112: // 'p'
                nMode = 2;
                break;
            }
        if(nMode < 0)
        {
            String sErrorMsg = "Invalid license mode: \"" + sMode + "\"";
//            Component._trace(sErrorMsg, 1);
            throw new RuntimeException(sErrorMsg);
        }
        if(sSig == null)
        {
            if(sKey == null)
                return;
        } else
        {
            sSoftware = xmlLicense.getSafeElement("software").getString();
            try
            {
                StringBuffer sb = new StringBuffer();
                sb.append(sSoftware).append(sLicensee).append((sAgreement != null) ? sAgreement : "").append(nMode).append(lDateFrom).append(lDateTo).append(lDateRenew).append(cSeats).append(cUsers).append(sSite).append(cServers).append(cSockets).append(cCores).append(uid);
                ByteArrayOutputStream streamRaw = new ByteArrayOutputStream();
                DataOutputStream streamSig = new DataOutputStream(streamRaw);
                streamSig.writeUTF(sb.toString());
                
                System.out.println("指纹： " + sb.toString()
                		+ "\n签名后：" + sign(sb.toString()));
                signature.update(streamRaw.toByteArray());
                if(signature.verify(Base.parseHex(sSig)) ^ true)
                {
                    String sMsg = "The " + sSoftware + " license signature " + "is not valid, please contact Oracle.";
                    System.err.println(sMsg);
                    return;
                }else{
                	System.out.println("签名验证OK：" + sb);
                }
            }
            catch(Exception e)
            {
                throw Base.ensureRuntimeException(e, "Error validating license signature for " + sSoftware);
            }
            if(sSoftware.endsWith(": Grid Edition"))
            {
                sEdition = "GE";
                sClass = "com.tangosol.license.CoherenceDataGridEdition";
            } else
            if(sSoftware.endsWith(": Enterprise Edition") ? true : sSoftware.endsWith(": Application Edition"))
            {
                sEdition = "EE";
                sClass = "com.tangosol.license.CoherenceApplicationEdition";
            } else
            if(sSoftware.endsWith(": Standard Edition") ? true : sSoftware.endsWith(": Caching Edition"))
            {
                sEdition = "SE";
                sClass = "com.tangosol.license.CoherenceCachingEdition";
            } else
            if(sSoftware.endsWith(": Compute Client"))
            {
                sEdition = "CC";
                sClass = "com.tangosol.license.CoherenceComputeClient";
            } else
            if(sSoftware.endsWith(": Real-Time Client"))
            {
                sEdition = "RTC";
                sClass = "com.tangosol.license.CoherenceRealTimeClient";
            } else
            if(sSoftware.endsWith(": Data Client"))
            {
                sEdition = "DC";
                sClass = "com.tangosol.license.CoherenceDataClient";
            }
        }
        if((sClass == null) ? sKey != null : false)
        {
            try
            {
                sClass = (new Binary(Base.parseHex(sKey.substring(9, sKey.length() - 1)))).getBufferInput().readUTF();
            }
            catch(IOException ioexception2)
            {
                return;
            }
            if(sClass.endsWith(".CoherenceEnterprise"))
            {
                sSoftware = TITLE + ": Enterprise Edition";
                sEdition = "EE";
                sClass = "com.tangosol.license.CoherenceApplicationEdition";
            } else
            if(sClass.endsWith(".Coherence"))
            {
                sSoftware = TITLE + ": Standard Edition";
                sEdition = "SE";
                sClass = "com.tangosol.license.CoherenceCachingEdition";
            } else
            {
                if(sClass.endsWith(".CoherenceLocal") ? false : !sClass.endsWith(".ClientAccess"))
                	return;
                sSoftware = TITLE + ": Data Client";
                sEdition = "DC";
                sClass = "com.tangosol.license.CoherenceDataClient";
            }
        }
        boolean fOem = sLicensee.startsWith("OEM:");
        String sApp = null;
        if(fOem)
        {
            sLicensee = sLicensee.substring(4);
            int ofColon = sLicensee.lastIndexOf(':');
            if(ofColon > 0)
            {
                sApp = sLicensee.substring(ofColon + 1);
                sLicensee = sLicensee.substring(0, ofColon);
            }
        }
	}
	
	
	
	public void testSign() throws Exception{
//		KeyStore keyStore = loadKeyStore("E:\\fx\\keystore\\rfs-keystore", "asdfasdf");
//		Key key = keyStore.getKey("rfs", "asdfasdf".toCharArray());
//		System.out.println(key);
//		if(key instanceof PrivateKey){
//			Signature sig = Signature.getInstance("SHA1withDSA");
//			sig.initSign((PrivateKey) key);
//			System.out.println(sig);
//			
//			
//		}
		
//		String fp = "Oracle Coherence: Grid Editionn/a200000null0000xF0A800CC0000011290D2E0B874855B8F";
//		String sign = sign(fp);
//		System.out.println(sign);
//        
//		
//		boolean verify = verify(fp, sign);
//		System.out.println(verify);
		
		
		UID uid = new UID("0xF0A800CC0000011290D2E0B874855B8F");
		int address = uid.getAddress();
		int count = uid.getCount();
		long timestamp = uid.getTimestamp();
		System.out.println(address + ".." + count + ".." + new Date(timestamp));
	}
	
	
	public static byte[] toBytes(String string) throws IOException{
		ByteArrayOutputStream streamRaw = new ByteArrayOutputStream();
        DataOutputStream streamSig = new DataOutputStream(streamRaw);
        streamSig.writeUTF(string);
        return streamRaw.toByteArray();
	}
	
	public static boolean verify(String input, String sign) throws Exception{
		InputStream streamCert = new FileInputStream("E:\\fx\\keystore\\rfs.cer");
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Certificate cert = factory.generateCertificate(streamCert);
		PublicKey key = cert.getPublicKey();

		Signature sig = Signature.getInstance("SHA1withDSA");
		sig.initVerify(key);
		System.out.println(sig);
		
		sig.update(toBytes(input));//(input.getBytes());
		return sig.verify(Hex.decodeHex(sign.toCharArray()));
	}
	
	public static String sign(String input) throws Exception{
		KeyStore keyStore = loadKeyStore("E:\\fx\\keystore\\rfs-keystore", "asdfasdf");
		Key key = keyStore.getKey("rfs", "asdfasdf".toCharArray());
		//System.out.println(key);
		if(key instanceof PrivateKey){
			Signature sig = Signature.getInstance("SHA1withDSA");
			sig.initSign((PrivateKey) key);
			System.out.println(sig);
			sig.update(toBytes(input));//(input.getBytes());
			return new String(Hex.encodeHex(sig.sign()));
		}
		return null;
	}
	
	
	private static KeyStore loadKeyStore(String store, String sPass) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore myKS = KeyStore.getInstance("JKS");
		FileInputStream fis = new FileInputStream(store);
		myKS.load(fis, sPass.toCharArray());
		fis.close();
		return myKS;
	}
	
	public static PublicKey getPublicKey(String store, String sPass, String alias) 
		throws KeyStoreException, NoSuchAlgorithmException,	CertificateException, IOException{
			KeyStore ks = loadKeyStore(store, sPass);
		Certificate cert = ks.getCertificate(alias);
		return cert.getPublicKey();
	}

	public static KeyPair getKeyPair(String store, String sPass, String kPass, String alias)
			throws CertificateException, IOException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException {

		KeyStore ks = loadKeyStore(store, sPass);
		//KeyPair keyPair = null;
		Key key = null;
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		if (ks.containsAlias(alias)) {
			key = ks.getKey(alias, kPass.toCharArray());
			if (key instanceof PrivateKey) {
				Certificate cert = ks.getCertificate(alias);
				publicKey = cert.getPublicKey();
				privateKey = (PrivateKey) key;
				return new KeyPair(publicKey, privateKey);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	public void testLoadDictionary(){
		loadDictionary();
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected void loadDictionary()
    {
        SimpleParser sp = new SimpleParser();
        ClassLoader cl = (com.tangosol.util.Base.class).getClassLoader();
        XmlElement xmlDoc;
        try
        {
            java.io.InputStream in = cl.getResourceAsStream("processor-dictionary.xml");
            in = new FileInputStream("D:\\m2.repo\\oracle-coherence\\coherence\\3.5-b459\\processor-dictionary.xml");
            if(in == null)
                throw new Exception("processor-dictionary.xml not found");
            xmlDoc = sp.parseXml(in);
            if(!xmlDoc.getName().equals("processor-dictionary"))
                throw new Exception("processor-dictionary.xml does not contain processor-dictionary element");
        }
        catch(Exception e)
        {
        	System.err.println(e);
            return;
        }
        Signature signature;
        try
        {
            java.io.InputStream streamCert = cl.getResourceAsStream("tangosol.cer");
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate cert = factory.generateCertificate(streamCert);
            signature = Signature.getInstance("SHA1withDSA");
            signature.initVerify(cert.getPublicKey());
        }
        catch(Exception e)
        {
            return;
        }
        List listTemplates = new ArrayList();
        Iterator i = xmlDoc.getElements("processor-template");
        do
        {
            if(!i.hasNext())
                break;
            XmlElement xml = (XmlElement)i.next();
            HashMap map = new HashMap();
            List list = xml.getElementList();
            StringBuffer sbConcat = new StringBuffer();
            XmlElement xmlExUnits = null;
            String sSig = null;
            for(ListIterator j = list.listIterator(); j.hasNext();)
            {
                XmlElement xmlSub = (XmlElement)j.next();
                String sName = xmlSub.getName();
                String sValue = xmlSub.getString();
                if(sName.equals("signature"))
                {
                    sSig = sValue;
                } else
                {
                    sbConcat.append(xmlSub);
                    if(sName.equals("execution-units"))
                        xmlExUnits = xmlSub;
                    if(xmlSub.getSafeAttribute("regex").getBoolean())
                    {
                        Pattern pat = Pattern.compile(sValue);
                        map.put(sName, pat);
                    } else
                    {
                        map.put(sName, sValue);
                    }
                }
            }

            if(sSig != null && xmlExUnits != null)
                try
                {
                    ByteArrayOutputStream streamRaw = new ByteArrayOutputStream();
                    DataOutputStream streamSig = new DataOutputStream(streamRaw);
                    streamSig.writeUTF(sbConcat.toString());
                    
                    System.out.println("指纹：" + sbConcat + "\n签名：" + sign(sbConcat.toString()));
                    signature.update(streamRaw.toByteArray());
                    if(signature.verify(Base.parseHex(sSig)))
                    {
                    	System.out.println("验证通过");
                        listTemplates.add(map);
                        int cThreads = xmlExUnits.getSafeAttribute("thread-count").getInt(0);
                        int cExUnits = xmlExUnits.getInt(0);
                        if(cThreads > 0)
                        {
                            Map mapClone = (Map)map.clone();
                            mapClone.put("execution-units", Integer.toString(cExUnits - cThreads));
                            listTemplates.add(mapClone);
                        }
                    } else
                    {
                        System.err.println("Skipping processor template with invalid signature:\n" + xml);
                    }
                }
                catch(Exception e)
                {
                }
        } while(true);
    }
}