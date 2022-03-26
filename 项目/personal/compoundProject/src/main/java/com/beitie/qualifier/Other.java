package com.beitie.qualifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author betieforever
 * @description ÃèÊö
 * @date 2021/12/8
 */
public class Other {
    private Parent parent;

    public static void main(String[] args) {
        Parent parent = new Parent();
        String defaultStr = parent.nameDefault;
        String nameProtected = parent.nameProtected;
        String namePublic = parent.namePublic;
    }
}
