/*
 * $Id: AbstractModelAction.java 5827 2012-06-01 10:11:22Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 */
package cn.redflagsoft.base.web.struts2;

import org.opoo.apps.license.annotation.ProductModule;
import org.opoo.apps.web.struts2.AbstractAppsAction;

import cn.redflagsoft.base.web.struts2.interceptor.Printable;

/**
 * @author Alex Lin
 *
 */
@ProductModule(edition = "common", module = "base")
public abstract class AbstractModelAction extends AbstractAppsAction implements Printable {

	private static final long serialVersionUID = 5916410946236602336L;
	
	private String printConfig;
	private boolean xlsExport = false;
	private boolean requireRefreshExcelRowHeight = true;

	/**
	 * @return the printConfig
	 */
	public String getPrintConfig() {
		return printConfig;
	}

	/**
	 * @param printConfig the printConfig to set
	 */
	public void setPrintConfig(String printConfig) {
		this.printConfig = printConfig;
	}

	/**
	 * @return the xlsExport
	 */
	public boolean isXlsExport() {
		return xlsExport;
	}

	/**
	 * @param xlsExport the xlsExport to set
	 */
	public void setXlsExport(boolean xlsExport) {
		this.xlsExport = xlsExport;
	}

	/**
	 * @return the requireRefreshExcelRowHeight
	 */
	public boolean isRequireRefreshExcelRowHeight() {
		return requireRefreshExcelRowHeight;
	}

	/**
	 * @param requireRefreshExcelRowHeight the requireRefreshExcelRowHeight to set
	 */
	public void setRequireRefreshExcelRowHeight(boolean requireRefreshExcelRowHeight) {
		this.requireRefreshExcelRowHeight = requireRefreshExcelRowHeight;
	}

//	/**
//	 * 将执行结果转化成Model数据结构。
//	 * 
//	 * @param result
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	protected Model executeResultToModel(Object result){
//		if(result != null){
//			//if(result instanceof com.opensymphony.xwork2.Result){
//				//return ((com.opensymphony.xwork2.Result) result).execute(invocation);
//			//}
//			if(result instanceof Model){
//				model = (Model) result;
//			}else if (result instanceof List){
//				model.setRows((List) result);
//			}else if(result instanceof Message){
//				Message msg = (Message) result;
//				model.setErrors(msg.getErrors());
//				model.setMessage(msg.getMessage());
//				model.setSuccess(msg.isSuccess());
//			}else if(result instanceof String){
//				model.setMessage((String) result);
//			}else if(result instanceof Serializable){
//				model.setData((Serializable)result);
//			}
//		}
//		return model;
//	}
	
//	public void setModelResult(Object result){
//		if(result != null){
//			//if(result instanceof com.opensymphony.xwork2.Result){
//				//return ((com.opensymphony.xwork2.Result) result).execute(invocation);
//			//}
//			if(result instanceof Model){
//				model = (Model) result;
//				addModelErrorsToActionErrors(model);
//			}else if (result instanceof List){
//				model.setRows((List<?>) result);
//			}else if(result instanceof Message){
//				Message msg = (Message) result;
//				model.setErrors(msg.getErrors());
//				model.setMessage(msg.getMessage());
//				model.setSuccess(msg.isSuccess());
//				addModelErrorsToActionErrors(model);
//			}else if(result instanceof Exception){
//				model.setException((Exception) result);
//				addModelErrorsToActionErrors(model);
//			}else if(result instanceof String){
//				model.setMessage((String) result);
//				addModelErrorsToActionErrors(model);
//			}else if(result instanceof Serializable){
//				model.setData((Serializable)result);
//			}
//		}
//	}
//
//	private void addModelErrorsToActionErrors(Model model){
//		if(model.getMessage() != null){
//			String msg = model.getMessage();
//			if(model.isSuccess()){
//				addActionMessage(msg);
//			}else{
//				addActionError(msg);
//			}
//		}
//		if(model.getErrors() != null){
//			addFieldErrors(model.getErrors());
//		}
//	}
//	private void addFieldErrors(Map<String, String> errors) {
//		for(Map.Entry<String, String> en: errors.entrySet()){
//			addFieldError(en.getKey(), en.getValue());
//		}
//	}
}
