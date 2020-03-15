package com.example.jvm;

/**
 * 数组实例，器类型是由 jvm 在运行期动态生成的，动态生成的，其父类就是Object
 * 对于数组来说，javaDoc经常将构成数组的元素为 Component , 实际上就是将数组降低一个维度后的类型
 *  助记符：
 *      anewarray 表示创建一个引用类型的（如：类，接口，数组）数组，并将其引用值压入栈顶
 *      newarray 表示创建一个引用类型的（如： int, float, char 等基本类型）数组，并将其引用值压入栈顶
 */
public class ClassLoaderTest2 {

    public static void main(String[] args) {
        // 主动使用会输出
        Parent2 parent4 = new Parent2();
        // 没有使用 不会输出， 其引用的是数组对象
        Parent2[] parent4s = new Parent2[1];
        int[] ints = new int[4];
    }

    private static void test1(){

    }
}

class Parent2{
    static {
        System.err.println("Parent2 static block");
    }
}
