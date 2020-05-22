package com.example.jdkstudy.jdk;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
public class ArrayListTest {

    @Test
    public void test1() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(5);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(3))
                list.remove(i);

            System.out.println();
        }
        System.out.println();
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(null);
    }

    @Test
    public void test6260652(){
        List<Integer> list = Arrays.asList(1,2,3);
        Object[] objects = list.toArray();
        System.out.println(objects.getClass().getSimpleName());
        objects[1] = new Object();
    }

    @Test
    public void test2(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            list.add(i);
        }
    }

}
//  构造函数： 指定初始化大小
//    public ArrayList(int initialCapacity) {
//      如果参数大于0 直接创建指定大小的数据
//        if (initialCapacity > 0) {
//            this.elementData = new Object[initialCapacity];
//      等于0 创建一个空数组
//        } else if (initialCapacity == 0) {
//            this.elementData = EMPTY_ELEMENTDATA;
//      抛出非法参数异常
//        } else {
//            throw new IllegalArgumentException("Illegal Capacity: "+
//                    initialCapacity);
//        }
//    }
//
//    构造函数： 默认构造函数  直接创建空数组
//    public ArrayList() {
//        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
//    }
//
//   构造函数：  集合参数构造函数
//    public ArrayList(Collection<? extends E> c) {
//        将参数转化为数组
//        elementData = c.toArray();
//         如果给定的集合（c）数据有值
//        if ((size = elementData.length) != 0) {
//            // c.toArray might (incorrectly) not return Object[] (see 6260652)
//              如果集合元素类型不是 Object 类型，我们会转成 Object
//            if (elementData.getClass() != Object[].class)
//                elementData = Arrays.copyOf(elementData, size, Object[].class);
//        } else {
//            // replace with empty array.  给定集合（c）无值，则默认空数组
//            this.elementData = EMPTY_ELEMENTDATA;
//        }
//    }
// 注意   1：ArrayList 无参构造器初始化时，默认大小是空数组，并不是大家常说的 10，10 是在第一次add 的时候扩容的数组值。
//        2：指定初始数据初始化时，我们发现一个这样子的注释 see 6260652，这是 Java 的一个bug，意思是当给定集合内的元素不是 Object 类型时，我们会转化成 Object 的类型。一般情况下都不会触发此 bug，
//          只有在下列场景下才会触发：ArrayList 初始化之后（ArrayList 元素 非 Object 类型），再次调用 toArray 方法，得到 Object 数组，并且往 Object 数组赋值时.

//    public boolean add(E e) {
//          确保数组大小是否足够，不够执行扩容，size 为当前数组的大小
//        ensureCapacityInternal(size + 1);  // Increments modCount!!
//          直接赋值，线程不安全的
//        elementData[size++] = e;
//        return true;
//    }

//      扩容  并把现有数据拷贝到新的数组里面去
//    private void grow(int minCapacity) {
//       overflow-conscious code
//        int oldCapacity = elementData.length;
//        oldCapacity >> 1 是把 oldCapacity 除以 2 的意思
//        int newCapacity = oldCapacity + (oldCapacity >> 1);
//          如果扩容后的值 < 我们的期望值，扩容后的值就等于我们的期望值
//        if (newCapacity - minCapacity < 0)
//            newCapacity = minCapacity;
//      如果扩容后的值 > jvm 所能分配的数组的最大值，那么就用 Integer 的最大值
//        if (newCapacity - MAX_ARRAY_SIZE > 0)
//            newCapacity = hugeCapacity(minCapacity);
//        // minCapacity is usually close to size, so this is a win:
//          扩容 并复制数据
//        elementData = Arrays.copyOf(elementData, newCapacity);
//    }
//  注意： 1. 扩容的规则并不是翻倍，是原来容量大小 + 容量大小的一半，直白来说，扩容后的大小是原来容量的 1.5 倍；
//        2.ArrayList 中的数组的最大值是 Integer.MAX_VALUE，超过这个值，JVM 就不会给数组分配内存空间了。
//        3.新增时，并没有对值进行严格的校验，所以 ArrayList 是允许 null 值的。
//        4.源码在扩容的时候，有边界限制，最大不能超过Integer 的最大值，最小不能小于0

//  扩容是通过这行代码来实现的： Arrays.copyOf(elementData, newCapacity); ，这行代码描述的本质是数组之间的拷贝，扩容是会先新建一个符合我们预期容量的新数组，然后把老数组的数据拷贝过去，
//  我们通过 System.arraycopy 方法进行拷贝，此方法是 native 的方法，源码如下：
//
//      @param      src      被拷贝的数组
//      @param      srcPos   从数组那里开始
//      @param      dest     目标数组
//      @param      destPos  从目标数组那个索引位置开始拷贝
//      @param      length   拷贝的长度
//      此方法是没有返回值的，通过 dest 的引用进行传值
//public static native void arraycopy(Object src,  int  srcPos,
//                                    Object dest, int destPos,
//                                    int length);

//  根据下标删除
//    public E remove(int index) {
//        检查下标
//        rangeCheck(index);
//          修改次数
//        modCount++;
//        E oldValue = elementData(index);
//        int numMoved = size - index - 1;
//        if (numMoved > 0)
//            System.arraycopy(elementData, index+1, elementData, index,numMoved);
//        elementData[--size] = null; // clear to let GC do its work
//
//        return oldValue;
//    }
//
//   可以删除 null 值
//  public boolean remove(Object o) {
// 如果要删除的值是 null，找到第一个值是 null 的删除
//      if (o == null) {
//          for (int index = 0; index < size; index++)
//              if (elementData[index] == null) {
//                  fastRemove(index);
//                  return true;
//              }
//      } else {
// 如果要删除的值不为 null，找到第一个和要删除的值相等的删除
//          for (int index = 0; index < size; index++)
// 这里是根据 equals 来判断值相等的，相等后再根据索引位置进行删除
//              if (o.equals(elementData[index])) {
//                  fastRemove(index);
//                  return true;
//              }
//      }
//      return false;
//  }

// ArrayList 迭代器
//public E next() {
//    checkForComodification();
//    int i = cursor;
//    if (i >= size)
//        throw new NoSuchElementException();
//    Object[] elementData = ArrayList.this.elementData;
//    if (i >= elementData.length)
//        throw new ConcurrentModificationException();
//    游标+1  多线程同时调用此方法时会存在线程安全问题
//    cursor = i + 1;
//    return (E) elementData[lastRet = i];
//}
//  迭代器删除 其本质调用的是通过下标删除
//public void remove() {
//    if (lastRet < 0)
//        throw new IllegalStateException();
//    checkForComodification();
//
//    try {
//        ArrayList.this.remove(lastRet);
//        cursor = lastRet;
//  astRet = -1 的操作目的，是防止重复删除操作
//        lastRet = -1;
//  删除元素成功，数组当前 modCount 就会发生变化，这里会把 expectedModCount 重新赋值，下次迭代时两者的值就会一致了
//        expectedModCount = modCount;
//    } catch (IndexOutOfBoundsException ex) {
//        throw new ConcurrentModificationException();
//    }
//}
