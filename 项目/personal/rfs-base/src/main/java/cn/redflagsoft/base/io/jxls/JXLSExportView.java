/*
 * $Id: JXLSExportView.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.io.jxls;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.Model;

import cn.redflagsoft.base.io.ExcelExportView;

/**
 * ʹ�� JXLS ʵ�ֵ����ݵ������ܡ�
 * @author Alex Lin
 *
 */
public class JXLSExportView extends ExcelExportView {
	private static final Log log = LogFactory.getLog(JXLSExportView.class);
	private String template;
	
	public File doExportToFile(Model model) throws Exception{
		String template = buildTemplatePath();
		File templateFile = new File(template);
		if(!templateFile.exists()){
			throw new IllegalArgumentException("ģ�岻���ڣ�" + template);
		}else{
			log.info("ģ�壺" + template);
		}
		
		File file = ceateTempXLSFile();
		try{
			Map<String,Object> beans = new HashMap<String,Object>();
			beans.put("model", model);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFile.getAbsolutePath(), beans, file.getAbsolutePath());
			return file;
		}catch(Exception e){
			if(file != null && file.isFile() && file.exists()){
				try {
					file.delete();
					if(log.isDebugEnabled()){
						log.debug("��ӡʱ�쳣��ɾ����ʱ�ļ���" + file);
					}
				} catch (Exception ex) {
					if(log.isDebugEnabled()){
						log.debug("ɾ����ʱ�ļ�����", e);
					}
				}
			}
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.io.ExportView#export(org.opoo.apps.Model)
	 */
	public InputStream doExport(Model model) throws Exception {
		return super.doExport(model);
		
//		File.createTempFile(prefix, suffix)
//		
//	    System.out.println(". = "+new File(".").getAbsolutePath());
//		String templateFile = ".\\src\\test\\resources\\excel_export\\template\\project.xls";
//		String outputFile = ".\\src\\test\\resources\\excel_export\\project-output.xls";
//		
//		List<Project> projects = projectService.findObjects(null);
//		Map beans = new HashMap();
//	    beans.put("projects", projects);
//	    XLSTransformer transformer = new XLSTransformer();
//	    transformer.transformXLS(templateFile, beans, outputFile);
//		return null;
	}
	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}
	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
	
	private String buildTemplatePath(){
		if(log.isDebugEnabled()){
			log.debug("���ã�" + template);
		}
		String path = AppsHome.getAppsHomePath() + template;
//		if(log.isDebugEnabled()){
//			log.debug("ģ���ļ���" + path);
//		}
		return path;
	}
	private static File getTempDir(){
		String tmpdir = AppsGlobals.getSetupProperty("tmpdir");
		if(StringUtils.isNotBlank(tmpdir)){
			File f = new File(tmpdir, "/export");
			if(!f.exists()){
				f.mkdirs();
			}
			return f;
		}
		
		
		File file = new File(AppsHome.getTemp(), "export");
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	
	private static File ceateTempXLSFile() throws IOException{
		File file = File.createTempFile("export-", ".xls", getTempDir());
		if(log.isDebugEnabled()){
			log.debug("������ʱ�ļ�: " + file.getAbsolutePath());
		}
//		ThreadLocalTempFileUtils.addTempFile(file);
		return file;
	}
	
	public static void main(String[] args) throws IOException{
		File file = ceateTempXLSFile();
		System.out.println(file.getAbsolutePath());
		//file.delete();
		
		
		String s = "�к͹���#yyyy-MM-dd_HH:mm#";
		SimpleDateFormat format = new SimpleDateFormat(s);
		String string = format.format(new Date());
		System.out.println(string);
	}
}
