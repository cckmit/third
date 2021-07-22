package cn.redflagsoft.base.service.order;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.service.OrderFinder;

/**
 * 根据Glossary的term来查找Glossary集合中指定对象的排序号。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class GlossaryTermOrderFinder extends AbstractGlossaryOrderFinder<String> implements OrderFinder<String>{
	/**
	 * @param glossaryCategory
	 */
	public GlossaryTermOrderFinder(short glossaryCategory) {
		super(glossaryCategory);
	}

	public int getOrder(String t) {
		for(Glossary g: getGlossaryList()){
			if(g.getTerm().equals(t)){
				return g.getDisplayOrder();
			}
		}
		return 0;
	}

}
