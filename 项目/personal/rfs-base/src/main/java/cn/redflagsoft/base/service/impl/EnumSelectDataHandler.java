/*
 * $Id: EnumSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DefaultLabelDataBean;
import cn.redflagsoft.base.bean.SelectDataSource;

/**
 * ����ö�����͵�ѡ��������Դ��
 * 
 * <p>ö�����͵����ݸ�ʽΪ��<code><b>1:��Ч;2:��Ч;3:��֪��</b></code>
 * <p>���������ʹ�ð�ǻ�ȫ�Ƿֺŷָ���һ����������ֵ�ͱ�ǩ֮��ʹ��ð�ŷָ���ö��
 * �������ս���Ϊ��ֵ���������͵ļ��ϡ�
 * 
 * <p>����������Ч��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EnumSelectDataHandler implements SelectDataHandler<Labelable> {

	public boolean supports(SelectDataSource dataSource) {
		return SelectDataSource.CAT_ö��  == dataSource.getCat();
	}
	/**
	 * ��ʽ��  1:��Ч;2:��Ч;3:��֪��
	 */
	public List<Labelable> findSelectData(SelectDataSource dataSource, ResultFilter filter) {
		return parseToList(dataSource.getSource());
	}
	
	public static List<Labelable> parseToList(String str){
		List<Labelable> list = new ArrayList<Labelable>();
		StringTokenizer st = new StringTokenizer(str, ";�� ");
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			Labelable labelable = parseToLabelable(token);
			if(labelable != null){
				list.add(labelable);
			}
		}
		return list;
	}
	
	public static Labelable parseToLabelable(String str){
		String[] arr = str.split(":");
		if(arr.length >= 2 && StringUtils.isNotBlank(arr[0]) && StringUtils.isNotBlank(arr[1])){
			return new DefaultLabelDataBean(arr[1].trim(), arr[0].trim());
		}
		return null;
	}
	
	public static String labelableListToString(List<Labelable> list){
		StringBuffer sb = new StringBuffer();
		for(Labelable lb:list){
			if(sb.length() > 0){
				sb.append(";");
			}
			sb.append(lb.getData()).append(":").append(lb.getLabel());
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args){
		List<Labelable> list = parseToList(" 1:��Ч;2:��Ч;3:��֪��");
		System.out.println(list);
	}

}
