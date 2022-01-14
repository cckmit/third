package com.zb.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MemoryTst {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1000021");
        BigDecimal b = new BigDecimal("1000201");
        a.subtract(b);
        System.out.println(a.toString());

    }
}
