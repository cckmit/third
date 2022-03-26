package com.beitie.qualifier.vo;

import com.beitie.qualifier.Parent;

/**
 * @author betieforever
 * @description √Ë ˆ
 * @date 2021/12/8
 */
public class ChildVO extends Parent {
    @Override
    public String toString() {
        return "ChildVO{" +
                "namePublic='" + namePublic + '\'' +
                ", nameProtected='" + nameProtected + '\'' +
                '}';
    }
}
