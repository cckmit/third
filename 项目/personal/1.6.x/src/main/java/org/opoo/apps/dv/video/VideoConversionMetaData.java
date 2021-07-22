package org.opoo.apps.dv.video;

import org.opoo.apps.dv.ConversionMetaData;

public interface VideoConversionMetaData extends ConversionMetaData {
	//RUNNING TIME
	
	/**
	 * 片长
	 * @return 片长
	 */
	long getRunningTime();

	/**
	 * 设置视频的片长
	 * @param runningTime 片长秒数
	 */
	void setRunningTime(long runningTime);
}
