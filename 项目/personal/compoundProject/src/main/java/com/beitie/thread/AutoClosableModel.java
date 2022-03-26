package com.beitie.thread;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/12
 */
public class AutoClosableModel implements AutoCloseable{
    ThreadLocal<String> t=new ThreadLocal<>();
    @Override
    public void close() throws Exception {
        t.remove();
        System.out.println("移除了中怄人");
    }
    public void setMsg(String msg){
        t.set(msg);
        System.out.println("zhongguoren");
    }

    public static void main(String[] args) {
        try(AutoClosableModel model= new AutoClosableModel();){
            model.setMsg("zhonguoren");
            System.out.println(111);
        }catch (Exception e){

        }
    }
}
