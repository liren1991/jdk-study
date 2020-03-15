package com.example.jvm;

/**
 * Class objects for array classes are not created by class loaders, but are created automatically as required by the Java runtime.
 * The class loader for an array class, as returned by Class.getClassLoader() is the same as the class loader for its element type;
 * if the element type is a primitive type, then the array class has no class loader  {@link java.lang.ClassLoader}
 * 数组的 class 对象的创建不是由类加载器创建的，它是由java虚拟机运行时按需要自动创建的。对于一个数组对象的 Class.getClassLoader() 返回的
 * 结果是数组中的元素的 Class.getClassLoader()； 如果数组的元素是基本类型，那么 Class.getClassLoader() 返回的是 null，即没有类加载器
 */
public class ClassLoaderTest7 {
    public static void main(String[] args) {
        String[] strings = new String[1];
        System.out.println("String[] 数组的类加载器===》》 " + strings.getClass().getClassLoader());
        ClassLoaderTest7[] classLoaderTest7s = new ClassLoaderTest7[1];
        System.out.println("ClassLoaderTest7[] 数组的类加载器===》》 " + classLoaderTest7s.getClass().getClassLoader());
        int[] ints = new int[1];
        System.out.println("int[] 数组的类加载器===》》 " + ints.getClass().getClassLoader());
    }
}
