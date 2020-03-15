package com.example.jvm;

/**
 * 与字节码注释冲突  所以使用 // 注释
 * 与 synchronized 关键字相关
 */
//
//Classfile /D:/work/jdk-test/target/classes/com/example/jdktest/jvm/ByteCodesTest2.class
//Last modified 2020-2-26; size 942 bytes
//        MD5 checksum 1523df7bb02861a33544cbcf5618ff41
//        Compiled from "ByteCodesTest2.java"
//public class com.example.jdktest.jvm.ByteCodesTest2
//        minor version: 0
//        major version: 52
//        flags: ACC_PUBLIC, ACC_SUPER
//        Constant pool:
//        #1 = Methodref          #7.#32         // java/lang/Object."<init>":()V
//        #2 = Fieldref           #33.#34        // java/lang/System.out:Ljava/io/PrintStream;
//        #3 = Fieldref           #6.#35         // com/example/jdktest/jvm/ByteCodesTest2.a:I
//        #4 = Methodref          #36.#37        // java/io/PrintStream.println:(I)V
//        #5 = Methodref          #36.#38        // java/io/PrintStream.println:(Ljava/lang/String;)V
//        #6 = Class              #39            // com/example/jdktest/jvm/ByteCodesTest2
//        #7 = Class              #40            // java/lang/Object
//        #8 = Utf8               a
//        #9 = Utf8               I
//        #10 = Utf8               <init>
//  #11 = Utf8               ()V
//          #12 = Utf8               Code
//          #13 = Utf8               LineNumberTable
//          #14 = Utf8               LocalVariableTable
//          #15 = Utf8               this
//          #16 = Utf8               Lcom/example/jdktest/jvm/ByteCodesTest2;
//          #17 = Utf8               getA
//          #18 = Utf8               ()I
//          #19 = Utf8               getB
//          #20 = Utf8               (Ljava/lang/String;)V
//          #21 = Utf8               str
//          #22 = Utf8               Ljava/lang/String;
//          #23 = Utf8               StackMapTable
//          #24 = Class              #39            // com/example/jdktest/jvm/ByteCodesTest2
//          #25 = Class              #41            // java/lang/String
//          #26 = Class              #40            // java/lang/Object
//          #27 = Class              #42            // java/lang/Throwable
//          #28 = Utf8               MethodParameters
//          #29 = Utf8               getC
//          #30 = Utf8               SourceFile
//          #31 = Utf8               ByteCodesTest2.java
//          #32 = NameAndType        #10:#11        // "<init>":()V
//          #33 = Class              #43            // java/lang/System
//          #34 = NameAndType        #44:#45        // out:Ljava/io/PrintStream;
//          #35 = NameAndType        #8:#9          // a:I
//          #36 = Class              #46            // java/io/PrintStream
//          #37 = NameAndType        #47:#48        // println:(I)V
//          #38 = NameAndType        #47:#20        // println:(Ljava/lang/String;)V
//          #39 = Utf8               com/example/jdktest/jvm/ByteCodesTest2
//          #40 = Utf8               java/lang/Object
//          #41 = Utf8               java/lang/String
//          #42 = Utf8               java/lang/Throwable
//          #43 = Utf8               java/lang/System
//          #44 = Utf8               out
//          #45 = Utf8               Ljava/io/PrintStream;
//          #46 = Utf8               java/io/PrintStream
//          #47 = Utf8               println
//          #48 = Utf8               (I)V
//          {
//public com.example.jdktest.jvm.ByteCodesTest2();
//        descriptor: ()V
//        flags: ACC_PUBLIC
//        Code:
//        stack=1, locals=1, args_size=1
//        0: aload_0
//        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//        4: return
//        LineNumberTable:
//        line 3: 0
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0       5     0  this   Lcom/example/jdktest/jvm/ByteCodesTest2;
//
//public synchronized int getA();
//        descriptor: ()I
//        flags: ACC_PUBLIC, ACC_SYNCHRONIZED
//        Code:
//        stack=2, locals=1, args_size=1
//        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        3: aload_0
//        4: getfield      #3                  // Field a:I
//        7: invokevirtual #4                  // Method java/io/PrintStream.println:(I)V
//        10: aload_0
//        11: getfield      #3                  // Field a:I
//        14: ireturn
//        LineNumberTable:
//        line 8: 0
//        line 9: 10
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0      15     0  this   Lcom/example/jdktest/jvm/ByteCodesTest2;
//
//public void getB(java.lang.String);
//        descriptor: (Ljava/lang/String;)V
//        flags: ACC_PUBLIC
//        Code:
//        stack=2, locals=4, args_size=2
//        0: aload_1
//        1: dup
//        2: astore_2
//        3: monitorenter
//        4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        7: aload_1
//        8: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        11: aload_2
//        12: monitorexit
//        13: goto          21
//        16: astore_3
//        17: aload_2
//        18: monitorexit
//        19: aload_3
//        20: athrow
//        21: return
//        Exception table:
//        from    to  target type
//        4    13    16   any
//        16    19    16   any
//        LineNumberTable:
//        line 13: 0
//        line 14: 4
//        line 15: 11
//        line 16: 21
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0      22     0  this   Lcom/example/jdktest/jvm/ByteCodesTest2;
//        0      22     1   str   Ljava/lang/String;
//        StackMapTable: number_of_entries = 2
//        frame_type = 255 /* full_frame */
//        offset_delta = 16
//        locals = [ class com/example/jdktest/jvm/ByteCodesTest2, class java/lang/String, class java/lang/Object ]
//        stack = [ class java/lang/Throwable ]
//        frame_type = 250 /* chop */
//        offset_delta = 4
//        MethodParameters:
//        Name                           Flags
//        str
//
//public static synchronized void getC(java.lang.String);
//        descriptor: (Ljava/lang/String;)V
//        flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
//        Code:
//        stack=2, locals=1, args_size=1
//        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        3: aload_0
//        4: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        7: return
//        LineNumberTable:
//        line 19: 0
//        line 20: 7
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0       8     0   str   Ljava/lang/String;
//        MethodParameters:
//        Name                           Flags
//        str
//        }
//        SourceFile: "ByteCodesTest2.java"

public class ByteCodesTest2 {

    private int a ;

    public synchronized int getA() {
        System.out.println(a);
        return a;
    }

    public void getB(String str){
        synchronized (str){
            System.out.println(str);
        }
    }

    public synchronized static void getC(String str){
        System.out.println(str);
    }
}
