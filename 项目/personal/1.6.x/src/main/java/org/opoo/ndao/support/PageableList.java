/*
 * $Id: PageableList.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * �ɷ�ҳ�� List��
 * @author Alex Lin(alex@opoo.org)
 * @version 1.1
 */
public class PageableList<E> extends ArrayList<E> implements List<E>, Pageable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3174994260669623903L;
	private final int pageSize;
    private final int itemCount;
    private final int startIndex;
    public PageableList(Collection<? extends E> c, int startIndex, int pageSize, int itemCount) {
        super(c);
        this.itemCount = itemCount;
        this.pageSize = pageSize;
        this.startIndex = startIndex;
    }

    public int getItemCount() {
        return itemCount;
    }

    /* unmodofied list
      public boolean add(Object o)
      {
        throw new UnsupportedOperationException();
      }
      public boolean addAll(Collection c)
      {
        throw new UnsupportedOperationException();
      }
      public void add(int index, Object element)
      {
        throw new UnsupportedOperationException();
      }
      public boolean addAll(int index, Collection c)
      {
        throw new UnsupportedOperationException();
      }

      public boolean remove(Object o)
      {
        throw new UnsupportedOperationException();
      }
      public Object remove(int index)
      {
        throw new UnsupportedOperationException();
      }
      public boolean removeAll(Collection c)
      {
        throw new UnsupportedOperationException();
      }
      public boolean retainAll(Collection c)
      {
        throw new UnsupportedOperationException();
      }
     */

    public int getPageSize() {
        return pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public String toString2() {
	return "{startIndex=" + startIndex + ", pageSize="
		+ pageSize + ", itemCount="
		+ itemCount + ", list="
		+ super.toString() + "}";
    }
}
