package org.opoo.apps.file;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;

public class FileUpdaterManager {
	
	/**
	 * 获取 XLS 文件的优化行高刷新器。
	 * @return
	 */
	public static final FileUpdater getXLSRowOptimalHeightRefreshUpdater(){
		if(Application.isContextInitialized()){
			boolean b = AppsGlobals.getSetupProperty("fileUpdaters.xlsRowOptimalHeightRefreshUpdater.useCommandLine", true);
			String beanName = b ? "commandLineXLSRowOptimalHeightRefreshUpdater" : "xlsRowOptimalHeightRefreshUpdater";
			return Application.getContext().get(beanName, FileUpdater.class);
		}else{
			throw new IllegalStateException("Spring 未初始化。");
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static final FileUpdater getFileUpdater(String name){
		if(Application.isContextInitialized()){
			return Application.getContext().get(name, FileUpdater.class);
		}else{
			throw new IllegalStateException("Spring 未初始化。");
		}
	}
}
