package org.opoo.apps.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.opoo.apps.security.Group;

public abstract class CollectionUtils {

	/**
	 * 
	 * @param <E>
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static <E> Collection<E> subtract(final Collection<E> a,	final Collection<E> b, final Comparator<E> c) {
		List<E> list = new ArrayList<E>(a) {
			private static final long serialVersionUID = 6540577522810618319L;
			@SuppressWarnings("unchecked")
			@Override
			public boolean remove(Object o) {
				if (o == null) {
					return super.remove(null);
				} else {
					for (int index = 0; index < size(); index++) {
						E e = get(index);
						if (c.compare(e, (E) o) == 0) {
							super.remove(index);
							return true;
						}
					}
				}
				return false;
			}
		};

		// ArrayList<E> list = new ArrayList<E>( a );
		for (Iterator<E> it = b.iterator(); it.hasNext();) {
			list.remove(it.next());
		}
		return list;
	}

	public static void main(String[] args) {
		Group g1 = new Group(1L, "111");
		Group g2 = new Group(2L, "222");
		Group g3 = new Group(3L, "333");
		Group g4 = new Group(4L, "444");
		Group g5 = new Group(5L, "555");

		List<Group> grps1 = new ArrayList<Group>();
		List<Group> grps2 = new ArrayList<Group>();

		grps1.add(g1);
		grps1.add(g2);
		grps1.add(g3);
		grps1.add(g4);

		grps2.add(g2);
		grps2.add(g3);
		grps2.add(g5);
		
		for(Iterator<Group> it = grps1.iterator(); it.hasNext();){
			Group g = it.next();
			if(g.equals(g4)){
				it.remove();
			}
		}

		System.out.println(grps1);
		System.out.println(grps2);

		Comparator<Group> c = new Comparator<Group>() {
			public int compare(Group o1, Group o2) {
				return o1.getId().intValue() - o2.getId().intValue();
			}
		};

		Collection<Group> cc = CollectionUtils.subtract(grps1, grps2, c);
		System.out.println(cc);
		cc = CollectionUtils.subtract(grps2, grps1, c);
		System.out.println(cc);
		
		grps1.removeAll(grps2);
		System.out.println(grps1);
	}
}
