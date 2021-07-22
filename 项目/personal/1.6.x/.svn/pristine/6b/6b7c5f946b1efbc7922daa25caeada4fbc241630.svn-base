package org.opoo.apps.el.freemarker;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.opoo.apps.el.Expression;
import org.opoo.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ExpressionImpl implements Expression {
	
	private static Configuration DEFAULT_CFG = new Configuration();
	private static String DEFAULT_TEMPLATE_KEY = "__default_template_4_stringel";
	private String expr;
	private Template template;

	public ExpressionImpl(String expr) {
		super();
		Assert.notBlank(expr, "表达式字符串不能为空。");
		this.expr = expr;
		try{
			StringReader sr = new StringReader(expr);
			this.template = new Template(DEFAULT_TEMPLATE_KEY, sr, DEFAULT_CFG);
			IOUtils.closeQuietly(sr);
		}catch(Exception e){
			throw new IllegalArgumentException(e);
		}
	}

	public String getValue(Object context) throws RuntimeException {
		StringWriter sw = new StringWriter();
		try {
			template.process(context, sw);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		IOUtils.closeQuietly(sw);
		return sw.toString();
	}

	public String toString(){
		return ExpressionImpl.class.getName() + "@" + hashCode()
			+ "[" + expr + "]";
	}

	public String getExpressionString() {
		return expr;
	}
}
