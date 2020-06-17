package com.example.jdkstudy.jdk;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Implementation notes.
 *
 * This map usually acts as a binned (bucketed) hash table, but
 * when bins get too large, they are transformed into bins of
 * TreeNodes, each structured similarly to those in
 * java.util.TreeMap. Most methods try to use normal bins, but
 * relay to TreeNode methods when applicable (simply by checking
 * instanceof a node).  Bins of TreeNodes may be traversed and
 * used like any others, but additionally support faster lookup
 * when overpopulated. However, since the vast majority of bins in
 * normal use are not overpopulated, checking for existence of
 * tree bins may be delayed in the course of table methods.
 *
 * Tree bins (i.e., bins whose elements are all TreeNodes) are
 * ordered primarily by hashCode, but in the case of ties, if two
 * elements are of the same "class C implements Comparable<C>",
 * type then their compareTo method is used for ordering. (We
 * conservatively check generic types via reflection to validate
 * this -- see method comparableClassFor).  The added complexity
 * of tree bins is worthwhile in providing worst-case O(log n)
 * operations when keys either have distinct hashes or are
 * orderable, Thus, performance degrades gracefully under
 * accidental or malicious usages in which hashCode() methods
 * return values that are poorly distributed, as well as those in
 * which many keys share a hashCode, so long as they are also
 * Comparable. (If neither of these apply, we may waste about a
 * factor of two in time and space compared to taking no
 * precautions. But the only known cases stem from poor user
 * programming practices that are already so slow that this makes
 * little difference.)
 *
 * Because TreeNodes are about twice the size of regular nodes, we
 * use them only when bins contain enough nodes to warrant use
 * (see TREEIFY_THRESHOLD). And when they become too small (due to
 * removal or resizing) they are converted back to plain bins.  In
 * usages with well-distributed user hashCodes, tree bins are
 * rarely used.  Ideally, under random hashCodes, the frequency of
 * nodes in bins follows a Poisson distribution
 * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
 * parameter of about 0.5 on average for the default resizing
 * threshold of 0.75, although with a large variance because of
 * resizing granularity. Ignoring variance, the expected
 * occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
 * factorial(k)). The first values are:
 *
 * 0:    0.60653066
 * 1:    0.30326533
 * 2:    0.07581633
 * 3:    0.01263606
 * 4:    0.00157952
 * 5:    0.00015795
 * 6:    0.00001316
 * 7:    0.00000094
 * 8:    0.00000006
 * more: less than 1 in ten million
 *
 * The root of a tree bin is normally its first node.  However,
 * sometimes (currently only upon Iterator.remove), the root might
 * be elsewhere, but can be recovered following parent links
 * (method TreeNode.root()).
 *
 * All applicable internal methods accept a hash code as an
 * argument (as normally supplied from a public method), allowing
 * them to call each other without recomputing user hashCodes.
 * Most internal methods also accept a "tab" argument, that is
 * normally the current table, but may be a new or old one when
 * resizing or converting.
 *
 * When bin lists are treeified, split, or untreeified, we keep
 * them in the same relative access/traversal order (i.e., field
 * Node.next) to better preserve locality, and to slightly
 * simplify handling of splits and traversals that invoke
 * iterator.remove. When using comparators on insertion, to keep a
 * total ordering (or as close as is required here) across
 * rebalancings, we compare classes and identityHashCodes as
 * tie-breakers.
 *
 * The use and transitions among plain vs tree modes is
 * complicated by the existence of subclass LinkedHashMap. See
 * below for hook methods defined to be invoked upon insertion,
 * removal and access that allow LinkedHashMap internals to
 * otherwise remain independent of these mechanics. (This also
 * requires that a map instance be passed to some utility methods
 * that may create new nodes.)
 *
 * The concurrent-programming-like SSA-based coding style helps
 * avoid aliasing errors amid all of the twisty pointer operations.
 */




public class HashMapTest {
    /**
     * 参考  https://zhuanlan.zhihu.com/p/21673805
     */
    @Test
    public void HashMapTest() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String key = "key" + i;
            final Integer value = i;
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    hashMap.put(key + "-" + j, value + j);
                }
            }).start();
        }
    }

    @Test
    public void test1(){
        int h;
        String key = "中华人民共和国";
//        System.out.println((1<<30) + "   " + Integer.MAX_VALUE);
        h = key.hashCode();
        System.out.println(h);
        int b = h >>> 16;
        System.out.println(b);
        System.out.println(h ^ b);
        HashMap hashMap = new HashMap<>();
        hashMap.put("ab",1);
        System.out.println();
    }

    @Test
    public void test3() throws InterruptedException {
        for (int i = 0; i <400 ; i++) {
            new HashMapThread().start();
        }

        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void test4(){
        System.out.println(Integer.MAX_VALUE+1 - Integer.MAX_VALUE);
        HashMap hashMap1 = new HashMap<>(1);
        HashMap hashMap2 = new HashMap<>(3);
        HashMap hashMap3 = new HashMap<>(11);
        HashMap hashMap4 = new HashMap<>(12);
        HashMap hashMap5 = new HashMap<>(57);
        System.out.println();
    }

    /*
    默认初始容量 16 Hashmap初始为16是因为:：首先要为2的幂次。这是因为，hashmap计算key的hash值进行存储的时候采用的方法是 “用key的hash值和hashmap的长度减一（length-1）按位与（&），2的幂次减一的二进制是111……，任何数和1与就是他本身，这样存储进来的hash位置就取决于key的二进制值了，这样会让hash分布相对分散。提升性能
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int TREEIFY_THRESHOLD = 8;

    static final int UNTREEIFY_THRESHOLD = 6;

    static final int MIN_TREEIFY_CAPACITY = 64;


    (h >>> 16) 扰动函数， jdk 1.7 扰动四次， 但是 1.8 可能认为多了可能边际效用也不大 为了提高性能扰动一次。例：
    key 的 hashCode
    h = key.hashCode() : 1111 1111 1111 1111 1111 0000 1110 1010

    计算hash
    h                  : 1111 1111 1111 1111 1111 0000 1110 1010
    h >>> 16           : 0000 0000 0000 0000 1111 1111 1111 1111
    h ^ (h >>> 16)     : 1111 1111 1111 1111 0000 1111 0001 0101

    计算下标
    n-1                : 0000 0000 0000 0000 0000 0000 0000 1111
    hash               : 1111 1111 1111 1111 0000 1111 0001 0101
    (n - 1) & hash     : 0000 0000 0000 0000 0000 0000 0000 0101
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

     */
}

class HashMapThread extends Thread
{
    public static AtomicInteger ai = new AtomicInteger(0);
    public static Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    @Override
    public void run()
    {
        while (ai.get() < 100000)
        {
            map.put(ai.get(), ai.get());
            ai.incrementAndGet();
        }
        System.out.println(Thread.currentThread().getName() + "执行结束完");
    }
}