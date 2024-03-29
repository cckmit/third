/*
 * $Id: PrintHandlerImpl.java 6307 2013-09-16 07:43:35Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.file.FileUpdater;
import org.opoo.apps.file.FileUpdaterManager;
import org.opoo.apps.service.AttachmentManager;
import org.springframework.util.Assert;

import cn.redflagsoft.base.io.DefaultExportViewManager;
import cn.redflagsoft.base.io.ExcelExportView;
import cn.redflagsoft.base.io.ExportViewManager;
import cn.redflagsoft.base.io.FileExportView;
import cn.redflagsoft.base.service.PrintHandler;

/**
 * 打印处理器。
 * 
 * @author Alex Lin
 *
 */
public class PrintHandlerImpl implements PrintHandler {
	protected static final Log log = LogFactory.getLog(PrintHandlerImpl.class);
	
//	private static final String excelRowHeightRefreshServiceNameKEY 
//				= "attachments.refreshService.excelRowHeightRefreshService";
	
	private ExportViewManager exportViewManager = new DefaultExportViewManager();
	private AttachmentManager attachmentManager;
	//private ApplicationContext applicationContext;
	//private FileTypeConverterManager fileTypeConverterManager;
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.PrintHandler#preparePrint(org.opoo.apps.Model)
	 */
	public Model preparePrint(String config, Model model, boolean xlsExport) throws Exception {
		return preparePrint(config, model, xlsExport, true);
	}
	public Model preparePrint(String config, Model model, boolean xlsExport, 
			boolean requireRefreshExcelRowHeight) throws Exception{
		Assert.notNull(config, "配置不能为空");
		
		if(config.contains("／")){
			config = StringUtils.replaceChars(config, '／', '/');
			log.info("路径中包含 '／'， 替换后：" + config);
		}
		
		
		verityModel(model);
		
		
		//导出
		FileExportView exportView = (FileExportView) exportViewManager.getExportView(config);
		File file = exportView.doExportToFile(model);
		
		//excel文件
		if(requireRefreshExcelRowHeight && exportView instanceof ExcelExportView){
			//在早期，导出时需要xls原始文件，而打印则不需要，所以在这里根据导出还是
			//打印来优化操作。
			//2010-09-29 在考虑到打印预览时有时候也需要下载原始的文件，所以这里改为
			//默认都保存xls文件。
			file = refreshExcelRowHeight(file, /*xlsExport*/ true);
		}
		
		//保存并生成excel
		Attachment a = attachmentManager.store(file, file.getName(), exportView.getMimeType());
		
		deleteTempFile(file);
		
		Model m = new Model();
		m.setData(a);
		m.setMessage(xlsExport ? "生成导出文件成功！" : "生成可打印文件成功！");
		return m;
	}
	
	
	/**
	 * 必须刷新行高还是可选？
	 * 目前实现时可选的，如果没有配置，不影响功能正常执行。
	 * @param file
	 * @return
	 */
	private File refreshExcelRowHeight(File file, boolean xlsExport){
		
		FileUpdater updater = null;
		try {
			updater = FileUpdaterManager.getXLSRowOptimalHeightRefreshUpdater();
		} catch (Exception e) {
			log.warn("获取行高刷新器出错", e);
			return file;
		}
		
		if(updater == null){
			//excelRowHeightRefreshServiceName = "excelRowHeightRefreshService";
			//throw new IllegalArgumentException("没有指定Excel行高刷新器名称");
			
			log.warn("没有指定 XLS 行高刷新器名称，跳过这个刷新功能");
			return file;
		}
		
		if(log.isDebugEnabled()){
			log.debug("XLS 行高刷新器：" + updater);
		}
		
		
		File old = file;
		//String filename = FilenameUtils.removeExtension(old.getAbsolutePath()) + "__.xls";
		String filename = FilenameUtils.removeExtension(old.getAbsolutePath());
		//如果要导出XLS，就必须生成原始XLS文件，否则不必生成，直接转成PDF
		if(xlsExport){
			filename += "__.xls";
		}else{
			filename += ".pdf";
		}
				
		file = new File(filename);
//		ThreadLocalTempFileUtils.addTempFile(file);
		
		try{
			//rs.refresh(old, file);
			//refresh(rs, old, file);
			updater.update(old, file);
			if(log.isDebugEnabled()){
				log.debug("刷新文件：" + file.getAbsolutePath());
			}
			
			//deleteTempFile(old);
			return file;
		}catch(RuntimeException e){
			deleteTempFile(file);
			throw e;
		}finally{
			deleteTempFile(old);
		}
	}
	
//	private void refresh(RefreshService rs, File input, File output){
//		if(rs instanceof ExcelRowHeightRefreshService){
//			ExcelRowHeightRefreshService ers = (ExcelRowHeightRefreshService) rs;
//			refresh(ers.getConnectionProvider(), input, output);
//		}else{
//			rs.refresh(input, output);
//		}
//	}
//	
//	/**
//	 * 由于原来的ExcelRowHeightRefreshService无法转化类型，所以这里重写刷新方法。
//	 * 
//	 * @param connectionProvider
//	 */
//	private void refresh(XConnectionProvider connectionProvider, File input, File output){
//		XDocumentConverter converter = connectionProvider.getConnection().getDocumentConverter();
//		if(converter instanceof XComponentRefresherAware){
//			((XComponentRefresherAware) converter).setComponentRefresher(new ExcelRowHeightRefresher());
//		}else{
//			throw new IllegalArgumentException("转化器未实现接口XComponentRefresherAware");
//		}
//		
//		converter.convert(input, output);
//	}
//	
	
	
	protected void deleteTempFile(File file){
		if(file != null && file.isFile()){
			try{
				file.delete();
				log.debug("删除临时文件：" + file.getAbsolutePath());
			}catch(Exception e){
				if(log.isDebugEnabled()){
					log.debug("删除临时文件出错：", e);
				}
			}
		}
		file = null;
	}
	
	protected void verityModel(Model model) throws IllegalArgumentException{
		
	}

	/**
	 * @return the exportViewManager
	 */
	public ExportViewManager getExportViewManager() {
		return exportViewManager;
	}

	/**
	 * @param exportViewManager the exportViewManager to set
	 */
	public void setExportViewManager(ExportViewManager exportViewManager) {
		this.exportViewManager = exportViewManager;
	}

	/**
	 * @return the attachmentManager
	 */
	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	/**
	 * @param attachmentManager the attachmentManager to set
	 */
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}



	public Model preparePrint(String config, Model model) throws Exception {
		return preparePrint(config, model, false);
	}





//	/* (non-Javadoc)
//	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
//	 */
//	public void setApplicationContext(ApplicationContext applicationContext)
//			throws BeansException {
//		this.applicationContext = applicationContext;
//	}


	public static void main(String[] args){
//		List<String> list = new CopyOnWriteArrayList<String>();
//		list.add("a");
//		list.add("b");
//		list.add("c");
//		Iterator<String> iterator = list.iterator();
//		while(iterator.hasNext()){
//			list.remove(iterator.next());
//			System.out.println(list);
//		}
	}
}
