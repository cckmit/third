package org.opoo.apps.license.hasp;

import Aladdin.Hasp;
import Aladdin.HaspStatus;

public class NoOpCallback implements HaspCallback<Integer> {
	
	public Integer doInHasp(Hasp hasp) throws HaspException {
		return HaspStatus.HASP_STATUS_OK;
	}
	
	private static NoOpCallback instance;

	public static NoOpCallback getInstance(){
		if(instance == null){
			instance = new NoOpCallback();
		}
		return instance;
	}
}
