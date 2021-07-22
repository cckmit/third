/*
 * $Id: SchemeParametersBuilder.java 5209 2011-12-14 03:15:57Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme;

import java.util.Map;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * WorkScheme�Ĳ�����������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SchemeParametersBuilder {

	/**
	 * ��������WorkScheme�Ĳ������ϣ�ͨ����MatterAffair�Զ���������WorkSchemeʱ��
	 * �ȵ����������������Ҫ���õ�WorkScheme�Ĳ�����Ȼ���ٵ���WorkScheme��
	 * 
	 * @param ma Ҫ����WorkScheme��MatterAffair����
	 * @param refWorkSchme ��MatterAffair�����漰��ԭ����WorkScheme
	 * @param refObject ��MatterAffair�����漰��ԭ����������
	 * @param refAffairObject ��MatterAffair�����漰��ԭ����AffairObject
	 * @return �����õ�map����
	 */
	Map<String,Object> buildParameters(MatterAffair ma, WorkScheme refWorkSchme, RFSObject refObject, RFSObject refAffairObject);
}
