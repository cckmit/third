/*
 * $Id: CodeMapSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.service.impl.SelectDataHandler;
import cn.redflagsoft.base.util.CodeMapUtils;

/**
 * 查询CodeMap体系中的数据集合。
 * 
 * <p>{@link SelectDataSource#getSource()} 的值为
 * 
 * @see CodeMapUtils#getCodeMapList(String)
 * @author Alex Lin(alex@opoo.org)
 * @since rfs-base 1.5.6
 */
public class CodeMapSelectDataHandler implements SelectDataHandler<Labelable> {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#supports(cn.redflagsoft.base.bean.SelectDataSource)
	 */
	public boolean supports(SelectDataSource arg0) {
		return SelectDataSource.CAT_术语表  == arg0.getCat();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#findSelectData(cn.redflagsoft.base.bean.SelectDataSource, org.opoo.ndao.support.ResultFilter)
	 */
	public List<Labelable> findSelectData(SelectDataSource arg0, ResultFilter arg1) {
		List<Labelable> list = CodeMapUtils.getCodeMapList(arg0.getSource());
		return list;
	}
}
