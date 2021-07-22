package cn.redflagsoft.base.service.order;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.service.OrderFinder;


/**
 * ����Glossary��code������Glossary������ָ�����������š�
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
