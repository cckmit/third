package cn.redflagsoft.base.service;

import org.opoo.apps.Model;

public interface PrintHandler {
	
	/**
	 * 准备打印数据。
	 * @param config 打印模板配置
	 * @param model 输入参数
	 * @return 输出参数
	 * @throws Exception
	 */
	Model preparePrint(String config, Model model) throws Exception;
	
	
	/**
	 * 准备打印数据。
	 * @param config 打印模板配置
	 * @param model 输入参数，查询结果
	 * @param xlsExport 是否导出xls
	 * @return
	 * @throws Exception
	 */
	Model preparePrint(String config, Model model, boolean xlsExport) throws Exception;
	
	/**
	 * 准备打印数据。
	 * @param config 打印模板配置
	 * @param model 输入参数，查询结果
	 * @param xlsExport 是否导出xls
	 * @param requireRefreshExcelRowHeight 是否需要刷新行高
	 * @return 
	 * @throws Exception
	 */
	Model preparePrint(String config, Model model, boolean xlsExport, boolean requireRefreshExcelRowHeight) throws Exception;
	
//	/**
//	 * 准备打印数据。
//	 * 
//	 * @param config 打印模板配置
//	 * @param model 输入参数，查询结果
//	 * @param xlsExport 是否导出xls
//	 * @param parameters 其他参数，例如原始的前台输入参数
//	 * @return
//	 */
//	Model preparePrint(String config, Model model, boolean xlsExport, Map<String,?> parameters) throws Exception;

//	/**
//	 * 在每次调用打印之后调用。
//	 */
//	void clearTempFiles();
}
