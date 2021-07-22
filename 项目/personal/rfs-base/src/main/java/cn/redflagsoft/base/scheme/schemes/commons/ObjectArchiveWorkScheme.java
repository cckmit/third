/*
 * $Id: ObjectArchiveWorkScheme.java 5604 2012-05-04 06:26:30Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectArchive;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectArchiveWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectArchive archive;
	
	/**
	 * @return the archive
	 */
	public ObjectArchive getArchive() {
		return archive;
	}

	/**
	 * @param archive the archive to set
	 */
	public void setArchive(ObjectArchive archive) {
		this.archive = archive;
	}

	public Object doScheme(){
		return archive(archive);
	}
	
	/**
	 * �鵵��
	 * ���÷����������壬�����ڼ̳�����д����Ϊdo����������д��
	 * @param archive
	 * @return
	 */
	protected Object archive(ObjectArchive archive){
		ObjectArchive archive2 = getObjectAdminService().archiveObject(getObject(), archive);
		addRelatedItem(archive2);

		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "�鵵�ɹ���";
	}
}
