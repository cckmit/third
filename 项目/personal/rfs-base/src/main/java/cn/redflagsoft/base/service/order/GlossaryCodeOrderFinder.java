package cn.redflagsoft.base.service.order;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.service.OrderFinder;


/**
 * 根据Glossary的code来查找Glossary集合中指定对象的排序号。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class GlossaryCodeOrderFinder extends AbstractGlossaryOrderFinder<Number> implements OrderFinder<Number>{
	/**
	 * @param glossaryCategory
	 */
	public GlossaryCodeOrderFinder(short glossaryCategory) {
		super(glossaryCategory);
	}

	public int getOrder(Number t) {
		if(t == null){
			return 0;
		}
		for(Glossary g: getGlossaryList()){
			if(g.getCode() == t.intValue()){
				return g.getDisplayOrder();
			}
		}
		return 0;
	}

}
