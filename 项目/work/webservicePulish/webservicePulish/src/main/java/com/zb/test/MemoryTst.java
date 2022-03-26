package com.zb.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MemoryTst {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("100001");
        BigDecimal b = new BigDecimal("100001");
        a.subtract(b);
        System.out.println(a.toString());

    }
}
