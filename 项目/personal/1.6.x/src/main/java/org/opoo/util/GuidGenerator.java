package org.opoo.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GuidGenerator
{

    private static final char HEX_TABLE[][];
    private static String smHostIpAddress;
	private static Log log = LogFactory.getLog(GuidGenerator.class);
    public static final int TYPE_CODE_START = 0;
    public static final int TYPE_CODE_LENGTH = 4;
    public static final int TYPE_CODE_END = 4;
    public static final int GUID_BASE_START = 4;
    public static final int GUID_BASE_LENGTH = 24;
    public static final int GUID_BASE_END = 28;
    public static final int LENGTH = 28;
    public static final int GUID_HASH_BEGIN = 12;
    public static final int GUID_HASH_END = 28;

    public GuidGenerator()
    {
    }

    private static void appendHex(StringBuffer buffer, byte byteValue)
    {
        int intValue = byteValue < 0 ? byteValue + 256 : ((int) (byteValue));
        char hex[] = HEX_TABLE[intValue];
        buffer.append(hex[0]);
        buffer.append(hex[1]);
    }

    private static void appendHex(StringBuffer buffer, byte bytes[])
    {
        for(int index = 0; index < bytes.length; index++)
            appendHex(buffer, bytes[index]);
    }

    private static void validateTypeCode(String typeCode)
    {
        if(typeCode == null)
            throw new IllegalArgumentException("GUID type not specified.");
        if(typeCode.length() != 4)
        {
            String message = (new StringBuilder()).append("Invalid GUID type \"").append(typeCode).append("\" specified.").toString();
            throw new IllegalArgumentException(message);
        } else
        {
            return;
        }
    }

    public static String newGuid()
    {
        return newGuid("guid");
    }

    public static String newGuid(String typeCode)
    {
        validateTypeCode(typeCode);
        StringBuffer buffer = new StringBuffer();
        buffer.append(typeCode);
        buffer.append(smHostIpAddress);
        
        byte uidBytes[] = null;
        UID uid = new UID();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(18);
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        try {
			uid.write(dataStream); 
			uidBytes = byteStream.toByteArray();
		} catch (IOException e) {
			throw new InternalError(e.toString());
		}finally{
			IOUtils.close(dataStream);
			IOUtils.close(byteStream);
		}
       
        for(int index = 6; index < 14; index++)
            appendHex(buffer, uidBytes[index]);

        return buffer.toString();
    }

    static 
    {
        HEX_TABLE = new char[256][2];
        char hex[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'A', 'B', 'C', 'D', 'E', 'F'
        };
        int byteValue = 0;
        for(char digit0 = '\0'; digit0 < '\020'; digit0++)
        {
            for(char digit1 = '\0'; digit1 < '\020'; digit1++)
            {
                HEX_TABLE[byteValue][0] = hex[digit0];
                HEX_TABLE[byteValue][1] = hex[digit1];
                byteValue++;
            }

        }

        try
        {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte addressBytes[] = inetAddress.getAddress();
            StringBuffer buffer = new StringBuffer();
            appendHex(buffer, addressBytes);
            smHostIpAddress = buffer.toString();
            if(!Character.isDigit(smHostIpAddress.charAt(0)))
            {
                String digitChar = Integer.toString((int)(Math.random() * 10D));
                smHostIpAddress = (new StringBuilder()).append(digitChar.substring(0, 1)).append(smHostIpAddress.substring(1)).toString();
            }
        }
        catch(Exception exception)
        {
            String message = "GUID Generator failed to initialize.";
            log.error(message, exception);
            throw new InternalError(exception.toString());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////
    public static File buildDirectoryPath(File rootDirectory, char prefix[])
    {
        StringBuffer path = new StringBuffer(rootDirectory.getAbsolutePath());
        for(int i = 0; i < prefix.length; i++)
        {
            path.append(File.separatorChar);
            path.append(prefix, 0, i + 1);
        }

        return new File(path.toString());
    }

    public static char[] generatePrefix()
    {
        char prefix[] = new char[4];
        for(int i = 0; i < prefix.length; i++)
            prefix[i] = (char)(97 + (int)(Math.random() * 26D));
        return prefix;
    }

    public static GuidKey generateKey(char prefix[])
    {
        return new GuidKey(GuidGenerator.newGuid(new String(prefix)));
    }
    
    public static void mkFileStorageDirs(File dir)
    {
        for(int i = 0; i < 4; i++)
            if(dir.exists() || dir.mkdirs())
                return;

        throw new RuntimeException((new StringBuilder()).append("Unable to create file storage directories ").append(dir.getAbsolutePath()).toString());
    }
    
    public static void main(String[] args) throws UnknownHostException{
    	String guid = GuidGenerator.newGuid();
    	System.out.println(guid);
    	System.out.println(UUID.randomUUID());
    	
        char prefix[] = generatePrefix();
        GuidKey storageKey = generateKey(prefix);
        File dir = buildDirectoryPath(new File("e:/work.home/filestorage"), prefix);
        System.out.println(dir);
        mkFileStorageDirs(dir);
        File file = new File(dir, storageKey.getGuid());
        System.out.println(file);
        
        System.out.println(InetAddress.getLocalHost());
    }
}

