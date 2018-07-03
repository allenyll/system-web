package com.sw.admin.config;

public class TextClass {
    public static void test(){
        System.out.println("aaa");
        Example.test();
        Example example = new Example();
        example.test2();
    }

    static int[] a = new int[5];

    public static void main(String[] args){
        //((TextClass)null).test();
        System.out.println(a[0]);
    }
    
}
