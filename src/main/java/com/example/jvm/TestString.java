package com.example.jvm;

import java.util.Random;
import java.util.Scanner;

public class TestString {
    /**
     *  使用 javap -v com.example.jvm.TestString 命令查看编译后字节码得出
     *  "a" 是被转化为 String对象的
     *  至于 1 true 等为什么不能直接调用方法，是因为它们为基本类型，基本类型是无法直接调用方法的。
     */
//    public static void main(String[] args) {
//       boolean b = "a".equals("a");
//       System.out.println(b);
//    }

    /**
     * 使用 javap -v com.example.jvm.TestString 命令查看编译后字节码得出
     * a+"b" 是被转化为 StringBuilder 对象进行拼接的
     */
    public static void main(String[] args) {
//        TestString testString = new TestString();
//        testString.test(String.valueOf(new Random().nextInt(10)));
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(str);
        boolean isTrue = true;
        for (int i = 0; i < (str.length() / 2); i++) {
            if (!(str.charAt(i) == str.charAt(str.length()-1-i))){
                isTrue = false;
                break;
            }

        }
        if (!isTrue)
            System.out.println("该字符串不是回文！");
        else
            System.out.println("该字符串是回文！");
    }

    private void test(String a) {
        String str = a + "b";
        System.out.println(str);
    }
}
