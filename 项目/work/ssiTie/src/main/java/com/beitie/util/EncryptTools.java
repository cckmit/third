package com.beitie.util;

import crypto.sm.SM4;
import crypto.sm.SMUtil;
import org.apache.commons.codec.binary.Base64;


public class EncryptTools {

	private static String CHARSET = "UTF-8";
	private static SM4 sm4 = new SM4();
	private static String keyStr="NANNINGZULIN2021";
	/**
	 *
	 * SM4加密
	 * @param dataStr
	 * @return
	 */
	public static String SM4Encode(String dataStr) {
		try {
			return Base64.encodeBase64String(sm4.encryptEcb(SMUtil.PKCS7Padding(dataStr.getBytes(CHARSET),dataStr.getBytes(CHARSET).length), keyStr.getBytes(CHARSET)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return dataStr;
		}
	}
	
	/**
	 * SM4解密
	 * @param dataStr
	 * @return
	 */
	public static String SM4Decode(String dataStr){
		try {
			byte[] plantext= Base64.decodeBase64(dataStr.getBytes(CHARSET));
			byte[] decodeData=sm4.decryptEcb(plantext, keyStr.getBytes(CHARSET));
			byte[] aa=SMUtil.PKCS7Cutting(decodeData,decodeData.length);
			return new String(aa,CHARSET);
		}catch(Exception e){
			e.printStackTrace();
			return dataStr;
		}
	}

	public static void main(String[] args){
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
				"<Response success=\"true\" msg=\"*\">\n" +
				"<lsxx>\n" +
				"<jylsh>交易流水号</jylsh>\n" +
				"<jyrq>交易日期</jyrq>\n" +
				"<jysj>交易时间</jysj>\n" +
				"<dfzh>对方账户</dfzh>\n" +
				"<dfhm>对方户名</dfhm>\n" +
				"<jyqd>交易渠道</jyqd>\n" +
				"<szbz>收支标志</szbz>\n" +
				"<jyqje>2200.00</jyqje>\n" +
				"<jyje>500.00</jyje>\n" +
				"<jyhje>1700.00</jyhje>\n" +
				"<zy>摘要</zy>\n" +
				"</lsxx>" +
				"<lsxx>\n" +
				"<jylsh>交易流水号</jylsh>\n" +
				"<jyrq>交易日期</jyrq>\n" +
				"<jysj>交易时间</jysj>\n" +
				"<dfzh>对方账户</dfzh>\n" +
				"<dfhm>对方户名</dfhm>\n" +
				"<jyqd>交易渠道</jyqd>\n" +
				"<szbz>收支标志</szbz>\n" +
				"<jyqje>2200.00</jyqje>\n" +
				"<jyje>500.00</jyje>\n" +
				"<jyhje>1700.00</jyhje>\n" +
				"<zy>摘要</zy>\n" +
				"</lsxx>" +
				"</Response>";
        try {
            String si = "<?xml version='1.0' encoding='UTF-8'?><Request><xzhq>123456789</xzhq><qkms>none</qkms><hzlsh>31000101</hzlsh><hzjg>Done</hzjg></Request>";
            String s = EncryptTools.SM4Encode(si);
			System.out.println(s);
            String ss = EncryptTools.SM4Decode(s);
            System.out.println(ss+"==");
            String s2 = EncryptTools.SM4Encode(data);
			System.out.println(s2);
            String ss2 = EncryptTools.SM4Decode(s2);
			System.out.println(ss2);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
