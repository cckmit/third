package cn.redflagsoft.base.service.order;

import java.util.List;

import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.service.GlossaryService;
import cn.redflagsoft.base.service.OrderFinder;


/**
 * 抽象的排序查找类，主要从指定类别的 Glossary 定义中根据code或者term去查找当前
 * Glossary对象的排序号。
 * 
 * <p>该抽象类主要定义了对指定类型的 Glossary 对象的查询和缓存。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T>
 */
public abstract class AbstractGlossaryOrderFinder<T> implements OrderFinder<T>{
	private short glossaryCategory;
	private List<Glossary> glossaryList = null;//new ArrayList<Glossary>();
	
	/**
	 * 创建 Glossary 排序号查找类实例。 
	 * @param glossaryCategory 指定的 glossary 类别
	 */
	public AbstractGlossaryOrderFinder(short glossaryCategory){
		this.glossaryCategory = glossaryCategory;
	}
	
	/**
	 * 获取指定类别的 Glossary 对象集合，并缓存。
	 * @return
	 */
	protected List<Glossary> getGlossaryList(){
		if(glossaryList == null){
			GlossaryService service = Application.getContext().get("glossaryService", GlossaryService.class);
			glossaryList = service.findByCategory2(glossaryCategory);
		}
		return glossaryList;
	}
}
