package com.example.jvm;

import java.util.concurrent.TimeUnit;

/**
 * 类的卸载：
 *     1. 当类被加载、连接和初始化后，它的生命周期就开始了，当代表类的 Class 对象不再被引用，即不可触及时，Class对象就会结束生命周期，类在方法区内的数据也会被卸载，从而结束类的生命周期。
 *     2. 一个类何时结束生命周期，取决于代表他的 Class 对象合适结束生命周期。
 *     3. 由java虚拟机自带的类加载器所加载的类，在虚拟机的生命周期中，始终不会被卸载（根类加载器，扩展类加载器，系统类加载器），java虚拟机会始终引用这些类加载器，
 *          而这些类加载器则会始终引用它们所加载的类的Class对象，因此这些Class对象始终是可触及的。
 *     4. 由用户自定义的类加载器所加载的类是可以被卸载的。
 *     5. 在类加载器的内部实现中，用一个java集合来存放所加载的类的引用，另一方面，一个Class对象总是会引用它的类加载器，调用 Class 对象的 getClassLoader() 方法，就能获得他的类加载器
 *          由此可见类的Class 实例 与类加载器之间为双向关联关系。
 *     6. 一个类的实例总是引用代表这个类的Class对象，在object类中定义了getClass() 方法，这个方法返回代表对象所属类的Class对象的引用。
 *          此外，在所有的java类都有一个静态属性 Class ,它应用代表这个类的Class 对象 。
 *     7. 子加载器所加载的类能够访问到父加载器所加载的类，父加载器所加载的类无法访问到子加载器所加载的类
 *
 */
public class ClassLoaderTest9 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        MyClassLoader myClassLoader = new MyClassLoader("loader2");
        myClassLoader.setPath("D:\\work\\jdk-test\\");
        Class<?> aClass = myClassLoader.loadClass("com.example.jdktest.jvm.entity.MySample");
        System.out.println("class:   " + aClass.hashCode());
        // 此处若注释  则不会加载 MyCat 类
        Object o = aClass.newInstance();
        myClassLoader = null;
        aClass = null;
        o= null;
        System.gc();
        TimeUnit.SECONDS.sleep(20);
    }
}



