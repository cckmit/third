package org.opoo.apps.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Lock utils class to replace calls to String.intern().
 */
public final class LockUtils {

    private static final ConcurrentMap<String, InternHolder> map = new ConcurrentHashMap<String, InternHolder>();

    //Use this incase bugs show up due the new intern call.
    public static String internFallBack(String s) {
        return s.intern();
    }

    //From Joshua Block's Effective Java 2nd Edition
    public static String intern(String s) {
        InternHolder result = map.get(s);
        if (result == null) {
            InternHolder holder = new InternHolder(s);
            result = map.putIfAbsent(s, holder);
            if (result == null){
                result = holder;
            }
        }
        result.updateAccessTime();
        return result.string;
   }


    public void printDetails(PrintStream stream){
        stream.println("----Strings interned Start----");
        for (String s : map.keySet()) {
            stream.println(map.get(s).toString());
        }
        stream.println("----Strings interned End----");
    }

    public static long internedCount() {
        return map.size();
    }

    public static long internedSize() {
        long size = 0;
        for (InternHolder internHolder : map.values()) {
            size += internHolder.string.length();
        }
        return size;
    }

    public static void doCleanup(long highWaterMark, long minLRUTime) {
        if(map.size() <= highWaterMark){
            return;
        }
        ArrayList<InternHolder> holderList = new ArrayList<InternHolder>(map.values());
        Collections.sort(holderList, new InternHolderComparator());
        long now = System.currentTimeMillis();
        for (InternHolder holder : holderList) {
            if(minLRUTime < (now - holder.lastAccessTime)){
                map.remove(holder.string);
                continue;
            }
            break;
        }
    }

    static class InternHolder {
        public final String string;
        public volatile long lastAccessTime;

        public InternHolder(final String s) {
            this.string = s;
            this.lastAccessTime = System.currentTimeMillis();
        }

        public void updateAccessTime(){
            this.lastAccessTime = System.currentTimeMillis();
        }

        public String toString(){
            return new StringBuilder()
                    .append("Key:")
                    .append(string)
                    .append(", lastAccessTime:")
                    .append(lastAccessTime).toString();
        }
    }

    static class InternHolderComparator implements Comparator<InternHolder> {
        public int compare(InternHolder o1, InternHolder o2) {
            return o1.lastAccessTime > o2.lastAccessTime ? 1 : (o1.lastAccessTime == o2.lastAccessTime ? 0 : -1);
        }
    }
}

