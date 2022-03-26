package com.beitie.stringUse;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/24
 */
public class StringUse {
    public static void main(String[] args) {
//        format();
//        split();
        converToASCII();
    }

    public static void  converToASCII(){
     char a ='a';
     System.out.println();
     int i = 5;
     char ii = 5+'0';
        System.out.printf("%c%n",ii);
        System.out.println((int)ii);
        char b = '0';
        System.out.printf("%d",(int)b);

    }

    public static void format(){
        /*

        常用类型：
            %s 字符串类型
            %d 数字类型
            %f 浮点类型
            %n 换行符
            %c  字符类型
            %% 百分号
            %h 散列码
            %b 布尔类型
         */
        System.out.print(String.format("_t%s_w%s %n",101201,10120111));
        System.out.print(String.format("%d %n",100*5));
        System.out.print(String.format("%f %n",100.0*5));
        System.out.print(String.format("字符a的大写为%c%n",'A'));
        System.out.print(String.format("字符a的散列码为%h%n",'0'));
        System.out.print(String.format("八折可以表示为为%d%%",80));
    }

    public static void split(){
        String str = ",aa,bb,cc,dd,,,,";
        char a = 1;
        char c = 0;
        char b= '中';
        System.out.println(str.split(",").length);
        final String[] split = str.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
