package org.opoo.apps.license.hasp;

import Aladdin.Hasp;
import Aladdin.HaspStatus;

public class HaspUtils {
	
	public static <T> T execute(long featureId, HaspCallback<T> callback) throws HaspException{
		Hasp hasp = new Hasp(featureId);
		hasp.loginScope(HaspConfig.LOCAL_SCOPE, HaspConfig.VENDOR_CODE);
		int status = hasp.getLastError();
		if(status != HaspStatus.HASP_STATUS_OK){
//			System.out.println(status);
			throw new HaspException(status, "login");
		}
		try{
			return callback.doInHasp(hasp);
		}finally{
			hasp.logout();
			status = hasp.getLastError();
			if(status != HaspStatus.HASP_STATUS_OK){
				throw new HaspException(status, "logout");
			}
		}
	}
	
	public static void main(String[] args){
		HaspUtils.execute(11, NoOpCallback.getInstance());
	}

	
/*	
	public static void main(String[] args) throws Exception{
		byte[] status = HaspUtils.execute(Hasp.HASP_DEFAULT_FID, new HaspCallback<byte[]>(){
			public byte[] doInHash(Hasp hasp) throws HaspException {
				return null;
			}
		});
		
		//System.out.println(new String(status));
		if(status != null){
			//System.out.println(new String(Base64.encodeBase64(status)));
			System.out.println(new String(status));
		}
		
		
		String s = "";
		byte[] data = Base64.decodeBase64(s.getBytes());
		int i = 0;
		for(byte b: data){
			System.out.print(b + ", ");
			i++;
			if(i >= 10){
				System.out.println();
				i = 0;
			}
		}
		
		
		final byte[] bb = {-38, 69, 48,};
		
		
		status = HaspUtils.execute(Hasp.HASP_DEFAULT_FID, new HaspCallback<byte[]>(){
			public byte[] doInHash(Hasp hasp) throws HaspException {
				hasp.decrypt(bb);
				return bb;
			}
		});
		if(status != null){
			System.out.println(new String(status));
		}
	}
	
*/
}
