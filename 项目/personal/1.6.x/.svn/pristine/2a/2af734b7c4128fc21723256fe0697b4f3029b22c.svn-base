package org.opoo.ndao.support;


/**
 * 默认的可分页对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DefaultPageable implements Pageable {
    private final int startIndex;
    private final int pageSize;
    private final int itemCount;
    public DefaultPageable(int startIndex, int pageSize, int itemCount) {
	this.startIndex = startIndex;
	this.pageSize = pageSize;
	this.itemCount = itemCount;
    }

    /**
     * getItemCount
     *
     * @return int
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * getPageSize
     *
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * getStartIndex
     *
     * @return int
     */
    public int getStartIndex() {
        return startIndex;
    }
}
