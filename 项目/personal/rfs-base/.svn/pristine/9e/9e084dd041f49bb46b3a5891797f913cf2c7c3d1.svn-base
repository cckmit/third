package cn.redflagsoft.base.audit.impl;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Domain;

import cn.redflagsoft.base.aop.annotation.Operation;
import cn.redflagsoft.base.audit.AbstractAuditor;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.audit.MutableAuditMessage;
import cn.redflagsoft.base.audit.NotAuditableException;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.WorkScheme;

public class OperationAuditor extends AbstractAuditor {

	private static final Log log = LogFactory.getLog(OperationAuditor.class);
	
//	private String targetName;				//接收 Operation中的targetName参数
//	private String operation;				//接收Operation中的operation参数
//	private int domainArgIndex;				//接收Operation中的domainArgIndex参数
//	private String domainType;				//接收Operation中的domainType参数
//	private int domainIdArgIndex;			//接收Operation中的domainIdArgIndex参数
//	private String details;
//	private String description;
	
	
//	public String getTargetName() {
//		return targetName;
//	}
//
//	public void setTargetName(String targetName) {
//		this.targetName = targetName;
//	}
//
//	public String getOperation() {
//		return operation;
//	}
//
//	public void setOperation(String operation) {
//		this.operation = operation;
//	}
//
//	public int getDomainArgIndex() {
//		return domainArgIndex;
//	}
//
//	public void setDomainArgIndex(int domainArgIndex) {
//		this.domainArgIndex = domainArgIndex;
//	}
//
//	public String getDomainType() {
//		return domainType;
//	}
//
//	public void setDomainType(String domainType) {
//		this.domainType = domainType;
//	}
//
//	public int getDomainIdArgIndex() {
//		return domainIdArgIndex;
//	}
//
//	public void setDomainIdArgIndex(int domainIdArgIndex) {
//		this.domainIdArgIndex = domainIdArgIndex;
//	}
//
//	public String getDetails() {
//		return details;
//	}
//
//	public void setDetails(String details) {
//		this.details = details;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}

	public boolean methodIsAuditable(Method method) {
		return true;
	}

	public void setAuditedClass(Class<?> audited) throws NotAuditableException {
		
	}
	
	public String buildDetails(MethodCall call, MutableAuditMessage auditMessage) {
		Operation operation = call.getMethod().getAnnotation(Operation.class);
		if(StringUtils.isNotBlank(operation.details())){
			return operation.details();
		}
		
		StringBuilder temp = new StringBuilder();
		temp.append("对 " + operation.targetName() + " 进行了 " + operation.operation() + " 操作！");
		return temp.toString();
	}

	public String buildDescription(MethodCall call, MutableAuditMessage message) {
		Operation operation = call.getMethod().getAnnotation(Operation.class);
		if(StringUtils.isNotBlank(operation.description())){
			return operation.description();
		}
		
		StringBuilder temp = new StringBuilder();
		temp.append("对 " + operation.targetName() + " 进行了 " + operation.operation() + " 操作！");
		return temp.toString();
	}

	@Override
	protected DomainIdentifier buildDomain(MethodCall call, MutableAuditMessage message) {
		Operation operation = call.getMethod().getAnnotation(Operation.class);
		//如果是返回值的处理
		if(operation.domainArgIndex() == -1){
			Object object = call.getResult();
			if (object instanceof Domain) {
				Domain<?> d = (Domain<?>) object;
				if (d.getId() != null) {
					return new DomainIdentifier(d);
				}
			}
		}
		
		Object[] values = call.getParameterValues();
		if(values == null){
			return null;
		}
		
		//如果domainType未设置，尝试读取domainClass来获取domainType
		String domainType = operation.domainType();
		if(StringUtils.isBlank(domainType) && !Domain.class.equals(operation.domainClass())){
			domainType = operation.domainClass().getName();
		}
		
		// 判断 被操作对象的 下标值
		if (operation.domainArgIndex() >= 0 && values.length > operation.domainArgIndex()) {
			Object object = values[operation.domainArgIndex()];
			if (object instanceof Domain) {
				Domain<?> d = (Domain<?>) object;
				if (d.getId() != null) {
					return new DomainIdentifier(d);
				}
			}
		}
		
		// 判断 被操作对象的Id的 下标值 跟 被操作对象的 类型
		if (operation.domainIdArgIndex() >= 0 && values.length > operation.domainIdArgIndex()
				&& StringUtils.isNotBlank(domainType)) {
			Object object = values[operation.domainIdArgIndex()];
			if (object != null) {
				return new DomainIdentifier(object.toString(), domainType);
			}
		}
		
		if(call.getTargetObject() instanceof WorkScheme){
			if(log.isDebugEnabled()){
				log.debug("Audit method in WorkScheme : " + call.getMethod());
			}
			WorkScheme ws = (WorkScheme) call.getTargetObject();
			RFSObject object = ws.getObject();
			if(object != null){
				return new DomainIdentifier(ws.getObject());
			}else{
				log.warn("WorkScheme的object为空，无法获取被审计对象。");
			}
		}
		log.debug("无法分析被操作对象！" + call.getMethod());
		return null;
	}

	@Override
	protected String buildOperation(MethodCall call, MutableAuditMessage message) {
		Operation operation = call.getMethod().getAnnotation(Operation.class);
		return operation.operation();
	}
}
