package com.beitie.stringUse;

/**
 * @author betieforever
 * @description ����
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

        �������ͣ�
            %s �ַ�������
            %d ��������
            %f ��������
            %n ���з�
            %c  �ַ�����
            %% �ٷֺ�
            %h ɢ����
            %b ��������
         */
        System.out.print(String.format("_t%s_w%s %n",101201,10120111));
        System.out.print(String.format("%d %n",100*5));
        System.out.print(String.format("%f %n",100.0*5));
        System.out.print(String.format("�ַ�a�Ĵ�дΪ%c%n",'A'));
        System.out.print(String.format("�ַ�a��ɢ����Ϊ%h%n",'0'));
        System.out.print(String.format("���ۿ��Ա�ʾΪΪ%d%%",80));
    }

    public static void split(){
        String str = ",aa,bb,cc,dd,,,,";
        char a = 1;
        char c = 0;
        char b= '��';
        System.out.println(str.split(",").length);
        final String[] split = str.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
