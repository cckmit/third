package org.opoo.apps.dvi.video;

import org.opoo.apps.dvi.ConversionMetaData;

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
