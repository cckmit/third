package org.opoo.apps.dv.video;

import org.opoo.apps.dv.ConversionMetaData;

public interface VideoConversionMetaData extends ConversionMetaData {
	//RUNNING TIME
	
	/**
	 * Ƭ��
	 * @return Ƭ��
	 */
	long getRunningTime();

	/**
	 * ������Ƶ��Ƭ��
	 * @param runningTime Ƭ������
	 */
	void setRunningTime(long runningTime);
}
