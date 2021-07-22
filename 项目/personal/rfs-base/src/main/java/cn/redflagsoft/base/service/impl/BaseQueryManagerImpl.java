/*
 * $Id: BaseQueryManagerImpl.java 6436 2015-02-02 09:46:40Z lcj $
 * 
 * Copyright 2007-2015 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.opoo.apps.query.ParametersBuilder;
import org.opoo.apps.query.impl.NamedListQuery;
import org.opoo.apps.query.impl.NamedPageQuery;
import org.opoo.apps.query.impl.QueryManagerImpl;
import org.opoo.ndao.hibernate3.HibernateQuerySupport;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.Assert;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class BaseQueryManagerImpl extends QueryManagerImpl {
	private static final Log log = LogFactory.getLog(BaseQueryManagerImpl.class);
	private HibernateQuerySupport support;
	private ParametersBuilder builder;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		registerQueryXmls();
	}

	private void registerQueryXmls() throws Exception {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		Resource[] resources = resolver.getResources("classpath*:queries.xml");
		if(resources != null && resources.length > 0){
			for (Resource resource : resources) {
				registerResource(resource);
			}
		}else{
			log.debug("No overlays queries.xml found.");
		}
	}
	
	private void registerResource(Resource resource) throws Exception{
		InputStream is = null;
		try{
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element e = iterator.next();
				String name = e.attributeValue("name");
				String type = e.attributeValue("type", "HQL");
				String sqs = e.elementText("sqs");
				String cqs = e.elementText("cqs");
				
				Assert.hasText(sqs, "查询配置的查询语句不能为空。");
				
				boolean isSQLQuery = "SQL".equalsIgnoreCase(type);
				if(isSQLQuery && !name.endsWith("|SQL")){
					name += "|SQL";
				}
				//list2
				NamedListQuery listQuery = new NamedListQuery(support, sqs, isSQLQuery, builder, resource);
				registerQuery(name + ".list2", listQuery);
				
				//page2
				if(StringUtils.isNotBlank(cqs)){
					NamedPageQuery pageQuery = new NamedPageQuery(support, sqs, cqs, isSQLQuery, builder, resource);
					registerQuery(name + ".page2", pageQuery);
				}
			}
		}finally{
			IOUtils.closeQuietly(is);
		}
	}

	@Override
	public void setHibernateQuerySupport(
			HibernateQuerySupport hibernateQuerySupport) {
		super.setHibernateQuerySupport(hibernateQuerySupport);
		this.support = hibernateQuerySupport;
	}

	@Override
	public void setParametersBuilder(ParametersBuilder parametersBuilder) {
		super.setParametersBuilder(parametersBuilder);
		this.builder = parametersBuilder;
	}
}
