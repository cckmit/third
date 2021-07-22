package cn.redflagsoft.base.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * A List type class for long values. The implementation uses an array. If the number
 * of elements grows larger than the capacity, the capacity will automatically grow.
 */
public final class LongList implements Iterable<Long>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7583825506219223392L;

	private static final int DEFAULT_CAPACITY = 50;

    private long[] elements;
    private int capacity;
    private int size;


    /**
     * Creates a new list of long values with a default capacity of 50.
     */
    public LongList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a new list of long values with a specified initial capacity.
     *
     * @param initialCapacity a capacity to initialize the list with.
     */
    public LongList(int initialCapacity) {
        size = 0;
        capacity = initialCapacity == 0 ? DEFAULT_CAPACITY : initialCapacity;
        elements = new long[capacity];
    }

    /**
     * Creates a new list of long values with an initial array of elements.
     *
     * @param longArray an array to create a list from.
     */
    public LongList(long [] longArray) {
        size = longArray.length;
        capacity = longArray.length + 3;
        elements = new long[capacity];
        System.arraycopy(longArray, 0, elements, 0, size);
    }

    /**
     * Adds a long value to the end of the list.
     *
     * @param value the value to add to the list.
     */
    public void add(long value) {
        elements[size] = value;
        size++;
        // Expand elements array if we need to.
        if (size == capacity) {
            capacity = capacity * 2;
            long[] newElements = new long[capacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Adds a long value to the list at the specified index.
     *
     * @param index the index in the list to add the value at.
     * @param value the value to add to the list.
     */
    public void add(int index, long value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " not valid.");
        }
        // Shift elements starting at the index forward.
        for (int i=size; i>index; i--) {
            elements[i] = elements[i-1];
        }
        elements[index] = value;
        size++;

        // Expand elements array if we need to.
        if (size == capacity) {
            capacity = capacity * 2;
            long[] newElements = new long[capacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Adds a series of long[] values to the end of the list.
     *
     * @param values the values to add to the list.
     */
    public void addAll(long[] values) {
        int index = size;
        size += values.length;
        
        // Expand elements array if we need to.
        if (size >= capacity) {
            capacity = (capacity + size) * 2;
            long[] newElements = new long[capacity];
            System.arraycopy(elements, 0, newElements, 0, index);
            elements = newElements;
        }
        
        System.arraycopy(values, 0, elements, index, values.length);
    }
    
    /**
     * Removes a value from the list at the specified index.
     *
     * @param index the index to remove a value at.
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not valid.");
        }
        size--;
        // Shift elements starting at the index backwards.
        System.arraycopy(elements, index + 1, elements, index, size - index);
    }

    /**
     * Removes all of the elements from the list.
     */
    public void clear() {
        size = 0;
    }

    /**
     * Returns the long value at the specified index. If the index is not
     * valid, an IndexOutOfBoundException will be thrown.
     *
     * @param index the index of the value to return.
     * @return the value at the specified index.
     */
    public long get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not valid.");
        }
        return elements[index];
    }

    /**
     * Returns the index in this list of the first occurrence of the specified value,
     * or -1 if this list does not contain this value.
     *
     * @param value the value to search for.
     * @return the index in this list of the first occurrence of the specified
     *      value, or -1 if this list does not contain this value.
     */
    public int indexOf(long value) {
        for (int i=0; i<size; i++) {
            if (elements[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the list contains the specified value.
     *
     * @param value the value to search for.
     * @return true if <tt>value</tt> is found in the list.
     */
    public boolean contains(long value) {
        return indexOf(value) != -1;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns a new array containing the list elements.
     *
     * @return an array of the list elements.
     */
    public long[] toArray() {
        if(size == 0){
            return new long[0];
        }
        int size = this.size;
        long[] newElements = new long[size];
        System.arraycopy(elements, 0, newElements, 0, size);
        return newElements;
    }

    public List<Long> toList() {
        ArrayList<Long> list = new ArrayList<Long>();
        for (long l : toArray()) {
            list.add(l);
        }
        return list;
    }

    public Set<Long> toSet() {
        LinkedHashSet<Long> set = new LinkedHashSet<Long>();
        for (long l : toArray()) {
            set.add(l);
        }
        return set;
    }

    public Iterator<Long> iterator() {
        return new Iterator<Long>(){
            private long[] snapshot = toArray();
            int idx = 0;

            public boolean hasNext() {
                return idx < snapshot.length;
            }

            public Long next() {
                return snapshot[idx++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LongList longList = (LongList) o;

        return Arrays.equals(elements, longList.elements);

    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<this.size; i++) {
            buf.append(elements[i]).append(" ");
        }
        return buf.toString();
    }
}
