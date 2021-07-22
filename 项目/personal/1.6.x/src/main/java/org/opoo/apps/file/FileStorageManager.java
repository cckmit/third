package org.opoo.apps.file;

public interface FileStorageManager {
	/**
	 * 获取指定名称的FileStorage。
	 * 
	 * @param storeName
	 * @return
	 */
	FileStorage getFileStorage(String storeName);
	
	/**
	 * 获取当前活动的FileStorage。
	 * 
	 * @return
	 */
	FileStorage getActiveFileStorage();
}
