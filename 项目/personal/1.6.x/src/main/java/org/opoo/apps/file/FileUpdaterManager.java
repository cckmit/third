package org.opoo.apps.file;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;

public class FileUpdaterManager {
	
	/**
	 * ��ȡ XLS �ļ����Ż��и�ˢ������
	 * @return
	 */
	public static final FileUpdater getXLSRowOptimalHeightRefreshUpdater(){
		if(Application.isContextInitialized()){
			boolean b = AppsGlobals.getSetupProperty("fileUpdaters.xlsRowOptimalHeightRefreshUpdater.useCommandLine", true);
			String beanName = b ? "commandLineXLSRowOptimalHeightRefreshUpdater" : "xlsRowOptimalHeightRefreshUpdater";
			return Application.getContext().get(beanName, FileUpdater.class);
		}else{
			throw new IllegalStateException("Spring δ��ʼ����");
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
			throw new IllegalStateException("Spring δ��ʼ����");
		}
	}
}
