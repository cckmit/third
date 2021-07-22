package org.opoo.apps.id.sequence;

import org.opoo.apps.test.SpringTests;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SequenceTest extends SpringTests{
	protected HibernateTemplate hibernateTemplate;
	public void etestGenerate(){
		HibernateBlockable blockable = new HibernateBlockable(hibernateTemplate, "myTestId");
		BlockableSequenceWrapper seq = new BlockableSequenceWrapper(blockable, "myTestId", 5, true);
		for(int i = 0 ; i < 106; i++){
			System.out.println(seq.getNext());
		}
		super.setComplete();
	}
	
	public void etestGenerate2(){
		Sequence seq = new HibernateSequence(hibernateTemplate, "myTestId", 5, 3);
		for(int i = 0 ; i < 106; i++){
			System.out.println(seq.getNext());
		}
	}
	
	public void testGenerate3(){
		HibernateSequenceProvider provider = new HibernateSequenceProvider();
		Sequence sequence = provider.getSequence("myTestId");
		for(int i = 0 ; i < 106; i++){
			System.out.println(sequence.getNext());
		}
	}
	
}
