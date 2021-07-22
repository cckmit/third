/*
 * $Id: CodeMapAction.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.web.struts2;

import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.util.Assert;

import cn.redflagsoft.base.util.CodeMapUtils;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CodeMapAction extends AbstractModelAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5959339716310316758L;
	
	private String key;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		Assert.notNull(key, "������� ����Ϊ�ա�");
		
		List<Labelable> list = CodeMapUtils.getCodeMapList(key);
		model.setRows(list);
		
		return super.execute();
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
