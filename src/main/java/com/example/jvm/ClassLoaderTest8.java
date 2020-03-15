package com.example.jvm;

import org.springframework.util.StringUtils;

import java.io.*;

/**
 * 命名空间
 *      1. 每个类加载器都有自己的命名空间，命名空间由该加载器及所有父类加载器所加载的类组成
 *      2. 在同一个命名空间中，不会出现类的完整名字（包括类的包名）相同的两个类
 *      3. 在不同的命名空间中，有可能会出现类的完整名字（包括类的包名） 相同的两个类
 * 不同类加载器的命名空间关系
 *      1. 同一个命名空间内的类时相互可见的
 *      2. 子加载器的命名空间包含所有父加载器的命名空间，因此有子加载器加载的类能访问父加载器加载的类，例如系统类加载器加载的类能看见根类加载器加载的类
 *      3. 由父加载器加载的类不能访问子加载器加载的类
 *      4. 如果两个加载器之间没有直接或间接的父子关系，那么他们各自加载的类相互不可见
 * 类加载器的双亲委托模型的好处：
 *      1. 可以确保java核心库的类型安全：所有的java应用都至少引用 java.lang.Object 类，也就是说在运行期，java.lang.Object 这个类会被加载到java虚拟机中，如果这个
 *          加载过程是由java应用自己的类加载器所完成的，那么很可能就会在jvm中存在多个版本的 java.lang.Object 类，而且这些类之间还是不兼容的，相互不可见。借助于双亲委托
 *          机制，java 核心类库中的类的加载工作都是由启动类加载器来统一完成的，从而确保了java应用所使用的都是同一个版本的java核心类库，他们之间是相互兼容的。
 *      2. 可以保证java核心类库所提供的类不会被自己定义的类锁替代
 *      3. 不同的类加载器可以为相同的类创建额外的命名空间，相同名称的类可以并存在java虚拟机中，只要用不同的类加载器来加载他们即可，不同类加载器所加载的类之间是不兼容的，
 *          这就相当于在java虚拟机内部创建了一个有一个相互隔离的java类空间，这类技术在很多框架中都得到了实际引用
 *
 *
 */
public class ClassLoaderTest8 {
    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader1 = new MyClassLoader("loader1");
        myClassLoader1.setPath("D:\\work\\jdk-test\\");
        Class zlass1 = myClassLoader1.loadClass("com.example.jdktest.jvm.Tetst8");
        System.err.println("myClassLoader1 加载的类： " + zlass1.hashCode());

        MyClassLoader myClassLoader2 = new MyClassLoader(myClassLoader1,"loader1");
        myClassLoader2.setPath("D:\\work\\jdk-test\\");
        Class zlass2 = myClassLoader2.loadClass("com.example.jdktest.jvm.Tetst8");
        System.err.println("myClassLoader2 加载的类： " +zlass2.hashCode());

        MyClassLoader myClassLoader3 = new MyClassLoader("loader1");
        myClassLoader3.setPath("D:\\work\\jdk-test\\");
        Class zlass3 = myClassLoader3.loadClass("com.example.jdktest.jvm.Tetst8");
        System.err.println("myClassLoader3 加载的类： " +zlass3.hashCode());



    }

}

class MyClassLoader extends ClassLoader {
    private String classLoaderName;
    private final String fileExtension = ".class";
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public MyClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    /**
     * 重点重写方法，会被 loadClass() 方法内部调用
     *
     * @param className
     * @return
     */
    @Override
    protected Class<?> findClass(String className) {
        System.err.println("使用自定义类加载器====================");
        byte[] data = this.loaderClassData(className);
        return this.defineClass(className, data, 0, data.length);
    }

    private byte[] loaderClassData(String classPath) {
        byte[] data = null;
        classPath = classPath.replace(".", "/");
        if (!StringUtils.isEmpty(this.path))
            classPath = this.path + classPath + this.fileExtension;

        try (InputStream inputStream = new FileInputStream(new File(classPath));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int ch;
            while ((ch = inputStream.read()) > -1) {
                outputStream.write(ch);
            }
            data = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public String toString() {
        return "MyClassLoader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                '}';

    }
}