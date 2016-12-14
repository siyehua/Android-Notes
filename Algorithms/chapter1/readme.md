#基础
重新温习了一些快忘记了的数学知识,并学了近点的二分查找

##1.1基础编程模型
###二分查找
```java
    /**
     * 二叉查找
     *
     * @param target target
     * @param a      arrays
     * @param lo     start index
     * @param hi     end index
     * @param depth  depth,first is 1;
     * @return depth
     */
    public static int rank(int target, int[] a, int lo, int hi, int depth) {
        System.out.printf("start: %-6d end: " + hi + "\n", lo);
        depth++;
        if (lo > hi) {
            System.out.println("no found.");
            return depth;
        }
        int mid = lo + (hi - lo) / 2;
        if (target < a[mid]) return rank(target, a, lo, mid - 1, depth);
        else if (target > a[mid]) return rank(target, a, mid + 1, hi, depth);
        else {
            System.out.println("success: " + mid);
            return depth;
        }
    }
```

###欧几里得:求两个数的最大公约数
```java
    /**
     * 欧几里得:求两个数最大公约数<br><br>
     * <b>自然语言描述</b><br>
     * 计算两个非负整数a和b的最大公约数:若b等于0,则最大公约数是a.<br>
     * 否则,将a除以b得到余数r,a和b的最大公约数即是b和r的最大公约数.
     *
     * @param a a
     * @param b b
     * @return greatest common divisor
     */
    public static int maxP(int a, int b) {
        if (b == 0) return a;
        int r = a % b;
        return maxP(b, r);
    }
```

###数学知识
 * 对数log,如果a的x次方等于N（a>0，且a不等于1），那么数x叫做以a为底N的对数（logarithm），记作x=logaN。其中，a叫做对数的底数，N叫做真数。<br>ln是以e为底的对数
 * N!阶乘,即:N!=1x2x3...xN
 * 2e-6,即:2 * 0.000001


##1.2数据抽象
介绍了java语言的一些特性

回文变位

如果字符串s重的字符循环移动任意位置之后能够得到另一个字符串t，那么s就被成为s的回环变位（circular rotation）.例如,ACTGACG就是TGACGAC的一个回环变位,反之亦然,判定这个条件在基因组序列的研究中是很重要的.提示:答案只需要一行用到indexOf(),length()和字符串连接的代码

```java
public boolean check(String a, String b){
    b +=b;
    return b.contains(a);
}
```

##1.3背包,队列,栈
这小节是算法的基础,包括数据集合,数据存储方式,以及java的泛型,迭代器

###集合:
 * Bag:背包即一个无序的集合.例如将抽奖券放进抽奖箱子里,取出来是随机的.
 * Queue:队列的典型特性是先进先出First In First Out(FIFO).例如:排队
 * Stack:栈的特性是先进后出First In Last Out(FILO),或者叫后进先出Last In First Out(LIFO).例如:学生作业总是先批改最后上交的<br>或者网页返回,总是先销毁最近看的<br>或者安卓的页面,按返回键,总是先退出最新打开的页面.
###数据存储方式
 数据最重要的操作是:增删改查,而不同的数据存储方式,操作数据的快慢则不同

 * 顺序存储:典型案例-数组.<b>特点</b>:<br>修改与查找速度快,修改的原理依赖于查找<br>删除与新增速度慢,无论是删除还是插入,都必须重新调整数据大小
 * 链式存储:典型案例-老鹰抓小鸡.<b>特点</b>:<br>修改与查找速度慢,修改的原理依赖于查找<br>删除与新增速度快,可以用很小的代价就删除或插入一个新的数据<br>
<br>老鹰抓小鸡游戏中,每个小鸡都抓住前一个小鸡的衣服,直到母鸡.
<br>增:来一只新的小鸡,只要新小鸡抓住其中一个小鸡的衣服,而原来抓着的那个小鸡改为抓新小鸡的衣服,即实现了插入行为
<br>删:老鹰抓住了一只小鸡,小鸡马上放开抓上一只小鸡的手,同时抓这只悲催鸡衣服的小鸡,马上向前抓,即实现了删除行为
<br>查:每个小鸡都差不多,并没有什么特殊标记.假设老鹰想抓住第5只小鸡,必须先数第1,2,3..一直顺着下去,才能抓到第5只,假设小鸡队伍很长,这样的效率就特别低,即实现了查找行为
<br>改:改是建立在查找的基础上的,原理一样.
###迭代器
迭代器的主要作用是可以快速遍历集合,使用方法是froeach,比fori的效率更高.

一个集合可以迭代,主要是实现了Iterable接口的iterator方法,返回一个iterator对象,从而实现了可迭代的功能

<b>数组也可以使用迭代器</b>

###Bag
[顺序存储](/Algorithms/chapter1/MyBagWithArr.java)
[链式存储](/Algorithms/chapter1/MyBag.java)

###Queue
[顺序存储](/Algorithms/chapter1/MyQueueWithArr.java)
[链式存储](/Algorithms/chapter1/MyQueue.java)

###Stack
[顺序存储](/Algorithms/chapter1/MyStackWithArr.java)
[链式存储](/Algorithms/chapter1/MyStack.java)

###Other
[双向链表](/Algorithms/chapter1/DoubleNodeStack.java)

```java
//应用:求一个正整数的二进制
public static String getBinaryStr(int number) {
    MyStack<Integer> integers = new MyStack<>();
    while (number > 0) {
        integers.push(number % 2);
        number /= 2;
    }
    String str = "";
    for (Integer item : integers) {
        str += item;
    }
    return str;
}
```