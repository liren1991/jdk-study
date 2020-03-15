package com.example.jvm;

/**
 * 查看字节码指令
 *      javap -c
 *      javap -verbose
 *
 * 1. 使用 javap -verbose 命令分析一个字节码文件时，将会分析该字节码文件的魔数、版本号、常量池、类信息、类的构造方法、类变量与成员变量等信息。
 * 2. 魔数： 所有 .class 字节码文件的前4个字节都是魔数，魔数值为固定值： 0xCAFEBABE。
 * 3. 魔数之后的4个字节为版本信息，前两个字节表示 minor version（次版本号），后面两个字节码表示 major version（主版本号）。这里的版本号为00 00 34，换算成十进制，表示次版本号为0，
 *      主版本号为52。所以，该文件的版本号为：1.8.0。可以通过java -version 命令来验证这一点。
 * 4. 常量池（constant pool）： 紧接着主版本号之后的就是常量池入口。一个java类中定义的很多信息都是由常量池来维护和描述的，可以将常量池看作是 class 文件的资源仓库，比如说 java 类
 *      中定义的方法与变量信息，都是存储在常量池中。常量池中主要存储两类常量： 字面量与符号引用。字面量如文本字符串，java中申明为 final 的常量值等，而符号引用如类和接口的全局限定名，
 *      字段的名称和描述，方法的名称和描述等。
 * 5. 常量池的总体结构： java类所对应的常量池主要有常量池数量与常量池组（常量表）这两部分共同构成。常量池数量紧跟在主版本号后面，占据2个字节；常量池数组则紧跟在常量池数量之后，常量池
 *      数组与一般的数组不同的是，常量池数组中不同的元素类型、结构都是不同的，长度当然也就不同；但是每一种元素的第一个数据都是 u1 类型，该字节码是个标志位，占据1个字节。jvm在解析常量池
 *      时，会根据这个 u1 类型来获取元素的具体类型。值得注意的是，常量池数组中元素的个数 = 常量池数 - 1（其中0暂时不使用），目的是满足某些常量池索引值的数据在特定情况下需要表达 ’不引
 *      用任何一个常量池‘ 的含义； 根本原因在于，索引为0也是一个常量（保留常量），只不过他不位于常量表中，这个常量就对应null值；所以，常量池的索引从1而非从0开始。
 * 6. 在jvm规范中，每个变量/字段都有描述信息，描述信息主要的作用是描述字段的数据类型、方法的参数列表（包括数量、类型、顺序）与返回值。根据描述符规则，基本数据类型和代表无返回值的void类型
 *      一个都用大写字符来表示，对象类型则使用字符串L加对象的全限定名称来表示。为了压缩字节码文件的体积，对于基本数据类型，jvm都只用一个大写字母来表示，如下所示： B-byte 、 C-char 、
 *      D-double 、 F-float  、 I-int 、 J-long 、 S-short 、 Z-boolean 、 V-void 、 L-引用类型（对象类型，如： L/java/lang/String）。
 * 7. 对于数组类型来说，每一个维度使用一个前置的 [ 来表示，如 int[] 被记录为： [I , String[][] 被记录为： [[Ljava/lang/String;
 * 8. 用描述符描述方法时，按照先参数列表，后返回值的顺序来描述。参数列表按照参数的严格顺序放在一组 () 之内， 如方法： String getRealNameByIdAndNickName(int id,String name) 的描述为：
 *      (I,Ljava/lang/String;) Ljava/lang/String
 *
 *  Classfile /D:/work/jdk-test/target/classes/com/example/jdktest/jvm/ByteCodesTest1.class   文件地址
 *   Last modified 2020-2-25; size 535 bytes                最后一次修改时间
 *   MD5 checksum b24d48595d9529d2a5a2b7dd6829b341          MD5检验码
 *   Compiled from "ByteCodesTest1.java"                    编译来源
 * public class com.example.jdktest.jvm.ByteCodesTest1      类的全量限定名
 *   minor version: 0                                       小版本号
 *   major version: 52                                      大版本号
 *   flags: ACC_PUBLIC, ACC_SUPER
 * Constant pool:                                           常量池
 *    #1 = Methodref          #4.#21         // java/lang/Object."<init>":()V
 *    #2 = Fieldref           #3.#22         // com/example/jdktest/jvm/ByteCodesTest1.a:I
 *    #3 = Class              #23            // com/example/jdktest/jvm/ByteCodesTest1
 *    #4 = Class              #24            // java/lang/Object
 *    #5 = Utf8               a
 *    #6 = Utf8               I
 *    #7 = Utf8               <init>                        构造方法
 *    #8 = Utf8               ()V                           无参无返回值
 *    #9 = Utf8               Code
 *   #10 = Utf8               LineNumberTable               行号表
 *   #11 = Utf8               LocalVariableTable            局部变量表
 *   #12 = Utf8               this
 *   #13 = Utf8               Lcom/example/jdktest/jvm/ByteCodesTest1;
 *   #14 = Utf8               getA
 *   #15 = Utf8               ()I
 *   #16 = Utf8               setA
 *   #17 = Utf8               (I)V
 *   #18 = Utf8               MethodParameters
 *   #19 = Utf8               SourceFile                    源文件
 *   #20 = Utf8               ByteCodesTest1.java           源文件名称
 *   #21 = NameAndType        #7:#8          // "<init>":()V
 *   #22 = NameAndType        #5:#6          // a:I
 *   #23 = Utf8               com/example/jdktest/jvm/ByteCodesTest1
 *   #24 = Utf8               java/lang/Object
 * {                                                        方法信息
 *   public com.example.jdktest.jvm.ByteCodesTest1();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: return
 *       LineNumberTable:
 *         line 6: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0  this   Lcom/example/jdktest/jvm/ByteCodesTest1;
 *
 *   public int getA();
 *     descriptor: ()I
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: getfield      #2                  // Field a:I
 *          4: ireturn
 *       LineNumberTable:
 *         line 11: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0  this   Lcom/example/jdktest/jvm/ByteCodesTest1;
 *
 *   public void setA(int);
 *     descriptor: (I)V
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=2, locals=2, args_size=2
 *          0: aload_0
 *          1: iload_1
 *          2: putfield      #2                  // Field a:I
 *          5: return
 *       LineNumberTable:
 *         line 15: 0
 *         line 16: 5
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       6     0  this   Lcom/example/jdktest/jvm/ByteCodesTest1;
 *             0       6     1     a   I
 *     MethodParameters:
 *       Name                           Flags
 *       a
 * }
 * SourceFile: "ByteCodesTest1.java"
 */
public class ByteCodesTest1 {

    private int a ;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
